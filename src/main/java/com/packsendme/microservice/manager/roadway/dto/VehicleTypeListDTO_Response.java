package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class VehicleTypeListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<VehicleTypeModel> vehiclesType = new ArrayList<VehicleTypeModel>();

	public VehicleTypeListDTO_Response(List<VehicleTypeModel> vehiclesType) {
		super();
		this.vehiclesType = vehiclesType;
	}

	public VehicleTypeListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
