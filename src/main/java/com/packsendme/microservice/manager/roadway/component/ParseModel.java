package com.packsendme.microservice.manager.roadway.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryRuleModel;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryCostsModel;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.category.CategoryCosts;
import com.packsendme.roadway.bre.model.category.CategoryRule;
import com.packsendme.roadway.bre.model.location.Location;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleRule;

@Component
public class ParseModel {

	
	/* ==============================================
	 *  V E H I C L E  - P A R S E R  
	 * ==============================================
	 */
	
	public VehicleRuleModel parserVehicle_TO_Model(VehicleRule vehicle, VehicleRuleModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new VehicleRuleModel();
		}
		entity.vehicle = vehicle.vehicle;
		entity.bodywork_vehicle = vehicle.bodywork_vehicle;
		entity.cargo_max = vehicle.cargo_max;
		entity.axis_total = vehicle.axis_total;
		entity.unity_measurement_weight = vehicle.unity_measurement_weight;
		entity.people = vehicle.people;
		return entity;
	}
	
	
	/* ==============================================
	 *  C A T E G O R Y  - P A R S E R  
	 * ==============================================
	 */
	
	public CategoryRuleModel parserCategory_TO_Model(CategoryRule category, CategoryRuleModel categoryModel, String typeOperation) {
		VehicleRuleModel vehicleModel = null;
		List<VehicleRuleModel> vehicleModelL = new ArrayList<VehicleRuleModel>();
		List<LocationModel> locationL = new ArrayList<LocationModel>();
		
		Map<String, CategoryCosts> categoryCountryCosts_Map = new HashMap<String, CategoryCosts>();
		Map<String,CategoryCostsModel> categoryVehicleCostsModel_Map = new HashMap<String, CategoryCostsModel>();
		Map<String,Map<String, CategoryCostsModel>> categoryCountryCostsModel_Map = new HashMap<String,Map<String, CategoryCostsModel>>(); 
		CategoryCostsModel categoryCostsModel = new CategoryCostsModel();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			categoryModel = new CategoryRuleModel();
		}
		// Category-Head
		categoryModel.name_category = category.name_category;
		categoryModel.weight_min = category.weight_min;
		categoryModel.weight_max = category.weight_max;
		categoryModel.axis_max = category.axis_max;
		categoryModel.unity_measurement_weight_min = category.unity_measurement_weight_min;
		categoryModel.unity_measurement_weight_max = category.unity_measurement_weight_max;

		// Category-Vehicle
		if(category.vehicles.size() >= 1) {
			for(VehicleRule v : category.vehicles) {
				vehicleModel = new VehicleRuleModel(v.vehicle, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
			categoryModel.vehicles = vehicleModelL;
		}
		// Category-Costs
		if(category.categoryCosts.size() >= 1) {
			for(Entry<String, Map<String, CategoryCosts>> entryCountry : category.categoryCosts.entrySet()) {
				String country_key = entryCountry.getKey();
				categoryCountryCosts_Map =  category.categoryCosts.get(country_key);
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
			categoryModel.categoryCosts = categoryCountryCostsModel_Map;
		}
		// Category-Location
		if(category.locations.size() >= 1) {
			for(Location l : category.locations) {
				LocationModel locationModel = new LocationModel(l.countryName, l.cityName, l.stateName, l.codCountry);
				locationL.add(locationModel);
				locationModel = null;
			}
			categoryModel.locations = locationL;
		}
		return categoryModel;
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
}
