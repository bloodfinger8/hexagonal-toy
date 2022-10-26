package kr.co._order.homework.application.order.usecase.service.collections;

import kr.co._order.homework.application.product.domain.Product;
import kr.co._order.homework.application.product.domain.exception.SoldOutException;

import java.util.*;
import java.util.stream.Collectors;

public class OrderProducts {
    private Map<String , Product> orderProductMap;
    private List<Product> products;

    private OrderProducts(Map<String , Product> orderProductMap, List<Product> products) {
        this.orderProductMap = orderProductMap;
        this.products = products;
    }

    public static OrderProducts of(List<Product> products, Set<String> ids) throws IllegalArgumentException{
        validateSize(products,ids);
        return new OrderProducts(products.stream().collect(Collectors.toMap(Product::getProductId, product -> product)),products);
    }

    private static void validateSize(List<Product> products , Set<String> ids) throws IllegalArgumentException{
        if(products.size() == 0 || !products.stream().map(Product::getProductId).collect(Collectors.toList()).containsAll(ids)){
            throw new IllegalArgumentException("상품 번호를 잘못 입력하셨습니다.");
        }
    }

    public Product get(String key) {
        return orderProductMap.get(key);
    }

    public int getPrice(String key) {
        return orderProductMap.get(key).getPrice();
    }

    public String getName(String key) {
        return orderProductMap.get(key).getName();
    }

    public void calculateOrderQuantity(Map<String , Integer> map) throws SoldOutException {
        products.forEach(product -> product.calculateQuantity(map.get(product.getProductId())));
    }

    public List<String> convertOrderHistory(Map<String , Integer> command) {
        return products.stream().map(product -> product.getName() + " - " + command.get(product.getProductId()) + "개")
                .collect(Collectors.toList());
    }
}
