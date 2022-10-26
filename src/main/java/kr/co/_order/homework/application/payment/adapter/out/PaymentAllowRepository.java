package kr.co._order.homework.application.payment.adapter.out;

import kr.co._order.homework.application.payment.domain.PaymentAllow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentAllowRepository extends JpaRepository<PaymentAllow , Long> {
}
