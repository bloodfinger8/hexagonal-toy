package kr.co._order.homework.application.payment.adapter.out;

import kr.co._order.homework.application.payment.domain.PaymentAllow;
import kr.co._order.homework.application.payment.usecase.port.out.SuccessPaymentAllowPort;
import org.springframework.stereotype.Component;

@Component
public class PaymentAllowPersistenceAdapter implements SuccessPaymentAllowPort {
    private final PaymentAllowRepository paymentAllowRepository;

    public PaymentAllowPersistenceAdapter(PaymentAllowRepository paymentAllowRepository) {
        this.paymentAllowRepository = paymentAllowRepository;
    }

    @Override
    public PaymentAllow successPayment(PaymentAllow paymentAllow) {
        return paymentAllowRepository.save(paymentAllow);
    }
}
