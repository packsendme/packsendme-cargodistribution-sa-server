package com.packsendme.microservice.manager.roadway.service;

import java.util.ArrayList;
import java.util.List;

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
import com.packsendme.microservice.manager.roadway.dao.VehicleDAO;
import com.packsendme.microservice.manager.roadway.dto.VehicleListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.Category_Model;
import com.packsendme.microservice.manager.roadway.repository.Vehicle_Model;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class VehicleManager_Service {

	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ParseDtoToModel vehicleParse;
	@Autowired
	private CategoryDAO categoryDAO;

	
	public ResponseEntity<?> findVehiclesAll() {
		Response<VehicleListDTO_Response> responseObj = null;
		try {
			 VehicleListDTO_Response vehicleListDTO_Response = new VehicleListDTO_Response(vehicleDAO.findAll());
			 responseObj = new Response<VehicleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), vehicleListDTO_Response);
			 return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<VehicleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveVehicles(VehicleBRE vehicleBRE) {
		Response<Vehicle_Model> responseObj = null;
		try {
			Vehicle_Model entity = vehicleParse.vehicleDto_TO_Model(vehicleBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			vehicleDAO.save(entity);
			responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteVehicles(VehicleBRE vehicleObj) {
		Response<Vehicle_Model> responseObj = null;
		boolean cat_resave = false;
		List<Vehicle_Model> newVehicleL = new ArrayList<Vehicle_Model>();
		Category_Model newCategory = new Category_Model();
		Vehicle_Model vehicleModel = new Vehicle_Model();
		try {
			vehicleModel.vehicle = vehicleObj.vehicle;
			Vehicle_Model entity = vehicleDAO.findOne(vehicleModel);
			if(vehicleDAO.remove(entity) == true) {
				// Find Category relationship with Vehicle will be removed
				List<Category_Model> categoryL = categoryDAO.findAll();
				for(Category_Model c : categoryL) {
					newCategory = c; 
					for(Vehicle_Model v : c.vehicle_ModelL) {
						if(!v.vehicle.equals(vehicleObj.vehicle)) {
							newVehicleL.add(v);
						}
						else {
							cat_resave = true;
						}
					}
					if(cat_resave == true) {
						categoryDAO.remove(newCategory);
						newCategory.vehicle_ModelL = null;
						newCategory.vehicle_ModelL = newVehicleL;
						categoryDAO.save(newCategory);
					}
				}
			}
			responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateVehicle(VehicleBRE vehicleBRE) {
		Response<Vehicle_Model> responseObj = null;
		try {
			Vehicle_Model vehicleModel = new Vehicle_Model();
			vehicleModel.vehicle = vehicleBRE.vehicle;
			Vehicle_Model entity = vehicleDAO.findOne(vehicleModel);
			if(entity != null) {
				entity = vehicleParse.vehicleDto_TO_Model(vehicleBRE, entity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				vehicleDAO.update(entity);
				responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<Vehicle_Model>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

	
}
