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
import com.packsendme.microservice.manager.roadway.component.ParseDtoToModel;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.BodyworkDAO;
import com.packsendme.microservice.manager.roadway.dao.VehicleDAO;
import com.packsendme.microservice.manager.roadway.dto.BodyworkListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class BodyworkManager_Service {
	
	@Autowired
	private BodyworkDAO bodyworkDAO;
	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ParseDtoToModel parserObj;

	public ResponseEntity<?> findBodyworkAll() {
		Response<BodyworkListDTO_Response> responseObj = null;
		try {
			BodyworkListDTO_Response bodyworkListDTO = new BodyworkListDTO_Response(bodyworkDAO.findAll());
			responseObj = new Response<BodyworkListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), bodyworkListDTO);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyworkListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveBodywork(BodyworkBRE bodyworkBRE) {
		Response<BodyWorkModel> responseObj = null;
		try {
			BodyWorkModel entity = parserObj.bodyworkDto_TO_Model(bodyworkBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			bodyworkDAO.save(entity);
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteBodywork(String id, BodyworkBRE bodyworkBRE) {
		Response<BodyWorkModel> responseObj = null;
		boolean cat_resave = false;
		List<String> newBodyWorkL = new ArrayList<String>();

		try {
			Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
			if(bodyWorkData.isPresent()) {
				BodyWorkModel bodyWorkEntity = bodyWorkData.get();
				if(bodyworkDAO.remove(bodyWorkEntity) == true) {
					// Find Category relationship with Vehicle will be removed
					List<VehicleModel> vehicleL = vehicleDAO.findAll();
					for(VehicleModel vehicle : vehicleL) {
						VehicleModel newVehicle = vehicle; 
						for(String bodyWork : vehicle.bodywork_vehicle) {
							if(!bodyWork.equals(bodyworkBRE.bodyWork)) {
								newBodyWorkL.add(bodyWork);
							}
							else {
								cat_resave = true;
							}
						}
						if(cat_resave == true) {
							vehicleDAO.remove(newVehicle);
							newVehicle.bodywork_vehicle = null;
							newVehicle.bodywork_vehicle = newBodyWorkL;
							vehicleDAO.save(newVehicle);
						}
					}
				}
				else {
					responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), bodyWorkData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), bodyWorkData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateBodywork(String id, BodyworkBRE bodyworkBRE) {
		Response<BodyWorkModel> responseObj = null;
		List<String> newBodyWorkL = new ArrayList<String>();
		boolean cat_resave = false;
		String bodyworkS = "";
		try {
			Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
			if(bodyWorkData.isPresent()) {
				BodyWorkModel bodyWorkEntity = bodyWorkData.get();
				bodyworkS = bodyWorkEntity.bodyWork;
				bodyWorkEntity = parserObj.bodyworkDto_TO_Model(bodyworkBRE, bodyWorkEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				bodyworkDAO.update(bodyWorkEntity);
				List<VehicleModel> vehicleL = vehicleDAO.findAll();
				for(VehicleModel vehicle : vehicleL) {
					VehicleModel newVehicle = vehicle; 
					for(String bodyWork : vehicle.bodywork_vehicle) {
						System.out.println(" ========================== " );
						System.out.println(" bodyWork VEHICLE "+ bodyWork);
						System.out.println(" bodyWork BODYWORK "+ bodyworkS);
						System.out.println(" ========================== " );
						
						if(bodyWork.equals(bodyworkS)) {
							cat_resave = true;
							newBodyWorkL.add(bodyworkBRE.bodyWork);
						}
						else {
							newBodyWorkL.add(bodyWork);
						}
					}
					if(cat_resave == true) {
						newVehicle.bodywork_vehicle = null;
						newVehicle.bodywork_vehicle = newBodyWorkL;
						vehicleDAO.update(newVehicle);
						cat_resave = false;
					}
				}
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), bodyWorkEntity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

}
