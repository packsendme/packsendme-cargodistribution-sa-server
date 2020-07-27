package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.BodyWork_Model;
import com.packsendme.microservice.manager.roadway.repository.IBodyworkManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class BodyworkDAO implements IRoadwayDAO<BodyWork_Model> {

	@Autowired
	IBodyworkManager_Repository roadwayManager_Rep; 
	
	
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
	public Optional<BodyWork_Model> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
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
