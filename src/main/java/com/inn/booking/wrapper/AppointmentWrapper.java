package com.inn.booking.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentWrapper {
	Integer id;
	String name;
//	String email;
//	String dob;
//	String phone;
//	String date;
//	String time;
	String description;
	String status;
	Integer price;
	Integer categoryId;
	String categoryName;
	
	public AppointmentWrapper(Integer id, String name,String description,Integer price) {
		this.id=id;
		this.name=name;
		this.description=description;
		this.price=price;
	}
	
	public AppointmentWrapper(Integer id, String name) {
		this.id=id;
		this.name=name;
	}
	

}
