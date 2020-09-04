package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryTypeManager_Repository extends MongoRepository<CategoryTypeModel, String>{

	@Query("{'name_category' :  {$eq: ?0}}")
	CategoryTypeModel findCategoryTypeByName(String category_name);
}
