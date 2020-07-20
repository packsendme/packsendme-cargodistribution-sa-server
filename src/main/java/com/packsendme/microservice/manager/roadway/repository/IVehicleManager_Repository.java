package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleManager_Repository extends MongoRepository<Vehicle_Model, String>{

	@Query("{'vehicle' :  {$eq: ?0}}")
	Vehicle_Model findVehicleByName(String vehicle);
}
