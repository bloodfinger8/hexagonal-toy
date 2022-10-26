package kr.co._order.homework.application.product.domain;

import kr.co._order.homework.application.product.domain.exception.SoldOutException;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_id", length = 90)
    private String productId;

    @Column(name="product_name", length = 90)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int quantity;

    public Product() {}

    private Product(String productId, String name, int price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    public static Product of(String productId, String name, int price, int quantity) {
        return new Product(productId,name,price,quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return this.id == product.id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void calculateQuantity(int orderQuantity) throws SoldOutException {
        if(this.quantity < orderQuantity){
            throw new SoldOutException();
        }
        this.quantity = this.quantity - orderQuantity;
    }
}
