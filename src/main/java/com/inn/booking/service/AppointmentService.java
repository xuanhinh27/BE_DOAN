package com.inn.booking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.booking.wrapper.AppointmentWrapper;

public interface AppointmentService {

	public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap);

	public ResponseEntity<List<AppointmentWrapper>> getAllAppointment();

	public ResponseEntity<String> updateAppointment(Map<String, String> requestMap);

	public ResponseEntity<String> deleteAppointment(Integer id);

	public ResponseEntity<String> updateStatus(Map<String, String> requestMap);

	public ResponseEntity<List<AppointmentWrapper>> getByCategory(Integer id);

	public ResponseEntity<AppointmentWrapper> getAppointmentById(Integer id);

}
