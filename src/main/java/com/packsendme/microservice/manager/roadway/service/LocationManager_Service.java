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
import com.packsendme.microservice.manager.roadway.dao.LocationDAO;
import com.packsendme.microservice.manager.roadway.dto.LocationListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.LocationModel;
import com.packsendme.roadway.bre.model.location.Location;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class LocationManager_Service {
	
	@Autowired
	private LocationDAO locationDAO;
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findLocationAll() {
		Response<LocationListDTO_Response> responseObj = null;
		try {
			LocationListDTO_Response locationListDTO = new LocationListDTO_Response(locationDAO.findAll());
			responseObj = new Response<LocationListDTO_Response>(0,HttpExceptionPackSend.CREATED_LOCATION.getAction(), locationListDTO);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<LocationListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveLocation(Location location) {
		Response<LocationModel> responseObj = null;
		try {
			LocationModel entity = parserObj.parserLocationBRE_TO_Model(location, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.CREATED_LOCATION.getAction(), entity);
			if(locationDAO.findOneByName(entity.countryName) == null) {
				locationDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.CREATED_LOCATION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteLocation(String id) {
		Response<LocationModel> responseObj = null;
		try {
			Optional<LocationModel> locationData = locationDAO.findOneById(id);
			if(locationData.isPresent()) {
				LocationModel locationEntity = locationData.get();
				if(locationDAO.remove(locationEntity) == true) {
					responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), locationData.get());
					return new ResponseEntity<>(responseObj, HttpStatus.OK);
				}
				else {
					responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), locationData.get());
					return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.DELETE_BODYWORK.getAction(), locationData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<LocationModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateLocation(String id, Location locationRule) {
		Response<String> responseObj = null;
		try {
			// Check if exist same bodywork in Database
			LocationModel locationModelFindName = locationDAO.findOneByIdAndName(id, locationRule.countryName);

			if(locationModelFindName == null) {
				Optional<LocationModel> locationData = locationDAO.findOneById(id);
				if(locationData.isPresent()) {
					LocationModel locationEntity = locationData.get();
					LocationModel locationEntityUp = parserObj.parserLocationBRE_TO_Model(locationRule, locationEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
					locationDAO.update(locationEntityUp);
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_LOCATION.getAction(), locationEntity.countryName);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_LOCATION.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_BODYWORK.getAction(), locationRule.countryName);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_LOCATION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

}
