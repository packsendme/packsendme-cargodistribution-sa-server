package com.packsendme.microservice.manager.roadway.repository;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
public class BodyWork_Model {
	
	public String bodyWork;
	public String type;
	
	public BodyWork_Model(String bodyWork, String type) {
		super();
		this.bodyWork = bodyWork;
		this.type = type;
	}

	public BodyWork_Model() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
