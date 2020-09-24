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
	public CategoryTypeModel categoryType;

	// Another Dependencies JAR
	public List<VehicleRuleModel> vehicles = new ArrayList<VehicleRuleModel>(); 
	public List<LocationModel> locations = new ArrayList<LocationModel>();
	public Map<String, List<CategoryCostsModel>> categoryCosts = new HashMap<String, List<CategoryCostsModel>>(); 

	public CategoryRuleModel(CategoryTypeModel categoryType, List<VehicleRuleModel> vehicles,
			List<LocationModel> locations, Map<String, List<CategoryCostsModel>> categoryCosts) {
		super();
		this.categoryType = categoryType;
		this.vehicles = vehicles;
		this.locations = locations;
		this.categoryCosts = categoryCosts;
	}





	public CategoryRuleModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
