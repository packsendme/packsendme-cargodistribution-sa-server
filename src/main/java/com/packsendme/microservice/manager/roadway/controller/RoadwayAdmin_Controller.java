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
import com.packsendme.microservice.manager.roadway.service.InitialsAdm_Service;
import com.packsendme.microservice.manager.roadway.service.UnityMeasurementAdm_Service;
import com.packsendme.roadway.bre.model.transport.Initials;

@RestController
@RequestMapping("/roadway/admin")
public class RoadwayAdmin_Controller {

	@Autowired
	private UnityMeasurementAdm_Service unityMeasurementAdmService;	
	

	@Autowired
	private InitialsAdm_Service initialsAdmService;	

	/***************************************
	 UNITY_MEASUREMENT :: GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/unitymeasurement")
	public ResponseEntity<?> getUnityMeasurement(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("country") String country) {	
		return unityMeasurementAdmService.findUnityMeasurementAll(country);
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/unitymeasurement")
	public ResponseEntity<?> postUnityMeasurement(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody UnityMeasurementDTO unityMeasurement)
	{	
		return unityMeasurementAdmService.saveUnityMeasurement(unityMeasurement);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/unitymeasurement")
	public ResponseEntity<?> deleteUnityMeasurement(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return unityMeasurementAdmService.deleteUnityMeasurement(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/unitymeasurement")
	public ResponseEntity<?> putUnityMeasurement(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id, 
			@Validated  @RequestBody UnityMeasurementDTO unityMeasurement)
	{	
		return unityMeasurementAdmService.updateUnityMeasurement(id, unityMeasurement);
	}
	
	/***************************************
	 INITIALS :: GET | POST | DELETE 
	***************************************/

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping("/initials")
	public ResponseEntity<?> getInitials(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp) {	
		return initialsAdmService.findInitialsAll();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/initials")
	public ResponseEntity<?> postInitials(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp,
			@Validated  @RequestBody Initials initials)
	{	
		return initialsAdmService.save(initials);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@DeleteMapping("/initials")
	public ResponseEntity<?> deleteInitials(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id)
	{	
		return initialsAdmService.delete(id);
	}
	
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PutMapping("/initials")
	public ResponseEntity<?> putInitials(@RequestHeader("isoLanguageCode") String isoLanguageCode,@RequestHeader("isoCountryCode") String isoCountryCode,
			@RequestHeader("isoCurrencyCode") String isoCurrencyCode,@RequestHeader("originApp") String originApp, @Validated @RequestParam("id") String id, 
			@Validated  @RequestBody Initials initials)
	{	
		return initialsAdmService.update(id, initials);
	}

}
