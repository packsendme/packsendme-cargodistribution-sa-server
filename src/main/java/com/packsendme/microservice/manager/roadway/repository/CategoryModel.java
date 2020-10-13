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
@Document(collection = "CategoryRuleManager_SA")
public class CategoryModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String name_category;
	public String transport;
	public Double weight_min;
	public Double weight_max;
	public String unity_measurement_weight_min;
	public String unity_measurement_weight_max;
	public List<VehicleRuleModel> vehicles = new ArrayList<VehicleRuleModel>(); 



	public CategoryModel(String id, String name_category, String transport, Double weight_min, Double weight_max,
			String unity_measurement_weight_min, String unity_measurement_weight_max, List<VehicleRuleModel> vehicles) {
		super();
		this.id = id;
		this.name_category = name_category;
		this.transport = transport;
		this.weight_min = weight_min;
		this.weight_max = weight_max;
		this.unity_measurement_weight_min = unity_measurement_weight_min;
		this.unity_measurement_weight_max = unity_measurement_weight_max;
		this.vehicles = vehicles;
	}



	public CategoryModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
