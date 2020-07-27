package com.packsendme.microservice.manager.roadway.dao;

import java.util.List;
import java.util.Optional;

public interface IRoadwayDAO<T> {

	public T save(T entity);

	public Optional<T> findOneById(String id);
	
	public List<T> findAll();
	
	public Boolean remove(T entity);
	
	public T update(T entity);
	
}
