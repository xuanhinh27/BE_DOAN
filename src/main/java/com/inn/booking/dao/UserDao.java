package com.inn.booking.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.inn.booking.POJO.User;
import com.inn.booking.wrapper.UserWrapper;

public interface UserDao extends JpaRepository<User, Integer> {
	
	User findByEmailId(@Param("email") String email);
	
	List<UserWrapper> getAllUser();
	
	@Transactional
	@Modifying
	void updateStatus(@Param("status") String status, @Param("id") Integer id);
	
	List<String> getAllAdmin();
	
	User findByEmail(String email);

}
