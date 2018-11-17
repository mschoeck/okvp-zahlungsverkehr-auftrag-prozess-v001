package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess;

/**
 * Variablen im Freigabeprozess
 * @author xcm1030
 *
 */
public interface ProzessVariablen {
	public static final String LON_AUFTRAGSID = "AuftragsId";
	public static final String DAT_AUFTRAGSABLAUF = "Auftragsablauf";
	public static final String STR_AUFTRAGSERFASSERID = "AuftragserfasserId";
	public static final String DBL_BETRAG = "betrag";
	public static final String STR_LETZTER_AUFTRAGSSTATUS = "letzteStatusMessage";
	
	public static final String FLG_FREIGABE_ERFOLGT ="FreigabeErfolgt";
	public static final String FLG_WEITERE_FREIGABE_ERFORDERLICH = "WeitereFreigabeErforderlich";
	public static final String STR_FREIGEBER_ID = "FreigeberId";
	public static final String STR_FREIGEBER_LISTE = "FreigeberListe";
}
