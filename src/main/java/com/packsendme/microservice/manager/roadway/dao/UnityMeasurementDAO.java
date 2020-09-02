package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.IUnityMeasurementAdmin_Repository;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class UnityMeasurementDAO implements IRoadwayAdmDAO<UnityMeasurementModel> {

	@Autowired
	IUnityMeasurementAdmin_Repository roadwayAdm_Rep; 
		
	@Override
	public UnityMeasurementModel save(UnityMeasurementModel entity) {
		try {
			return entity = roadwayAdm_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Optional<UnityMeasurementModel> findOneById(String id) {
		try {
			return roadwayAdm_Rep.findById(id);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<UnityMeasurementModel> findAll(String name) {
		try {
			List<UnityMeasurementModel> entityL = new ArrayList<UnityMeasurementModel>(); 
			entityL = roadwayAdm_Rep.findUnityMeasurementByCountry(name);
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(UnityMeasurementModel entity) {
		try {
			roadwayAdm_Rep.delete(entity);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public UnityMeasurementModel update(UnityMeasurementModel entity) {
		try {
			UnityMeasurementModel entityModel = roadwayAdm_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

	@Override
	public UnityMeasurementModel findOneByName(String name) {
		try {
			return roadwayAdm_Rep.findUnityMeasurementByName(name);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public UnityMeasurementModel findOneByIdAndName(String id, String name) {
		try {
			return null;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

 
	
}
