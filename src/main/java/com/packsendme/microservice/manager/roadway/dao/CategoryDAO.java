package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.Category_Model;
import com.packsendme.microservice.manager.roadway.repository.ICategoryManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class CategoryDAO implements IRoadwayDAO<Category_Model> {

	@Autowired
	ICategoryManager_Repository roadwayManager_Rep; 
	
	
	@Override
	public Category_Model save(Category_Model entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Category_Model findOne(Category_Model account) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Category_Model> findAll() {
		try {
			List<Category_Model> entityL = new ArrayList<Category_Model>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(Category_Model entity) {
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
	public Category_Model update(Category_Model entity) {
		try {
			Category_Model entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	
}
