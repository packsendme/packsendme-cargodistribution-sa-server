package com.packsendme.microservice.manager.roadway.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryCostsModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.TransportModel;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.category.CategoryCosts;
import com.packsendme.roadway.bre.model.category.CategoryRule;
import com.packsendme.roadway.bre.model.category.CategoryType;
import com.packsendme.roadway.bre.model.location.LocationRule;
import com.packsendme.roadway.bre.model.transport.Transport;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleType;

@Component
public class ParseComponent {

	 private static final ModelMapper modelMapper = new ModelMapper();
	
	/* ==============================================
	 *  V E H I C L E  - P A R S E R  
	 * ==============================================
	 */
	
	public VehicleRuleModel parserVehicle_TO_Model(VehicleRule vehicle, VehicleRuleModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new VehicleRuleModel();
		}
		entity.vehicle_type = vehicle.vehicle_type;
		entity.bodywork_vehicle = vehicle.bodywork_vehicle;
		entity.cargo_max = vehicle.cargo_max;
		entity.axis_total = vehicle.axis_total;
		entity.unity_measurement_weight = vehicle.unity_measurement_weight;
		entity.people_transport = vehicle.people_transport; 
		entity.people = vehicle.people;
		return entity;
	}
	
	
	/* ==============================================
	 *  C A T E G O R Y    
	 * ==============================================
	 */
	
	public CategoryTypeModel parserCategoryType_TO_Model(CategoryType categoryTypeBRE, CategoryTypeModel categoryTypeModel, String typeOperation) {
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			categoryTypeModel = new CategoryTypeModel();
		}
		// Category-Head
		categoryTypeModel.name_category = categoryTypeBRE.name_category;
		categoryTypeModel.transport_type = categoryTypeBRE.transport_type;
		categoryTypeModel.weight_min = categoryTypeBRE.weight_min;
		categoryTypeModel.weight_max = categoryTypeBRE.weight_max;
		categoryTypeModel.unity_measurement_weight_min = categoryTypeBRE.unity_measurement_weight_min;
		categoryTypeModel.unity_measurement_weight_max = categoryTypeBRE.unity_measurement_weight_max;
		return categoryTypeModel;
	}
	
	public List<CategoryRule> parserCategoryModel_TO_BRE(List<CategoryRuleModel> categoriesRules) {
		List<CategoryRule> categoryRuleBRE_L = new ArrayList<CategoryRule>();

		for(CategoryRuleModel model : categoriesRules) {
			CategoryRule categoryRuleBRE_Obj = new CategoryRule(); 
			
			categoryRuleBRE_Obj.id = model.id;
			
			// Category-Type (Convert:  CategoryType Model to CategoryType BRE)
			CategoryType categoryTypeBRE = modelMapper.map(model.categoryType, CategoryType.class);
			categoryRuleBRE_Obj.categoryType = categoryTypeBRE;


			// Category-Vehicle
			List<VehicleRule> vehicles_BRE = new ArrayList<VehicleRule>();
			if(model.vehicles.size() >= 1) {
				for(VehicleRuleModel v : model.vehicles) {
					VehicleRule vehicleOjb_BRE = new VehicleRule(v.vehicle_type, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people_transport, v.people);
					vehicles_BRE.add(vehicleOjb_BRE);
					vehicleOjb_BRE = null;
				}
				categoryRuleBRE_Obj.vehicles = vehicles_BRE;
			}
			
			// Category-Location (Convert: Model to BRE)
			List<LocationRule> locations_BRE = new ArrayList<LocationRule>();
			if(model.locations.size() >= 1) {
				for(LocationModel l : model.locations) {
					LocationRule locationObj_BRE = new LocationRule(l.countryName, l.cityName, l.stateName, l.codCountry);
					locations_BRE.add(locationObj_BRE);
					locationObj_BRE = null;
				} 
				categoryRuleBRE_Obj.locations = locations_BRE;
			}

			
			// Category-Costs (Convert: Model to BRE)
			List<CategoryCosts> categoryCostsL_BRE = new ArrayList<CategoryCosts>();
			CategoryCosts catCostsObj_BRE = null;
			if(model.categoryCosts.size() >= 1) {
				for(Entry<String, List<CategoryCostsModel>> costsMap_Model:  model.categoryCosts.entrySet()) {
					List<CategoryCostsModel> catCostsL_Model = costsMap_Model.getValue();
					for(CategoryCostsModel costsModel_Obj : catCostsL_Model) {
						catCostsObj_BRE = new CategoryCosts();
						catCostsObj_BRE.weight_cost = costsModel_Obj.weight_cost;
						catCostsObj_BRE.vehicle = costsModel_Obj.vehicle;
						catCostsObj_BRE.distance_cost = costsModel_Obj.distance_cost;
						catCostsObj_BRE.worktime_cost = costsModel_Obj.worktime_cost;
						catCostsObj_BRE.average_consumption_cost = costsModel_Obj.average_consumption_cost;
						catCostsObj_BRE.rate_exchange = costsModel_Obj.rate_exchange;
						catCostsObj_BRE.current_exchange = costsModel_Obj.current_exchange;
						catCostsObj_BRE.countryName = costsModel_Obj.countryName;
						categoryCostsL_BRE.add(catCostsObj_BRE);
					}
				}
				categoryRuleBRE_Obj.categoryCosts = categoryCostsL_BRE;
			}
			categoryRuleBRE_L.add(categoryRuleBRE_Obj);
			categoryRuleBRE_Obj = new CategoryRule();
		}
		return categoryRuleBRE_L;
	}
	
	public CategoryRuleModel parserCategory_TO_Model(CategoryRule categoryRuleBRE, CategoryRuleModel categoryRuleModel, String typeOperation) {
		CategoryTypeModel categoryTypeModel = null;
		List<VehicleRuleModel> vehicleModelL = new ArrayList<VehicleRuleModel>();
		List<LocationModel> locationL = new ArrayList<LocationModel>();
		
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			categoryRuleModel = new CategoryRuleModel();
		}
		// Category-Type (Update in CategoryRule class - no trigger method)
		categoryTypeModel = new CategoryTypeModel(categoryRuleBRE.categoryType.name_category, categoryRuleBRE.categoryType.transport_type, 
				categoryRuleBRE.categoryType.weight_min, categoryRuleBRE.categoryType.weight_max, categoryRuleBRE.categoryType.unity_measurement_weight_min, 
				categoryRuleBRE.categoryType.unity_measurement_weight_max);
		
		categoryRuleModel.categoryType = categoryTypeModel;

		// Category-Vehicle
		VehicleRuleModel vehicleModel = null;
		if(categoryRuleBRE.vehicles.size() >= 1) {
			for(VehicleRule v : categoryRuleBRE.vehicles) {
				vehicleModel = new VehicleRuleModel(v.vehicle_type, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people_transport, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
			categoryRuleModel.vehicles = vehicleModelL;
		}
		
		System.out.println(" ===================================================");
		System.out.println(" TOTAL SIZE => vehicles :: "+ categoryRuleModel.vehicles.size());
		System.out.println(" ===================================================");

		
		// Category-Location
		if(categoryRuleBRE.locations.size() >= 1) {
			for(LocationRule l : categoryRuleBRE.locations) {
				LocationModel locationModel = new LocationModel(l.countryName, l.cityName, l.stateName, l.codCountry);
				locationL.add(locationModel);
				locationModel = null;
				System.out.println(" TOTAL locations :: "+ l.countryName);

			}
			categoryRuleModel.locations = locationL;
			System.out.println(" ===================================================");
			System.out.println(" TOTAL SIZE => locations :: "+ locationL.size());
			System.out.println(" ===================================================");
			
		}
		
		// Interation :: Category-Costs
		CategoryCostsModel costsModelObj = new CategoryCostsModel();
		List<CategoryCostsModel> categoriesCostsL = new ArrayList<CategoryCostsModel>();
		Map<String, List<CategoryCostsModel>> categoryCostsModel_Map = new HashMap<String, List<CategoryCostsModel>>();
		//List<Map<String,List<CategoryCosts>>> categoryCosts
		System.out.println(" ================= CategoryCosts ==================================");
		
		if(categoryRuleBRE.categoryCosts.size() >= 1) {
			for(LocationRule locObj : categoryRuleBRE.locations) {
				for(CategoryCosts categorycostsBREObj : categoryRuleBRE.categoryCosts){
					costsModelObj = new CategoryCostsModel();
					if(locObj.countryName.equals(categorycostsBREObj.countryName)) {
						costsModelObj.countryName = categorycostsBREObj.countryName;
						costsModelObj.vehicle = categorycostsBREObj.vehicle;
						costsModelObj.weight_cost = categorycostsBREObj.weight_cost;
						costsModelObj.distance_cost = categorycostsBREObj.distance_cost;
						costsModelObj.worktime_cost = categorycostsBREObj.worktime_cost;
						costsModelObj.average_consumption_cost = categorycostsBREObj.average_consumption_cost;
						costsModelObj.rate_exchange = categorycostsBREObj.rate_exchange;
						costsModelObj.current_exchange = categorycostsBREObj.current_exchange;
						categoriesCostsL.add(costsModelObj);
						
						
						System.out.println(" OBJ => CategoryCosts "+ costsModelObj.countryName);
						System.out.println(" OBJ => CategoryCosts "+ costsModelObj.vehicle);
						System.out.println(" OBJ => CategoryCosts "+ categoriesCostsL.size());

					}
				}
				System.out.println(" TOTAL SIZE => CategoryCosts "+ locObj.countryName);
				System.out.println(" TOTAL SIZE => CategoryCosts "+ categoriesCostsL.size());

				categoryCostsModel_Map.put(locObj.countryName, categoriesCostsL);
				categoriesCostsL = new ArrayList<CategoryCostsModel>();
			}
			categoryRuleModel.categoryCosts = categoryCostsModel_Map;
		}
		System.out.println(" ===================================================");

		return categoryRuleModel;
	}
	
	/* ==============================================
	 *  L O C A T I O N  - P A R S E R  
	 * ==============================================
	 */
	public LocationModel parserLocation_TO_Model(LocationRule location, LocationModel entity, String operationType) {
		
		if(operationType.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new LocationModel();
		}
		entity.countryName = location.countryName;
		entity.cityName = location.cityName;
		entity.stateName = location.stateName;
		entity.codCountry = location.codCountry;
		return entity;
	}
	
	/* ==============================================
	 *  B O D Y  W O R K   - P A R S E R  
	 * ==============================================
	 */
	
	public BodyWorkModel parserBodywork_TO_Model(BodyworkRule bodywork, BodyWorkModel entity, String operationType) {
		if(operationType.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new BodyWorkModel();
		}
		entity.bodyWork = bodywork.bodyWork;
		entity.type = bodywork.type;
		return entity;
	}
	

	/* ==============================================
	 *  R O A D W A Y - BRE - P A R S E R  
	 * ==============================================
	 */
	
	public RoadwayModel parserRoadway_TO_Model(RoadwayBRE roadwayBRE, RoadwayModel roadwayModel, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			roadwayModel = new RoadwayModel();
		}
		roadwayModel.rule_name = roadwayBRE.rule_name;
		roadwayModel.date_creation = roadwayBRE.date_creation;
		roadwayModel.date_change = roadwayBRE.date_change;
		roadwayModel.status = roadwayBRE.status;
		
		CategoryRuleModel categoryRuleModel = parserCategory_TO_Model(roadwayBRE.category.categoryRule, roadwayModel.categoryRule, typeOperation);
		roadwayModel.categoryRule = categoryRuleModel;
		
		return roadwayModel;
	}
	
	/* ==============================================
	 *  V E H I C L E - TYPE  - P A R S E R  
	 * ==============================================
	 */
	
	public VehicleTypeModel parserVehicleType_TO_Model(VehicleType vehicleBRE, VehicleTypeModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new VehicleTypeModel();
		}
		entity.type_vehicle = vehicleBRE.type_vehicle;
		return entity;
	}
	
	/* ==============================================
	 * U N I T Y  - P A R S E R  
	 * ==============================================
	 */
	
	public UnityMeasurementModel parserUnityMeasurement_TO_Model(UnityMeasurementDTO unityDTO, UnityMeasurementModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new UnityMeasurementModel();
		}
		entity.unitMeasurement = unityDTO.unitMeasurement;
		entity.origin_country = unityDTO.origin_country;
		return entity;
	}
	
	/* ==============================================
	 * T R A N S P O R T  - P A R S E R  
	 * ==============================================
	 */
	
	public TransportModel parserTransport_TO_Model(Transport tranportDTO, TransportModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new TransportModel();
		}
		entity.name_transport = tranportDTO.name_transport;
		return entity;
	}
}
