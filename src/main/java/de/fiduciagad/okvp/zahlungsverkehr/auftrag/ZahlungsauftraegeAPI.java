package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface ZahlungsauftraegeAPI {

	/** 
	 * Offene Aufträge für einen Benutzer ermitteln 
	 * */
	ResponseEntity<List<Zahlungsauftrag>> getAuftraegeOffen(String userid);

	/** 
	 * Vom Benutzer eingereichte und noch nicht abgeschlossene Aufträge ermitteln
	 * @return
	 */
	ResponseEntity<List<Zahlungsauftrag>> getAuftraegeEingereicht(String userid);

	/**
	 * Details zu einem Auftrag laden
	 * @return
	 */
	ResponseEntity<Zahlungsauftrag> getAuftragById(Long id);

	/** 
	 * Nächste Aktion bei der Freigabe ermitteln
	 * @return
	 */
	ResponseEntity<ZahlungsauftragFreigabeschritt> getAuftragsfreigabeNaechsterSchritt(Long id, String userid);

	/**
	 * Auftrag freigeben
	 */
	ResponseEntity<ZahlungsauftragFreigabeschritt> auftragFreigeben(Long id, String schrittid, String userid);

	/**
	 * Auftrag ablehnen
	 */
	ResponseEntity<Long> auftragAblehnen(Long id, String schrittid, String userid);

}