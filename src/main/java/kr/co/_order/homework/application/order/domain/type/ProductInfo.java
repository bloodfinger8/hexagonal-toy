package kr.co._order.homework.application.order.domain.type;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class ProductInfo {
    @Column(length = 80, nullable = false)
    private String productId;

    @Column(length = 90)
    private String productName;

    @Column(length = 1024)
    private String productThumbnailUrl;

    public ProductInfo() {}

    private ProductInfo(String productId, String productName, String productThumbnailUrl) {
        this.productId = productId;
        this.productName = productName;
        this.productThumbnailUrl = productThumbnailUrl;
    }

    public static ProductInfo of(String orderProductId, String name ) {
        return new ProductInfo(orderProductId,name,"");
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }
}
