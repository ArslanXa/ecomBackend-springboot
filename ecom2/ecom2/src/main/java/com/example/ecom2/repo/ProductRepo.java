package com.example.ecom2.repo;

import com.example.ecom2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE " +
            "lower(p.name) LIKE lower(concat('%',:keyword,'%')) or " +
            "lower(p.description) LIKE lower(concat('%',:keyword,'%')) or " +
            "lower(p.brand) LIKE lower(concat('%',:keyword,'%')) or " +
            "lower(p.category) like lower(concat('%',:keyword,'%'))")
    List<Product> searchProducts(String keyword);
}
