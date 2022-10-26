package kr.co._order.homework.application.order.domain;

import kr.co._order.homework.application.order.domain.type.OrderStatus;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;

@Entity
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="order_id",length = 80, nullable = false, unique = true)
    private String orderId;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 40, nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order")
    @OrderBy(" createdAt DESC ")
    @BatchSize(size = 100)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @CreationTimestamp
    private ZonedDateTime transactedAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Orders)) {
            return false;
        }
        Orders order = (Orders) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Orders() {}
    private Orders(String orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public static Orders createOrder(String orderId) {
        return new Orders(orderId,OrderStatus.PAYMENT);
    }

    public String getOrderId() {
        return orderId;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    //결제 성공
    public void successPayment() {
        statusChange(s -> this.orderStatus.canNext(s), OrderStatus.PAYMENT);
        this.transactedAt = ZonedDateTime.now(ZoneOffset.UTC);
    }

    private void statusChange(Predicate<OrderStatus> predicate, OrderStatus newStatus) {
        if (!predicate.test(newStatus)) return;
        orderStatus = newStatus;
    }
}
