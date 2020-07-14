package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.BodyWork_Model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class BodyworkListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<BodyWork_Model> bodyWorkL = new ArrayList<BodyWork_Model>();

	
	public BodyworkListDTO_Response(List<BodyWork_Model> bodyWorkL) {
		super();
		this.bodyWorkL = bodyWorkL;
	}

	public BodyworkListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
