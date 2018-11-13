package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001;

/**
 * Konstanten des Freigabeprozesses.
 * @author xcm1030
 *
 */
public interface ProzessKonstanten {
	/* Key des Prozesses, mit dem er gestartet werden kann */
	public static final String KEY_PROZESS_AUFTRAGSFREIGABE="P_Auftragsfreigabe";

	/* Events, die im Prozess für Messaging hinterlegt sind */
	public static final String TOPIC_AUFTRAGSTATUS= "Auftragstatus";
	public static final String MESSAGE_AUFTRAGSTATUS_OFFEN = "Msg_Auftragstatus_Offen";
	public static final String MESSAGE_AUFTRAGSTATUS_FREIGEGEBEN = "Msg_Auftragstatus_Freigegeben";
	public static final String MESSAGE_AUFTRAGSTATUS_ABGELEHNT = "Msg_Auftragstatus_Abgelehnt";
	public static final String MESSAGE_AUFTRAGSTATUS_IN_FREIGABE ="Msg_Auftragstatus_InFreigabe";
	public static final String MESSAGE_AUFTRAGSTATUS_IN_WEITERER_FREIGABE = "Msg_Auftragstatus_InWeitererFreigabe";
	public static final String MESSAGE_AUFTRAGSTATUS_ABGElAUFEN= "Msg_Auftragsstatus_Abgelaufen";
	public static final String MESSAGE_AUFTRAGSTATUS_FEHLERHAFT= "Msg_Auftragstatus_Fehlerhaft";
	
	/* Topics, die im Prozess für Serviceaufrufe hinterlegt sind */
    public static final String TOPIC_FREIGEBER_ERMITTELN = "cmdFreigeberErmitteln";
		
	/* Form Keys */
	public static final String FORM_AUFTRAGSFREIGABE = "Auftragsfreigabe";
}
