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
import com.packsendme.microservice.manager.roadway.dao.TransportDAO;
import com.packsendme.microservice.manager.roadway.dto.TransportListDTO_Response;
import com.packsendme.microservice.manager.roadway.repository.TransportModel;
import com.packsendme.roadway.bre.model.transport.Transport;

@Service
@ComponentScan({"com.packsendme.microservice.manager.roadway.dao","com.packsendme.microservice.manager.roadway.component"})
public class TransportManager_Service {
	
	@Autowired
	private TransportDAO transportDAO;
	@Autowired
	private ParseComponent parserObj;

	public ResponseEntity<?> findTransportAll() {
		Response<TransportListDTO_Response> responseObj = null;
		try {
			TransportListDTO_Response TransportListDTO = new TransportListDTO_Response(transportDAO.findAll());
			responseObj = new Response<TransportListDTO_Response>(0,HttpExceptionPackSend.CREATE_TRANSPORT.getAction(), TransportListDTO);
			return new ResponseEntity<>(responseObj, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<TransportListDTO_Response>(0,HttpExceptionPackSend.CREATED_BODYWORK.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> saveTransport(Transport transport) {
		Response<TransportModel> responseObj = null;
		try {
			TransportModel entity = parserObj.parserTransport_TO_Model(transport, null, RoadwayManagerConstants.ADD_OP_ROADWAY);
			responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.CREATE_TRANSPORT.getAction(), entity);
			if(transportDAO.findOneByName(transport.name_transport) == null) {
				transportDAO.save(entity);
				return new ResponseEntity<>(responseObj, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.CREATE_TRANSPORT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}

	public ResponseEntity<?> deleteTransport(String id) {
		Response<TransportModel> responseObj = null;
		try {
			Optional<TransportModel> transportData = transportDAO.findOneById(id);
			if(transportData.isPresent()) {
				TransportModel transportEntity = transportData.get();
				if(transportDAO.remove(transportEntity) == true) {
					responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.DELETE_TRANSPORT.getAction(), transportData.get());
					return new ResponseEntity<>(responseObj, HttpStatus.OK);
				}
				else {
					responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.DELETE_TRANSPORT.getAction(), transportData.get());
					return new ResponseEntity<>(responseObj, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			else {
				responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.DELETE_TRANSPORT.getAction(), transportData.get());
				return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<TransportModel>(0,HttpExceptionPackSend.FAIL_EXECUTION.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	
	public ResponseEntity<?> updateTransport(String id, Transport transport) {
		Response<String> responseObj = null;
		try {
			// Check if exist same Transport in Database
			TransportModel transportModelFindName = transportDAO.findOneByIdAndName(id, transport.name_transport);

			if(transportModelFindName == null) {
				Optional<TransportModel> transportData = transportDAO.findOneById(id);
				if(transportData.isPresent()) {
					TransportModel transportEntity = transportData.get();
					TransportModel transportEntityUp = parserObj.parserTransport_TO_Model(transport, transportEntity, RoadwayManagerConstants.UPDATE_OP_ROADWAY);
					transportDAO.update(transportEntityUp);
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_TRANSPORT.getAction(), transportEntity.name_transport);
					return new ResponseEntity<>(responseObj, HttpStatus.ACCEPTED);
				}
				else {
					responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_TRANSPORT.getAction(), null);
					return new ResponseEntity<>(responseObj, HttpStatus.NOT_FOUND);
				}
			}
			else {
				responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_TRANSPORT.getAction(), transport.name_transport);
				return new ResponseEntity<>(responseObj, HttpStatus.FOUND);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			responseObj = new Response<String>(0,HttpExceptionPackSend.UPDATE_TRANSPORT.getAction(), null);
			return new ResponseEntity<>(responseObj, HttpStatus.BAD_REQUEST);
		}
	}
	

}
