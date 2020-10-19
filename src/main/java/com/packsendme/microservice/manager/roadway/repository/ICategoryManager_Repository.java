package com.packsendme.microservice.manager.roadway.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryManager_Repository extends MongoRepository<CategoryModel, String>{

	@Query("{'name_category' :  {$eq: ?0}}")
	CategoryModel findCategoryByName(String category_name);
	
	@Query("{'transport' :  {$eq: ?0}}")
	List<CategoryModel> findCategoryByTransport(String transport);
}
