package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.Vehicle_Model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class VehicleListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Vehicle_Model> vehiclesL = new ArrayList<Vehicle_Model>();

	
	public VehicleListDTO_Response(List<Vehicle_Model> vehiclesL) {
		super();
		this.vehiclesL = vehiclesL;
	}

	public VehicleListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
