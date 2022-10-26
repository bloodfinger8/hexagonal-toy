package kr.co._order.homework.application.order.adapter.out;

import kr.co._order.homework.application.order.domain.Orders;
import kr.co._order.homework.application.order.usecase.port.out.GenerateOrderPort;
import kr.co._order.homework.application.order.usecase.port.out.SearchOrderPort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPersistenceAdapter implements GenerateOrderPort, SearchOrderPort {
    private final OrderRepository orderRepository;

    public OrderPersistenceAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Orders generateOrder(Orders order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Orders> searchOrder() {
        return orderRepository.findAll();
    }
}
