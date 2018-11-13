package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

import java.util.ArrayList;

/**
 * Response vom Prozess zum Client.
 * Enthält eine Liste von Auftragsids. Rückgabeobjekte für die Selektion von Aufträgen 
 * z.B. eines Users nach Status.
 * Wird vom Client aus verwendet.
 * @author xcm1030
 *
 */
public class Prozessauftragsliste {
	
	private ArrayList<Prozesssauftrag> auftragsliste = new ArrayList<>();
    
    public Prozessauftragsliste() {
    }

	public ArrayList<Prozesssauftrag> getAuftragsliste() {
		return auftragsliste;
	}

	public void setAuftragsliste(ArrayList<Prozesssauftrag> auftragsListe) {
		this.auftragsliste = auftragsListe;
	}
    
}
