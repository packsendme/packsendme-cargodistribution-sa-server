package com.packsendme.microservice.manager.roadway.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IUnityMeasurementAdmin_Repository extends MongoRepository<UnityMeasurementModel, String>{
	
	@Query("{'origin_country' :  {$eq: ?0}}")
	List<UnityMeasurementModel> findUnityMeasurementByCountry(String origin_country);

	@Query("{'unitMeasurement' :  {$eq: ?0}}")
	UnityMeasurementModel findUnityMeasurementByName(String unitMeasurement);

}
