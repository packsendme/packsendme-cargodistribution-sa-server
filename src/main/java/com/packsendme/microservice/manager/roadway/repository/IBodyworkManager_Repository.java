package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBodyworkManager_Repository extends MongoRepository<BodyWork_Model, String>{

	@Query("{'bodyWork' :  {$eq: ?0}}")
	BodyWork_Model findBodyworkByName(String bodyWork);
}
