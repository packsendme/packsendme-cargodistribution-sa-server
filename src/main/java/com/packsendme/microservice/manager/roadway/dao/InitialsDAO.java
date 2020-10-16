package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IInitialsAdmin_Repository;
import com.packsendme.microservice.manager.roadway.repository.InitialsModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class InitialsDAO implements IRoadwayDAO<InitialsModel> {

	@Autowired
	IInitialsAdmin_Repository initialsAdm_Rep; 
	
	
	@Override
	public InitialsModel save(InitialsModel entity) {
		try {
			return entity = initialsAdm_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<InitialsModel> findOneById(String id) {
		try {
			return initialsAdm_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<InitialsModel> findAll() {
		try {
			List<InitialsModel> entityL = new ArrayList<InitialsModel>(); 
			entityL = initialsAdm_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(InitialsModel entity) {
		try {
			initialsAdm_Rep.delete(entity);
			return true; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}		
	}

	@Override
	public InitialsModel update(InitialsModel entity) {
		try {
			InitialsModel entityModel = initialsAdm_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	@Override
	public InitialsModel findOneByIdAndName(String id, String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public InitialsModel findOneByName(String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	} 
}
