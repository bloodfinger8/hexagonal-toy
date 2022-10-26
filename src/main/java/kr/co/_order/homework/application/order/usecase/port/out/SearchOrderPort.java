package kr.co._order.homework.application.order.usecase.port.out;

import kr.co._order.homework.application.order.domain.Orders;

import java.util.List;

public interface SearchOrderPort {
    List<Orders> searchOrder();
}
