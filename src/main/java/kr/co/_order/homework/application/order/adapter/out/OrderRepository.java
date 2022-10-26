package kr.co._order.homework.application.order.adapter.out;

import kr.co._order.homework.application.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders , Long> {
}
