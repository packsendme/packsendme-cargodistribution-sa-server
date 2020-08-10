package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "CategoryRuleManager_SA")
public class CategoryRuleModel implements Serializable{

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

	// Another Dependencies JAR
	public List<VehicleRuleModel> vehicles = new ArrayList<VehicleRuleModel>(); 
	public List<LocationModel> locations = new ArrayList<LocationModel>();
	public Map<String,Map<String, CategoryCostsModel>> categoryCosts = new HashMap<String,Map<String, CategoryCostsModel>>(); 

	public CategoryRuleModel(String id, String name_category, Double weight_min, Double weight_max, Integer axis_max,
			String unity_measurement_weight_min, String unity_measurement_weight_max, List<VehicleRuleModel> vehicles,
			List<LocationModel> locations, Map<String, Map<String, CategoryCostsModel>> categoryCosts) {
		super();
		this.id = id;
		this.name_category = name_category;
		this.weight_min = weight_min;
		this.weight_max = weight_max;
		this.axis_max = axis_max;
		this.unity_measurement_weight_min = unity_measurement_weight_min;
		this.unity_measurement_weight_max = unity_measurement_weight_max;
		this.vehicles = vehicles;
		this.locations = locations;
		this.categoryCosts = categoryCosts;
	}

	public CategoryRuleModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
