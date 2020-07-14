package com.packsendme.microservice.manager.roadway.dao;

import java.util.List;

public interface IRoadwayDAO<T> {

	public T save(T entity);

	public T findOne(T entity);
	
	public List<T> findAll();
	
	public Boolean remove(T entity);
	
	public T update(T entity);
	
}
