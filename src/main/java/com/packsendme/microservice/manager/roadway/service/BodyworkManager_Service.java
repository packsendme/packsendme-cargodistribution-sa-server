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
	private ParseDtoToModel parserObj;
	@Autowired
	private VehicleManager_Service vehicleService;

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
		try {
			Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
			if(bodyWorkData.isPresent()) {
				BodyWorkModel bodyWorkEntity = bodyWorkData.get();
				if(bodyworkDAO.remove(bodyWorkEntity) == true) {
					crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, bodyWorkEntity, bodyworkBRE);
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
		try {
			Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
			if(bodyWorkData.isPresent()) {
				BodyWorkModel bodyWorkEntity = bodyWorkData.get();
				BodyWorkModel bodyWorkEntityUp = parserObj.bodyworkDto_TO_Model(bodyworkBRE, bodyWorkEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				bodyworkDAO.update(bodyWorkEntityUp);
				crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, bodyWorkEntity, bodyworkBRE);

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
	

	public ResponseEntity<?> crudTrigger(String operationType, BodyWorkModel bodyworkModal, BodyworkBRE bodyworkBRE) {
		Response<BodyworkBRE> responseObj = null;
		try {
			vehicleService.crudTrigger(operationType, bodyworkModal, bodyworkBRE);
			responseObj = new Response<BodyworkBRE>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), bodyworkBRE);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyworkBRE>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	

}
