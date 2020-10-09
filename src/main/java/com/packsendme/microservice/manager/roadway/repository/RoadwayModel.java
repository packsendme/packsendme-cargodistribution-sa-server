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
@Document(collection = "RoadwayBRE_SA")
public class RoadwayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String type_bre;
	public String date_creation;
	public String date_change;
	public  List<CategoryRuleModel> categories = new ArrayList<CategoryRuleModel>();
	public String status;
	public String version;
	

	public RoadwayModel(String id, String type_bre, String date_creation, String date_change,
			List<CategoryRuleModel> categories, String status, String version) {
		super();
		this.id = id;
		this.type_bre = type_bre;
		this.date_creation = date_creation;
		this.date_change = date_change;
		this.categories = categories;
		this.status = status;
		this.version = version;
	}

	public RoadwayModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
