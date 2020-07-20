package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.BusinessRule_Model;
import com.packsendme.microservice.manager.roadway.repository.IBusinessRuleManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class RoadwayDAO implements IRoadwayDAO<BusinessRule_Model> {

	@Autowired
	IBusinessRuleManager_Repository roadwayManager_Rep; 

	
	@Override
	public BusinessRule_Model save(BusinessRule_Model entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BusinessRule_Model findOne(BusinessRule_Model entity) {
		try {
			return  entity = roadwayManager_Rep.findBusinessRuleByCategory(entity.category_name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<BusinessRule_Model> findAll() {
		try {
			List<BusinessRule_Model> entityL = new ArrayList<BusinessRule_Model>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(BusinessRule_Model entity) {
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
	public BusinessRule_Model update(BusinessRule_Model entity) {
		try {
			BusinessRule_Model entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}		
	}
}
