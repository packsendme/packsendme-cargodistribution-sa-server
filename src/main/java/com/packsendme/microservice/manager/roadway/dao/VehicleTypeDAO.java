package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IVehicleManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.IVehicleTypeAdmin_Repository;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class VehicleTypeDAO implements IRoadwayDAO<VehicleTypeModel> {

	@Autowired
	IVehicleTypeAdmin_Repository roadwayAdm_Rep; 
		
	@Override
	public VehicleTypeModel save(VehicleTypeModel entity) {
		try {
			return entity = roadwayAdm_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<VehicleTypeModel> findOneById(String id) {
		try {
			return roadwayAdm_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<VehicleTypeModel> findAll() {
		try {
			List<VehicleTypeModel> entityL = new ArrayList<VehicleTypeModel>(); 
			entityL = roadwayAdm_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(VehicleTypeModel entity) {
		try {
			roadwayAdm_Rep.delete(entity);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public VehicleTypeModel update(VehicleTypeModel entity) {
		try {
			VehicleTypeModel entityModel =  roadwayAdm_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public VehicleTypeModel findOneByName(String name) {
		try {
			return roadwayAdm_Rep.findVehicleTypeByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public VehicleTypeModel findOneByIdAndName(String id, String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

 
	
}
