package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "RoadwayBRE_SA")
public class RoadwayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String rule_name;
	public String category_name;
	public String date_creation;
	public String date_change;
	public String status;
	
	public CategoryRuleModel categoryRule = new CategoryRuleModel();
	
	public RoadwayModel(String id, String rule_name, String category_name, String date_creation, String date_change,
			String status, CategoryRuleModel categoryRule) {
		super();
		this.id = id;
		this.rule_name = rule_name;
		this.category_name = category_name;
		this.date_creation = date_creation;
		this.date_change = date_change;
		this.status = status;
		this.categoryRule = categoryRule;
	}

	public RoadwayModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
