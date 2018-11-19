package de.fiduciagad.okvp.zahlungsverkehr.uiprozess;

import java.io.Serializable;
import java.util.ArrayList;

/** 
 * Response vom Prozess für Client
 * @param prozesId Ermöglicht dem Client den Zugriff auf die Prozess-API
 * @param schrittid Ermöglicht dem Client den Zugriff auf die Prozessaufgabe über Prozess-API
 * @param ui-service Darüber erkennt der Client die nächste Maske
 * @author xcm1030
 *
 */
public class Bedienerschritt implements Serializable {
	
	private static final long serialVersionUID = -5369788542977662260L;
	
	private String prozessid;
	private String schrittid;
	private String uiservice;
	private String uiserviceName;
	private String[] buttonnames = {" "};
	private String[] buttonvariables = {" "};
	
	public Bedienerschritt() {
	}
	
	public Bedienerschritt(String prozessid, String schrittid, String uiservice) {
		this.prozessid = prozessid;
		this.schrittid = schrittid;
		this.uiservice = uiservice;
	}
	
	public String getProzessid() {
		return prozessid;
	}
	public void setProzessid(String prozessId) {
		this.prozessid = prozessId;
	}
	public String getSchrittid() {
		return schrittid;
	}
	public void setSchrittid(String aufgabeId) {
		this.schrittid = aufgabeId;
	}
	public String getUiservice() {
		return uiservice;
	}
	public void setUiservice(String uiservice) {
		this.uiservice = uiservice;
	}

	public String[] getButtonnames() {
		return buttonnames;
	}

	public void setButtonnames(String[] buttons) {
		this.buttonnames = buttons;
	}

	public String[] getButtonvariables() {
		return buttonvariables;
	}

	public void setButtonvariables(String[] buttonvariables) {
		this.buttonvariables = buttonvariables;
	}

	public String getUiserviceName() {
		return uiserviceName;
	}

	public void setUiserviceName(String uiserviceName) {
		this.uiserviceName = uiserviceName;
	}
}
