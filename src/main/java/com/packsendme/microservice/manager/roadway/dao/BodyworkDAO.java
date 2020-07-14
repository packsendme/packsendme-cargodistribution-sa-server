package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.BodyWork_Model;
import com.packsendme.microservice.manager.roadway.repository.IRoadwayManager_Repository;

@Component
public class BodyworkDAO implements IRoadwayDAO<BodyWork_Model> {

	@Autowired
	IRoadwayManager_Repository<BodyWork_Model> roadwayManager_Rep; 

	
	@Override
	public BodyWork_Model save(BodyWork_Model entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BodyWork_Model findOne(BodyWork_Model entity) {
		try {
			return entity = roadwayManager_Rep.findBodyworkByName(entity.bodyWork);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BodyWork_Model> findAll() {
		try {
			List<BodyWork_Model> entityL = new ArrayList<BodyWork_Model>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(BodyWork_Model entity) {
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
	public BodyWork_Model update(BodyWork_Model entity) {
		try {
			BodyWork_Model entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
}
