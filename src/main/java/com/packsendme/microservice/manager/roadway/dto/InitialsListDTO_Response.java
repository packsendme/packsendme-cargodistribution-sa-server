package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.InitialsModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class InitialsListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<InitialsModel> initials = new ArrayList<InitialsModel>();

	
	public InitialsListDTO_Response(List<InitialsModel> initials) {
		super();
		this.initials = initials;
	}

	public InitialsListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
