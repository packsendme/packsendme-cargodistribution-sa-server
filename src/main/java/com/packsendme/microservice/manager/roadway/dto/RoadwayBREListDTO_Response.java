package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class RoadwayBREListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<RoadwayBRE> roadways_bre = new ArrayList<RoadwayBRE>();

 

	public RoadwayBREListDTO_Response(List<RoadwayBRE> roadways_bre) {
		super();
		this.roadways_bre = roadways_bre;
	}



	public RoadwayBREListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
