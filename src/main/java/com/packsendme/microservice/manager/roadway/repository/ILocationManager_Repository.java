package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocationManager_Repository extends MongoRepository<LocationModel, String>{

	@Query("{'countryName' : ?0, id :  { $ne : ?1}}")
	LocationModel findLocationByCountry(String country, String id);
}
