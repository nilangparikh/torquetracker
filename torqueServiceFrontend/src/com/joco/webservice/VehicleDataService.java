package com.joco.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.google.inject.Inject;
import com.joco.trackerservice.common.IDataWriter;
import com.joco.trackerservice.common.IRequestProcessor;
import com.joco.trackerservice.common.RequestProcessorException;
import com.joco.trackerservice.datawriter.ConsoleDataWriter;
import com.joco.trackerservice.rabbitmq.RabbitQueueWriter;
import com.joco.trackerservice.requestprocessor.TorqueRequestProcessor;

@Path("/")
public class VehicleDataService
{
	
	private final IRequestProcessor requestProcessor;
	

    @Inject
    VehicleDataService(IRequestProcessor requestProcessor) 
    {
        this.requestProcessor = requestProcessor;
        
    }

	
	@GET
	@Path("/torque")
	public Response processTorqueData( 
			  @Context
			  Request request,
			  @Context
			  UriInfo uriInfo)
	{
		
		MultivaluedMap<String, String> requestData = uriInfo.getQueryParameters();
		Map<String,String> dataToBeProcessed = new HashMap<String,String>();
		
		for (String key : requestData.keySet())
		{
			dataToBeProcessed.put(key, requestData.getFirst(key));
		}
		
		
		try
		{
			requestProcessor.processRequest(dataToBeProcessed);
		}
		catch (RequestProcessorException ex)
		{
			return Response.serverError().build();
			//Log
		}
		
		return Response.ok().build();
		
	}
}
