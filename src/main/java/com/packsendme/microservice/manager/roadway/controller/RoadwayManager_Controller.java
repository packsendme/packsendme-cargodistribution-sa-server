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
import org.springframework.web.bind.annotation.RestController;

import com.packsendme.microservice.manager.roadway.service.BodyworkManager_Service;
import com.packsendme.microservice.manager.roadway.service.CategoryManager_Service;
import com.packsendme.microservice.manager.roadway.service.RoadwayManager_Service;
import com.packsendme.microservice.manager.roadway.service.VehicleManager_Service;
import com.packsendme.roadway.bre.model.businessrule.BusinessRuleRoadwayBRE;
import com.packsendme.roadway.bre.model.category.CategoryBRE;
import com.packsendme.roadway.bre.model.vehicle.BodyworkBRE;
import com.packsendme.roadway.bre.model.vehicle.VehicleBRE;


@RestController
@RequestMapping("/manager/roadway")
public class RoadwayManager_Controller {

	@Autowired
	private VehicleManager_Service vehiclesService;
	
	@Autowired
	private CategoryManager_Service categoryService;	
	
	@Autowired
	private BodyworkManager_Service bodyworkService;	
	
	@Autowired
	private RoadwayManager_Service roadwayService;	
	
	/***************************************
	 VEHICLE :: GET | POST | DELETE 
	 ***************************************/
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/vehicle")
	public ResponseEntity<?> getVehicles(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return vehiclesService.findVehiclesAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/vehicle")
	public ResponseEntity<?> postVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleBRE vehicle)
	{	
		return vehiclesService.saveVehicles(vehicle);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/vehicle")
	public ResponseEntity<?> deleteVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleBRE vehicle)
	{	
		return vehiclesService.deleteVehicles(vehicle);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/vehicle")
	public ResponseEntity<?> putVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleBRE vehicle)
	{	
		return vehiclesService.updateVehicle(vehicle);
	}

	
	/***************************************
	 CATEGORY :: GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/vehicle")
	public ResponseEntity<?> getCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return categoryService.findCategoryAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/category")
	public ResponseEntity<?> postCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody CategoryBRE category)
	{	
		return categoryService.saveCategory(category);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/category")
	public ResponseEntity<?> deleteCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody CategoryBRE category)
	{	
		return categoryService.deleteCategory(category);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/category")
	public ResponseEntity<?> putCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody CategoryBRE category)
	{	
		return categoryService.updateCategory(category);
	}
	
	/***************************************
	 BODYWORK <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/bodywork")
	public ResponseEntity<?> getBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return bodyworkService.findBodyworkAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/bodywork")
	public ResponseEntity<?> postBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody BodyworkBRE bodywork)
	{	
		return bodyworkService.saveBodywork(bodywork);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/bodywork")
	public ResponseEntity<?> deleteBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody BodyworkBRE bodywork)
	{	
		return bodyworkService.deleteBodywork(bodywork);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/bodywork")
	public ResponseEntity<?> putBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody BodyworkBRE bodywork)
	{	
		return bodyworkService.updateBodywork(bodywork);
	}

	
	/***************************************
	 ROADWAY <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/roadway")
	public ResponseEntity<?> getRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return roadwayService.findRoadwayAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/roadway")
	public ResponseEntity<?> postRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody BusinessRuleRoadwayBRE roadwayBRE)
	{	
		return roadwayService.saveRoadway(roadwayBRE);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/roadway")
	public ResponseEntity<?> deleteRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody BusinessRuleRoadwayBRE roadwayBRE)
	{	
		return roadwayService.deleteRoadway(roadwayBRE);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadway")
	public ResponseEntity<?> updateRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody BusinessRuleRoadwayBRE roadwayBRE)
	{	
		return roadwayService.updateRoadway(roadwayBRE);
	}

}
