package com.sai.spring.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sai.spring.shop.bean.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	User findByPhoneNumber(String phoneNumber);
	User findByName(String name);
	@Modifying
	@Query("update User u set u.email = ?1 where u.email = ?2")
	void updateEmailByEmail(String newEmail, String oldEmail);
}
