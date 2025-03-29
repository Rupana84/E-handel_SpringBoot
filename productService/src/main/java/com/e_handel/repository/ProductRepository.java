package com.e_handel.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.e_handel.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface ProductRepository extends JpaRepository<Product, Long> {
        List<Product> findByCategory(String category);
    }

