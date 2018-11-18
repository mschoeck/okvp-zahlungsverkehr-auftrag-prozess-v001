package de.fiduciagad.okvp.zahlungsverkehr;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.Zahlungsauftrag;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.ZahlungsauftragRepository;

@SpringBootApplication()

public class ProcessApplication {
	private final static Logger LOGGER = Logger.getLogger(ProcessApplication.class.getName());

	public static void main(String... args) {
		SpringApplication.run(ProcessApplication.class, args);
		LOGGER.info("Application " + ProcessApplication.class.getName() + " läuft");
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
						.allowedHeaders("Content-Type", "Date", "Total-Count", "loginInfo")
						.exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo").maxAge(3600);
			}
		};
	}

	@Component
	public class AppStartupRunner implements ApplicationRunner {

		@Autowired
		ZahlungsauftragRepository repository;

		@Override
		public void run(ApplicationArguments args) throws Exception {

			List<Zahlungsauftrag> auftraege = new ArrayList<>();
			Zahlungsauftrag z1 = new Zahlungsauftrag();
			z1.setId(11L);
			z1.setArt("Überweisung");
			z1.setEmpfaengerName("Schöcki World");
			z1.setEmpfaengerInstitut("VR Bank");
			z1.setEmpfaengerIBAN("DE43 2000 3000 4000 5000");
			z1.setEmpfaengerBIC("VRBXYZNXQ");
			z1.setBetrag(BigDecimal.valueOf(500.00));
			z1.setVerwendung("Rechnung 0815");
			z1.setSofertausfuehren(true);
			z1.setAusfuehrenZum(null);
			z1.setSenderKontoart("Girokonto");
			z1.setSenderIBAN("DE43 1000 2000 3000 4444");
			z1.setSenderBIC("VRBXBXZZ");
			z1.setErfasser("ycm4444");
			z1.setStatus("Erfasst");
			z1.setProzessId(null);
			z1.setAktuellerFreigeber(null);
			auftraege.add(z1);

			Zahlungsauftrag z2 = new Zahlungsauftrag();
			z2.setId(12L);
			z2.setArt("Dauerauftrag");
			z2.setEmpfaengerName("Nikolaus");
			z2.setEmpfaengerInstitut("Raiffeisenbank");
			z2.setEmpfaengerIBAN("DE43 1000 2000 3000 4000");
			z2.setEmpfaengerBIC("VRBXYZNXQ");
			z2.setBetrag(BigDecimal.valueOf(515.00));
			z2.setVerwendung("Auftragnr. 124881");
			z2.setSofertausfuehren(false);
			z2.setAusfuehrenZum(LocalDate.of(2018, 12, 6));
			z2.setSenderKontoart("Girokonto");
			z2.setSenderIBAN("DE43 1000 2000 3000 4444");
			z2.setSenderBIC("VRBXBXZZ");
			z2.setErfasser("ycm4444");
			z2.setStatus("Erfasst");
			z2.setProzessId(null);
			z2.setAktuellerFreigeber(null);
			auftraege.add(z2);

			Zahlungsauftrag z3 = new Zahlungsauftrag();
			z3.setId(13L);
			z3.setArt("Überweisung");
			z3.setEmpfaengerName("Meister AG");
			z3.setEmpfaengerInstitut("VR Bank");
			z3.setEmpfaengerIBAN("DE43 8989 9999 8989 9999");
			z3.setEmpfaengerBIC("VBRBARZ");
			z3.setBetrag(BigDecimal.valueOf(1599.00));
			z3.setVerwendung("Auftragnr. 124881");
			z3.setSofertausfuehren(true);
			z3.setAusfuehrenZum(null);
			z3.setSenderKontoart("Girokonto");
			z3.setSenderIBAN("DE43 1000 2000 3000 4444");
			z3.setSenderBIC("VRBXBXZZ");
			z3.setErfasser("ycm4444");
			z3.setStatus("Erfasst");
			z3.setProzessId(null);
			z3.setAktuellerFreigeber(null);
			auftraege.add(z3);

			Zahlungsauftrag z4 = new Zahlungsauftrag();
			z4.setId(14L);
			z4.setArt("Überweisung");
			z4.setEmpfaengerName("Agile Camp");
			z4.setEmpfaengerInstitut("Raiffeisenbank");
			z4.setEmpfaengerIBAN("FR76 1000 1000 1000 1000");
			z4.setEmpfaengerBIC("RBVBBVX");
			z4.setBetrag(BigDecimal.valueOf(150.00));
			z4.setVerwendung("Rnr 20181101");
			z4.setSofertausfuehren(false);
			z4.setAusfuehrenZum(LocalDate.of(2018, 4, 23));
			z4.setSenderKontoart("Girokonto");
			z4.setSenderIBAN("'DE43 6767 7676 6767 7676");
			z4.setSenderBIC("VRBXBXZZ");
			z4.setErfasser("ycm5555");
			z4.setStatus("Erfasst");
			z4.setProzessId(null);
			z4.setAktuellerFreigeber(null);
			auftraege.add(z4);

			Zahlungsauftrag z5 = new Zahlungsauftrag();
			z5.setId(15L);
			z5.setArt("Terminüberweisung");
			z5.setEmpfaengerName("Mr. Computer");
			z5.setEmpfaengerInstitut("Deutsche Bank");
			z5.setEmpfaengerIBAN("DE43 4242 0000 4242 0000");
			z5.setEmpfaengerBIC("DBXYZXYZ");
			z5.setBetrag(BigDecimal.valueOf(50.00));
			z5.setVerwendung("ehalt 1118");
			z5.setSofertausfuehren(true);
			z5.setAusfuehrenZum(LocalDate.of(2018, 4, 23));
			z5.setSenderKontoart("Girokonto");
			z5.setSenderIBAN("'DE43 6767 7676 6767 7676");
			z5.setSenderBIC("VRBXBXZZ");
			z5.setErfasser("ycm6666");
			z5.setStatus("Erfasst");
			z5.setProzessId(null);
			z5.setAktuellerFreigeber(null);
			auftraege.add(z5);
			
			repository.saveAll(auftraege);

		}

	}

}
