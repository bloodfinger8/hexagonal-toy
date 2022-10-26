package kr.co._order.homework.application.order.usecase.service;

import kr.co._order.homework.application.order.domain.OrderProduct;
import kr.co._order.homework.application.order.domain.OrderProductPrice;
import kr.co._order.homework.application.order.domain.Orders;
import kr.co._order.homework.application.order.domain.type.ProductInfo;
import kr.co._order.homework.application.order.usecase.port.in.GenerateOrderUseCase;
import kr.co._order.homework.application.order.usecase.port.out.GenerateOrderPort;
import kr.co._order.homework.application.order.usecase.port.out.GenerateOrderProductPort;
import kr.co._order.homework.application.order.usecase.service.collections.OrderProducts;
import kr.co._order.homework.application.order.usecase.service.response.GenerateOrderRes;
import kr.co._order.homework.application.payment.domain.Amount;
import kr.co._order.homework.application.payment.domain.PaymentAllow;
import kr.co._order.homework.application.payment.usecase.port.out.SuccessPaymentAllowPort;
import kr.co._order.homework.application.product.domain.exception.SoldOutException;
import kr.co._order.homework.application.product.usecase.port.out.SearchAllProductPort;
import kr.co._order.homework.application.util.KeyGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class GenerateOrderServiceImpl implements GenerateOrderUseCase {
    private final SearchAllProductPort searchAllProductPort;
    private final GenerateOrderPort generateOrderPort;
    private final GenerateOrderProductPort generateOrderProductPort;
    private final SuccessPaymentAllowPort successPaymentAllowPort;
    private final KeyGenerator keyGenerator = new KeyGenerator();

    public GenerateOrderServiceImpl(SearchAllProductPort searchAllProductPort, GenerateOrderPort generateOrderPort, GenerateOrderProductPort generateOrderProductPort, SuccessPaymentAllowPort successPaymentAllowPort) {
        this.searchAllProductPort = searchAllProductPort;
        this.generateOrderPort = generateOrderPort;
        this.generateOrderProductPort = generateOrderProductPort;
        this.successPaymentAllowPort = successPaymentAllowPort;
    }

    @Override
    @Transactional
    public GenerateOrderRes generateOrder(Map<String , Integer> command) throws SoldOutException, IllegalArgumentException {
        var orderProductCollection = OrderProducts.of(searchAllProductPort.searchAllOrderProducts(command.keySet()),command.keySet());
        orderProductCollection.calculateOrderQuantity(command);

        var order = generateOrderPort.generateOrder(Orders.createOrder(keyGenerator.makeStringKey()));
        int orderPrice = sumAmount(command, orderProductCollection, order);

        var paymentAllow = successPaymentAllowPort.successPayment(PaymentAllow.payOrder(order.getOrderId(), Amount.from(orderPrice)));
        return GenerateOrderRes.of(orderProductCollection.convertOrderHistory(command), orderPrice, paymentAllow);
    }


    private int sumAmount(Map<String, Integer> command, OrderProducts orderProducts, Orders order) {
        int orderPrice = 0;
        for(Map.Entry<String, Integer> entry : command.entrySet()) {
            OrderProductPrice orderProductPrice = OrderProductPrice.calculateOrderedPrice(orderProducts.getPrice(entry.getKey()), entry.getValue());
            generateOrderProductPort.generateOrderProduct(OrderProduct.createOrderProduct(keyGenerator.makeStringKey(), order, ProductInfo.of(entry.getKey(), orderProducts.getName(entry.getKey())), orderProductPrice, entry.getValue()));
            orderPrice +=  orderProductPrice.getOrderedTotalPrice();
        }
        return orderPrice;
    }
}
