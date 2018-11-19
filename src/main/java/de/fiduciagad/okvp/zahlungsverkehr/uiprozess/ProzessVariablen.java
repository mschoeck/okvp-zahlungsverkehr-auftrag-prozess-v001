package de.fiduciagad.okvp.zahlungsverkehr.uiprozess;

public interface ProzessVariablen {
	
	public static final String PROZESS_AUFTRAGSBEARBEITUNG = "P_Auftragsbearbeitung";
	public static final String VAR_START_USERID = "startUserid";
	public static final String VAR_AKTION = "aktion";
	
	public static final String SCHRITT_LETZTER = "empty";
	public static final String AKTION_DEFAULT ="default";
	public static final String AKTION_FREIGEBEN ="freigeben";
	
	public static final String AKTION_EINGEREICHT ="eingereichteAuftraege";
	public static final String AKTION_OFFEN= "offeneAuftraege";
	
	//public static final String AKTION_LOESCHEN = "loeschen";
}
