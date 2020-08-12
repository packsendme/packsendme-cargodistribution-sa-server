package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleManager_Repository extends MongoRepository<VehicleRuleModel, String>{

	@Query("{id : { $ne : ?0}, vehicle : ?1}")
	VehicleRuleModel findVehicleByIdAndName(String id, String name);

	@Query("{'vehicle' : ?0}")
	VehicleRuleModel findVehicleByName(String name);
}
