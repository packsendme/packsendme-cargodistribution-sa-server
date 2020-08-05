 package com.packsendme.microservice.manager.roadway.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;
import com.packsendme.roadway.bre.model.category.CategoryBRE;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class CategoryManager_Service {
	
	@Autowired
	private CategoryDAO categoryManagerDAO;
	@Autowired
	private RoadwayManager_Service roadwayManager; 
	
	@Autowired
	private ParseDtoToModel parserObj;

	public ResponseEntity<?> findCategoryAll() {
		Response<CategoryListDTO_Response> responseObj = null;
		try {
			CategoryListDTO_Response categoryListDTO_Response = new CategoryListDTO_Response(categoryManagerDAO.findAll());
			responseObj = new Response<CategoryListDTO_Response>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), categoryListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveCategory(CategoryBRE categoryBRE) {
		Response<CategoryModel> responseObj = null;
		try {
			CategoryModel entity = parserObj.categoryDto_TO_Model(categoryBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			categoryManagerDAO.save(entity);
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteCategory(String id, CategoryBRE categoryBRE) {
		Response<CategoryModel> responseObj = null;
		try {
			Optional<CategoryModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryModel categoryEntity = categoryData.get(); 
				categoryManagerDAO.remove(categoryEntity);
				roadwayManager.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, categoryEntity);
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), categoryData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateCategory(String id, CategoryBRE categoryBRE) {
		Response<CategoryModel> responseObj = null;
		try {
			Optional<CategoryModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryModel categoryEntity = categoryData.get(); 
				categoryEntity = parserObj.categoryDto_TO_Model(categoryBRE, categoryEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryManagerDAO.update(categoryEntity);
				roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, null, categoryEntity);
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), categoryEntity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTrigger(String operationType, VehicleModel vehicleModelOld, VehicleModel vehicleModelNew) {
		List<VehicleModel> catVehicleNewL = new ArrayList<VehicleModel>();
		Response<CategoryModel> responseObj = null;
		boolean statusCrud = false;
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryModel category : categoryL) {
				for(VehicleModel catVehicleDB : category.vehicles) {
					if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
						if(!catVehicleDB.vehicle.equals(vehicleModelNew.vehicle)) {
							catVehicleNewL.add(catVehicleDB);
						}
						else {
							statusCrud = true;
						}
					} 
					else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
						if(catVehicleDB.vehicle.equals(vehicleModelOld.vehicle)) {
							catVehicleNewL.add(vehicleModelNew);
							statusCrud = true;
						}
						else {
							catVehicleNewL.add(catVehicleDB);
						}
					}
				}
				if (statusCrud == true) {
					CategoryModel categoryNew = category; 
					categoryManagerDAO.remove(category);
					categoryNew.vehicles = null;
					categoryNew.vehicles = catVehicleNewL;
					categoryManagerDAO.save(categoryNew);
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	
	
	
}
