package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Zahlungsauftrag implements Serializable {

	private static final long serialVersionUID = -2147791132878672179L;
	
	@Id
	private Long id;
	
	private String art;

	private String empfaengerName;
	private String empfaengerInstitut;
	private String empfaengerIBAN;
	private String empfaengerBIC;

	private BigDecimal betrag;
	private String verwendung;
	private boolean sofertausfuehren;
	private LocalDate ausfuehrenZum;

	private String senderKontoart;
	private String senderIBAN;
	private String senderBIC;

	private String erfasser;
	private String status;
	
	private String prozessId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getArt() {
		return art;
	}
	public void setArt(String art) {
		this.art = art;
	}
	public String getEmpfaengerName() {
		return empfaengerName;
	}
	public void setEmpfaengerName(String empfaengerName) {
		this.empfaengerName = empfaengerName;
	}
	public String getEmpfaengerInstitut() {
		return empfaengerInstitut;
	}
	public void setEmpfaengerInstitut(String empfaengerInstitut) {
		this.empfaengerInstitut = empfaengerInstitut;
	}
	public String getEmpfaengerIBAN() {
		return empfaengerIBAN;
	}
	public void setEmpfaengerIBAN(String empfaengerIBAN) {
		this.empfaengerIBAN = empfaengerIBAN;
	}
	public String getEmpfaengerBIC() {
		return empfaengerBIC;
	}
	public void setEmpfaengerBIC(String empfaengerBIC) {
		this.empfaengerBIC = empfaengerBIC;
	}
	public BigDecimal getBetrag() {
		return betrag;
	}
	public void setBetrag(BigDecimal betrag) {
		this.betrag = betrag;
	}
	public String getVerwendung() {
		return verwendung;
	}
	public void setVerwendung(String verwendung) {
		this.verwendung = verwendung;
	}
	public boolean isSofertausfuehren() {
		return sofertausfuehren;
	}
	public void setSofertausfuehren(boolean sofertausfuehren) {
		this.sofertausfuehren = sofertausfuehren;
	}
	public LocalDate getAusfuehrenZum() {
		return ausfuehrenZum;
	}
	public void setAusfuehrenZum(LocalDate ausfuehrenZum) {
		this.ausfuehrenZum = ausfuehrenZum;
	}
	public String getSenderKontoart() {
		return senderKontoart;
	}
	public void setSenderKontoart(String senderKontoart) {
		this.senderKontoart = senderKontoart;
	}
	public String getSenderIBAN() {
		return senderIBAN;
	}
	public void setSenderIBAN(String senderIBAN) {
		this.senderIBAN = senderIBAN;
	}
	public String getSenderBIC() {
		return senderBIC;
	}
	public void setSenderBIC(String senderBIC) {
		this.senderBIC = senderBIC;
	}
	public String getErfasser() {
		return erfasser;
	}
	public void setErfasser(String erfasser) {
		this.erfasser = erfasser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProzessId() {
		return prozessId;
	}
	public void setProzessId(String prozessId) {
		this.prozessId = prozessId;
	}

}
