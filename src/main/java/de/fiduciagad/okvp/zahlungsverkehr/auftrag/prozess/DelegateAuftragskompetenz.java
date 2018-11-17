package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.variable.Variables;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001.ProzessVariablen;

/**
 * Kapselt die Methoden, die etwas mit Kompetenz und Kompetenzträgerermittlung zu tun haben.
 * @author xcm1030
 *
 */
public class DelegateAuftragskompetenz {
	
	private final static Logger LOGGER = Logger.getLogger(DelegateAuftragskompetenz.class.getName());
	
	private final static DelegateAuftragskompetenz delegateKompetenz = new DelegateAuftragskompetenz();
	
	private DelegateAuftragskompetenz() {
	}
	
	public static DelegateAuftragskompetenz getDelegateKompetez()
	{
		return delegateKompetenz;
	}
	/**
	 * Vor jeder Freigabe mauss die Kompetenz erneut geprüft weren, da dem aufrufenden
	 * Client nicht getraut werden kann.
	 * @param userId
	 * @return
	 */
	public boolean hatUserKompetenzFuerFreigabe(String userId) {
		return true;
	}
		
	public String gibNächstenFreigeber(Prozesssauftrag auftrag, List<String> listVorhandeneFreigaben) {
		if (listVorhandeneFreigaben == null || listVorhandeneFreigaben.size() == 0) {
			return auftrag.getErfasserid();
		}
			
		String aktuellsterFreigeber = listVorhandeneFreigaben.get(listVorhandeneFreigaben.size() - 1);
		if ("xcm4444".equals(aktuellsterFreigeber)) {
			return("xcm5555");
		}
		else if ("xcm5555".equals(aktuellsterFreigeber)){
			return("xcm6666");
		} 
		else {
			return null;
		}
	}
}
