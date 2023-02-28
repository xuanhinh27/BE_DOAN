package com.inn.booking.POJO;

import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@NamedQuery(name = "Appointment.getAllAppointment",
		query="select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name,p.email,p.dob,p.phone,p.date,p.description,p.time,p.status,p.category.id,p.category.name,p.user.id,p.user.name) from Appointment p")

@NamedQuery(name="Appointment.updateAppointmentStatus",
query = "update Appointment p set p.status=:status where p.id=:id")

@NamedQuery(name="Appointment.getAppointmentByCategory",
query = "select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name,p.user.id,p.user.name,p.date,p.time,p.status) from Appointment p where p.user.id=:id and p.status='true'")

@NamedQuery(name = "Appointment.getAppointmentById",
query = "select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name,p.description) from Appointment p where p.category.id=:id and p.status='true'")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "appointment")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "dob")
	private String dob;
	@Column(name = "phone")
	private String phone;
	@Column(name = "date")
	private String date;
	@Column(name = "time")
	private String time;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk")
	private Category category;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_fk")
	private User user;

	@Column(name = "description")
	private String description;
	
	@Column(name = "status")
	private String status;

}
