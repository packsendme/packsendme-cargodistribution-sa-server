package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class BodyworkListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<BodyWorkModel> bodies = new ArrayList<BodyWorkModel>();

	
	public BodyworkListDTO_Response(List<BodyWorkModel> bodies) {
		super();
		this.bodies = bodies;
	}

	public BodyworkListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
