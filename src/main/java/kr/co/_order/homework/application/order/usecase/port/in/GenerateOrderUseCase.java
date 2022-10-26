package kr.co._order.homework.application.order.usecase.port.in;

import kr.co._order.homework.application.order.usecase.service.response.GenerateOrderRes;

import java.util.Map;

public interface GenerateOrderUseCase {
    GenerateOrderRes generateOrder(Map<String , Integer> command);
}
