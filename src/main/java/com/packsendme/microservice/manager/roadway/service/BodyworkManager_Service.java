package com.packsendme.microservice.manager.roadway.service;

import java.util.ArrayList;
import java.util.List;

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
import com.packsendme.microservice.manager.roadway.repository.BodyWork_Model;
import com.packsendme.microservice.manager.roadway.repository.Vehicle_Model;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class BodyworkManager_Service {
	
	private BodyworkDAO bodyworkDAO;
	private VehicleDAO vehicleDAO;
	private ParseDtoToModel parserObj;

	public ResponseEntity<?> findBodyworkAll() {
		Response<BodyworkListDTO_Response> responseObj = null;
		try {
			BodyworkListDTO_Response bodyworkListDTO = new BodyworkListDTO_Response(bodyworkDAO.findAll());
			responseObj = new Response<BodyworkListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), bodyworkListDTO);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BodyworkListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveBodywork(BodyworkBRE bodyworkBRE) {
		Response<BodyWork_Model> responseObj = null;
		try {
			BodyWork_Model entity = parserObj.bodyworkDto_TO_Model(bodyworkBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			bodyworkDAO.save(entity);
			responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteBodywork(BodyworkBRE bodyworkBRE) {
		Response<BodyWork_Model> responseObj = null;
		boolean cat_resave = false;
		List<String> newBodyWorkL = new ArrayList<String>();
		BodyWork_Model entity = new BodyWork_Model();

		try {
			entity.bodyWork = bodyworkBRE.bodyWork;
			entity = bodyworkDAO.findOne(entity);
			
			if(bodyworkDAO.remove(entity) == true) {
				// Find Category relationship with Vehicle will be removed
				List<Vehicle_Model> vehicleL = vehicleDAO.findAll();
				for(Vehicle_Model vehicle : vehicleL) {
					Vehicle_Model newVehicle = vehicle; 
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
			responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateBodywork(BodyworkBRE bodyworkBRE) {
		Response<BodyWork_Model> responseObj = null;
		try {
			BodyWork_Model bodyworkModel = new BodyWork_Model();
			bodyworkModel.bodyWork = bodyworkBRE.bodyWork;
			BodyWork_Model entity = bodyworkDAO.findOne(bodyworkModel);
			if(entity != null) {
				entity = parserObj.bodyworkDto_TO_Model(bodyworkBRE, entity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				bodyworkDAO.update(entity);
				responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BodyWork_Model>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

}
