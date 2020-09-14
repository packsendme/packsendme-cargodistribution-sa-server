package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "TransportManager_SA")
public class TransportModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String name_transport;
	
	public TransportModel(String name_transport) {
		super();
		this.name_transport = name_transport;
	}

	public TransportModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
