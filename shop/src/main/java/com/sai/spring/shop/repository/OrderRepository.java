package com.sai.spring.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sai.spring.shop.bean.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
