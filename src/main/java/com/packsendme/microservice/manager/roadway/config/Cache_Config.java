package com.packsendme.microservice.manager.roadway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Cache_Config {

	// roadwaymanagerSA-cache
	@Value(value = "${redis.cache.roadwayBRE_SA}")
	public String roadwayBRE_SA;


}
