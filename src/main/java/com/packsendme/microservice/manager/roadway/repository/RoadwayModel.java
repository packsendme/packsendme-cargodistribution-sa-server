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
@Document(collection = "RoadwayBRE_SA")
public class RoadwayModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String transport;
	public String date_creation;
	public String date_change;
	public String status;
	public String version;
	
	public List<CategoryModel> categories = new ArrayList<CategoryModel>();
	public List<LocationModel> locations = new ArrayList<LocationModel>();
	public Map<String, List<CostsModel>> costs = new HashMap<String, List<CostsModel>>(); 

	
	
	public RoadwayModel(String id, String transport, String date_creation, String date_change, String status,
			String version, List<CategoryModel> categories, List<LocationModel> locations,
			Map<String, List<CostsModel>> costs) {
		super();
		this.id = id;
		this.transport = transport;
		this.date_creation = date_creation;
		this.date_change = date_change;
		this.status = status;
		this.version = version;
		this.categories = categories;
		this.locations = locations;
		this.costs = costs;
	}



	public RoadwayModel() {
		super();
		// TODO Auto-generated constructor stub
	}
}
