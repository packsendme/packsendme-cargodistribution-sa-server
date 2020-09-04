package com.packsendme.microservice.manager.roadway.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryCostsModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryTypeModel;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.category.CategoryCosts;
import com.packsendme.roadway.bre.model.category.CategoryRule;
import com.packsendme.roadway.bre.model.category.CategoryType;
import com.packsendme.roadway.bre.model.location.LocationRule;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleType;

@Component
public class ParseComponent {

	
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
	
	public CategoryRuleModel parserCategoryRule_TO_Model(CategoryRule categoryBRE, CategoryRuleModel categoryRuleModel, String typeOperation) {
		VehicleRuleModel vehicleModel = null;
		CategoryTypeModel categoryTypeModel = null;
		List<VehicleRuleModel> vehicleModelL = new ArrayList<VehicleRuleModel>();
		List<LocationModel> locationL = new ArrayList<LocationModel>();
		
		Map<String, CategoryCosts> categoryCountryCosts_Map = new HashMap<String, CategoryCosts>();
		Map<String,CategoryCostsModel> categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
		Map<String,Map<String, CategoryCostsModel>> categoryCountryCostsModel_Map = new HashMap<String,Map<String, CategoryCostsModel>>(); 
		CategoryCostsModel categoryCostsModel = new CategoryCostsModel();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			categoryRuleModel = new CategoryRuleModel();
		}
		// Category-Type (Update in CategoryRule class - no trigger method)
		categoryTypeModel = new CategoryTypeModel(categoryBRE.categoryType.name_category, categoryBRE.categoryType.transport_type, 
				categoryBRE.categoryType.weight_min, categoryBRE.categoryType.weight_max, categoryBRE.categoryType.unity_measurement_weight_min, 
				categoryBRE.categoryType.unity_measurement_weight_max);
		
		categoryRuleModel.type_category = categoryTypeModel;

		// Category-Vehicle
		if(categoryBRE.vehicles.size() >= 1) {
			for(VehicleRule v : categoryBRE.vehicles) {
				vehicleModel = new VehicleRuleModel(v.vehicle_type, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
			categoryRuleModel.vehicles = vehicleModelL;
		}
		// Category-Costs
		if(categoryBRE.categoryCosts.size() >= 1) {
			for(Entry<String, Map<String, CategoryCosts>> entryCountry : categoryBRE.categoryCosts.entrySet()) {
				String country_key = entryCountry.getKey();
				categoryCountryCosts_Map =  categoryBRE.categoryCosts.get(country_key);
				for(Map.Entry<String,CategoryCosts> entryVehicle : categoryCountryCosts_Map.entrySet()) {
					String vehicle_key =  entryVehicle.getKey();
					CategoryCosts costsObj = entryVehicle.getValue();
					categoryCostsModel.weight_cost = costsObj.weight_cost;
					categoryCostsModel.distance_cost = costsObj.distance_cost;
					categoryCostsModel.worktime_cost = costsObj.worktime_cost;
					categoryCostsModel.average_consumption_cost = costsObj.average_consumption_cost;
					categoryCostsModel.rate_exchange = costsObj.rate_exchange;
					categoryCostsModel.current_exchange = costsObj.current_exchange;
					categoryVehicleCostsModel_Map.put(vehicle_key, categoryCostsModel);
					categoryCostsModel = new CategoryCostsModel();
				}
				categoryCountryCostsModel_Map.put(country_key, categoryVehicleCostsModel_Map);
				categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
			}
			categoryRuleModel.categoryCosts = categoryCountryCostsModel_Map;
		}
		// Category-Location
		if(categoryBRE.locations.size() >= 1) {
			for(LocationRule l : categoryBRE.locations) {
				LocationModel locationModel = new LocationModel(l.countryName, l.cityName, l.stateName, l.codCountry);
				locationL.add(locationModel);
				locationModel = null;
			}
			categoryRuleModel.locations = locationL;
		}
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
		
		CategoryRuleModel categoryRuleModel = parserCategoryRule_TO_Model(roadwayBRE.category.categoryRule, roadwayModel.categoryRule, typeOperation);
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
}
