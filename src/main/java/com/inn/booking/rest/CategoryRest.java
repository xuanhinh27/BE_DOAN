package com.inn.booking.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.inn.booking.POJO.Category;

@RequestMapping(path = "/category")
public interface CategoryRest {
     @PostMapping(path = "/add")
     public ResponseEntity<String> addNewCategory(@RequestBody(required = true) Map<String, String> reqestMap);
     
     @GetMapping(path = "/get")
     public ResponseEntity<List<Category>> gatAllCategory(@RequestParam(required = false) String filterValue);
     
     @PutMapping(path = "/update")
     public ResponseEntity<String> updateCategory(@RequestBody(required = true) Map<String, String> requestMap);
}
