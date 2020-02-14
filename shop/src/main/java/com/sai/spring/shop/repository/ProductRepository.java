package com.sai.spring.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.spring.shop.bean.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByIdIn(List<Long> ids);
}
