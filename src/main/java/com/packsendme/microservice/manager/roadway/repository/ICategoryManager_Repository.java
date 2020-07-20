package com.packsendme.microservice.manager.roadway.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryManager_Repository extends MongoRepository<Category_Model, String>{

	@Query("{'category_name' :  {$eq: ?0}}")
	Category_Model findCategoryByName(String category_name);
}
