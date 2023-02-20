package com.inn.booking.restImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.inn.booking.rest.DashboardRest;
import com.inn.booking.service.DashboardService;

@RestController
public class DashboardRestImpl implements DashboardRest {
    @Autowired
    DashboardService dashboardService;
	@Override
	public ResponseEntity<Map<String, Object>> getCount() {
		
		return dashboardService.getCount();
	}

}
