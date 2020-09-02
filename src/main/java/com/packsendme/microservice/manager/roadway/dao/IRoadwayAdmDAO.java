package com.packsendme.microservice.manager.roadway.dao;

import java.util.List;
import java.util.Optional;

public interface IRoadwayAdmDAO<T> {

	public T save(T entity);

	public Optional<T> findOneById(String id);
	
	public List<T> findAll(String name);
	
	public Boolean remove(T entity);
	
	public T update(T entity);
	
	public T findOneByIdAndName(String id, String name);

	public T findOneByName(String name);

}
