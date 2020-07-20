package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "RoadwayBusinessRuleManager")
public class BusinessRule_Model implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String rule_name;
	public String category_name;
	public String date_creation;
	public String date_change;
	public String status;
	
	public List<Vehicle_Model> vehicleInstance = new ArrayList<Vehicle_Model>();
	public Map<String,Map<String, VehicleCosts_Model>> vehicleCosts = new HashMap<String, Map<String, VehicleCosts_Model>>();
	
	
	public BusinessRule_Model(String rule_name, String category_name, String date_creation, String date_change,
			String status, List<Vehicle_Model> vehicleInstance,
			Map<String, Map<String, VehicleCosts_Model>> vehicleCosts) {
		super();
		this.rule_name = rule_name;
		this.category_name = category_name;
		this.date_creation = date_creation;
		this.date_change = date_change;
		this.status = status;
		this.vehicleInstance = vehicleInstance;
		this.vehicleCosts = vehicleCosts;
	}


	public BusinessRule_Model() {
		super();
		// TODO Auto-generated constructor stub
	}
}
