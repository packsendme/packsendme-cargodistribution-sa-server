package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UnityMeasurementDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	public String id;	
	public String unitMeasurement;
	public String origin_country;

}
