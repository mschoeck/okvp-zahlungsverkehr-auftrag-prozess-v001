package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001;

/**
 * Response für den Client bei Start des Prozesses.
 * id: die ProzessId
 * @author xcm1030
 *
 */
public class Prozessreferenz {
    private String id;
    
    public Prozessreferenz() {
    }
    
    public Prozessreferenz(String id) {
        this.id = id;
    }

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
