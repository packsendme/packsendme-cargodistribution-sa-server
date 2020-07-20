package com.packsendme.microservice.manager.roadway.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClientException;
import com.packsendme.microservice.manager.roadway.repository.Vehicle_Model;

@Component
@ComponentScan({"com.packsendme.microservice.manager.roadway.repository"})
public class VehicleDAO implements IRoadwayDAO<Vehicle_Model> {

	/*@Autowired
	IRoadwayManager_Repository<Vehicle_Model> roadwayManager_Rep; 
	*/ 
	
	@Override
	public Vehicle_Model save(Vehicle_Model entity) {
		try {
			return entity = null;// roadwayManager_Rep.insert(entity);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Vehicle_Model findOne(Vehicle_Model entity) {
		try {
			return entity = null; //roadwayManager_Rep.findVehicleByName(entity.vehicle);
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Vehicle_Model> findAll() {
		try {
			List<Vehicle_Model> entityL = new ArrayList<Vehicle_Model>(); 
			entityL = null;// roadwayManager_Rep.findAll();
			return entityL;
		}
		catch (MongoClientException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Boolean remove(Vehicle_Model entity) {
		try {
			//roadwayManager_Rep.delete(entity);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	@Override
	public Vehicle_Model update(Vehicle_Model entity) {
		try {
			Vehicle_Model entityModel =  null;// roadwayManager_Rep.save(entity);
			return entityModel; 
		}
		catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
	}

 
	
}
