package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoadwayManager_Repository extends MongoRepository<RoadwayBusinessRule_Model, String>{


	/*@Query("{'vehicle' :  {$eq: ?0}}")
	T findVehicleByName(String vehicle);
	
	@Query("{'bodyWork' :  {$eq: ?0}}")
	T findBodyworkByName(String bodyWork);
	
	@Query("{'category_name' :  {$eq: ?0}}")
	T findRoadwayByCategory(String category_name);*/
}
