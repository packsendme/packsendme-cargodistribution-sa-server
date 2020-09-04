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
import com.packsendme.microservice.manager.roadway.dao.UnityMeasurementDAO;
import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementListDTO_Response;
import com.packsendme.microservice.manager.roadway.dto.VehicleTypeListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.UnityMeasurementModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;
import com.packsendme.roadway.bre.model.vehicle.VehicleType;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class UnityMeasurementAdm_Service {

	@Autowired
	private UnityMeasurementDAO unityDAO;
	@Autowired
	private ParseComponent parserObj;

	
	public ResponseEntity<?> findUnityMeasurementAll(String name) {
		Response<UnityMeasurementListDTO_Response> responseObj = null;
		try {
			UnityMeasurementListDTO_Response unityListDTO_Response = new UnityMeasurementListDTO_Response(unityDAO.findAll(name));
			 responseObj = new Response<UnityMeasurementListDTO_Response>(0,HttpExceptionPackSend.CREATED_UNITY_MEASUREMENT.getAction(), unityListDTO_Response);
			 return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<UnityMeasurementListDTO_Response>(0,HttpExceptionPackSend.CREATED_UNITY_MEASUREMENT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveUnityMeasurement(UnityMeasurementDTO unityMeasurement) {
		Response<UnityMeasurementModel> responseObj = null;
		try {
			UnityMeasurementModel entity = parserObj.parserUnityMeasurement_TO_Model(unityMeasurement, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<UnityMeasurementModel>(0,HttpExceptionPackSend.CREATED_UNITY_MEASUREMENT.getAction(), entity);
			if(unityDAO.findOneByName(entity.unitMeasurement) == null) {
				unityDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<UnityMeasurementModel>(0,HttpExceptionPackSend.CREATED_UNITY_MEASUREMENT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteUnityMeasurement(String id) {
		Response<UnityMeasurementModel> responseObj = null;
		try {
			Optional<UnityMeasurementModel> unityData = unityDAO.findOneById(id);
			if (unityData.isPresent()) {
				UnityMeasurementModel unityEntity = unityData.get();
				if(unityDAO.remove(unityEntity) == true) {
					responseObj = new Response<UnityMeasurementModel>(0,HttpExceptionPackSend.DELETE_UNITY_MEASUREMENT.getAction(), unityData.get());
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<UnityMeasurementModel>(0,HttpExceptionPackSend.DELETE_UNITY_MEASUREMENT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateUnityMeasurement(String id, UnityMeasurementDTO unityDTO) {
		Response<UnityMeasurementModel> responseObj = null;
		UnityMeasurementModel entity = null;
		responseObj = new Response<UnityMeasurementModel>(0,HttpExceptionPackSend.UPDATE_UNITY_MEASUREMENT.getAction(), entity);
		try {
			Optional<UnityMeasurementModel> unityData = unityDAO.findOneById(id);
			if(unityData != null) {
				entity = parserObj.parserUnityMeasurement_TO_Model(unityDTO, unityData.get(), RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				entity = unityDAO.update(entity);
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
