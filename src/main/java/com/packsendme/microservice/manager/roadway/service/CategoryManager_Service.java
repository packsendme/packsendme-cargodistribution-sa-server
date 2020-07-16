 package com.packsendme.microservice.manager.roadway.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.manager.roadway.component.ParseDtoToModel;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.CategoryDAO;
import com.packsendme.microservice.manager.roadway.dto.CategoryListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.Category_Model;
import com.packsendme.roadway.bre.model.category.CategoryBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class CategoryManager_Service {
	
	private CategoryDAO categoryManagerDAO;
	private ParseDtoToModel parserObj;

	public ResponseEntity<?> findCategoryAll() {
		Response<CategoryListDTO_Response> responseObj = null;
		try {
			CategoryListDTO_Response categoryListDTO_Response = new CategoryListDTO_Response(categoryManagerDAO.findAll());
			responseObj = new Response<CategoryListDTO_Response>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), categoryListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<CategoryListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveCategory(CategoryBRE categoryBRE) {
		Response<Category_Model> responseObj = null;
		try {
			Category_Model entity = parserObj.categoryDto_TO_Model(categoryBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			categoryManagerDAO.save(entity);
			responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteCategory(CategoryBRE categoryBRE) {
		Response<Category_Model> responseObj = null;
		try {
			Category_Model entity = new Category_Model();
			entity.name_category = categoryBRE.name_category;
			categoryManagerDAO.remove(entity);
			responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateCategory(CategoryBRE categoryBRE) {
		Response<Category_Model> responseObj = null;
		try {
			Category_Model categoryModel = new Category_Model();
			categoryModel.name_category = categoryBRE.name_category;
			Category_Model entity = categoryManagerDAO.findOne(categoryModel);
			if(entity != null) {
				entity = parserObj.categoryDto_TO_Model(categoryBRE, entity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryManagerDAO.update(entity);
				responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<Category_Model>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
}
