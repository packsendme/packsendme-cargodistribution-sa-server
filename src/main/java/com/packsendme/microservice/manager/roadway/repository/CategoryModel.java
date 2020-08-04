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
@Document(collection = "CategoryManager_SA")
public class CategoryModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String name_category;
	public Double weight_min;
	public Double weight_max;
	public Integer axis_max;
	public String unity_measurement_weight_min;
	public String unity_measurement_weight_max;
	public List<VehicleModel> vehicles = new ArrayList<VehicleModel>(); 

	
	public CategoryModel(String name_category, List<VehicleModel> vehicles, Double weight_min,
			Double weight_max, Integer axis_max, String unity_measurement_weight_min,
			String unity_measurement_weight_max) {
		super();
		this.name_category = name_category;
		this.vehicles = vehicles;
		this.weight_min = weight_min;
		this.weight_max = weight_max;
		this.axis_max = axis_max;
		this.unity_measurement_weight_min = unity_measurement_weight_min;
		this.unity_measurement_weight_max = unity_measurement_weight_max;
	}


	public CategoryModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
}
