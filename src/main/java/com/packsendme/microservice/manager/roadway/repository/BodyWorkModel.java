package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Document(collection = "BodyWorkManager_SA")
public class BodyWorkModel implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public String id;
	public String bodyWork;
	public String type;
	
	public BodyWorkModel(String bodyWork, String type) {
		super();
		this.bodyWork = bodyWork;
		this.type = type;
	}

	public BodyWorkModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
