package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "InitialsAdm_SA")
public class InitialsModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String name;
	
	public InitialsModel(String name) {
		super();
		this.name = name;
	}

	public InitialsModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
