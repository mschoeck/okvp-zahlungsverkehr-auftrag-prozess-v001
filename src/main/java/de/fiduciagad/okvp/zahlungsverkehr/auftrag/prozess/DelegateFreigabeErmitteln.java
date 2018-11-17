package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess;

import java.util.ArrayList;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.kompetenz.AuftragsKompetenzHandler;

@Controller
public class DelegateFreigabeErmitteln implements JavaDelegate{
	@Autowired
	private AuftragsKompetenzHandler kompetenzhandler;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DelegateExecution processExecution = execution.getProcessInstance();
		
		ArrayList<String> listVorhandeneFreigaben = (ArrayList<String>) processExecution.getVariable(ProzessVariablen.STR_FREIGEBER_LISTE);
		String defaultFreigeber = (String) processExecution.getVariable(ProzessVariablen.STR_AUFTRAGSERFASSERID);
		String naechsterFreigeber = kompetenzhandler.gibNächstenFreigeber(defaultFreigeber, listVorhandeneFreigaben);
		
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
