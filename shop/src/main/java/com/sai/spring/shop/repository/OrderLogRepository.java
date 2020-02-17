package com.sai.spring.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sai.spring.shop.bean.OrderLog;

public interface OrderLogRepository extends JpaRepository<OrderLog, Long>{

}
