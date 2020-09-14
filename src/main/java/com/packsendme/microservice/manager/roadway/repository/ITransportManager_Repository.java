package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransportManager_Repository extends MongoRepository<TransportModel, String>{

	@Query("{'name_transport' :  {$eq: ?0}}")
	TransportModel findTransportByName(String name_transport);
}
