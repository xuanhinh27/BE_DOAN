package com.inn.booking.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import com.inn.booking.POJO.Appointment;
import com.inn.booking.wrapper.AppointmentWrapper;

public interface AppointmentDao extends JpaRepository<Appointment, Integer> {

	List<AppointmentWrapper> getAllAppointment();
    
	@Modifying
	@Transactional
	void updateAppointmentStatus(@Param("status") String status,@Param("id") Integer id);

	List<AppointmentWrapper> getAppointmentByCategory(Integer id);

	AppointmentWrapper getAppointmentById(@Param("id") Integer id);

}
