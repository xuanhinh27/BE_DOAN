package com.inn.booking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.booking.POJO.Bill;

public interface BillService {

	public ResponseEntity<String> generateReport(Map<String, Object> requestMap);

	public ResponseEntity<List<Bill>> getBills();

	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap);

	public ResponseEntity<String> deleteBill(Integer id);

}
