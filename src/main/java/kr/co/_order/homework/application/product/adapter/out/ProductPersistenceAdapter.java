package kr.co._order.homework.application.product.adapter.out;

import kr.co._order.homework.application.product.usecase.port.out.SearchAllProductPort;
import kr.co._order.homework.application.product.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ProductPersistenceAdapter implements SearchAllProductPort {

    private final ProductRepository productRepository;

    public ProductPersistenceAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> searchAllProducts() {
        return productRepository.findAll();
    }


    @Override
    public List<Product> searchAllOrderProducts(Set<String> ids) {
        return productRepository.findAllByProductIdIn(ids);
    }
}
