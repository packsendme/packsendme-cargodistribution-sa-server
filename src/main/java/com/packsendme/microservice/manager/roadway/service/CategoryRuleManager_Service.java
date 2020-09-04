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
			CategoryRuleListDTO_Response categoryListDTO_Response = new CategoryRuleListDTO_Response(categoryManagerDAO.findAll());
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
			CategoryRuleModel entity = parserObj.parserCategoryRule_TO_Model(category, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
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
				roadwayManager.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, categoryEntity);
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
				String categoryName_old = categoryEntity.type_category.name_category; 
				categoryEntity = parserObj.parserCategoryRule_TO_Model(categoryBRE, categoryEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				categoryManagerDAO.update(categoryEntity);

				// Trigger Method - Update Roadway-Entity
				roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryEntity);
				
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
			String categoryName_old = categoryEntity.type_category.name_category; 
			
			if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
				ResponseEntity<?> responseEntity = deleteCategory(categoryEntity.id);
				return responseEntity;
			}
			else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
				categoryEntity.type_category = categoryTypeModel;
				categoryManagerDAO.update(categoryEntity);
				// Trigger Method - Update Roadway-Entity
				ResponseEntity<?> responseEntity = roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryEntity);
				return responseEntity;
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
		List<VehicleRuleModel> catVehicleNewL = new ArrayList<VehicleRuleModel>();
		Response<CategoryRuleModel> responseObj = null;
		boolean statusCrud = false;
		Map<String,CategoryCostsModel> categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
		Map<String,Map<String, CategoryCostsModel>> categoryCountryCostsModel_Map = new HashMap<String,Map<String, CategoryCostsModel>>(); 
		
		// Find Category relationship with Vehicle will be removed
		try {
			List<CategoryRuleModel> categoryL = categoryManagerDAO.findAll();
			for(CategoryRuleModel category_old : categoryL) {
				for(VehicleRuleModel catVehicleDB : category_old.vehicles) {
					if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
						if(!catVehicleDB.vehicle_type.equals(vehicleModelNew.vehicle_type)) {
							catVehicleNewL.add(catVehicleDB);
							
							// Category-Costs
							if(category_old.categoryCosts.size() >= 1) {
								for(Entry<String, Map<String, CategoryCostsModel>> entryCountry : category_old.categoryCosts.entrySet()) {
									String country_key = entryCountry.getKey();
									Map<String, CategoryCostsModel> categoryCountryCosts_Map = category_old.categoryCosts.get(country_key);
									for(Map.Entry<String,CategoryCostsModel> entryVehicle : categoryCountryCosts_Map.entrySet()) {
										String vehicle_key =  entryVehicle.getKey();
										if(!vehicle_key.equals(vehicleModelNew.vehicle_type)) {
											CategoryCostsModel costsObj = entryVehicle.getValue();
											categoryVehicleCostsModel_Map.put(vehicle_key, costsObj);
										}
									}
									categoryCountryCostsModel_Map.put(country_key, categoryVehicleCostsModel_Map);
									categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
								}
							}
						}
						else {
							statusCrud = true;
						}

					} 
					else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
						if(catVehicleDB.vehicle_type.equals(vehicleS_Old)) {
							catVehicleNewL.add(vehicleModelNew);
							statusCrud = true;
							
							// Category-Costs
							if(category_old.categoryCosts.size() >= 1) {
								for(Entry<String, Map<String, CategoryCostsModel>> entryCountry : category_old.categoryCosts.entrySet()) {
									String country_key = entryCountry.getKey();
									Map<String, CategoryCostsModel> categoryCountryCosts_Map = category_old.categoryCosts.get(country_key);
									for(Map.Entry<String,CategoryCostsModel> entryVehicle : categoryCountryCosts_Map.entrySet()) {
										String vehicle_key =  entryVehicle.getKey();
										System.out.println("================================================");
										System.out.println("categoryCosts UPDATE-Y vehicle_key   "+ vehicle_key);
										System.out.println("categoryCosts UPDATE-Y vehicleS_Old   "+ vehicleS_Old);

										if(vehicle_key.equals(vehicleS_Old)) {
											CategoryCostsModel costsObj = entryVehicle.getValue();
											categoryVehicleCostsModel_Map.put(vehicleModelNew.vehicle_type, costsObj);
											System.out.println("categoryCosts KEY CHANGE   "+ vehicleModelNew.vehicle_type);
										}
									}
									categoryCountryCostsModel_Map.put(country_key, categoryVehicleCostsModel_Map);
									categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
								}
							}
						}
						else {
							catVehicleNewL.add(catVehicleDB);
							// Category-Costs
							if(category_old.categoryCosts.size() >= 1) {
								for(Entry<String, Map<String, CategoryCostsModel>> entryCountry : category_old.categoryCosts.entrySet()) {
									String country_key = entryCountry.getKey();
									Map<String, CategoryCostsModel> categoryCountryCosts_Map = category_old.categoryCosts.get(country_key);
									for(Map.Entry<String,CategoryCostsModel> entryVehicle : categoryCountryCosts_Map.entrySet()) {
										String vehicle_key =  entryVehicle.getKey();
										System.out.println("categoryCosts UPDATE-N vehicle_key   "+ vehicle_key);
										System.out.println("categoryCosts UPDATE-N vehicleS_Old   "+ vehicleS_Old);

										if(!vehicle_key.equals(vehicleS_Old)) {
											CategoryCostsModel costsObj = entryVehicle.getValue();
											categoryVehicleCostsModel_Map.put(vehicle_key, costsObj);
											CategoryCostsModel costsNewVehicleObj = new CategoryCostsModel(0.0, 0.0, 0.0, 0.0, 0.0, "0.0");
											categoryVehicleCostsModel_Map.put(vehicleModelNew.vehicle_type, costsNewVehicleObj);
											System.out.println("categoryCosts NO CHANGE   "+ vehicle_key);
										}
									}
									categoryCountryCostsModel_Map.put(country_key, categoryVehicleCostsModel_Map);
									categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
								}
							}
						}
					}
				}
				if (statusCrud == true) {
					CategoryRuleModel categoryNew = category_old; 
					String categoryName_old = category_old.type_category.name_category;
					categoryManagerDAO.remove(category_old);
					categoryNew.vehicles = null;
					categoryNew.vehicles = catVehicleNewL;
					categoryNew.categoryCosts = null;
					categoryNew.categoryCosts = categoryCountryCostsModel_Map;
					categoryManagerDAO.save(categoryNew);
					System.out.println("================================================");

					roadwayManager.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, categoryName_old, categoryNew);
					catVehicleNewL = new ArrayList<VehicleRuleModel>();
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