package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class UnityMeasurementListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<UnityMeasurementModel> unityMeasurements = new ArrayList<UnityMeasurementModel>();

	public UnityMeasurementListDTO_Response(List<UnityMeasurementModel> unityMeasurements) {
		super();
		this.unityMeasurements = unityMeasurements;
	}

	public UnityMeasurementListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
}
