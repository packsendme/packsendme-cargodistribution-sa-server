package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.ITransportManager_Repository;
import com.packsendme.microservice.manager.roadway.repository.TransportModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class TransportDAO implements IRoadwayDAO<TransportModel> {

	@Autowired
	ITransportManager_Repository transportManager_Rep; 
	
	
	@Override
	public TransportModel save(TransportModel entity) {
		try {
			return entity = transportManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<TransportModel> findOneById(String id) {
		try {
			return transportManager_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<TransportModel> findAll() {
		try {
			List<TransportModel> entityL = new ArrayList<TransportModel>(); 
			entityL = transportManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(TransportModel entity) {
		try {
			transportManager_Rep.delete(entity);
			return true; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return false; 
		}		
	}

	@Override
	public TransportModel update(TransportModel entity) {
		try {
			TransportModel entityModel = transportManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}
	
	@Override
	public TransportModel findOneByIdAndName(String id, String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public TransportModel findOneByName(String name) {
		try {
			return transportManager_Rep.findTransportByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	} 
}
