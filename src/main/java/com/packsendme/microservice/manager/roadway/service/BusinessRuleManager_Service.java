package com.packsendme.microservice.manager.roadway.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.manager.roadway.component.ParseDtoToModel;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.RoadwayDAO;
import com.packsendme.microservice.manager.roadway.dto.RoadwayBREListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.BusinessRule_Model;
import com.packsendme.roadway.bre.model.businessrule.BusinessRuleRoadwayBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class BusinessRuleManager_Service {
	
	private RoadwayDAO roadwayBRE_DAO;
	private ParseDtoToModel parserObj;

	public ResponseEntity<?> findRoadwayAll() {
		Response<RoadwayBREListDTO_Response> responseObj = null;
		try {
			RoadwayBREListDTO_Response roadwayBREListDTO_Response = new RoadwayBREListDTO_Response(roadwayBRE_DAO.findAll());
			responseObj = new Response<RoadwayBREListDTO_Response>(0,HttpExceptionPackSend.CREATED_ROADWAYBRE.getAction(), roadwayBREListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<RoadwayBREListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveRoadway(BusinessRuleRoadwayBRE businessRuleBRE) {
		Response<BusinessRule_Model> responseObj = null;
		try {
			BusinessRule_Model  entity = parserObj.roadwayBRE_TO_Model(businessRuleBRE,null,RoadwayManagerConstants.ADD_OP_ROADWAY);
			roadwayBRE_DAO.save(entity);
			responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteRoadway(BusinessRuleRoadwayBRE businessRuleObj) {
		Response<BusinessRule_Model> responseObj = null;
		try {
			BusinessRule_Model boadwayBusinessModel = new BusinessRule_Model();
			boadwayBusinessModel.category_name = businessRuleObj.category_name;
			BusinessRule_Model entity = roadwayBRE_DAO.findOne(boadwayBusinessModel);
			if(roadwayBRE_DAO.remove(entity) == true) {
				responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateRoadway(BusinessRuleRoadwayBRE businessRuleBRE) {
		Response<BusinessRule_Model> responseObj = null;
		try {
			BusinessRule_Model boadwayBusinessModel = new BusinessRule_Model();
			boadwayBusinessModel.category_name = businessRuleBRE.category_name;
			BusinessRule_Model entity = roadwayBRE_DAO.findOne(boadwayBusinessModel);
			if(entity != null) {
				entity = parserObj.roadwayBRE_TO_Model(businessRuleBRE,entity,RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				roadwayBRE_DAO.update(entity);
				responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), entity);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<BusinessRule_Model>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	
}
