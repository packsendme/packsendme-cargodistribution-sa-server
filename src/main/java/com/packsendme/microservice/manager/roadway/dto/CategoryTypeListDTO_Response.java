package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryTypeListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<CategoryTypeModel> categoryTypeL = new ArrayList<CategoryTypeModel>();

	
	public CategoryTypeListDTO_Response(List<CategoryTypeModel> categoryTypeL) {
		super();
		this.categoryTypeL = categoryTypeL;
	}

	public CategoryTypeListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
