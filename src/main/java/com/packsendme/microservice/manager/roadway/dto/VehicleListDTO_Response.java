package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.VehicleModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class VehicleListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<VehicleModel> vehiclesL = new ArrayList<VehicleModel>();

	
	public VehicleListDTO_Response(List<VehicleModel> vehiclesL) {
		super();
		this.vehiclesL = vehiclesL;
	}

	public VehicleListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
