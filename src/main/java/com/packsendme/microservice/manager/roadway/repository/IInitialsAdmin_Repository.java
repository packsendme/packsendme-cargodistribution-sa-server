package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IInitialsAdmin_Repository extends MongoRepository<InitialsModel, String>{

	@Query("{'name' :  {$eq: ?0}}")
	InitialsModel findInitialsByName(String name);

}
