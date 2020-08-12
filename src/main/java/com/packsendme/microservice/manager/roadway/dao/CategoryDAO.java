package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;
import com.packsendme.microservice.manager.roadway.repository.ICategoryManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class CategoryDAO implements IRoadwayDAO<CategoryRuleModel> {

	@Autowired
	ICategoryManager_Repository roadwayManager_Rep; 
	
	
	@Override
	public CategoryRuleModel save(CategoryRuleModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<CategoryRuleModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryRuleModel> findAll() {
		try {
			List<CategoryRuleModel> entityL = new ArrayList<CategoryRuleModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(CategoryRuleModel entity) {
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
	public CategoryRuleModel update(CategoryRuleModel entity) {
		try {
			CategoryRuleModel entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public CategoryRuleModel findOneByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryRuleModel findOneByIdAndName(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
