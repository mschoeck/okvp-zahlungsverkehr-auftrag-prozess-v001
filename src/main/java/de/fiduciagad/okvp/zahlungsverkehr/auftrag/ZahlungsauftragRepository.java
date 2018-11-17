package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZahlungsauftragRepository extends JpaRepository<Zahlungsauftrag, Long> {
    
}