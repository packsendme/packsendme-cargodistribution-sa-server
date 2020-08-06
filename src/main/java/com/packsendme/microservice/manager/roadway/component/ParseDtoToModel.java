package com.packsendme.microservice.manager.roadway.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.CostsModel;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;
import com.packsendme.roadway.bre.model.businessrule.CostsBRE;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.category.CategoryBRE;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Component
public class ParseDtoToModel {

	public VehicleModel vehicleDto_TO_Model(VehicleBRE vehicleBRE, VehicleModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new VehicleModel();
		}
		entity.vehicle = vehicleBRE.vehicle;
		entity.bodywork_vehicle = vehicleBRE.bodywork_vehicle;
		entity.cargo_max = vehicleBRE.cargo_max;
		entity.axis_total = vehicleBRE.axis_total;
		entity.unity_measurement_weight = vehicleBRE.unity_measurement_weight;
		entity.people = vehicleBRE.people;
		return entity;
	}
	
	public CategoryModel categoryDto_TO_Model(CategoryBRE categoryBRE, CategoryModel entity, String typeOperation) {
		VehicleModel vehicleModel = null;
		List<VehicleModel> vehicleModelL = new ArrayList<VehicleModel>();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new CategoryModel();
		}
		
		if(categoryBRE.vehicles.size() >= 1) {
			for(VehicleBRE v : categoryBRE.vehicles) {
				vehicleModel = new VehicleModel(v.vehicle, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
		}
		entity.name_category = categoryBRE.name_category;
		entity.vehicles = vehicleModelL;
		entity.weight_min = categoryBRE.weight_min;
		entity.weight_max = categoryBRE.weight_max;
		entity.axis_max = categoryBRE.axis_max;
		entity.unity_measurement_weight_min = categoryBRE.unity_measurement_weight_min;
		entity.unity_measurement_weight_max = categoryBRE.unity_measurement_weight_max;
		return entity;
	}
	
	public BodyWorkModel bodyworkDto_TO_Model(BodyworkBRE bodyworkBRE, BodyWorkModel entity, String operationType) {
		if(operationType.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new BodyWorkModel();
		}
		System.out.println(" +++++++++++++++++++++++++++++++++ ");
		System.out.println(" ------------------ "+ bodyworkBRE.bodyWork);
		System.out.println(" +++++++++++++++++++++++++++++++++ ");

		entity.bodyWork = bodyworkBRE.bodyWork;
		entity.type = bodyworkBRE.type;
		return entity;
	}
	
	
	public RoadwayModel roadwayBRE_TO_Model(RoadwayBRE roadwayBRE, RoadwayModel roadwayModel, String typeOperation) {

		List<VehicleModel> vehiclesL = new ArrayList<VehicleModel>();
		List<String> bodyWorkL = new ArrayList<String>();
		//Map<String,Map<String, CostsModel>> costsModel = new HashMap<String, Map<String, CostsModel>>();		
		Map<String,CostsModel> costs_Map = new HashMap<String, CostsModel>();
		Map<String,Map<String,CostsModel>> costsModel_Map = new HashMap<String, Map<String,CostsModel>>();

		Map<String, CostsBRE> costsBRE_Map = new HashMap<String, CostsBRE>();
		
		CostsModel costsModel = new CostsModel(); 
		VehicleModel vehicleModel = new VehicleModel();
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			roadwayModel = new RoadwayModel();
		}
		roadwayModel.rule_name = roadwayBRE.rule_name;
		roadwayModel.date_creation = roadwayBRE.date_creation;
		roadwayModel.date_change = roadwayBRE.date_change;
		roadwayModel.status = roadwayBRE.status;


		// CATEGORY - Instances
		roadwayModel.categoryInstance.name_category = roadwayBRE.categoryInstance.name_category; 
		roadwayModel.categoryInstance.weight_min = roadwayBRE.categoryInstance.weight_min;
		roadwayModel.categoryInstance.weight_max = roadwayBRE.categoryInstance.weight_max;
		roadwayModel.categoryInstance.axis_max = roadwayBRE.categoryInstance.axis_max;
		roadwayModel.categoryInstance.unity_measurement_weight_min = roadwayBRE.categoryInstance.unity_measurement_weight_min;
		roadwayModel.categoryInstance.unity_measurement_weight_max = roadwayBRE.categoryInstance.unity_measurement_weight_max;

		for(VehicleBRE v : roadwayBRE.categoryInstance.vehicles) {
			vehicleModel.vehicle = v.vehicle;
			vehicleModel.cargo_max = v.cargo_max;
			vehicleModel.axis_total = v.axis_total;
			vehicleModel.unity_measurement_weight = v.unity_measurement_weight;
			vehicleModel.people = v.people;
			
			for(String bodywork : v.bodywork_vehicle) {
				bodyWorkL.add(bodywork);
			}
			vehicleModel.bodywork_vehicle = bodyWorkL;
			vehiclesL.add(vehicleModel);
			vehicleModel = new VehicleModel();
			bodyWorkL = new ArrayList<String>();
		}
		roadwayModel.categoryInstance.vehicles = vehiclesL;
		
		// COSTS - Instances
		for(Entry<String, Map<String, CostsBRE>> entryCountry : roadwayBRE.costsInstance.entrySet()) {
			String country_key = entryCountry.getKey();
			costsBRE_Map =  roadwayBRE.costsInstance.get(country_key);
			for(Map.Entry<String,CostsBRE> entryCar : costsBRE_Map.entrySet()) {
				String car_key =  entryCar.getKey();
				CostsBRE costsBRE = entryCar.getValue();
				costsModel.weight_cost = costsBRE.weight_cost;
				costsModel.distance_cost = costsBRE.distance_cost;
				costsModel.worktime_cost = costsBRE.worktime_cost;
				costsModel.average_consumption_cost = costsBRE.average_consumption_cost;
				costsModel.rate_exchange = costsBRE.rate_exchange;
				costsModel.current_exchange = costsBRE.current_exchange;
				costs_Map.put(car_key, costsModel);
				costsModel = new CostsModel();
			}
			costsModel_Map.put(country_key, costs_Map);
			costs_Map = new HashMap<String, CostsModel>();
		}
		roadwayModel.costsInstance = costsModel_Map;
		return roadwayModel;
	}
}
