package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusinessRuleManager_Repository extends MongoRepository<RoadwayModel, String>{

	@Query("{'category' :  {$eq: ?0}}")
	RoadwayModel findBusinessRuleByCategory(String category);
}
