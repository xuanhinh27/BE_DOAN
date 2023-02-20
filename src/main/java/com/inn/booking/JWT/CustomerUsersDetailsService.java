package com.inn.booking.JWT;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import com.inn.booking.dao.UserDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerUsersDetailsService implements UserDetailsService {
	@Autowired
	UserDao userDao;

	private com.inn.booking.POJO.User userDetail;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside loadUserByUsername {} ", username);
		userDetail = userDao.findByEmailId(username);
		if (!Objects.isNull(userDetail)) {
			return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
		} else {

			throw new UsernameNotFoundException("User not found.");
		}
	}

	public com.inn.booking.POJO.User getUserDetails() {
		return userDetail;
	}

}
