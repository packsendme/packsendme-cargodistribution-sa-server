package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.Category_Model;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Category_Model> categoryL = new ArrayList<Category_Model>();

	
	public CategoryListDTO_Response(List<Category_Model> categoryL) {
		super();
		this.categoryL = categoryL;
	}

	public CategoryListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}