package com.packsendme.microservice.manager.roadway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.manager.roadway.dto.UnityMeasurementDTO;
import com.packsendme.microservice.manager.roadway.repository.VehicleTypeModel;
import com.packsendme.microservice.manager.roadway.service.UnityMeasurementAdm_Service;
import com.packsendme.microservice.manager.roadway.service.VehicleTypeAdm_Service;

@RestController
@RequestMapping("/roadway/admin")
public class RoadwayAdmin_Controller {

	@Autowired
	private VehicleTypeAdm_Service vehiclesAdmService;
	
	@Autowired
	private UnityMeasurementAdm_Service unityMeasurementAdmService;	
	
	/***************************************
	 VEHICLE_TYPE :: GET | POST | DELETE 
	 ***************************************/
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/vehicletype")
	public ResponseEntity<?> getVehicles(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return vehiclesAdmService.findVehiclesTypeAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/vehicletype")
	public ResponseEntity<?> postVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleTypeModel vehicleType)
	{	
		return vehiclesAdmService.saveVehiclesType(vehicleType);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/vehicletype")
	public ResponseEntity<?> deleteVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return vehiclesAdmService.deleteVehiclesType(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/vehicletype")
	public ResponseEntity<?> putVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody VehicleTypeModel vehicleType)
	{	
		return vehiclesAdmService.updateVehicleType(id, vehicleType);
	}

	
	/***************************************
	 UNITY_MEASUREMENT :: GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/unitymeasurement")
	public ResponseEntity<?> getCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("country") String country) {	
		return unityMeasurementAdmService.findUnityMeasurementAll(country);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/unitymeasurement")
	public ResponseEntity<?> postCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody UnityMeasurementDTO unityMeasurement)
	{	
		return unityMeasurementAdmService.saveUnityMeasurement(unityMeasurement);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/unitymeasurement")
	public ResponseEntity<?> deleteCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return unityMeasurementAdmService.deleteUnityMeasurement(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/unitymeasurement")
	public ResponseEntity<?> putCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id, 
			@Validated  @RequestBody UnityMeasurementDTO unityMeasurement)
	{	
		return unityMeasurementAdmService.updateUnityMeasurement(id, unityMeasurement);
	}
	
}
