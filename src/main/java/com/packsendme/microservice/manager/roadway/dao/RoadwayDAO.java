package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IRoadwayManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.RoadwayBusinessRule_Model;

@Component
public class RoadwayDAO implements IRoadwayDAO<RoadwayBusinessRule_Model> {

	@Autowired
	IRoadwayManager_Repository<RoadwayBusinessRule_Model> roadwayManager_Rep; 

	
	@Override
	public RoadwayBusinessRule_Model save(RoadwayBusinessRule_Model entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RoadwayBusinessRule_Model findOne(RoadwayBusinessRule_Model entity) {
		try {
			return entity = roadwayManager_Rep.findRoadwayByCategory(entity.category_name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RoadwayBusinessRule_Model> findAll() {
		try {
			List<RoadwayBusinessRule_Model> entityL = new ArrayList<RoadwayBusinessRule_Model>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(RoadwayBusinessRule_Model entity) {
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
	public RoadwayBusinessRule_Model update(RoadwayBusinessRule_Model entity) {
		try {
			RoadwayBusinessRule_Model entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}
}
