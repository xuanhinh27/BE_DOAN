package com.inn.booking.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.booking.JWT.CustomerUsersDetailsService;
import com.inn.booking.JWT.JwtFilter;
import com.inn.booking.JWT.JwtUtil;
import com.inn.booking.POJO.User;
import com.inn.booking.constants.CafeConstants;
import com.inn.booking.dao.UserDao;
import com.inn.booking.service.UserService;
import com.inn.booking.utils.CafeUtils;
import com.inn.booking.utils.EmailUtils;
import com.inn.booking.wrapper.UserWrapper;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	EmailUtils emailUtils;
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	CustomerUsersDetailsService customerUsersDetailsService;
	@Override
	public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signup {}", requestMap);
        try {
        if(validationSignUp(requestMap)) {
        	User user = userDao.findByEmailId(requestMap.get("email"));
        	if(Objects.isNull(user)) {
        		userDao.save(getUserFromMap(requestMap));
        		return CafeUtils.getResponseEntity("Successfully Registred.", HttpStatus.OK);
        	}else {
        		return CafeUtils.getResponseEntity("Email already exists.", HttpStatus.BAD_REQUEST);
        	}
        }else {
        	return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
        }
        }catch (Exception e) {
			e.printStackTrace();
		}
        return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean validationSignUp(Map<String, String> requestMap) {
		if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
		requestMap.containsKey("email") && requestMap.containsKey("password")) {
			return true;
		}else {
			return false;
		}
	}
	private User getUserFromMap(Map<String, String> requestMap) {
		User user = new User();
		user.setName(requestMap.get("name"));
		user.setEmail(requestMap.get("email"));
		user.setContactNumber(requestMap.get("contactNumber"));
		user.setPassword(requestMap.get("password"));
		user.setStatus("false");
		user.setRole("user");
		return user;
		
	}

	@Override
	public ResponseEntity<String> login(Map<String, String> requestMap) {
		log.info("Inside login");
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
			if(authentication.isAuthenticated()) {
				if(customerUsersDetailsService.getUserDetails().getStatus().equalsIgnoreCase("true")) {
					return new ResponseEntity<String>("{\"token\":\""+jwtUtil.generateToken(customerUsersDetailsService.getUserDetails().getEmail(), customerUsersDetailsService.getUserDetails().getRole())+"\",\"role\":\""+ customerUsersDetailsService.getUserDetails().getRole()+"\"}",HttpStatus.OK);
				}else {
					return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval"+"\"}",HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			log.info("{}",e);
		}
		return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials"+"\"}",HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<List<UserWrapper>> getAllUser() {
		try {
//			if(jwtFilter.isAdmin()) {
				return new ResponseEntity<List<UserWrapper>>(userDao.getAllUser(),HttpStatus.OK);
//			}else {
//				return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> update(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
			  Optional<User> optional=userDao.findById(Integer.parseInt(requestMap.get("id")));
			  if(!optional.isEmpty()) {
				  userDao.updateStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
				  sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(),userDao.getAllAdmin());
				  return CafeUtils.getResponseEntity("User updated successfully", HttpStatus.OK);
			  }else {
				return  CafeUtils.getResponseEntity("User id does not exist", HttpStatus.OK);
			  }
				
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
		allAdmin.remove(jwtFilter.getCurrentUser());
		if(status != null && status.equalsIgnoreCase("true") ) {
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Approved", "USER:-"+user+"\n is approuved by\nADMIN:-"+jwtFilter.getCurrentUser()	, allAdmin);
		}else {
			emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account Disabled", "USER:-"+user+"\n is disabled by\nADMIN:-"+jwtFilter.getCurrentUser()	, allAdmin);
		}
		
	}

	@Override
	public ResponseEntity<String> checkToken() {
		return CafeUtils.getResponseEntity("true", HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
		try {
			User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
			if(!userObj.equals(null)) {
				if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {
					userObj.setPassword(requestMap.get("newPassword"));
					userDao.save(userObj);
					return CafeUtils.getResponseEntity("Password updated Successfully.", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
			}
			return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@Override
	public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
		try {
			User user = userDao.findByEmail(requestMap.get("email"));
			if(!Objects.isNull(user) && !Strings.isNullOrEmpty(user.getEmail())) {
				emailUtils.forgotMail(user.getEmail(), "Credentials by Cafe Management System", user.getPassword());
			return CafeUtils.getResponseEntity("Check your mail for Credentials.", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
