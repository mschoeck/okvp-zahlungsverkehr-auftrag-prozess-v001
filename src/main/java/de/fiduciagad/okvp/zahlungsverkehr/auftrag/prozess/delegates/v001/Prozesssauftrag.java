package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

import java.util.Date;

/**
 * Request vom Client zum Prozess.
 * Der vom Freigabeprozess zu behandelnde Auftrag.
 * Der Prozess benötigt kein Wissen über den Auftrag, außer:
 * @param id: Die eindeutige Id, damit die Tasks Auftragsfunktionalität nutzen können.
 * @param erfasserId: Der Erfasser oder Verantwortliche des Auftrags, der über Ergebnisse informiert werden will.
 * @param ablaufDatum: Das Datum, bis zu dem ein Auftag ausgeführt werden kann.
 * €param betrag: Der Auftragsbetrag, der für die Kompetenzprüfung benötigt wird.
 * Repräsentation als JSON:
 *  { "id" : "19439458094",
 *   "erfasserId" : "xcm1070",
 *   "ablaufDatum" : "2018-04-23T23:59:59.0000"
 *   "betrag" : "5000,00"
 *  }
 *
 */
public class Prozesssauftrag {

	private Long id;
    private String erfasserid;
    private Date ablaufdatum;
    private Double betrag;

    public Prozesssauftrag() {
    	
    }
    
    public Prozesssauftrag(Long id, String erfasserId, Date ablaufDatum, Double betrag) {
        this.id = id;
        this.erfasserid = erfasserId;
        this.ablaufdatum = ablaufDatum;
        this.betrag = betrag;
    }

	public Long getId() {
		return id;
	}

	public String getErfasserid() {
		return erfasserid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setErfasserid(String erfasserId) {
		this.erfasserid = erfasserId;
	}

	public Date getAblaufdatum() {
		return ablaufdatum;
	}

	public void setAblaufdatum(Date ablaufDatum) {
		this.ablaufdatum = ablaufDatum;
	}

	public Double getBetrag() {
		return betrag;
	}

	public void setBetrag(Double betrag) {
		this.betrag = betrag;
	}
}
