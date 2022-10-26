package kr.co._order.homework.application.order.usecase.port.out;

import kr.co._order.homework.application.order.domain.OrderProduct;

public interface GenerateOrderProductPort {
    OrderProduct generateOrderProduct(OrderProduct orderProduct);
}
