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
import com.packsendme.microservice.manager.roadway.component.ParseComponent;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.CategoryRuleDAO;
import com.packsendme.microservice.manager.roadway.dto.CategoryRuleListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.roadway.bre.model.category.Category;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class CategoryManager_Service {
	
	@Autowired
	private CategoryRuleDAO categoryManagerDAO;
	@Autowired
	private RoadwayManager_Service roadwayManager; 
	
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findCategoryAll() {
		Response<CategoryRuleListDTO_Response> responseObj = null;
		try {
			List<CategoryModel> categoriesRulesModel_L = categoryManagerDAO.findAll();
			List<Category> categoriesRulesBRE_L =  parserObj.parserCategoryModel_TO_BRE(categoriesRulesModel_L);
			CategoryRuleListDTO_Response categoryListDTO_Response = new CategoryRuleListDTO_Response(categoriesRulesBRE_L);
			responseObj = new Response<CategoryRuleListDTO_Response>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), categoryListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> findCategoriesByTransports(String name) {
		Response<CategoryRuleListDTO_Response> responseObj = null;
		try {
			List<CategoryModel> categoriesRulesModel_L = categoryManagerDAO.findEntityByParameters(name);
			List<Category> categoriesRulesBRE_L =  parserObj.parserCategoryModel_TO_BRE(categoriesRulesModel_L);
			CategoryRuleListDTO_Response categoryListDTO_Response = new CategoryRuleListDTO_Response(categoriesRulesBRE_L);
			responseObj = new Response<CategoryRuleListDTO_Response>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), categoryListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveCategory(Category category) {
		Response<CategoryModel> responseObj = null;
		try {
			if(categoryManagerDAO.findOneByName(category.name_category) == null) {
				CategoryModel entity = parserObj.parserCategoryBRE_TO_Model(category, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
				categoryManagerDAO.save(entity);
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.CREATED_CATEGORY.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteCategory(String id) {
		Response<CategoryModel> responseObj = null;
		try {
			Optional<CategoryModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryModel categoryEntity = categoryData.get(); 
				categoryManagerDAO.remove(categoryEntity);
				//roadwayManager.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, categoryEntity);
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

	public ResponseEntity<?> preparedUpdateCategory(String id, Category categoryBRE) {
		Response<CategoryModel> responseObj = null;
		try {
			CategoryModel catCheckModel = categoryManagerDAO.findOneByName(categoryBRE.name_category);

			if(catCheckModel == null){
				return updateCategory(id, categoryBRE); 
			}
			else if(catCheckModel.id.equals(id)) {
				return updateCategory(id, categoryBRE); 
			}
			else if(!catCheckModel.id.equals(id)) {
				System.out.println(" preparedUpdateCategory ID != ID "+ catCheckModel.id );

				responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
			return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateCategory(String id, Category categoryBRE) {
		Response<CategoryModel> responseObj = null;
		try {
			Optional<CategoryModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryModel categoryEntity = categoryData.get(); 
				CategoryModel entity = parserObj.parserCategoryBRE_TO_Model(categoryBRE, categoryEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryManagerDAO.update(entity);
		
				// Trigger Method - Update Roadway-Entity
				//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryEntity);
						
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
	
	/*******************************************************************************************************************
	 * CRUD-TRIGGER 
	 ********************************************************************************************************************/
	
	 
	public ResponseEntity<?> crudTrigger(String operationType, String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		Response<CategoryModel> responseObj = null;
		try {
			if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
				return crudTriggerDelete(vehicleS_Old, vehicleModelNew);
			}
			else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
				return crudTriggerUpdate(vehicleS_Old, vehicleModelNew);
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTriggerDelete(String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		List<VehicleRuleModel> catVehicleNewL = new ArrayList<VehicleRuleModel>();

		Response<CategoryModel> responseObj = null;
		boolean statusCrud = false;
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryModel categoryDB : categoryL) {
				for(VehicleRuleModel catVehicleDB : categoryDB.vehicles) {
					catVehicleNewL.add(catVehicleDB);
					// Vehicle - Type (vehicle change == vehicle DDBB)
					if(catVehicleDB.vehicle_type.equals(vehicleModelNew.vehicle_type)) {
						statusCrud = true;
						catVehicleNewL.remove(catVehicleDB);
					}
				}
				if (statusCrud == true) {
					categoryDB.vehicles = catVehicleNewL;
					categoryManagerDAO.update(categoryDB);
					//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryNew);
					catVehicleNewL = new ArrayList<VehicleRuleModel>();
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
	
	public ResponseEntity<?> crudTriggerUpdate(String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		List<VehicleRuleModel> catVehicleNewL = new ArrayList<VehicleRuleModel>();
		
		Response<CategoryModel> responseObj = null;
		boolean statusCrud = false;
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryModel categoryDB : categoryL) {
				for(VehicleRuleModel catVehicleDB : categoryDB.vehicles) {
					catVehicleNewL.add(catVehicleDB);
					// Vehicle - Type (vehicle change == vehicle DDBB)
					if(catVehicleDB.vehicle_type.equals(vehicleModelNew.vehicle_type)) {
						statusCrud = true;
						catVehicleNewL.remove(catVehicleDB);
						catVehicleNewL.add(vehicleModelNew);
					}
				}
				if (statusCrud == true) {
					categoryDB.vehicles = catVehicleNewL;
					categoryManagerDAO.update(categoryDB);
					//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryNew);
					catVehicleNewL = new ArrayList<VehicleRuleModel>();
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
