package com.packsendme.microservice.manager.roadway.service;

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
import com.packsendme.microservice.manager.roadway.dao.BodyworkDAO;
import com.packsendme.microservice.manager.roadway.dto.BodyworkListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class BodyworkManager_Service {
	
	@Autowired
	private BodyworkDAO bodyworkDAO;
	@Autowired
	private ParseComponent parserObj;
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
	
	public ResponseEntity<?> saveBodywork(BodyworkRule bodywork) {
		Response<BodyWorkModel> responseObj = null;
		try {
			BodyWorkModel entity = parserObj.parserBodywork_TO_Model(bodywork, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), entity);
			if(bodyworkDAO.findOneByName(entity.bodyWork) == null) {
				bodyworkDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteBodywork(String id) {
		Response<BodyWorkModel> responseObj = null;
		try {
			Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
			if(bodyWorkData.isPresent()) {
				BodyWorkModel bodyWorkEntity = bodyWorkData.get();
				if(bodyworkDAO.remove(bodyWorkEntity) == true) {
					crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, bodyWorkEntity);
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
	
	public ResponseEntity<?> updateBodywork(String id, BodyworkRule bodyworkRule) {
		Response<String> responseObj = null;
		try {
			// Check if exist same bodywork in Database
			BodyWorkModel bodyworkModelFindName = bodyworkDAO.findOneByIdAndName(id, bodyworkRule.bodyWork);
			
			if(bodyworkModelFindName == null) {
				Optional<BodyWorkModel> bodyWorkData = bodyworkDAO.findOneById(id);
				if(bodyWorkData.isPresent()) {
					BodyWorkModel bodyWorkEntity = bodyWorkData.get();
					String bodyworkS_change = bodyWorkEntity.bodyWork;
					BodyWorkModel bodyWorkEntityUp = parserObj.parserBodywork_TO_Model(bodyworkRule, bodyWorkEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
					if (bodyworkDAO.update(bodyWorkEntityUp) != null) {
						crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, bodyworkS_change, bodyWorkEntity);
					}
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), bodyWorkEntity.bodyWork);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), bodyworkRule.bodyWork);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

	public ResponseEntity<?> crudTrigger(String operationType, String bodyworkModal, BodyWorkModel bodyworkRule) {
		Response<BodyWorkModel> responseObj = null;
		try {
			vehicleService.crudTrigger(operationType, bodyworkModal, bodyworkRule);
			if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), null);
			}
			else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
				responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<BodyWorkModel>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	

}
