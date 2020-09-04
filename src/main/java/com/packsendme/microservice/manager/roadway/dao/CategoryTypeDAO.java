package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;
import com.packsendme.microservice.manager.roadway.repository.ICategoryTypeManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class CategoryTypeDAO implements IRoadwayDAO<CategoryTypeModel> {

	@Autowired
	ICategoryTypeManager_Repository roadwayManager_Rep; 
	
	
	@Override
	public CategoryTypeModel save(CategoryTypeModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<CategoryTypeModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryTypeModel> findAll() {
		try {
			List<CategoryTypeModel> entityL = new ArrayList<CategoryTypeModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(CategoryTypeModel entity) {
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
	public CategoryTypeModel update(CategoryTypeModel entity) {
		try {
			CategoryTypeModel entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public CategoryTypeModel findOneByName(String name) {
		try {
			CategoryTypeModel entityModel = roadwayManager_Rep.findCategoryTypeByName(name);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public CategoryTypeModel findOneByIdAndName(String id, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
