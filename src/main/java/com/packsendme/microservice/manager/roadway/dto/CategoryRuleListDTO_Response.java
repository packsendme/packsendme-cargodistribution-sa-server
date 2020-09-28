package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.roadway.bre.model.category.CategoryRule;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryRuleListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<CategoryRule> categories = new ArrayList<CategoryRule>();

	
	public CategoryRuleListDTO_Response(List<CategoryRule> categories) {
		super();
		this.categories = categories;
	}

	public CategoryRuleListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
