package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.ICategoryManager_Repository;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class CategoryRuleDAO implements IRoadwayDAO<CategoryModel> {

	@Autowired
	ICategoryManager_Repository roadwayManager_Rep; 
	
	
	@Override
	public CategoryModel save(CategoryModel entity) {
		try {
			return entity = roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<CategoryModel> findOneById(String id) {
		try {
			return roadwayManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryModel> findAll() {
		try {
			List<CategoryModel> entityL = new ArrayList<CategoryModel>(); 
			entityL = roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(CategoryModel entity) {
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
	public CategoryModel update(CategoryModel entity) {
		try {
			CategoryModel entityModel = roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public CategoryModel findOneByName(String name) {
		try {
			return roadwayManager_Rep.findCategoryByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public CategoryModel findOneByIdAndName(String id, String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CategoryModel> findEntityByParameters(String name) {
		try {
			return roadwayManager_Rep.findCategoryByTransport(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
