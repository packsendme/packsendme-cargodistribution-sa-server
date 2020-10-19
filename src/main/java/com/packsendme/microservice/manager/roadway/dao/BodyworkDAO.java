package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.IBodyworkManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class BodyworkDAO implements IRoadwayDAO<BodyWorkModel> {

	@Autowired
	IBodyworkManager_Repository roadwayManager_Rep; 
	
	
	@Override
	public BodyWorkModel save(BodyWorkModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<BodyWorkModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BodyWorkModel> findAll() {
		try {
			List<BodyWorkModel> entityL = new ArrayList<BodyWorkModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(BodyWorkModel entity) {
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
	public BodyWorkModel update(BodyWorkModel entity) {
		try {
			BodyWorkModel entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	@Override
	public BodyWorkModel findOneByName(String name) {
		try {
			return roadwayManager_Rep.findBodyWorkByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BodyWorkModel findOneByIdAndName(String id, String name) {
		try {
			return roadwayManager_Rep.findBodyWorkByIdAndName(id, name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BodyWorkModel> findEntityByParameters(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
 
}
