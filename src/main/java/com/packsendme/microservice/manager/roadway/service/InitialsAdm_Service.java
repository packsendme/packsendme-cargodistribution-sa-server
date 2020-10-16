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
import com.packsendme.microservice.manager.roadway.dao.InitialsDAO;
import com.packsendme.microservice.manager.roadway.dao.UnityMeasurementDAO;
import com.packsendme.microservice.manager.roadway.dto.InitialsListDTO_Response;
import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.IInitialsAdmin_Repository;
import com.packsendme.microservice.manager.roadway.repository.InitialsModel;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;
import com.packsendme.roadway.bre.model.transport.Initials;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class InitialsAdm_Service {

	@Autowired
	private InitialsDAO initialsDAO;
	@Autowired
	private ParseComponent parserObj;

	
	public ResponseEntity<?> findInitialsAll() {
		Response<InitialsListDTO_Response> responseObj = null;
		try {
			InitialsListDTO_Response unityListDTO_Response = new InitialsListDTO_Response(initialsDAO.findAll());
			 responseObj = new Response<InitialsListDTO_Response>(0,HttpExceptionPackSend.CREATED_INITIALS.getAction(), unityListDTO_Response);
			 return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<InitialsListDTO_Response>(0,HttpExceptionPackSend.CREATED_INITIALS.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> save(Initials initials) {
		Response<InitialsModel> responseObj = null;
		try {
			InitialsModel entity = parserObj.parserInitiales_TO_Model(initials, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<InitialsModel>(0,HttpExceptionPackSend.CREATED_INITIALS.getAction(), entity);
			if(initialsDAO.findOneByName(entity.name) == null) {
				initialsDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<InitialsModel>(0,HttpExceptionPackSend.CREATED_INITIALS.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> delete(String id) {
		Response<InitialsModel> responseObj = null;
		try {
			Optional<InitialsModel> initialsData = initialsDAO.findOneById(id);
			if (initialsData.isPresent()) {
				InitialsModel initialsEntity = initialsData.get();
				if(initialsDAO.remove(initialsEntity) == true) {
					responseObj = new Response<InitialsModel>(0,HttpExceptionPackSend.DELETE_UNITY_MEASUREMENT.getAction(), initialsData.get());
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<InitialsModel>(0,HttpExceptionPackSend.DELETE_INITIALS.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> update(String id, Initials initials) {
		Response<InitialsModel> responseObj = null;
		InitialsModel entity = null;
		responseObj = new Response<InitialsModel>(0,HttpExceptionPackSend.UPDATE_INITIALS.getAction(), entity);
		try {
			Optional<InitialsModel> initialsData = initialsDAO.findOneById(id);
			if(initialsData != null) {
				entity = parserObj.parserInitiales_TO_Model(initials, initialsData.get(), RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				entity = initialsDAO.update(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
