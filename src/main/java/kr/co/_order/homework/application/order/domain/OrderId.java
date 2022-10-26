package kr.co._order.homework.application.order.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderId {
    @Column(name = "id")
    private String id;

    public OrderId() {}

    private OrderId(String id) {
        this.id = id;
    }

    public static OrderId from(String id) {
        return new OrderId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderId)) {
            return false;
        }
        OrderId orderId = (OrderId) o;
        return Objects.equals(id, orderId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String idString() {
        return id;
    }
}
