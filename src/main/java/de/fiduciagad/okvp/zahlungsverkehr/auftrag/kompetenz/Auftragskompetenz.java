package de.fiduciagad.okvp.zahlungsverkehr.auftrag.kompetenz;

public class Auftragskompetenz {
	private EnumAuftragskompetenztyp kompetenzklasse;
	
	Auftragskompetenz(EnumAuftragskompetenztyp kompetenzklasse)
	{
		this.kompetenzklasse = kompetenzklasse;
	}
	
	public EnumAuftragskompetenztyp getKompetenzklasse() {
		return kompetenzklasse;
	}
	
	public void setKompetenzklasse(EnumAuftragskompetenztyp kompetenzklasse) {
		this.kompetenzklasse = kompetenzklasse;
	}
}
