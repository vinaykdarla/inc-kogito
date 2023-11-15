package com.vnay.test.incubation.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kie.kogito.incubation.application.AppRoot;
import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.processes.ProcessIds;

@ApplicationScoped
public class IncubationRestService {
	@Inject
    AppRoot appRoot;
    @Inject
    IncubationRestServiceImpl incubationRestServiceImpl;

    public DataContext customProcessInvocation(Map<String, Object> payload, String processName) {
         MapDataContext ctx = MapDataContext.of(payload);
         return incubationRestServiceImpl.evaluate(appRoot.get(ProcessIds.class).get(processName), ctx);
    }

}
