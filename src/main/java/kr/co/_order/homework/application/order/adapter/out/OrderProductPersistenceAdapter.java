package kr.co._order.homework.application.order.adapter.out;

import kr.co._order.homework.application.order.domain.OrderProduct;
import kr.co._order.homework.application.order.usecase.port.out.GenerateOrderProductPort;
import org.springframework.stereotype.Component;

@Component
public class OrderProductPersistenceAdapter implements GenerateOrderProductPort {
    private final OrderProductRepository orderProductRepository;

    public OrderProductPersistenceAdapter(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProduct generateOrderProduct(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }
}
