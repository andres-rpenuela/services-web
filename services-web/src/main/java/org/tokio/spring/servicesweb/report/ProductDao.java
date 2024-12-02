package org.tokio.spring.servicesweb.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.tokio.spring.servicesweb.domain.Product;

import java.util.Set;

@Repository
public interface ProductDao extends JpaRepository<Product,Long> {

    @Query(value = "SELECT p FROM Product p ORDER BY p.id")
    Set<Product> findAllProducts();

    Set<Product> findByCategoryOrderById(String category);
}
