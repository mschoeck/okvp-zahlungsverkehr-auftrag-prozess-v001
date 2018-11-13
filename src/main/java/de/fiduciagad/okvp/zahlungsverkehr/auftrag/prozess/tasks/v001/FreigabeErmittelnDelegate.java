package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.tasks.v001;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Prozesssauftrag;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.DelegateAuftragskompetenz;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001.ProzessVariablen;

public class FreigabeErmittelnDelegate implements JavaDelegate{

	private final static Logger LOGGER = Logger.getLogger(FreigabeErmittelnDelegate.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("execute gerufen für: " + execution.getId());
		DelegateExecution processExecution = execution.getProcessInstance();
		processExecution.getVariables().entrySet().stream().forEach(entry -> LOGGER.info(entry.toString()));
		
		ArrayList<String> listVorhandeneFreigaben = (ArrayList<String>) processExecution.getVariable(ProzessVariablen.STR_FREIGEBER_LISTE);
		String naechsterFreigeber = DelegateAuftragskompetenz.getDelegateKompetez().gibNächstenFreigeber(new Prozesssauftrag(), listVorhandeneFreigaben);
		
		if (naechsterFreigeber != null) {
			variablenFuerNeueFreigabeZuruecksetzen(processExecution, naechsterFreigeber);
		}
		else {
			processExecution.setVariable(ProzessVariablen.FLG_WEITERE_FREIGABE_ERFORDERLICH, Variables.booleanValue(false));
		}
	}

	private void variablenFuerNeueFreigabeZuruecksetzen(DelegateExecution processExecution, String freigeberId) {
		processExecution.setVariable(ProzessVariablen.STR_FREIGEBER_ID, Variables.stringValue(freigeberId));
		processExecution.setVariable(ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(false));
		processExecution.setVariable(ProzessVariablen.FLG_WEITERE_FREIGABE_ERFORDERLICH, Variables.booleanValue(true));
	}
}
