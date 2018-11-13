package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

import java.util.logging.Logger;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.error.v001.ErrorHanlder;

/**
 * Kapselt die Methodenaufrufe zum Auftragsservice
 * @author xcm1030
 *
 */
public class DelegateAuftrag {
	
	private final static Logger LOGGER = Logger.getLogger(DelegateAuftrag.class.getName());
	
	private final static DelegateAuftrag delegateAuftrag = new DelegateAuftrag();

	private DelegateAuftrag() {
	}
	
	public static DelegateAuftrag getDelegateAuftrag()
	{
		return delegateAuftrag;
	}
	
	/**
	 * Benachrichtige die Auftragsverwaltung +ber eine Statusänderung
	 * @param status
	 */
	public void sendeStatusÄnderung(Auftragsstatus status)
	{
		// Todo korreter Aufruf des Auftragsservices in AuftragServiceProviderDelegate
		
	}
	
	
}
