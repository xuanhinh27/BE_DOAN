package com.inn.booking.restImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.booking.constants.CafeConstants;
import com.inn.booking.rest.AppointmentRest;
import com.inn.booking.service.AppointmentService;
import com.inn.booking.utils.CafeUtils;
import com.inn.booking.wrapper.AppointmentWrapper;

@RestController
public class AppointmentRestImpl implements AppointmentRest {
    @Autowired
    AppointmentService productService;
	@Override
	public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap) {
		try {
			return productService.addNewAppointment(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<List<AppointmentWrapper>> getAllAppointment() {
		try {
			return productService.getAllAppointment();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<AppointmentWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<String> updateAppointment(Map<String, String> requestMap) {
		try {
			return productService.updateAppointment(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<String> deleteAppointment(Integer id) {
		try {
			return productService.deleteAppointment(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			return productService.updateStatus(requestMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<List<AppointmentWrapper>> getByCategory(Integer id) {
		try {
			return productService.getByCategory(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<AppointmentWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@Override
	public ResponseEntity<AppointmentWrapper> getAppointmentById(Integer id) {
		try {
			return productService.getAppointmentById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<AppointmentWrapper>(new AppointmentWrapper(),HttpStatus.OK);
	}

}
