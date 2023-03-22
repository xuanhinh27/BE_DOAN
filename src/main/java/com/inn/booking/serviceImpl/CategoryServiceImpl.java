package com.inn.booking.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.booking.JWT.JwtFilter;
import com.inn.booking.POJO.Category;
import com.inn.booking.constants.CafeConstants;
import com.inn.booking.dao.CategoryDao;
import com.inn.booking.service.CategoryService;
import com.inn.booking.utils.CafeUtils;
@Service
public class CategoryServiceImpl implements CategoryService {
    
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	JwtFilter jwtFilter;
	
	@Override
	public ResponseEntity<String> addNewCategory(Map<String, String> reqestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(reqestMap,false)) {
					categoryDao.save(getCategoryFromMap(reqestMap, false));
					return new ResponseEntity<String>("{\"message\":\""+"Thêm dịch vụ thành công"+"\" ,\"status\": "+ true+" }",HttpStatus.OK);
				}
			}else {
				return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZATION_ACCESS, HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CafeUtils.getResponseEntity(CafeConstants.SOMTHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private boolean validateCategoryMap(Map<String, String> reqestMap, boolean validateId) {
		if(reqestMap.containsKey("name")) {
			if(reqestMap.containsKey("id") && validateId) {
				return true;
			}else if(!validateId) {
				return true;
			}
		}
		return false;
	}
	
	private Category getCategoryFromMap(Map<String, String> requsetMap, Boolean isAdd) {
		Category category = new Category();
		if(isAdd) {
			category.setId(Integer.parseInt(requsetMap.get("id")));
		}
		category.setName(requsetMap.get("name"));
		category.setPrice(requsetMap.get("price"));
		category.setDescrip(requsetMap.get("descrip"));
		return category;
	}

	@Override
	public ResponseEntity<List<Category>> gatAllCategory(String filterValue) {
		try {
//			if(!Strings.isNullOrEmpty(filterValue) && filterValue.equals("true")) {
				return new ResponseEntity<List<Category>>(categoryDao.getAllCategory(), HttpStatus.OK);
//			}
//			return new ResponseEntity<List<Category>>(categoryDao.findAll(),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Category>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
		try {
			if(jwtFilter.isAdmin()) {
				if(validateCategoryMap(requestMap, true)) {
					Optional<Category> optional = categoryDao.findById(Integer.parseInt(requestMap.get("id")));
					if(!optional.isEmpty()) {
						categoryDao.save(getCategoryFromMap(requestMap, true));
						return new ResponseEntity<String>("{\"message\":\""+"Cập nhật dịch vụ thành công"+"\" ,\"status\": "+ true+" }",HttpStatus.OK);
					}else {
						return CafeUtils.getResponseEntity("Dịch vụ không tồn tại", HttpStatus.BAD_REQUEST);
					}
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
}
