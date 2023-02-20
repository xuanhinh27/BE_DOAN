package com.inn.booking.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.inn.booking.dao.BillDao;
import com.inn.booking.dao.CategoryDao;
import com.inn.booking.dao.AppointmentDao;
import com.inn.booking.service.DashboardService;
@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    AppointmentDao productDao;
    @Autowired
    BillDao billDao;
	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("category", categoryDao.count());
		map.put("appointment", productDao.count());
		map.put("bill", billDao.count());
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

}
