package com.packsendme.microservice.manager.roadway.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.packsendme.lib.common.constants.generic.HttpExceptionPackSend;
import com.packsendme.lib.common.response.Response;
import com.packsendme.microservice.manager.roadway.component.ParseModel;
import com.packsendme.microservice.manager.roadway.component.RoadwayManagerConstants;
import com.packsendme.microservice.manager.roadway.dao.VehicleTypeDAO;
import com.packsendme.microservice.manager.roadway.dto.VehicleTypeListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class VehicleTypeAdm_Service {

	@Autowired
	private VehicleTypeDAO vehicleDAO;
	@Autowired
	private ParseModel parserObj;

	
	public ResponseEntity<?> findVehiclesTypeAll() {
		Response<VehicleTypeListDTO_Response> responseObj = null;
		try {
			VehicleTypeListDTO_Response vehicleListDTO_Response = new VehicleTypeListDTO_Response(vehicleDAO.findAll());
			 responseObj = new Response<VehicleTypeListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), vehicleListDTO_Response);
			 return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<VehicleTypeListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveVehiclesType(VehicleTypeModel vehicle) {
		Response<VehicleTypeModel> responseObj = null;
		try {
			VehicleTypeModel entity = parserObj.parserVehicleType_TO_Model(vehicle, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<VehicleTypeModel>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), entity);
			if(vehicleDAO.findOneByName(entity.type_vehicle) == null) {
				vehicleDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleTypeModel>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteVehiclesType(String id) {
		Response<VehicleTypeModel> responseObj = null;
		responseObj = new Response<VehicleTypeModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), null);

		try {
			Optional<VehicleTypeModel> vehicleData = vehicleDAO.findOneById(id);
			if (vehicleData.isPresent()) {
				VehicleTypeModel vehicleEntity = vehicleData.get();
				if(vehicleDAO.remove(vehicleEntity) == true) {
					return new ResponseEntity<>(responseObj, HttpStatus.OK);
				}
				else{
					return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
				}
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleTypeModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<?> updateVehicleType(String id, VehicleTypeModel vehicle) {
		Response<String> responseObj = null;
		responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_UNITY_MEASUREMENT.getAction(), null);
		try {
			VehicleTypeModel vehicleModelFindName = vehicleDAO.findOneByName(vehicle.type_vehicle);
			if(vehicleModelFindName != null) {
				vehicleModelFindName = vehicleDAO.update(vehicleModelFindName);
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
