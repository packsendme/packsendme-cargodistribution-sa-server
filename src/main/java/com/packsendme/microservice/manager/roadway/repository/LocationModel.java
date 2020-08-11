package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
@Document(collection = "LocationManager_SA")
public class LocationModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;
	public String countryName;
	public String cityName;
	public String stateName;
	public String codCountry;
	
	
	public LocationModel(String countryName, String cityName, String stateName, String codCountry) {
		super();
		this.countryName = countryName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.codCountry = codCountry;
	}


	public LocationModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
