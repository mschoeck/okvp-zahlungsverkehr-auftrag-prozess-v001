package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import java.io.Serializable;

/** 
 * Response vom Prozess für Client
 * @param prozesId Ermöglicht dem Client den Zugriff auf die Prozess-API
 * @param schrittid Ermöglicht dem Client den Zugriff auf die Prozessaufgabe über Prozess-API
 * @param schrittart Darüber erkennt der Client die nächste Maske
 * @author xcm1030
 *
 */
public class ZahlungsauftragFreigabeschritt implements Serializable {
	
	private static final long serialVersionUID = -5369788542977662260L;
	
	private Long auftragid;
	private String schrittid;
	private String schrittart;
	
	public ZahlungsauftragFreigabeschritt() {
	}
	
	public ZahlungsauftragFreigabeschritt(Long auftragid, String schrittid, String schrittart) {
		this.auftragid = auftragid;
		this.schrittid = schrittid;
		this.schrittart = schrittart;
	}
	
	public Long getAuftragid() {
		return auftragid;
	}
	public void setAuftragid(Long prozessId) {
		this.auftragid = prozessId;
	}
	public String getSchrittid() {
		return schrittid;
	}
	public void setSchrittid(String aufgabeId) {
		this.schrittid = aufgabeId;
	}
	public String getSchrittart() {
		return schrittart;
	}
	public void setSchrittart(String aufgabeArt) {
		this.schrittart = aufgabeArt;
	}
}
