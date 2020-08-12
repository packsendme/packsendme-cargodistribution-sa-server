package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IVehicleManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class VehicleDAO implements IRoadwayDAO<VehicleRuleModel> {

	@Autowired
	IVehicleManager_Repository roadwayManager_Rep; 
		
	@Override
	public VehicleRuleModel save(VehicleRuleModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<VehicleRuleModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<VehicleRuleModel> findAll() {
		try {
			List<VehicleRuleModel> entityL = new ArrayList<VehicleRuleModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(VehicleRuleModel entity) {
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
	public VehicleRuleModel update(VehicleRuleModel entity) {
		try {
			VehicleRuleModel entityModel =  roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public VehicleRuleModel findOneByName(String name) {
		try {
			return roadwayManager_Rep.findVehicleByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public VehicleRuleModel findOneByIdAndName(String id, String name) {
		try {
			return roadwayManager_Rep.findVehicleByIdAndName(id, name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

 
	
}
