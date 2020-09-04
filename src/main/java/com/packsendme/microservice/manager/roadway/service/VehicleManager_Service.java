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
import com.packsendme.microservice.manager.roadway.dao.VehicleDAO;
import com.packsendme.microservice.manager.roadway.dto.VehicleListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.VehicleRuleModel;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleRule;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class VehicleManager_Service {

	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ParseComponent parserObj;
	@Autowired
	private CategoryRuleManager_Service categoryService;

	
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
	
	public ResponseEntity<?> saveVehicles(VehicleRule vehicle) {
		Response<VehicleRuleModel> responseObj = null;
		try {
			VehicleRuleModel entity = parserObj.parserVehicle_TO_Model(vehicle, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<VehicleRuleModel>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), entity);
			if(vehicleDAO.findOneByName(entity.vehicle_type) == null) {
				vehicleDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleRuleModel>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteVehicles(String id) {
		Response<VehicleRuleModel> responseObj = null;
		try {
			Optional<VehicleRuleModel> vehicleData = vehicleDAO.findOneById(id);
			if (vehicleData.isPresent()) {
				VehicleRuleModel vehicleEntity = vehicleData.get();
				if(vehicleDAO.remove(vehicleEntity) == true) {
					categoryService.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, vehicleEntity);
				}
			}
			responseObj = new Response<VehicleRuleModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), vehicleData.get());
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleRuleModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateVehicleCheck(String id, VehicleRule vehicle) {
		Response<String> responseObj = null;
		try {
			VehicleRuleModel vehicleModelFindName = vehicleDAO.findOneByName(vehicle.vehicle_type);
			if(vehicleModelFindName != null) {
				if (vehicleModelFindName.id.equals(id)) {
					ResponseEntity<?> responseUpdate = updateVehicle(id,vehicle);
					return new ResponseEntity<>(responseUpdate, HttpStatus.ACCEPTED);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), vehicle.vehicle_type);
					return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
				}
			}
			else {
				ResponseEntity<?> responseUpdate = updateVehicle(id,vehicle);
				return new ResponseEntity<>(responseUpdate, HttpStatus.ACCEPTED);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
				


	public ResponseEntity<?> updateVehicle(String id, VehicleRule vehicle) {
		Response<String> responseObj = null;
		try {
			// Check if exist same vehicle in Database
			VehicleRuleModel vehicleModelFindName = vehicleDAO.findOneByIdAndName(id, vehicle.vehicle_type);
			
			if(vehicleModelFindName == null) {
				Optional<VehicleRuleModel> vehicleData = vehicleDAO.findOneById(id);
				if(vehicleData.isPresent()) {
					VehicleRuleModel vehicleEntity = vehicleData.get();
					String vehicleS_old = vehicleEntity.vehicle_type;
					VehicleRuleModel vehicleEntityChange = parserObj.parserVehicle_TO_Model(vehicle, vehicleEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
					
					if(vehicleDAO.update(vehicleEntityChange) != null) {
						categoryService.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, vehicleS_old, vehicleEntityChange);
					}
						
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), vehicleEntity.vehicle_type);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), vehicle.vehicle_type);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTrigger(String operationType, String bodyworkS_old, BodyworkRule bodywork) {
		boolean statusCrud = false;
		Response<VehicleRuleModel> responseObj = null;
		List<String> bodyWorkL = new ArrayList<String>();

		// FindAll - Vehicle to relationship with BodyWork will be removed or update
		try {
			List<VehicleRuleModel> vehicleL = vehicleDAO.findAll();
			for(VehicleRuleModel vehicleOld : vehicleL) {
				for(String bodyWork : vehicleOld.bodywork_vehicle) {
					if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
						if(!bodyWork.equals(bodywork.bodyWork)) {
							bodyWorkL.add(bodyWork);
						}
						else {
							statusCrud = true;
						}
					} else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {

						if(bodyWork.equals(bodyworkS_old)) {
							bodyWorkL.add(bodywork.bodyWork);
							statusCrud = true;
						}
						else {
							bodyWorkL.add(bodyWork);
 						}
					}		
				}
				if(statusCrud == true) {
					System.out.println("(7) crudTrigger - SAVE "+ statusCrud);
					String vehicleS_Old = vehicleOld.vehicle_type;
					VehicleRuleModel vehicleNew = vehicleOld; 
					vehicleDAO.remove(vehicleOld);
					vehicleNew.bodywork_vehicle = null;
					vehicleNew.bodywork_vehicle = bodyWorkL;
					vehicleDAO.save(vehicleNew);
					bodyWorkL = new ArrayList<String>();
					statusCrud = false;
					categoryService.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, vehicleS_Old, vehicleNew);
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleRuleModel>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
}
