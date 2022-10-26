package kr.co._order.homework.application.payment.domain;

import kr.co._order.homework.application.order.domain.OrderId;
import kr.co._order.homework.application.payment.domain.type.PaymentType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
public class PaymentAllow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "orderId", unique = true, length = 40))
    private OrderId orderId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, nullable = false)
    private PaymentType paymentMethodType;

    @Embedded
    private Amount amount;

    @CreationTimestamp
    private ZonedDateTime createdAt;
    private ZonedDateTime approvedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentAllow)) {
            return false;
        }
        PaymentAllow paymentAllow = (PaymentAllow) o;
        return Objects.equals(id, paymentAllow.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public PaymentAllow() {}
    private PaymentAllow(OrderId orderId, PaymentType paymentMethodType, Amount amount) {
        this.orderId = orderId;
        this.paymentMethodType = paymentMethodType;
        this.amount = amount;
    }

    public static PaymentAllow payOrder(String orderId, Amount amount ) {
        return new PaymentAllow(OrderId.from(orderId) , PaymentType.MONEY, amount);
    }

    public long getTotalOrderPrice() {
        return amount.getTotalOrderPrice();
    }

    public long getDeliveryFee() {
        return amount.getDeliveryFee();
    }
}
