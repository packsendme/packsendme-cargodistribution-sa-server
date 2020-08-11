package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.LocationModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class LocationListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<LocationModel> locationL = new ArrayList<LocationModel>();

	
	public LocationListDTO_Response(List<LocationModel> locationL) {
		super();
		this.locationL = locationL;
	}

	public LocationListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
