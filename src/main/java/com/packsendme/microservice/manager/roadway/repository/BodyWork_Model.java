package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Document
public class BodyWork_Model implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public long _id;
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
