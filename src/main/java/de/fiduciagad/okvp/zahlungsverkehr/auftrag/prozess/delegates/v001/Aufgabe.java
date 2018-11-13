package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

/** 
 * Response vom Prozess für Client
 * @param prozesId Ermöglicht dem Client den Zugriff auf die Prozess-API
 * @param aufgabeid Ermöglicht dem Client den Zugriff auf die Prozessaufgabe über Prozess-API
 * @param aufgabeart Darüber erkennt der Client die nächste Maske
 * @author xcm1030
 *
 */
public class Aufgabe {
	private String prozessid;
	private String aufgabeid;
	private String aufgabeart;
	
	public Aufgabe() {
	}
	
	public Aufgabe(String prozessId, String aufgabeId, String aufgabeArt) {
		this.prozessid = prozessId;
		this.aufgabeid = aufgabeId;
		this.aufgabeart = aufgabeArt;
	}
	
	public String getProzessid() {
		return prozessid;
	}
	public void setProzessid(String prozessId) {
		this.prozessid = prozessId;
	}
	public String getAufgabeid() {
		return aufgabeid;
	}
	public void setAufgabeid(String aufgabeId) {
		this.aufgabeid = aufgabeId;
	}
	public String getAufgabeart() {
		return aufgabeart;
	}
	public void setAufgabeart(String aufgabeArt) {
		this.aufgabeart = aufgabeArt;
	}
}
