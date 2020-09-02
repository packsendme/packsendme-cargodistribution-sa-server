package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IVehicleTypeAdmin_Repository extends MongoRepository<VehicleTypeModel, String>{

	@Query("{'type_vehicle' :  {$eq: ?0}}")
	VehicleTypeModel findVehicleTypeByName(String type_vehicle);

}
