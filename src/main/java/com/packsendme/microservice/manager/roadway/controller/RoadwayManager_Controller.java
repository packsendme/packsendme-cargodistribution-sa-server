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

import com.packsendme.microservice.manager.roadway.service.BodyworkManager_Service;
import com.packsendme.microservice.manager.roadway.service.CategoryManager_Service;
import com.packsendme.microservice.manager.roadway.service.LocationManager_Service;
import com.packsendme.microservice.manager.roadway.service.RoadwayManager_Service;
import com.packsendme.microservice.manager.roadway.service.TransportManager_Service;
import com.packsendme.microservice.manager.roadway.service.VehicleManager_Service;
import com.packsendme.microservice.manager.roadway.service.VehicleTypeManager_Service;
import com.packsendme.roadway.bre.model.businessrule.RoadwayBRE;
import com.packsendme.roadway.bre.model.category.Category;
import com.packsendme.roadway.bre.model.location.Location;
import com.packsendme.roadway.bre.model.transport.Transport;
import com.packsendme.roadway.bre.model.vehicle.BodyworkRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleRule;
import com.packsendme.roadway.bre.model.vehicle.VehicleType;

@RestController
@RequestMapping("/roadway/manager")
public class RoadwayManager_Controller {

	
	@Autowired
	private VehicleManager_Service vehiclesService;
	
	@Autowired
	private CategoryManager_Service categoryService;	
	
	@Autowired
	private BodyworkManager_Service bodyworkService;	
	
	@Autowired
	private RoadwayManager_Service roadwayService;	

	@Autowired
	private LocationManager_Service locationService;	

	@Autowired
	private VehicleTypeManager_Service vehiclesAdmService;

	@Autowired
	private TransportManager_Service transportService;
	
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
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleRule vehicle)
	{	
		return vehiclesService.saveVehicles(vehicle);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/vehicle")
	public ResponseEntity<?> deleteVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return vehiclesService.deleteVehicles(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/vehicle")
	public ResponseEntity<?> putVehicle(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody VehicleRule vehicle)
	{	
		return vehiclesService.updateVehicleCheck(id, vehicle);
	}

	/***************************************
	 VEHICLE_TYPE :: GET | POST | DELETE 
	 ***************************************/
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/vehicletype")
	public ResponseEntity<?> getVehicleType(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return vehiclesAdmService.findVehiclesTypeAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/vehicletype")
	public ResponseEntity<?> postVehicleType(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated  @RequestBody VehicleType vehicleType)
	{	
		return vehiclesAdmService.saveVehiclesType(vehicleType);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/vehicletype")
	public ResponseEntity<?> deleteVehicleType(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return vehiclesAdmService.deleteVehiclesType(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/vehicletype")
	public ResponseEntity<?> putVehicleType(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody VehicleType vehicleType)
	{	
		return vehiclesAdmService.updateVehicleType(id, vehicleType);
	}
	
	/***************************************
	 CATEGORY :: GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/category")
	public ResponseEntity<?> getCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return categoryService.findCategoryAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/category")
	public ResponseEntity<?> postCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody Category category){	
		return categoryService.saveCategory(category);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/category")
	public ResponseEntity<?> deleteCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return categoryService.deleteCategory(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/category")
	public ResponseEntity<?> putCategory(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id, 
			@Validated  @RequestBody Category category)
	{	
		return categoryService.updateCategory(id, category);
	}
	

	/***************************************
	 BODYWORK (RELASHIONSHIP - VEHICLE) <--> GET | POST | DELETE 
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
			@Validated  @RequestBody BodyworkRule bodywork)
	{	
		return bodyworkService.saveBodywork(bodywork);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/bodywork")
	public ResponseEntity<?> deleteBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return bodyworkService.deleteBodywork(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/bodywork")
	public ResponseEntity<?> putBodywork(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id, 
			@Validated  @RequestBody BodyworkRule bodywork)
	{	
		return bodyworkService.updateBodywork(id,bodywork);
	}

	
	/***************************************
	 ROADWAY <--> GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/roadwaybre")
	public ResponseEntity<?> getRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return roadwayService.findRoadwayAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/roadwaybre")
	public ResponseEntity<?> postRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody RoadwayBRE roadwayBRE)
	{	
		return roadwayService.saveRoadway(roadwayBRE);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/roadwaybre")
	public ResponseEntity<?> deleteRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return roadwayService.deleteRoadway(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/roadwaybre")
	public ResponseEntity<?> updateRoadway(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody RoadwayBRE roadwayBRE)
	{	
		return roadwayService.updateRoadway(id, roadwayBRE);
	}
	

	/***************************************
	 TRANSPORT <--> GET | POST | DELETE 
	***************************************/
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/transport")
	public ResponseEntity<?> getTransport(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return transportService.findTransportAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/transport")
	public ResponseEntity<?> postTransport(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody Transport transport)
	{	
		return transportService.saveTransport(transport);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/transport")
	public ResponseEntity<?> deleteTransport(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return transportService.deleteTransport(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/transport")
	public ResponseEntity<?> updateTransport(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody Transport transport)
	{	
		return transportService.updateTransport(id, transport);
	}

	
	/***************************************
	 LOCATION <--> GET | POST | DELETE 
	***************************************/


	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/location")
	public ResponseEntity<?> getLocation(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return locationService.findLocationAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/location")
	public ResponseEntity<?> postLocation(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, 
			@Validated  @RequestBody Location location)
	{	
		return locationService.saveLocation(location);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/location")
	public ResponseEntity<?> deleteLocation(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return locationService.deleteLocation(id);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/location")
	public ResponseEntity<?> updateLocation(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id,
			@Validated  @RequestBody Location location)
	{	
		return locationService.updateLocation(id, location);
	}


}
