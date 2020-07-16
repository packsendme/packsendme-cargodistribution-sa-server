package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IRoadwayManager_Repository<T> extends MongoRepository<T, String> {
	
	@Query("{'vehicle' :  {$eq: ?0}}")
	Vehicle_Model findVehicleByName(String vehicle);
	
	@Query("{'bodyWork' :  {$eq: ?0}}")
	BodyWork_Model findBodyworkByName(String bodyWork);
	
	@Query("{'category_name' :  {$eq: ?0}}")
	RoadwayBusinessRule_Model findRoadwayByCategory(String category_name);
}
