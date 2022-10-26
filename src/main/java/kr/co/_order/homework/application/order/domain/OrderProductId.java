package kr.co._order.homework.application.order.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderProductId {
    private String id;

    public OrderProductId() {}

    private OrderProductId(String id) {
        this.id = id;
    }

    public static OrderProductId from(String id) {
        return new OrderProductId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderProductId)) {
            return false;
        }
        OrderProductId orderProductId = (OrderProductId) o;
        return Objects.equals(id, orderProductId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String idString() {
        return id;
    }
}
