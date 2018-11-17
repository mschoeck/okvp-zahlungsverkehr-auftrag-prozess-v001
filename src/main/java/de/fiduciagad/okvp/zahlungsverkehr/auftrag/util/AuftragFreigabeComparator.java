package de.fiduciagad.okvp.zahlungsverkehr.auftrag.util;

import java.util.Comparator;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.Zahlungsauftrag;

public class AuftragFreigabeComparator implements Comparator<Zahlungsauftrag>{

	public int compare(Zahlungsauftrag a, Zahlungsauftrag b) 
    {
		if (a.getAusfuehrenZum() != null && b.getAusfuehrenZum() != null) {
			return a.getAusfuehrenZum().compareTo(b.getAusfuehrenZum()); 
		}
		else if (a.getBetrag() != null && b.getBetrag()!= null) {
			return a.getBetrag().compareTo(b.getBetrag());
		}
		else {
			return a.getId().compareTo(b.getId());
		}
		
    } 
}