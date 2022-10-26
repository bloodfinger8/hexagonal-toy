package kr.co._order.homework.application.payment.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Amount {
    private static final long DELIVERY_FEE = 2500;
    private static final long DELIVERY_EVENT_STANDARD = 50000;

    private long orderPrice;
    private long deliveryFee;
    private long totalOrderPrice;
    private long discount;

    public Amount() {}

    private Amount(long orderPrice, long deliveryFee, long totalOrderPrice, long discount) {
        this.orderPrice = orderPrice;
        this.deliveryFee = deliveryFee;
        this.totalOrderPrice = totalOrderPrice;
        this.discount = discount;
    }

    public static Amount from(long orderPrice) {
        long deliveryFee = deliveryEvent(orderPrice);
        return new Amount(orderPrice, deliveryFee , deliveryFee + orderPrice ,0);
    }

    public static long deliveryEvent(long total){
        if(total >= DELIVERY_EVENT_STANDARD) {
            return 0;
        }
        return DELIVERY_FEE;
    }

    public long getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public long getDeliveryFee() {
        return deliveryFee;
    }
}

