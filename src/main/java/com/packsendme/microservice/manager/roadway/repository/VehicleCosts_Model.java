package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Document
public class VehicleCosts_Model implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Double weight_cost;
	public Double distance_cost;
	public Double worktime_cost;
	public Double average_consumption_cost;
	
	public Double rate_exchange;
	public String current_exchange;
	
	public VehicleCosts_Model(Double weight_cost, Double distance_cost, Double worktime_cost, Double average_consumption_cost,
			Double rate_exchange, String current_exchange) {
		super();
		this.weight_cost = weight_cost;
		this.distance_cost = distance_cost;
		this.worktime_cost = worktime_cost;
		this.average_consumption_cost = average_consumption_cost;
		this.rate_exchange = rate_exchange;
		this.current_exchange = current_exchange;
	}

	public VehicleCosts_Model() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
