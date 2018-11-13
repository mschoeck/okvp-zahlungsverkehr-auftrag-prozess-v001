package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

/**
 * Request vom Prozess bei Statusänderung.
 * Austauschobjekt zur Kommunikation einer Statusänderung zur Auftragskomponente.
 * Wird in der Regel von der serverseitigen Auftragskompenente verwendet.
 * @author xcm1030
 *
 */
public class Auftragsstatus {
	private Long auftragsid;
	private String prozessid;
	private String auftragsstatus;
	
	public Auftragsstatus() {
	}
	
	public Auftragsstatus(Long auftragsid, String prozessid, String auftragsstatus) {
		this.auftragsid = auftragsid;
		this.prozessid = prozessid;
		// Todo: schoener als EM modellieren
		this. auftragsstatus = auftragsstatus;
	}
	
	public Long getAuftragsid() {
		return auftragsid;
	}
	
	public void setAuftragsid(Long auftragsid) {
		this.auftragsid = auftragsid;
	}
	
	public String getAuftragsstatus() {
		return auftragsstatus;
	}
	public void setAuftragsstatus(String auftragsstatus) {
		this.auftragsstatus = auftragsstatus;
	}

	public String getProzessid() {
		return prozessid;
	}

	public void setProzessid(String prozessid) {
		this.prozessid = prozessid;
	}
}
