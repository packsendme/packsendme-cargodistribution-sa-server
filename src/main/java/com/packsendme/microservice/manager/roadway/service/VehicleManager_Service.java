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
import com.packsendme.microservice.manager.roadway.dao.VehicleDAO;
import com.packsendme.microservice.manager.roadway.dto.VehicleListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.BodyWorkModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class VehicleManager_Service {

	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ParseDtoToModel vehicleParse;
	@Autowired
	private CategoryManager_Service categoryService;

	
	public ResponseEntity<?> findVehiclesAll() {
		Response<VehicleListDTO_Response> responseObj = null;
		try {
			 VehicleListDTO_Response vehicleListDTO_Response = new VehicleListDTO_Response(vehicleDAO.findAll());
			 responseObj = new Response<VehicleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), vehicleListDTO_Response);
			 return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			// TODO: handle exception
			responseObj = new Response<VehicleListDTO_Response>(0,HttpExceptionPackSend.CREATED_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveVehicles(VehicleBRE vehicleBRE) {
		Response<VehicleModel> responseObj = null;
		try {
			VehicleModel entity = vehicleParse.vehicleDto_TO_Model(vehicleBRE, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			vehicleDAO.save(entity);
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), entity);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.SIMULATION_ROADWAY.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> deleteVehicles(String id, VehicleBRE vehicleBRE) {
		Response<VehicleModel> responseObj = null;
		try {
			Optional<VehicleModel> vehicleData = vehicleDAO.findOneById(id);
			if (vehicleData.isPresent()) {
				VehicleModel vehicleEntity = vehicleData.get();
				if(vehicleDAO.remove(vehicleEntity) == true) {
					categoryService.crudTrigger(RoadwayManagerConstants.DELETE_OP_ROADWAY, null, vehicleEntity);
				}
			}
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.DELETE_VEHICLE.getAction(), vehicleData.get());
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateVehicle(String id, VehicleBRE vehicleBRE) {
		Response<VehicleModel> responseObj = null;
		try {
			Optional<VehicleModel> vehicleData = vehicleDAO.findOneById(id);
			if(vehicleData.isPresent()) {
				VehicleModel vehicleEntity = vehicleData.get();
				String vehicleS_old = vehicleEntity.vehicle;

				VehicleModel vehicleEntityChange = vehicleParse.vehicleDto_TO_Model(vehicleBRE, vehicleEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				vehicleDAO.update(vehicleEntityChange);
				categoryService.crudTrigger(RoadwayManagerConstants.UPDATE_OP_ROADWAY, vehicleS_old, vehicleEntityChange);
				responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), vehicleEntity);
				return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
			}
			else {
				responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> crudTrigger(String operationType, String bodyworkS_old, BodyworkBRE bodyworkBRE) {
		boolean statusCrud = false;
		Response<VehicleModel> responseObj = null;
		List<String> bodyWorkL = new ArrayList<String>();
		System.out.println(" ");
		System.out.println(" -------------------------------------------- ");

		System.out.println("(1) crudTrigger - VehicleModel ");
		// FindAll - Vehicle to relationship with BodyWork will be removed or update
		try {
			List<VehicleModel> vehicleL = vehicleDAO.findAll();
			System.out.println(" (2) crudTrigger - vehicleL "+ vehicleL.size());
			
			for(VehicleModel vehicleOld : vehicleL) {
				System.out.println(" (3) crudTrigger - vehicleOld.vehicle "+ vehicleOld.vehicle);

				for(String bodyWork : vehicleOld.bodywork_vehicle) {
					System.out.println("(4) crudTrigger - bodyWork "+ bodyWork);

					if(operationType.equals(RoadwayManagerConstants.DELETE_OP_ROADWAY)) {
						if(!bodyWork.equals(bodyworkBRE.bodyWork)) {
							System.out.println("(5) crudTrigger - bodyWork "+ bodyworkBRE.bodyWork);
							bodyWorkL.add(bodyWork);
						}
						else {
							System.out.println("(5-1) crudTrigger - bodyWork "+ bodyworkBRE.bodyWork);
							statusCrud = true;
						}
					} else if(operationType.equals(RoadwayManagerConstants.UPDATE_OP_ROADWAY)) {
						System.out.println("(6-0) crudTrigger - bodyWork "+ bodyWork);
						System.out.println("(6-0) crudTrigger - bodyWork "+ bodyworkS_old);

						if(bodyWork.equals(bodyworkS_old)) {
							System.out.println("(6-1) crudTrigger - bodyWork "+ bodyworkS_old);
							bodyWorkL.add(bodyworkBRE.bodyWork);
							statusCrud = true;
						}
						else {
							System.out.println("(6-2) crudTrigger - bodyWork "+ bodyworkS_old);
							bodyWorkL.add(bodyWork);
 						}
					}		
				}
				if(statusCrud == true) {
					System.out.println("(7) crudTrigger - SAVE "+ statusCrud);
					String vehicleS_Old = vehicleOld.vehicle;
					VehicleModel vehicleNew = vehicleOld; 
					vehicleDAO.remove(vehicleOld);
					vehicleNew.bodywork_vehicle = null;
					vehicleNew.bodywork_vehicle = bodyWorkL;
					System.out.println("(7-1) crudTrigger - SAVE "+ bodyWorkL.size());
					System.out.println("(7-2) crudTrigger - SAVE "+ vehicleNew.vehicle);

					vehicleDAO.save(vehicleNew);
					statusCrud = false;
					categoryService.crudTrigger(operationType, vehicleS_Old, vehicleNew);
				}
			}
			return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<VehicleModel>(0,HttpExceptionPackSend.UPDATE_VEHICLE.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	

	
}
