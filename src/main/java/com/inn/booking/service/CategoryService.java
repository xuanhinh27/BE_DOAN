package com.inn.booking.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.inn.booking.POJO.Category;

public interface CategoryService {

     public	ResponseEntity<String> addNewCategory(Map<String, String> reqestMap);

	public ResponseEntity<List<Category>> gatAllCategory(String filterValue);

	public ResponseEntity<String> updateCategory(Map<String, String> requestMap);

}
