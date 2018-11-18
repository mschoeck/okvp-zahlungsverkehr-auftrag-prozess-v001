package de.fiduciagad.okvp.zahlungsverkehr.auftrag.util;

import java.util.Comparator;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.Zahlungsauftrag;

public class AuftragFreigabeComparator implements Comparator<Zahlungsauftrag>{

	private final int KLEINER = -1;
	private final int GROESSER = 1;
	private final int GLEICH = 0;
	public int compare(Zahlungsauftrag a, Zahlungsauftrag b) 
    {
		int compareVal = 0;
		if (a.getAusfuehrenZum() != null && b.getAusfuehrenZum() != null) {
            compareVal = a.getAusfuehrenZum().compareTo(b.getAusfuehrenZum());
            if (compareVal != GLEICH) return compareVal<0?KLEINER:GROESSER;
		}
		else if (a.getAusfuehrenZum() == null) return GROESSER;
		else if (b.getAusfuehrenZum() == null) return KLEINER;
		
		if (a.getBetrag() != null && b.getBetrag()!= null) {
			compareVal = a.getBetrag().compareTo(b.getBetrag());
	        if (compareVal != GLEICH) return compareVal<0?KLEINER:GROESSER;
		}
		else {
			return a.getId().compareTo(b.getId());
		}
		return KLEINER;
    } 
}