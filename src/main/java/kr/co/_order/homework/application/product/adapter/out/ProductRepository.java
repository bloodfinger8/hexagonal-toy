package kr.co._order.homework.application.product.adapter.out;

import kr.co._order.homework.application.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Set;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Product> findAllByProductIdIn(Set<String> ids);
}
