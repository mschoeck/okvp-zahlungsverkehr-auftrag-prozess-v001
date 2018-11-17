package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess;

import java.util.Optional;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.Zahlungsauftrag;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.ZahlungsauftragRepository;

@Controller
public class DelegateStatusupdate implements JavaDelegate{
	
	@Autowired
	private ZahlungsauftragRepository repository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		String eventName = execution.getBpmnModelElementInstance().getName();
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		runtimeService.setVariable(execution.getProcessInstanceId(), ProzessVariablen.STR_LETZTER_AUFTRAGSSTATUS, Variables.stringValue(eventName));
		Long auftragid = (Long) runtimeService.getVariable(execution.getProcessInstanceId(), ProzessVariablen.LON_AUFTRAGSID);

		Optional<Zahlungsauftrag> auftrag = repository.findById(auftragid);
		if (auftrag.isPresent())
		{
			auftrag.get().setStatus(eventName);
		}
	}
}
