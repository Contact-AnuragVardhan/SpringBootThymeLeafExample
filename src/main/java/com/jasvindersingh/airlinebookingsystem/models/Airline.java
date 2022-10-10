package com.jasvindersingh.airlinebookingsystem.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name="airlines")
public class Airline {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length=100,nullable = false,unique=true)
	private String airlineName;
	@Column(length=100,nullable = false)
	private String placeFrom;
	@Column(length=100,nullable = false)
	private String placeTo;
	@Column(length=100,nullable = false)
	private String phone;

}
