package com.packsendme.microservice.manager.roadway.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.repository.BodyWork_Model;
import com.packsendme.microservice.manager.roadway.repository.Category_Model;
import com.packsendme.microservice.manager.roadway.repository.BusinessRule_Model;
import com.packsendme.microservice.manager.roadway.repository.VehicleCosts_Model;
import com.packsendme.microservice.manager.roadway.repository.Vehicle_Model;
import com.packsendme.roadway.bre.model.businessrule.BusinessRuleRoadwayBRE;
import com.packsendme.roadway.bre.model.businessrule.VehicleCostsBRE;
import com.packsendme.roadway.bre.model.category.CategoryBRE;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Component
public class ParseDtoToModel {

	public Vehicle_Model vehicleDto_TO_Model(VehicleBRE vehicleBRE, Vehicle_Model entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new Vehicle_Model();
		}
		entity.vehicle = vehicleBRE.vehicle;
		entity.bodywork_vehicle = vehicleBRE.bodywork_vehicle;
		entity.cargo_max = vehicleBRE.cargo_max;
		entity.axis_total = vehicleBRE.axis_total;
		entity.unity_measurement_weight = vehicleBRE.unity_measurement_weight;
		entity.people = vehicleBRE.people;
		return entity;
	}
	
	public Category_Model categoryDto_TO_Model(CategoryBRE categoryBRE, Category_Model entity, String typeOperation) {
		Vehicle_Model vehicleModel = null;
		List<Vehicle_Model> vehicleModelL = new ArrayList<Vehicle_Model>();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new Category_Model();
		}
		
		if(categoryBRE.vehicleL.size() >= 1) {
			for(VehicleBRE v : categoryBRE.vehicleL) {
				vehicleModel = new Vehicle_Model(v.vehicle, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
		}
		entity.name_category = categoryBRE.name_category;
		entity.vehicle_ModelL = vehicleModelL;
		entity.weight_min = categoryBRE.weight_min;
		entity.weight_max = categoryBRE.weight_max;
		entity.axis_max = categoryBRE.axis_max;
		entity.unity_measurement_weight_min = categoryBRE.unity_measurement_weight_min;
		entity.unity_measurement_weight_max = categoryBRE.unity_measurement_weight_max;
		return entity;
	}
	
	public BodyWork_Model bodyworkDto_TO_Model(BodyworkBRE bodyworkBRE, BodyWork_Model entity, String operationType) {
		if(operationType.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new BodyWork_Model();
		}
		System.out.println(" +++++++++++++++++++++++++++++++++ ");
		System.out.println(" ------------------ "+ bodyworkBRE.bodyWork);
		System.out.println(" +++++++++++++++++++++++++++++++++ ");

		entity.bodyWork = bodyworkBRE.bodyWork;
		entity.bodyWork = bodyworkBRE.type;
		return entity;
	}
	
	
	public BusinessRule_Model roadwayBRE_TO_Model(BusinessRuleRoadwayBRE roadwayBRE, BusinessRule_Model roadwayModel, String typeOperation) {
		List<Vehicle_Model> vehicleInstanceL = new ArrayList<Vehicle_Model>();
		List<String> bodyWorkL = new ArrayList<String>();
		Map<String,Map<String, VehicleCosts_Model>> costsModel = new HashMap<String, Map<String, VehicleCosts_Model>>();		
		Map<String,VehicleCosts_Model> vehicleCostsModel = new HashMap<String, VehicleCosts_Model>();
		Map<String, VehicleCostsBRE> vehiCosts = new HashMap<String, VehicleCostsBRE>();
		VehicleCosts_Model vehicleCostsObj = new VehicleCosts_Model(); 
		Vehicle_Model vehicleModel = new Vehicle_Model();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			roadwayModel = new BusinessRule_Model();
		}

		roadwayModel.rule_name = roadwayBRE.rule_name;
		roadwayModel.category_name = roadwayBRE.category_name;
		roadwayModel.date_creation = roadwayBRE.date_creation;
		roadwayModel.date_change = roadwayBRE.date_change;
		roadwayModel.status = roadwayBRE.status;

		// Vehicle Instance
		for(VehicleBRE v : roadwayBRE.vehicleInstance) {
			vehicleModel.vehicle = v.vehicle;
			vehicleModel.cargo_max = v.cargo_max;
			vehicleModel.axis_total = v.axis_total;
			vehicleModel.unity_measurement_weight = v.unity_measurement_weight;
			vehicleModel.people = v.people;
			
			for(String bodywork : v.bodywork_vehicle) {
				bodyWorkL.add(bodywork);
			}
			vehicleModel.bodywork_vehicle = bodyWorkL;
			vehicleInstanceL.add(vehicleModel);
			vehicleModel = new Vehicle_Model(); 
		}
		roadwayModel.vehicleInstance = vehicleInstanceL;
		
		// Vehicle Costs
		for(Map.Entry<String,Map<String, VehicleCostsBRE>> entryCountry : roadwayBRE.vehicleCosts.entrySet()) {
			String country_key = entryCountry.getKey();
			vehiCosts =  roadwayBRE.vehicleCosts.get(country_key);
			for(Map.Entry<String,VehicleCostsBRE> entryCar : vehiCosts.entrySet()) {
				String car_key =  entryCar.getKey();
				VehicleCostsBRE vehicleCostsBRE = entryCar.getValue();
				vehicleCostsObj.weight_cost = vehicleCostsBRE.weight_cost;
				vehicleCostsObj.distance_cost = vehicleCostsBRE.distance_cost;
				vehicleCostsObj.worktime_cost = vehicleCostsBRE.worktime_cost;
				vehicleCostsObj.average_consumption_cost = vehicleCostsBRE.average_consumption_cost;
				vehicleCostsObj.rate_exchange = vehicleCostsBRE.rate_exchange;
				vehicleCostsObj.current_exchange = vehicleCostsBRE.current_exchange;
				vehicleCostsModel.put(car_key, vehicleCostsObj);
				vehicleCostsObj = new VehicleCosts_Model();
			}
			costsModel.put(country_key, vehicleCostsModel);
			vehicleCostsModel = new HashMap<String, VehicleCosts_Model>();
		}
		roadwayModel.vehicleCosts = costsModel;
		return roadwayModel;
	}
}
