package com.example.pricenotifierui.repository;

import com.example.pricenotifierui.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByTask_Id(Long taskId);

    Page<Product> findAllByTask_Id(Long taskId, Pageable pageable);
}
