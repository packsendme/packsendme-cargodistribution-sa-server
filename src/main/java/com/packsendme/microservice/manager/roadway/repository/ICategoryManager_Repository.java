package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryManager_Repository extends MongoRepository<CategoryModel, String>{

	@Query("{'category_name' :  {$eq: ?0}}")
	CategoryModel findCategoryByName(String category_name);
	
	@Query("{'transport' :  {$eq: ?0}}")
	CategoryModel findCategoryByTransport(String transport);
}
