package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "VehicleManager")
public class Vehicle_Model implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;	
	public String vehicle;
	public List<String> bodywork_vehicle = new ArrayList<String>();
	public Double cargo_max;
	public Integer axis_total;
	public String unity_measurement_weight;
	public Integer people;
	
	public Vehicle_Model(String vehicle, List<String> bodywork_vehicle, Double cargo_max, Integer axis_total,
			String unity_measurement_weight, Integer people) {
		super();
		this.vehicle = vehicle;
		this.bodywork_vehicle = bodywork_vehicle;
		this.cargo_max = cargo_max;
		this.axis_total = axis_total;
		this.unity_measurement_weight = unity_measurement_weight;
		this.people = people;
	}

	public Vehicle_Model() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
		
}
