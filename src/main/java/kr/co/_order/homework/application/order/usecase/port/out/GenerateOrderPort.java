package kr.co._order.homework.application.order.usecase.port.out;

import kr.co._order.homework.application.order.domain.Orders;

public interface GenerateOrderPort {
    Orders generateOrder(Orders order);
}
