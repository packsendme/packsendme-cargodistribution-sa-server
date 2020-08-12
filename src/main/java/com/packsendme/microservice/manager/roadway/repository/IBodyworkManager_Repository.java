package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBodyworkManager_Repository extends MongoRepository<BodyWorkModel, String>{

/*	@Query("{'bodyWork' :  {$eq: ?0}}")
	BodyWorkModel findBodyworkByName(String bodyWork);
	*/
	
	@Query("{id : { $ne : ?0}, bodyWork : ?1}")
	BodyWorkModel findBodyWorkByIdAndName(String id, String name);

	@Query("{'bodyWork' : ?0}")
	BodyWorkModel findBodyWorkByName(String name);

}

