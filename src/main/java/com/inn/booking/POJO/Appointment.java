package com.inn.booking.POJO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;


@NamedQuery(name = "Appointment.getAllAppointment",
query="select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name,p.description,p.status,p.price,p.category.id,p.category.name) from Appointment p")

@NamedQuery(name="Appointment.updateAppointmentStatus",
query = "update Appointment p set p.status=:status where p.id=:id")

@NamedQuery(name="Appointment.getAppointmentByCategory",
query = "select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name) from Appointment p where p.category.id=:id and p.status='true'")

@NamedQuery(name = "Appointment.getAppointmentById",
query = "select new com.inn.booking.wrapper.AppointmentWrapper(p.id,p.name,p.description,p.price) from Appointment p where p.category.id=:id and p.status='true'")

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_fk",nullable = false)
	private Category category;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "status")
	private String status;

}
