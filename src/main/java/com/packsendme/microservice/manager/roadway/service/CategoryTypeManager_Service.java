 package com.packsendme.microservice.manager.roadway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.manager.roadway.component.ParseComponent;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.CategoryTypeDAO;
import com.packsendme.microservice.manager.roadway.dto.CategoryTypeListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;
import com.packsendme.roadway.bre.model.category.CategoryType;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class CategoryTypeManager_Service {
	
	@Autowired
	private CategoryTypeDAO categoryTypeDAO;
	@Autowired
	private CategoryRuleManager_Service categoryRuleManager; 
	
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findCategoryTypeAll() {
		Response<CategoryTypeListDTO_Response> responseObj = null;
		try {
			CategoryTypeListDTO_Response categoryListDTO_Response = new CategoryTypeListDTO_Response(categoryTypeDAO.findAll());
			responseObj = new Response<CategoryTypeListDTO_Response>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), categoryListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryTypeListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveCategoryType(CategoryType category) {
		Response<CategoryTypeModel> responseObj = null;
		try {
			System.out.println(" ---------------------------------- ");
			System.out.println(" CATEGORY TYPE  - SAVE "+ category.name_category);
			CategoryTypeModel entity = parserObj.parserCategoryType_TO_Model(category, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			categoryTypeDAO.save(entity);
			System.out.println(" CATEGORY TYPE - SAVED "+ category.name_category);
			responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteCategoryType(String id) {
		Response<CategoryTypeModel> responseObj = null;
		try {
			Optional<CategoryTypeModel> categoryData = categoryTypeDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryTypeModel categoryTypeEntity = categoryData.get(); 
				categoryTypeDAO.remove(categoryTypeEntity);
				categoryRuleManager.crudTrigger_FromCategoryType(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, categoryTypeEntity);
				responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), categoryData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateCategoryType(String id, CategoryType categoryTypeBRE) {
		Response<CategoryTypeModel> responseObj = null;
		try {
			Optional<CategoryTypeModel> categoryData = categoryTypeDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryTypeModel categoryTypeEntity = categoryData.get(); 
				categoryTypeEntity = parserObj.parserCategoryType_TO_Model(categoryTypeBRE, categoryTypeEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryTypeDAO.update(categoryTypeEntity);

				// Trigger Method - Update Roadway-Entity
				categoryRuleManager.crudTrigger_FromCategoryType(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryTypeBRE, categoryTypeEntity);

				responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), categoryTypeEntity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryTypeModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
