package kr.co._order.homework.application.payment.usecase.port.out;

import kr.co._order.homework.application.payment.domain.PaymentAllow;

public interface SuccessPaymentAllowPort {
    PaymentAllow successPayment(PaymentAllow paymentAllow);
}
