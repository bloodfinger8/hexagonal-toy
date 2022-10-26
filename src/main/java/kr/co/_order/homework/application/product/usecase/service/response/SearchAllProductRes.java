package kr.co._order.homework.application.product.usecase.service.response;

import kr.co._order.homework.application.product.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class SearchAllProductRes {
    private List<ProductData> productData;

    private SearchAllProductRes(List<ProductData> productData) {
        this.productData = productData;
    }

    public static SearchAllProductRes from(List<Product> products) {
        return new SearchAllProductRes(products.stream().map(ProductData::from).collect(Collectors.toList()));
    }

    public List<ProductData> getProductData() {
        return productData;
    }

    public static class ProductData {
        private String productId;
        private String name;
        private int price;
        private int quantity;

        private ProductData(String productId, String name, int price, int quantity) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }
        public static ProductData from(Product product) {
            return new ProductData(product.getProductId(),product.getName(),product.getPrice() , product.getQuantity());
        }

        @Override
        public String toString() {
            return  productId  +"    "+  name +"    "+ price +"    '"+ quantity + '\'' ;
        }
    }
}
