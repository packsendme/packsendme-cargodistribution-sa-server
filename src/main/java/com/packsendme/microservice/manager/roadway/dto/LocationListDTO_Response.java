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
	
	public List<LocationModel> locations = new ArrayList<LocationModel>();

	
	public LocationListDTO_Response(List<LocationModel> locations) {
		super();
		this.locations = locations;
	}

	public LocationListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

/*
A pureza interior de uma criança transcende a sua capacidade para ver
, se almas de luz que 
  
*/