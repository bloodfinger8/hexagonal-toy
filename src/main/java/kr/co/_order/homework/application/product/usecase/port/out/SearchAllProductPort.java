package kr.co._order.homework.application.product.usecase.port.out;

import kr.co._order.homework.application.product.domain.Product;

import java.util.List;
import java.util.Set;

public interface SearchAllProductPort {
    List<Product> searchAllProducts();

    List<Product> searchAllOrderProducts(Set<String> ids);
}
