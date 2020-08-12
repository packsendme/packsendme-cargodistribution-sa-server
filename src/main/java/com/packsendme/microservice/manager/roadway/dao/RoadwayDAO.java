package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.IBusinessRuleManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class RoadwayDAO implements IRoadwayDAO<RoadwayModel> {

	@Autowired
	IBusinessRuleManager_Repository roadwayManager_Rep; 

	
	@Override
	public RoadwayModel save(RoadwayModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<RoadwayModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<RoadwayModel> findAll() {
		try {
			List<RoadwayModel> entityL = new ArrayList<RoadwayModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(RoadwayModel entity) {
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
	public RoadwayModel update(RoadwayModel entity) {
		try {
			RoadwayModel entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}

	@Override
	public RoadwayModel findOneByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoadwayModel findOneByIdAndName(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}
}
