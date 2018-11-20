package de.fiduciagad.okvp.zahlungsverkehr.auftrag.kompetenz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class AuftragsKompetenzHandler {
	public static final double GRENZE_EINZELKOMPETENZ = 10000.00;
	
	private HashMap<String, Auftragskompetenz> userKompetenzen = new HashMap<>();
	private HashMap<EnumAuftragskompetenztyp, ArrayList <String>> kompetenzarten = new HashMap<>();
	
	AuftragsKompetenzHandler() {
		ArrayList<String> kompetenzartEK = new ArrayList<>();
		ArrayList<String> kompetenzartGK = new ArrayList<>();
		ArrayList<String> kompetenzartV = new ArrayList<>();
		userKompetenzen.put("gk4444", new Auftragskompetenz (EnumAuftragskompetenztyp.GEMEINSCHAFTSKOMPETENZ));
		userKompetenzen.put("gk5555", new Auftragskompetenz (EnumAuftragskompetenztyp.GEMEINSCHAFTSKOMPETENZ));
		kompetenzartGK.add("gk4444");
		kompetenzartGK.add("gk5555");
		kompetenzarten.put (EnumAuftragskompetenztyp.GEMEINSCHAFTSKOMPETENZ, kompetenzartGK);
		userKompetenzen.put("ek6666", new Auftragskompetenz (EnumAuftragskompetenztyp.EINZELKOMPETENZ));
		userKompetenzen.put("ek7777", new Auftragskompetenz (EnumAuftragskompetenztyp.EINZELKOMPETENZ));
		kompetenzartEK.add("ek6666");
		kompetenzartEK.add("ek7777");
		kompetenzarten.put(EnumAuftragskompetenztyp.EINZELKOMPETENZ, kompetenzartEK);
		userKompetenzen.put("vorstand", new Auftragskompetenz (EnumAuftragskompetenztyp.VORSTAND));
		kompetenzartV.add("vorstand");
		kompetenzarten.put(EnumAuftragskompetenztyp.VORSTAND, kompetenzartV);
	}
 
	public String gibNaechstenFreigeber(String aktuellerUser, List<String> listVorhandeneFreigaben, BigDecimal betrag) {
		Auftragskompetenz userKompetenz = userKompetenzen.get(aktuellerUser);
		if (userKompetenz.getKompetenzklasse() == EnumAuftragskompetenztyp.VORSTAND)
			return null;

		if (userKompetenz.getKompetenzklasse() == EnumAuftragskompetenztyp.EINZELKOMPETENZ) {
//			if (betrag != null && betrag.doubleValue() > GRENZE_EINZELKOMPETENZ)
//			  return kompetenzarten.get(EnumAuftragskompetenztyp.VORSTAND).get(0);
				return null;			
		}
		if (userKompetenz.getKompetenzklasse() == EnumAuftragskompetenztyp.GEMEINSCHAFTSKOMPETENZ) {
			for (String user: listVorhandeneFreigaben)
			{
				if (!aktuellerUser.equalsIgnoreCase(user)) {
					 // User hat zusammen mit einem der letzten Freigeber die benötigte Kompetenz, keine weitere Freigabe erforderlich.
					 return null;
				}
			}
		}
		for (String user : kompetenzarten.get(EnumAuftragskompetenztyp.GEMEINSCHAFTSKOMPETENZ)) {
			if (!aktuellerUser.equalsIgnoreCase(user)) {   
					return user;
			}
		}
		for (String user : kompetenzarten.get(EnumAuftragskompetenztyp.EINZELKOMPETENZ)){
			if (!aktuellerUser.equalsIgnoreCase(user)) {   
				return user;
			}
		}
	    return kompetenzarten.get(EnumAuftragskompetenztyp.VORSTAND).get(0);
	}
	
	public boolean hatUserKompetenzTeilnahmeFreigabeprozess (String userId, List<String>listVorhandeneFreigaben) {
		Auftragskompetenz kompetenz = userKompetenzen.get(userId);
		return kompetenz != null;
	}
		
}
