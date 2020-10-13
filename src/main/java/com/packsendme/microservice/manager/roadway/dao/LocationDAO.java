package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.ILocationManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class LocationDAO implements IRoadwayDAO<LocationModel> {

	@Autowired
	ILocationManager_Repository locationManager_Rep; 
	
	
	@Override
	public LocationModel save(LocationModel entity) {
		try {
			return entity = locationManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<LocationModel> findOneById(String id) {
		try {
			return locationManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<LocationModel> findAll() {
		try {
			List<LocationModel> entityL = new ArrayList<LocationModel>(); 
			entityL = locationManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(LocationModel entity) {
		try {
			locationManager_Rep.delete(entity);
			return true; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}		
	}

	@Override
	public LocationModel update(LocationModel entity) {
		try {
			LocationModel entityModel = locationManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	@Override
	public LocationModel findOneByIdAndName(String id, String name) {
		try {
			return locationManager_Rep.findLocationByIdAndCountry(id, name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public LocationModel findOneByName(String name) {
		try {
			return locationManager_Rep.findLocationByCountry(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	} 
}
