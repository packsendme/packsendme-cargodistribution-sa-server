package com.packsendme.microservice.manager.roadway.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class CategoryRuleListDTO_Response implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<CategoryRuleModel> categoryL = new ArrayList<CategoryRuleModel>();

	
	public CategoryRuleListDTO_Response(List<CategoryRuleModel> categoryL) {
		super();
		this.categoryL = categoryL;
	}

	public CategoryRuleListDTO_Response() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}