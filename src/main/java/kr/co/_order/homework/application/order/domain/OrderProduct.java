package kr.co._order.homework.application.order.domain;

import kr.co._order.homework.application.order.domain.type.ProductInfo;
import kr.co._order.homework.application.order.domain.type.OrderProductStatus;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.function.Predicate;

@Entity
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_product_id", updatable = false ,length = 40))
    private OrderProductId orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id" , referencedColumnName = "order_id", nullable = false)
    private Orders order;

    @Embedded
    private ProductInfo productInfo;

    @Embedded
    private OrderProductPrice orderProductPrice;

    private int quantity;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 40, nullable = false)
    private OrderProductStatus orderProductStatus;

    private ZonedDateTime autoConfirmReserveDatetime;

    @CreationTimestamp
    private ZonedDateTime createdAt;
    private ZonedDateTime confirmedAt;
    @UpdateTimestamp
    private ZonedDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderProduct)) {
            return false;
        }
        OrderProduct orderProduct = (OrderProduct) o;
        return Objects.equals(id, orderProduct.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public OrderProduct() {}
    private OrderProduct(OrderProductId orderProductId, Orders order, ProductInfo productInfo, OrderProductPrice orderProductPrice, int quantity, OrderProductStatus orderProductStatus) {
        this.orderProductId = orderProductId;
        this.order = order;
        this.productInfo = productInfo;
        this.orderProductPrice = orderProductPrice;
        this.quantity = quantity;
        this.orderProductStatus = orderProductStatus;
    }

    public static OrderProduct createOrderProduct(String orderProductId, Orders order, ProductInfo productInfo, OrderProductPrice price, int quantity) {
        return new OrderProduct(OrderProductId.from(orderProductId),order,productInfo,price,quantity,OrderProductStatus.PAYMENT);
    }

    public String getOrderProductId() {
        return orderProductId.idString();
    }
    public ProductInfo getProductInfo() {
        return productInfo;
    }
    public int getQuantity() {
        return quantity;
    }

    //결제완료
    public void successPayment() {
        statusChange(s -> this.orderProductStatus.canNext(s), OrderProductStatus.PAYMENT);
        this.updatedAt = ZonedDateTime.now(ZoneOffset.UTC);
    }

    private void statusChange(Predicate<OrderProductStatus> predicate, OrderProductStatus newStatus) {
        if (!predicate.test(newStatus)) return;
        this.orderProductStatus = newStatus;
    }
}
