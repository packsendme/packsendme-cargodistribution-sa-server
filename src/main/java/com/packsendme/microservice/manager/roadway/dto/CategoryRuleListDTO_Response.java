package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.roadway.bre.model.category.Category;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryRuleListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Category> categories = new ArrayList<Category>();

	
	public CategoryRuleListDTO_Response(List<Category> categories) {
		super();
		this.categories = categories;
	}

	public CategoryRuleListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
