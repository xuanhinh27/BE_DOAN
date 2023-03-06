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
	String email;
	String dob;
	String phone;
	String date;
	String time;
	String description;
	String status;

	Integer categoryId;
	String categoryName;

	Integer userId;
	String userName;

	public AppointmentWrapper(Integer id, String name,String description) {
		this.id=id;
		this.name=name;
		this.description=description;


	}
	
	public AppointmentWrapper(Integer id, String name,Integer userId,String userName,String date,String time,String status,String description) {
		this.id=id;
		this.name=name;
		this.userId=userId;
		this.userName=userName;
		this.date = date;
		this.time = time;
		this.status = status;
		this.description = description;
	}
	

}
