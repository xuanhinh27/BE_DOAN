package com.inn.booking.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inn.booking.wrapper.AppointmentWrapper;

@RequestMapping(path = "/appointment")
public interface AppointmentRest {
	
	@PostMapping(path = "/add")
	public ResponseEntity<String> addNewAppointment(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/get")
	public ResponseEntity<List<AppointmentWrapper>> getAllAppointment();
	
	@PutMapping(path = "/update")
	public ResponseEntity<String> updateAppointment(@RequestBody Map<String, String> requestMap);
	
	@PostMapping(path = "/delete/{id}")
	public ResponseEntity<String> deleteAppointment(@PathVariable Integer id);
	
	@PostMapping(path = "/updateStatus")
	public ResponseEntity<String> updateStatus(@RequestBody Map<String, String> requestMap);
	
	@GetMapping(path = "/getByCategory/{id}")
	public ResponseEntity<List<AppointmentWrapper>> getByCategory(@PathVariable Integer id);
	
	@GetMapping(path = "/getById/{id}")
	public ResponseEntity<AppointmentWrapper> getAppointmentById(@PathVariable Integer id);
	

}
