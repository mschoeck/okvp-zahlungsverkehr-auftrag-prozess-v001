package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ZahlungsauftraegeController {

	@Autowired
	private ZahlungsauftragRepository repository;
		
	@RequestMapping(value="/zahlungsauftraege", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Zahlungsauftrag>> getAlleAuftraege() {

		List<Zahlungsauftrag> auftraege = repository.findAll();
		return ResponseEntity.ok(auftraege);
		
	}

	@RequestMapping(value="/zahlungsauftraege/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Zahlungsauftrag> getZahlungsauftragById(@PathVariable Long id) {
		
		Optional<Zahlungsauftrag> optAuftrag = repository.findById(id);
		
		if(optAuftrag.isPresent()) {
			return ResponseEntity.ok(optAuftrag.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
			
	}

}
