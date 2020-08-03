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
@Document(collection = "RoadwayBusinessRuleManager")
public class RoadwayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public long _id;
	public String rule_name;
	public String category_name;
	public String date_creation;
	public String date_change;
	public String status;
	
	public CategoryModel categoryInstance = new CategoryModel();
	public Map<String,Map<String, CostsModel>> costsInstance = new HashMap<String, Map<String, CostsModel>>();
	
	
	public RoadwayModel(String rule_name, String category_name, String date_creation, String date_change,
			String status, CategoryModel categoryInstance,
			Map<String, Map<String, CostsModel>> costsInstance) {
		super();
		this.rule_name = rule_name;
		this.category_name = category_name;
		this.date_creation = date_creation;
		this.date_change = date_change;
		this.status = status;
		this.categoryInstance = categoryInstance;
		this.costsInstance = costsInstance;
	}


	public RoadwayModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
