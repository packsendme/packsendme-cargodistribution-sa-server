package com.packsendme.microservice.manager.roadway.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter 
@Document
public class CategoryCostsModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String vehicle;
	public String countryName;
	public Double weight_cost;
	public Double distance_cost;
	public Double worktime_cost;
	public Double average_consumption_cost;
	public Double rate_exchange;
	public String current_exchange;
	public Boolean statusChange;

	
	public CategoryCostsModel(String vehicle, String countryName, Double weight_cost, Double distance_cost,
			Double worktime_cost, Double average_consumption_cost, Double rate_exchange, String current_exchange,
			Boolean statusChange) {
		super();
		this.vehicle = vehicle;
		this.countryName = countryName;
		this.weight_cost = weight_cost;
		this.distance_cost = distance_cost;
		this.worktime_cost = worktime_cost;
		this.average_consumption_cost = average_consumption_cost;
		this.rate_exchange = rate_exchange;
		this.current_exchange = current_exchange;
		this.statusChange = statusChange;
	}




	public CategoryCostsModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
