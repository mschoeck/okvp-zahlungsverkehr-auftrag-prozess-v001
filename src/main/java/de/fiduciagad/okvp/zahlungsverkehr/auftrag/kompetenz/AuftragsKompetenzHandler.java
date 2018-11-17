package de.fiduciagad.okvp.zahlungsverkehr.auftrag.kompetenz;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.ZahlungsauftraegeController;

@Component
public class AuftragsKompetenzHandler {
	
	private final static Logger LOGGER = Logger.getLogger(AuftragsKompetenzHandler.class.getName());
	
	public AuftragsKompetenzHandler() {
	}
 
	public boolean hatUserKompetenzFuerFreigabe(String userId) {
		return true;
	}
		
	public String gibNächstenFreigeber(String defaultFreigeber, List<String> listVorhandeneFreigaben) {
		if (listVorhandeneFreigaben == null || listVorhandeneFreigaben.size() == 0) {
			LOGGER.info("Freigeber = " + defaultFreigeber);
			return defaultFreigeber;
		}
			
		String aktuellsterFreigeber = listVorhandeneFreigaben.get(listVorhandeneFreigaben.size() - 1);
		String naechsterFreigeber = null;
		if ("ycm4444".equals(aktuellsterFreigeber)) {
			naechsterFreigeber = "ycm5555";
		}
		else if ("ycm5555".equals(aktuellsterFreigeber)){
			naechsterFreigeber = "ycm6666";
		} 
		LOGGER.info("Letzter Freigeber = " + aktuellsterFreigeber + " Nächster Freigeber = " + naechsterFreigeber);
		
		return naechsterFreigeber;
	}
}
