package edu.du.sb1024.repository;

import edu.du.sb1024.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
