package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IVehicleManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class VehicleDAO implements IRoadwayDAO<VehicleModel> {

	@Autowired
	IVehicleManager_Repository roadwayManager_Rep; 
		
	@Override
	public VehicleModel save(VehicleModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<VehicleModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<VehicleModel> findAll() {
		try {
			List<VehicleModel> entityL = new ArrayList<VehicleModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(VehicleModel entity) {
		try {
			roadwayManager_Rep.delete(entity);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public VehicleModel update(VehicleModel entity) {
		try {
			VehicleModel entityModel =  roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

 
	
}
