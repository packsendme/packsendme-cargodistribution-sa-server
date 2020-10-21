package com.packsendme.microservice.manager.roadway.component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.CostsModel;
import com.packsendme.microservice.manager.roadway.repository.InitialsModel;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.microservice.manager.roadway.repository.TransportModel;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.businessrule.RoadwayCosts;
import com.packsendme.roadway.bre.model.category.Category;
import com.packsendme.roadway.bre.model.location.Location;
import com.packsendme.roadway.bre.model.transport.Initials;
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
	
	public List<Category> parserCategoryModel_TO_BRE(List<CategoryModel> categoriesRules) {
		List<Category> categoryRuleBRE_L = new ArrayList<Category>();

		for(CategoryModel model : categoriesRules) {
			Category categoryBRE_Obj = new Category(); 
			categoryBRE_Obj.id = model.id;
			categoryBRE_Obj.name_category = model.name_category;
			categoryBRE_Obj.transport = model.transport;
			categoryBRE_Obj.weight_min = model.weight_min;
			categoryBRE_Obj.weight_max = model.weight_max;
			categoryBRE_Obj.unity_measurement_weight_min = model.unity_measurement_weight_min;
			categoryBRE_Obj.unity_measurement_weight_max = model.unity_measurement_weight_max;
 
			// Category-Vehicle
			List<VehicleRule> vehicles_BRE = new ArrayList<VehicleRule>();
			if(model.vehicles.size() >= 1) {
				for(VehicleRuleModel v : model.vehicles) {
					VehicleRule vehicleOjb_BRE = new VehicleRule(v.vehicle_type, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people_transport, v.people);
					vehicles_BRE.add(vehicleOjb_BRE);
					vehicleOjb_BRE = null;
				}
				categoryBRE_Obj.vehicles = vehicles_BRE;
			}
			categoryRuleBRE_L.add(categoryBRE_Obj);
		}
		return categoryRuleBRE_L;
	}
	
	public CategoryModel parserCategoryBRE_TO_Model(Category categoryBRE, CategoryModel categoryModel, String typeOperation) {
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			categoryModel = new CategoryModel();
		}
		
		// Category-Head
		categoryModel.name_category = categoryBRE.name_category;
		categoryModel.transport = categoryBRE.transport;
		categoryModel.weight_min = categoryBRE.weight_min;
		categoryModel.weight_max = categoryBRE.weight_max;
		categoryModel.unity_measurement_weight_min = categoryBRE.unity_measurement_weight_min;
		categoryModel.unity_measurement_weight_max = categoryBRE.unity_measurement_weight_max;

		// Category-Vehicle
		List<VehicleRuleModel> vehicleModelL = new ArrayList<VehicleRuleModel>();
		VehicleRuleModel vehicleModel = null;
		if(categoryBRE.vehicles.size() >= 1) {
			for(VehicleRule v : categoryBRE.vehicles) {
				vehicleModel = new VehicleRuleModel(v.vehicle_type, v.bodywork_vehicle, v.cargo_max, v.axis_total, v.unity_measurement_weight, v.people_transport, v.people);
				vehicleModelL.add(vehicleModel);
				vehicleModel = null;
			}
			categoryModel.vehicles = vehicleModelL;
		}
		return categoryModel;
	}
	
	/* ==============================================
	 *  L O C A T I O N  - P A R S E R  
	 * ==============================================
	 */
	public LocationModel parserLocationBRE_TO_Model(Location locationBRE, LocationModel entity, String operationType) {
		
		if(operationType.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new LocationModel();
		}
		entity.countryName = locationBRE.countryName;
		entity.cityName = locationBRE.cityName;
		entity.stateName = locationBRE.stateName;
		entity.codCountry = locationBRE.codCountry;
		return entity;
	}
	
	public List<Location> parserLocationModel_To_BRE(List<LocationModel> locationModel_L) {
		List<Location> locationL = new ArrayList<Location>();
		for(LocationModel l : locationModel_L) {
			Location locationBRE = new Location();
			locationBRE.countryName = l.countryName;
			locationBRE.cityName = l.cityName;
			locationBRE.stateName = l.stateName;
			locationBRE.codCountry = l.codCountry;
			locationL.add(locationBRE);
		}
		return locationL;
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
	
	public RoadwayModel parserRoadwayBRE_TO_Model(RoadwayBRE roadwayBRE, RoadwayModel roadwayModel, String typeOperation) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			roadwayModel = new RoadwayModel();
		}
		roadwayModel.name_bre = roadwayBRE.name_bre;
		roadwayModel.transport = roadwayBRE.transport;
		roadwayModel.date_creation = dateFormat.parse(roadwayBRE.date_creation);
		roadwayModel.date_change = dateFormat.parse(roadwayBRE.date_change);
		roadwayModel.status = roadwayBRE.status;
		roadwayModel.version = roadwayBRE.version;
		
		// CATEGORY-Entity
		if(roadwayBRE.categories.size() >= 1) {
			List<CategoryModel> categoriesL = new ArrayList<CategoryModel>();
			for(Category categoryBRE :  roadwayBRE.categories) {
				CategoryModel categoryModel_Obj = parserCategoryBRE_TO_Model(categoryBRE, null, typeOperation);
				categoriesL.add(categoryModel_Obj);
			}
			roadwayModel.categories = categoriesL;
		}
		
		// LOCATION-Entity
		if(roadwayBRE.locations.size() >= 1) {
			List<LocationModel> locationL = new ArrayList<LocationModel>();
			for(Location locationBRE :  roadwayBRE.locations) {
				LocationModel locationModel_Obj = parserLocationBRE_TO_Model(locationBRE, null, typeOperation);
				locationL.add(locationModel_Obj);
			}
			roadwayModel.locations = locationL;
		}
		
		
		// COSTS-Entity
		CostsModel costsModel_Obj = new CostsModel();
		List<CostsModel> categoriesCostsModelL = new ArrayList<CostsModel>();
		Map<String, List<CostsModel>> categoryCostsModel_Map = new HashMap<String, List<CostsModel>>();
		if(roadwayBRE.costs.size() >= 1) {
			for(Location locBRE_Obj : roadwayBRE.locations) {
				for(RoadwayCosts costsBREObj : roadwayBRE.costs){
					costsModel_Obj = new CostsModel();
					if(locBRE_Obj.countryName.equals(costsBREObj.countryName)) {
						costsModel_Obj.countryName = costsBREObj.countryName;
						costsModel_Obj.vehicle = costsBREObj.vehicle;
						costsModel_Obj.weight_cost = costsBREObj.weight_cost;
						costsModel_Obj.distance_cost = costsBREObj.distance_cost;
						costsModel_Obj.worktime_cost = costsBREObj.worktime_cost;
						costsModel_Obj.average_consumption_cost = costsBREObj.average_consumption_cost;
						costsModel_Obj.rate_exchange = costsBREObj.rate_exchange;
						costsModel_Obj.current_exchange = costsBREObj.current_exchange;
						costsModel_Obj.statusChange = costsBREObj.statusChange;
						categoriesCostsModelL.add(costsModel_Obj);
					}
				}
				categoryCostsModel_Map.put(locBRE_Obj.countryName, categoriesCostsModelL);
				categoriesCostsModelL = new ArrayList<CostsModel>();
			}
			roadwayModel.costs = categoryCostsModel_Map;
		}
		return roadwayModel;
	}
	
	public List<RoadwayBRE> parserRoadwayModel_TO_BRE(List<RoadwayModel> roadway_Model_L) throws ParseException {
		
		List<RoadwayBRE> roadwayBRE_L = new ArrayList<RoadwayBRE>(); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(RoadwayModel roadwayModel_Obj : roadway_Model_L) {
			RoadwayBRE roadwayBRE = new RoadwayBRE(); 

			roadwayBRE.name_bre = roadwayModel_Obj.name_bre;
			roadwayBRE.transport = roadwayModel_Obj.transport;
			roadwayBRE.date_creation = dateFormat.format(roadwayModel_Obj.date_creation);
			roadwayBRE.date_change = dateFormat.format(roadwayModel_Obj.date_change);
			roadwayBRE.status = roadwayModel_Obj.status;
			roadwayBRE.version = roadwayModel_Obj.version;
			
			// CATEGORY-Entity
			List<Category> categoriesL = new ArrayList<Category>();
			if(roadwayModel_Obj.categories.size() >= 1) {
				categoriesL = parserCategoryModel_TO_BRE(roadwayModel_Obj.categories);
			}
			roadwayBRE.categories = categoriesL;
	
			
			// LOCATION-Entity
			List<Location> locationL = new ArrayList<Location>();
			if(roadwayModel_Obj.locations.size() >= 1) {
				locationL = parserLocationModel_To_BRE(roadwayModel_Obj.locations);
			}
			roadwayBRE.locations = locationL;
			
					
			// COSTS-Entity
			RoadwayCosts costs_BRE = new RoadwayCosts();
			List<RoadwayCosts> costs_BRE_L = new ArrayList<RoadwayCosts>();
			if(roadwayModel_Obj.costs.size() >= 1) {
				for(Map.Entry<String, List<CostsModel>> entryCosts : roadwayModel_Obj.costs.entrySet()) {
					List<CostsModel> costsModel_L = entryCosts.getValue();
					for(CostsModel costs_Model : costsModel_L){
						costs_BRE = new RoadwayCosts();
						costs_BRE.countryName = costs_Model.countryName;
						costs_BRE.vehicle = costs_Model.vehicle;
						costs_BRE.weight_cost = costs_Model.weight_cost;
						costs_BRE.distance_cost = costs_Model.distance_cost;
						costs_BRE.worktime_cost = costs_Model.worktime_cost;
						costs_BRE.average_consumption_cost = costs_Model.average_consumption_cost;
						costs_BRE.rate_exchange = costs_Model.rate_exchange;
						costs_BRE.current_exchange = costs_Model.current_exchange;
						costs_BRE.statusChange = costs_Model.statusChange;
						costs_BRE_L.add(costs_BRE);
					}
				}
			}
			roadwayBRE.costs = costs_BRE_L;
			roadwayBRE_L.add(roadwayBRE);
		}
		return roadwayBRE_L;
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
		entity.initials = tranportDTO.initials;
		return entity;
	}
	
	/* ==============================================
	 * I N I T I A L E S  - P A R S E R  
	 * ==============================================
	 */
	
	public InitialsModel parserInitiales_TO_Model(Initials initials, InitialsModel entity, String typeOperation) {
		if(typeOperation.equals(RoadwayManagerConstants.ADD_OP_ROADWAY)) {
			entity = new InitialsModel();
		}
		entity.name = initials.name;
		return entity;
	}
}
