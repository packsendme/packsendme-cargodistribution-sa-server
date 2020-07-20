package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.BusinessRule_Model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class RoadwayBREListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<BusinessRule_Model> roadwayBREL = new ArrayList<BusinessRule_Model>();

	
	public RoadwayBREListDTO_Response(List<BusinessRule_Model> roadwayBREL) {
		super();
		this.roadwayBREL = roadwayBREL;
	}

	public RoadwayBREListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
