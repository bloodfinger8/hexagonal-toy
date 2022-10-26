package kr.co._order.homework.application.order.domain;

import javax.persistence.*;

@Embeddable
@Access(AccessType.FIELD)
public class OrderProductPrice {
    @Column(nullable = false)
    private Long orderedTotalPrice;

    @Column(nullable = false)
    private Long orderedProductPrice;

    @Column(nullable = false)
    private Long orderedOriginalProductPrice;

    public OrderProductPrice() {}

    private OrderProductPrice(Long totalPrice, Long orderedProductPrice, Long originalProductPrice) {
        this.orderedTotalPrice = totalPrice;
        this.orderedProductPrice = orderedProductPrice;
        this.orderedOriginalProductPrice = originalProductPrice;
    }

    public static OrderProductPrice calculateOrderedPrice( long  price, long quantity) {
        return new OrderProductPrice(price * quantity ,price , price);
    }

    public Long getOrderedTotalPrice() {
        return orderedTotalPrice;
    }
}
