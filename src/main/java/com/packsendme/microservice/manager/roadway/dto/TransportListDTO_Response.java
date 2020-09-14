package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.TransportModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class TransportListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<TransportModel> transporties = new ArrayList<TransportModel>();

	
	public TransportListDTO_Response(List<TransportModel> transporties) {
		super();
		this.transporties = transporties;
	}

	public TransportListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
