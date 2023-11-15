package com.vnay.test.incubation;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.kie.kogito.incubation.common.MapDataContext;

import com.vnay.test.incubation.service.IncubationRestService;

@Path("/customProcessService")
public class CustomIncubationRestAPI {

@Inject
IncubationRestService incubationRestService;

@POST
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public Map<String,Object> getResponse(@RequestBody(required = true) Map<String,Object> request) {
	MapDataContext responseMap = (MapDataContext) incubationRestService.customProcessInvocation(request, (String)request.get("processName"));
	return responseMap.toMap();
}
}
