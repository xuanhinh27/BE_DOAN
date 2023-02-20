package com.inn.booking.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.booking.JWT.JwtFilter;
import com.inn.booking.POJO.Category;
import com.inn.booking.POJO.Appointment;
import com.inn.booking.constants.CafeConstants;
import com.inn.booking.dao.AppointmentDao;
import com.inn.booking.service.AppointmentService;
import com.inn.booking.utils.CafeUtils;
import com.inn.booking.wrapper.AppointmentWrapper;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentDao productDao;
    @Autowired
    JwtFilter jwtFilter;
	@Override
	public ResponseEntity<String> addNewAppointment(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateAppointmentMap(requestMap,false)) {
					productDao.save(getAppointmentFromMap(requestMap,false));
					return CafeUtils.getResponseEntity("Appointment Added successfully", HttpStatus.OK);
				}
				return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private boolean validateAppointmentMap(Map<String, String> requestMap, boolean validateId) {
		if(requestMap.containsKey("name")) {
			if(requestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId) {
				return true;
			}
		}
		return false;
	}
	
	private Appointment getAppointmentFromMap(Map<String, String> requestMap, boolean isAdd) {
		Category category = new Category();
		category.setId(Integer.parseInt(requestMap.get("categoryId")));
		
		Appointment appointment = new Appointment();
		
		if(isAdd) {
			appointment.setId(Integer.parseInt(requestMap.get("id")));
		}else {
			appointment.setStatus("true");
		}
		appointment.setCategory(category);
		appointment.setName(requestMap.get("name"));
		appointment.setDescription(requestMap.get("description"));
		appointment.setPrice(Integer.parseInt(requestMap.get("price")));
		return appointment;
	}

	@Override
	public ResponseEntity<List<AppointmentWrapper>> getAllAppointment() {
		try {
			return new ResponseEntity<List<AppointmentWrapper>>(productDao.getAllAppointment(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<AppointmentWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateAppointment(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateAppointmentMap(requestMap, true)) {
					Optional<Appointment> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						Appointment appointment = getAppointmentFromMap(requestMap, true);
						appointment.setStatus(optional.get().getStatus());
						productDao.save(appointment);
						return CafeUtils.getResponseEntity("Appointment Updated successfully", HttpStatus.OK);
					}else {
						return CafeUtils.getResponseEntity("Appointment id does not exist.", HttpStatus.OK);
					}
				}else {
					return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> deleteAppointment(Integer id) {
		try {
			if(jwtFilter.isAdmin()) {
				Optional<Appointment> optional = productDao.findById(id);
				if(!optional.isEmpty()) {
					productDao.deleteById(id);
					return CafeUtils.getResponseEntity("Appointment Deleted Successfully",HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity("Appointment id does not exist.", HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				Optional<Appointment> optional = productDao.findById(Integer.parseInt(requestMap.get("id")));
				if(!optional.isEmpty()) {
					productDao.updateAppointmentStatus(requestMap.get("status"),Integer.parseInt(requestMap.get("id")));
					return CafeUtils.getResponseEntity("Appointment Status Updated Successfully", HttpStatus.OK);
				}else {
					return CafeUtils.getResponseEntity("Appointment id does not exist.", HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<List<AppointmentWrapper>> getByCategory(Integer id) {
		try {
			return new ResponseEntity<List<AppointmentWrapper>>(productDao.getAppointmentByCategory(id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<AppointmentWrapper>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<AppointmentWrapper> getAppointmentById(Integer id) {
		try {
			return new ResponseEntity<AppointmentWrapper>(productDao.getAppointmentById(id),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<AppointmentWrapper>(new AppointmentWrapper(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
