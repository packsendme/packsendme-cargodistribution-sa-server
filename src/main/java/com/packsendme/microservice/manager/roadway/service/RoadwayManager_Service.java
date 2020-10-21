package com.packsendme.microservice.manager.roadway.service;

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
import com.packsendme.microservice.manager.roadway.dao.RoadwayDAO;
import com.packsendme.microservice.manager.roadway.dto.RoadwayBREListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.RoadwayModel;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class RoadwayManager_Service {
	
	@Autowired
	private RoadwayDAO roadwayBRE_DAO;
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findRoadwayAll() {
		Response<RoadwayBREListDTO_Response> responseObj = null;
		try {
			
			List<RoadwayBRE> roadwaybre_L = parserObj.parserRoadwayModel_TO_BRE(roadwayBRE_DAO.findAll());
			RoadwayBREListDTO_Response roadwayBREListDTO_Response = new RoadwayBREListDTO_Response(roadwaybre_L);
			responseObj = new Response<RoadwayBREListDTO_Response>(0,HttpExceptionPackSend.CREATED_ROADWAYBRE.getAction(), roadwayBREListDTO_Response);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayBREListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveRoadway(RoadwayBRE roadwayBRE) {
		Response<RoadwayModel> responseObj = null;
		try {
			RoadwayModel  entity = parserObj.parserRoadwayBRE_TO_Model(roadwayBRE,null,RoadwayManagerConstants.ADD_OP_ROADWAY);
			roadwayBRE_DAO.save(entity);
			responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.CREATE_ROADWAYBRE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteRoadway(String id) {
		Response<RoadwayModel> responseObj = null;
		try {
			Optional<RoadwayModel> roadwayBREData = roadwayBRE_DAO.findOneById(id);
			if(roadwayBREData.isPresent()) {
				RoadwayModel roadwayBRE_Entity = roadwayBREData.get(); 
				if(roadwayBRE_DAO.remove(roadwayBRE_Entity) == true) {
					responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), roadwayBRE_Entity);
				}
				else {
					responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), roadwayBRE_Entity);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.DELETE_ROADWAYBRE.getAction(), roadwayBREData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateRoadway(String id, RoadwayBRE businessRuleBRE) {
		Response<RoadwayModel> responseObj = null;
		try {
			Optional<RoadwayModel> roadwayBREData = roadwayBRE_DAO.findOneById(id);
			if(roadwayBREData.isPresent()) {
				RoadwayModel roadwayBRE_Entity = roadwayBREData.get(); 
				roadwayBRE_Entity = parserObj.parserRoadwayBRE_TO_Model(businessRuleBRE,roadwayBRE_Entity,RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				roadwayBRE_DAO.update(roadwayBRE_Entity);
				responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), roadwayBRE_Entity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<RoadwayModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	/*
	public ResponseEntity<?> crudTrigger(String operationType, String categoryName_Old, CategoryRuleModel categoryModelNew) {
		RoadwayModel roadwayObj_Model = new RoadwayModel();
		Response<CategoryRuleModel> responseObj = null;
	
		// Find Category relationship with Vehicle will be removed
		try {
			List<RoadwayModel> roadwayL = roadwayBRE_DAO.findAll();
			for(RoadwayModel roadway_entity : roadwayL) {
				
				if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
					if(roadway_entity.categoryRule.categoryType.name_category.equals(categoryModelNew.categoryType.name_category)) {
						roadwayObj_Model = roadway_entity;
						roadwayBRE_DAO.remove(roadwayObj_Model);
						roadwayObj_Model = new RoadwayModel();
					}
				} 
				else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
					if(roadway_entity.categoryRule.categoryType.name_category.equals(categoryName_Old)) {
						roadwayObj_Model = roadway_entity;
						roadwayObj_Model.categoryRule = null;
						roadwayObj_Model.categoryRule = categoryModelNew;
						roadwayBRE_DAO.update(roadwayObj_Model);
						roadwayObj_Model = new RoadwayModel();
					}
				}
			}
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<CategoryRuleModel>(0,HttpExceptionPackSend.UPDATE_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}*/


	
}
