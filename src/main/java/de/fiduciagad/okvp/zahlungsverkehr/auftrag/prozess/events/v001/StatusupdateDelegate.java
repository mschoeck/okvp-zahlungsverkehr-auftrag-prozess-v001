package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.events.v001;

import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Auftragsstatus;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.DelegateAuftrag;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001.ProzessVariablen;

public class StatusupdateDelegate implements JavaDelegate{

	private final static Logger LOGGER = Logger.getLogger(StatusupdateDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("execute gerufen für: " + execution.getId());
		String eventName = execution.getBpmnModelElementInstance().getName();
		LOGGER.info("status: " + eventName);
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		runtimeService.setVariable(execution.getProcessInstanceId(), ProzessVariablen.STR_LETZTE_STATUS_MSG, Variables.stringValue(eventName));
		Long auftragid = (Long) runtimeService.getVariable(execution.getProcessInstanceId(), ProzessVariablen.LON_AUFTRAGSID);

		DelegateAuftrag.getDelegateAuftrag().sendeStatusÄnderung(new Auftragsstatus(auftragid, execution.getProcessInstanceId(), eventName));
	}
}
