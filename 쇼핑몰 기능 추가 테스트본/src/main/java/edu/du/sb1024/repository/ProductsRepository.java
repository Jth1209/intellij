package edu.du.sb1024.repository;

import edu.du.sb1024.entity.Order;
import edu.du.sb1024.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
