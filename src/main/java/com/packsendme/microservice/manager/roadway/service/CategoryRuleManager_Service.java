 package com.packsendme.microservice.manager.roadway.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import com.packsendme.microservice.manager.roadway.repository.CategoryCostsModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.roadway.bre.model.category.CategoryRule;
import com.packsendme.roadway.bre.model.category.CategoryType;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class CategoryRuleManager_Service {
	
	@Autowired
	private CategoryRuleDAO categoryManagerDAO;
	@Autowired
	private RoadwayManager_Service roadwayManager; 
	
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findCategoryAll() {
		Response<CategoryRuleListDTO_Response> responseObj = null;
		try {
			List<CategoryRuleModel> categoriesRulesModel_L = categoryManagerDAO.findAll();
			List<CategoryRule> categoriesRulesBRE_L =  parserObj.parserCategoryModel_TO_BRE(categoriesRulesModel_L);
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
	
	public ResponseEntity<?> saveCategory(CategoryRule category) {
		Response<CategoryRuleModel> responseObj = null;
		try {
			System.out.println(" saveCategory "+ category);
			System.out.println(" categoryType "+ category.categoryType.name_category);
			System.out.println(" categoryCosts "+ category.categoryCosts.size());
			System.out.println(" vehicles "+ category.vehicles.size());
			
			CategoryRuleModel entity = parserObj.parserCategory_TO_Model(category, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			categoryManagerDAO.save(entity);
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.FOUND_CATEGORY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteCategory(String id) {
		Response<CategoryRuleModel> responseObj = null;
		try {
			Optional<CategoryRuleModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryRuleModel categoryEntity = categoryData.get(); 
				categoryManagerDAO.remove(categoryEntity);
				//roadwayManager.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, categoryEntity);
				responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), categoryData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.DELETE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.NOT_FOUND_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> updateCategory(String id, CategoryRule categoryBRE) {
		Response<CategoryRuleModel> responseObj = null;
		try {
			Optional<CategoryRuleModel> categoryData = categoryManagerDAO.findOneById(id);
			if(categoryData.isPresent()) {
				CategoryRuleModel categoryEntity = categoryData.get(); 
				String categoryName_old = categoryEntity.categoryType.name_category; 
				CategoryRuleModel entity = parserObj.parserCategory_TO_Model(categoryBRE, categoryEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryManagerDAO.update(entity);

				// Trigger Method - Update Roadway-Entity
				//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryEntity);
				
				responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), categoryEntity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/*******************************************************************************************************************
	 * CRUD-TRIGGER 
	 ********************************************************************************************************************/
	
	public ResponseEntity<?> crudTrigger_FromCategoryType(String operationType, CategoryType categoryTypeBRE, CategoryTypeModel categoryTypeModel) {
		Response<CategoryRuleModel> responseObj = null;
		try {
			CategoryRuleModel categoryEntity = categoryManagerDAO.findOneByName(categoryTypeBRE.name_category);
			String categoryName_old = categoryEntity.categoryType.name_category; 
			
			if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
				ResponseEntity<?> responseEntity = deleteCategory(categoryEntity.id);
				return responseEntity;
			}
			else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
				categoryEntity.categoryType = categoryTypeModel;
				categoryManagerDAO.update(categoryEntity);
				// Trigger Method - Update Roadway-Entity
				//ResponseEntity<?> responseEntity = roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryEntity);
				//return responseEntity;
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	public ResponseEntity<?> crudTrigger(String operationType, String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		Response<CategoryRuleModel> responseObj = null;
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
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTriggerDelete(String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		List<VehicleRuleModel> catVehicleNewL = new ArrayList<VehicleRuleModel>();
		List<CategoryCostsModel> catCostsL = new ArrayList<CategoryCostsModel>();
		Map<String,List<CategoryCostsModel>> catCostsVehicleM = new HashMap<String, List<CategoryCostsModel>>();
		
		Response<CategoryRuleModel> responseObj = null;
		boolean statusCrud = false;
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryRuleModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryRuleModel categoryDB : categoryL) {
				for(VehicleRuleModel catVehicleDB : categoryDB.vehicles) {
					catVehicleNewL.add(catVehicleDB);
					// Vehicle - Type (vehicle change == vehicle DDBB)
					if(catVehicleDB.vehicle_type.equals(vehicleModelNew.vehicle_type)) {
						statusCrud = true;
						catVehicleNewL.remove(catVehicleDB);
						// Costs - Vehicle
						if(categoryDB.categoryCosts.size() >= 1) {
							for(Entry<String, List<CategoryCostsModel>> entryCountry : categoryDB.categoryCosts.entrySet()) {
								String country_key = entryCountry.getKey();
								List <CategoryCostsModel> categoryVehicleCostsL =  categoryDB.categoryCosts.get(country_key);
								for(CategoryCostsModel costsObj : categoryVehicleCostsL) {
									catCostsL.add(costsObj);
									if(costsObj.vehicle.equals(vehicleModelNew.vehicle_type)) {
										catCostsL.remove(costsObj);
									}
								}
								catCostsVehicleM.put(country_key, catCostsL);
								catCostsL = new ArrayList<CategoryCostsModel>();
							}
						}
					}
				}
				if (statusCrud == true) {
					categoryDB.vehicles = catVehicleNewL;
					categoryDB.categoryCosts = catCostsVehicleM;
					categoryManagerDAO.update(categoryDB);

					//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryNew);
					catVehicleNewL = new ArrayList<VehicleRuleModel>();
					catCostsVehicleM = new HashMap<String, List<CategoryCostsModel>>();
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTriggerUpdate(String vehicleS_Old, VehicleRuleModel vehicleModelNew) {
		List<VehicleRuleModel> catVehicleNewL = new ArrayList<VehicleRuleModel>();
		List<CategoryCostsModel> catCostsL = new ArrayList<CategoryCostsModel>();
		Map<String,List<CategoryCostsModel>> catCostsVehicleM = new HashMap<String, List<CategoryCostsModel>>();
		
		Response<CategoryRuleModel> responseObj = null;
		boolean statusCrud = false;
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryRuleModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryRuleModel categoryDB : categoryL) {
				for(VehicleRuleModel catVehicleDB : categoryDB.vehicles) {
					catVehicleNewL.add(catVehicleDB);
					// Vehicle - Type (vehicle change == vehicle DDBB)
					if(catVehicleDB.vehicle_type.equals(vehicleModelNew.vehicle_type)) {
						statusCrud = true;
						catVehicleNewL.remove(catVehicleDB);
						catVehicleNewL.add(vehicleModelNew);
						// Costs - Vehicle
						if(categoryDB.categoryCosts.size() >= 1) {
							for(Entry<String, List<CategoryCostsModel>> entryCountry : categoryDB.categoryCosts.entrySet()) {
								String country_key = entryCountry.getKey();
								List <CategoryCostsModel> categoryVehicleCostsL =  categoryDB.categoryCosts.get(country_key);
								for(CategoryCostsModel costsObj : categoryVehicleCostsL) {
									catCostsL.add(costsObj);
									if(costsObj.vehicle.equals(vehicleModelNew.vehicle_type)) {
										catCostsL.remove(costsObj);
										CategoryCostsModel costsNew = costsObj;
										costsNew.vehicle = vehicleModelNew.vehicle_type;
										catCostsL.add(costsNew);
									}
								}
								catCostsVehicleM.put(country_key, catCostsL);
								catCostsL = new ArrayList<CategoryCostsModel>();
							}
						}
					}
				}
				if (statusCrud == true) {
					categoryDB.vehicles = catVehicleNewL;
					categoryDB.categoryCosts = catCostsVehicleM;
					categoryManagerDAO.update(categoryDB);

					//roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryNew);
					catVehicleNewL = new ArrayList<VehicleRuleModel>();
					catCostsVehicleM = new HashMap<String, List<CategoryCostsModel>>();
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_CATEGORY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
