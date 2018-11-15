package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ZahlungsauftragRepository extends JpaRepository<Zahlungsauftrag, Long> {
    
}