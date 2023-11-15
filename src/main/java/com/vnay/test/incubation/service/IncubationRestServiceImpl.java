package com.vnay.test.incubation.service;

import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.kie.kogito.Model;
import org.kie.kogito.incubation.common.DataContext;
import org.kie.kogito.incubation.common.Id;
import org.kie.kogito.incubation.common.LocalUri;
import org.kie.kogito.incubation.common.MapDataContext;
import org.kie.kogito.incubation.processes.LocalProcessId;
import org.kie.kogito.incubation.processes.services.StraightThroughProcessService;
import org.kie.kogito.process.Process;
import org.kie.kogito.process.ProcessInstance;
import org.kie.kogito.process.Processes;

import com.vnay.test.incubation.util.IncubationRestMapper;

@ApplicationScoped
public class IncubationRestServiceImpl implements StraightThroughProcessService{
	@Inject
	Instance<Processes> processesInstance;

	@Override
	public DataContext evaluate(Id processId, DataContext inputContext) {
		Processes processes = processesInstance.get();
		LocalUri localUri = processId.toLocalId().asLocalUri();
		if(localUri.startsWith(LocalProcessId.PREFIX)) {
			Process<? extends Model> process = processes.processById(((LocalProcessId)processId).processId());
			 MapDataContext mdc = inputContext.as(MapDataContext.class);
	            Class<? extends Model> modelType = process.createModel().getClass();
	            Model model = IncubationRestMapper.convertValue(mdc, modelType);
	            ProcessInstance<? extends Model> instance = process.createInstance(model);
	            instance.start();
	            Map<String, Object> map = instance.variables().toMap();
	            return MapDataContext.of(map);
		}
		
		return null;
	}

}
