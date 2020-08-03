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
import com.packsendme.microservice.manager.roadway.dao.CategoryDAO;
import com.packsendme.microservice.manager.roadway.dao.VehicleDAO;
import com.packsendme.microservice.manager.roadway.dto.VehicleListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.CategoryModel;
import com.packsendme.microservice.manager.roadway.repository.VehicleModel;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class VehicleManager_Service {

	@Autowired
	private VehicleDAO vehicleDAO;
	@Autowired
	private ParseDtoToModel vehicleParse;
	@Autowired
	private CategoryDAO categoryDAO;

	
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
	
	public ResponseEntity<?> deleteVehicles(String id, VehicleBRE vehicleObj) {
		Response<VehicleModel> responseObj = null;
		boolean cat_resave = false;
		List<VehicleModel> newVehicleL = new ArrayList<VehicleModel>();
		CategoryModel newCategory = new CategoryModel();
		try {
			Optional<VehicleModel> vehicleData = vehicleDAO.findOneById(id);
			String vehicleName = vehicleData.get().vehicle;
			
			if (vehicleData.isPresent()) {
				VehicleModel vehicleEntity = vehicleData.get();
				if(vehicleDAO.remove(vehicleEntity) == true) {
					// Find Category relationship with Vehicle will be removed
					List<CategoryModel> categoryL = categoryDAO.findAll();
					
					System.out.println("============================");
					System.out.println(" categoryL "+ categoryL.size());
					System.out.println("============================");
					
					if(categoryL.size() > 0) {
						for(CategoryModel c : categoryL) {
							newCategory = c;
							
							System.out.println("============================");
							System.out.println(" Category_Model ");
							System.out.println("============================");

							
							for(VehicleModel v : c.vehicles) {
								System.out.println("============================");
								System.out.println(" vehicleDATA "+ v.vehicle);
								System.out.println(" vehicleName "+ vehicleName);
								System.out.println("============================");

								if(!v.vehicle.equals(vehicleName)) {
									System.out.println("============================");
									System.out.println(" IF "+ v.vehicle);
									System.out.println("============================");
									newVehicleL.add(v);
								}
								else {
									cat_resave = true;
									System.out.println("============================");
									System.out.println(" ELSE "+ v.vehicle);
									System.out.println("============================");

								}
							}
							if(cat_resave == true) {
								cat_resave = false;
								System.out.println("============================");
								System.out.println(" UPDATE ");
								System.out.println("============================");
								newCategory.vehicles = null;
								newCategory.vehicles = newVehicleL;
								categoryDAO.update(newCategory);
							}
						}
					}
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
				vehicleEntity = vehicleParse.vehicleDto_TO_Model(vehicleBRE, vehicleEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
				vehicleDAO.update(vehicleEntity);
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
	

	
}
