package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "VehicleType_SA")
public class VehicleTypeModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;	
	public String type_vehicle;
	 
	public VehicleTypeModel(String type_vehicle) {
		super();
		this.type_vehicle = type_vehicle;
	}


	public VehicleTypeModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
		
}
