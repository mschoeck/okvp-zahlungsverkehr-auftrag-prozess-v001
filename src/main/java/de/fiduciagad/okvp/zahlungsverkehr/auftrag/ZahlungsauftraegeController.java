package de.fiduciagad.okvp.zahlungsverkehr.auftrag;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceQuery;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.kompetenz.AuftragsKompetenzHandler;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.ProzessKonstanten;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.ProzessVariablen;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.util.AuftragFreigabeComparator;

@RestController
public class ZahlungsauftraegeController {
	
	private final static Logger LOGGER = Logger.getLogger(ZahlungsauftraegeController.class.getName());

	@Autowired
	private ZahlungsauftragRepository repository;
	
	@Autowired
	private AuftragsKompetenzHandler kompetenzhandler;
	
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
		
	@RequestMapping("/zahlungsauftraege/offen")
     public ResponseEntity<List<Zahlungsauftrag>> getAuftraegeOffen(@RequestParam String userid) {
		 
		 // alle offenen Aufträge finden, die der User erfasst hat.
	     List<Zahlungsauftrag> auftraege = repository.findAll();
	     List<Zahlungsauftrag> offeneAuftraege = new ArrayList<Zahlungsauftrag>();
	     for (Zahlungsauftrag auftrag : auftraege) 
	     {
	    	 if (userid.contentEquals(auftrag.getErfasser()) && 
	    	     auftrag.getStatus().equals(ProzessKonstanten.AUFTRAGSTATUS_OFFEN))
	    	 {
	    		  offeneAuftraege.add(auftrag);
	    	 }
	     }

	     // alle Auftraege finden, die dem User zur Freigabe zugeordnet sind.
         List<Task> tasklist = taskService.createTaskQuery().processDefinitionKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE).taskAssignee(userid).initializeFormKeys().list();
         List<Long> auftragsIds = new ArrayList<Long>();
         Long auftragId;
	     for (Task task : tasklist) {
	    	 if (ProzessKonstanten.FORM_AUFTRAGSFREIGABE.equals(task.getFormKey())) {
	    		 auftragId = (Long) runtimeService.getVariable(task.getProcessInstanceId(), ProzessVariablen.LON_AUFTRAGSID);
	    		 auftragsIds.add(auftragId);
	    	 }
	     }
	     offeneAuftraege.addAll(repository.findAllById(auftragsIds));
	     
	     // Auftragsliste sortieren
	     offeneAuftraege.sort(new AuftragFreigabeComparator());
         Collections.reverse(offeneAuftraege);
         
	     return ResponseEntity.ok(offeneAuftraege);
	 }

	 @RequestMapping("/zahlungsauftraege/eingereicht")
     public ResponseEntity<List<Zahlungsauftrag>> getAuftraegeEingereicht(@RequestParam String userid) {

         List<Task> tasklist = taskService.createTaskQuery().processDefinitionKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE).list();

		 ArrayList<Long> auftragidListe = new ArrayList<Long>();
		 String processInstanceId;
		 Long auftragsId;
		 String erfasserId;
		 for (Task task : tasklist) {
			 if (!userid.equals(task.getAssignee())) {
				 processInstanceId = task.getProcessInstanceId();
				 erfasserId = (String) runtimeService.getVariable(processInstanceId, ProzessVariablen.STR_AUFTRAGSERFASSERID);
				 if (userid.equals(erfasserId)) {
					 auftragsId = (Long) runtimeService.getVariable(processInstanceId, ProzessVariablen.LON_AUFTRAGSID);				 
					 auftragidListe.add(auftragsId);
				 }	 
			 }
		 }
		 
		 List<Zahlungsauftrag> auftragsliste = repository.findAllById(auftragidListe);

		 // Auftragsliste sortieren
		 auftragsliste.sort(new AuftragFreigabeComparator());
	     Collections.reverse(auftragsliste);

	     return ResponseEntity.ok(auftragsliste);		 
	 }
	 
	@RequestMapping(value="/zahlungsauftraege/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Zahlungsauftrag> getAuftragById(@PathVariable Long id) {
		
		Optional<Zahlungsauftrag> optAuftrag = repository.findById(id);
		
		if(optAuftrag.isPresent()) {
			return ResponseEntity.ok(optAuftrag.get());
		}
		else {
			return ResponseEntity.notFound().build();
		}
			
	}
	
	 @RequestMapping("/zahlungsauftraege/{id}/freigabeschritt")
	 public ResponseEntity<ZahlungsauftragFreigabeschritt> getAuftragsfreigabeNaechsterSchritt (@PathVariable Long id, @RequestParam String userid) {
		 Optional<Zahlungsauftrag> auftrag = repository.findById(id);
		 if (auftrag == null ) {
				return ResponseEntity.badRequest().build();
		 };
		 
		 // Wenn es noch keinen Prozess, gibt, starte ihn, ansonsten nimm bestehenden auf.
		 String prozessId = auftrag.get().getProzessId();
		 if (prozessId == null)
		 {
			 prozessId = starteAuftragsfreigabe(auftrag.get(), userid);
		 }
		 
		 if (prozessId == null ) {
		 };
         
		 Task task = taskService.createTaskQuery().processInstanceId(auftrag.get().getProzessId()).
				             taskAssignee(userid).
				             initializeFormKeys().singleResult();
		 ZahlungsauftragFreigabeschritt schritt = null;
		 if (task!= null) {
			 schritt = new ZahlungsauftragFreigabeschritt(id, task.getId(), task.getFormKey());
		 }
		 return ResponseEntity.ok(schritt);
	 }
	 
	 @RequestMapping("/zahlungsauftraege/{id}/freigabeschritt/{schrittid}/freigeben")
     public ResponseEntity<ZahlungsauftragFreigabeschritt> auftragFreigeben(@PathVariable Long id, @PathVariable String schrittid, @RequestParam String userid) {

		LOGGER.info("freigeben gerufen f+r" + id + " schritt: " + schrittid + " und User " + userid); 
		Task task = taskService.createTaskQuery().taskId(schrittid).initializeFormKeys().singleResult();
		if (task == null || !userid.equals(task.getAssignee())) {
			return ResponseEntity.badRequest().build();
		}
		LOGGER.info("Task gefunden." + task.getId());
		String prozessid = task.getProcessInstanceId();
		Long auftragId = (Long) runtimeService.getVariable(prozessid, ProzessVariablen.LON_AUFTRAGSID);
		if (!id.equals(auftragId)) {
			return ResponseEntity.badRequest().build();
		}
		
		if (!kompetenzhandler.hatUserKompetenzFuerFreigabe(userid)) {
			return ResponseEntity.badRequest().build();
		}
		
		runtimeService.setVariable(prozessid, ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(true));
		ArrayList<String> freigeberListe = ((ArrayList<String>) runtimeService.getVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE));
		freigeberListe.add(userid);
		runtimeService.setVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE, freigeberListe);

	    ZahlungsauftragFreigabeschritt schritt = null;
		schritt = new ZahlungsauftragFreigabeschritt(id, task.getId(), task.getFormKey());
		
		taskService.complete(task.getId());
		
		return ResponseEntity.ok(schritt);
	}
	
	 @RequestMapping("/zahlungsauftraege/{id}/freigabeschritt/{schrittid}/ablehnen")
     public ResponseEntity<Long> auftragAblehnen(@PathVariable Long id, @PathVariable String schrittid, @RequestParam String userid) {
		Task task = taskService.createTaskQuery().taskId(schrittid).singleResult();
		if (task == null || !userid.equals(task.getAssignee())) {
			return ResponseEntity.badRequest().build();
		}
		
		String prozessid = task.getProcessInstanceId();
		Long auftragId = (Long) runtimeService.getVariable(prozessid, ProzessVariablen.LON_AUFTRAGSID);
		if (!id.equals(auftragId)) {
			return ResponseEntity.badRequest().build();
		}
		
		if (!kompetenzhandler.hatUserKompetenzFuerFreigabe(userid)) {
			return ResponseEntity.badRequest().build();
		}
		
		runtimeService.setVariable(prozessid, ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(false));
		ArrayList<String> freigeberListe = ((ArrayList<String>) runtimeService.getVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE));
		freigeberListe.add(userid);
		runtimeService.setVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE, freigeberListe);
		
		taskService.complete(task.getId());
		
		return ResponseEntity.ok(id);
     }
	 
	 private String starteAuftragsfreigabe(Zahlungsauftrag auftrag, String userid) {
		VariableMap variables = initVariablesProzesstart(auftrag, userid);
		
		//prüfe, ob schon ein oder mehrere Prozesse zum Auftrag laufen
		ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE).variableValueEquals(ProzessVariablen.LON_AUFTRAGSID, Variables.longValue(new Long(auftrag.getId())));
		if (query.count() > 1) {
			return null;
		}
		else if (query.count() == 1)  {
			return query.singleResult().getProcessInstanceId();
		}
		
		// starte den Freigabeprozess
		ProcessInstance freigabeProzess = runtimeService.startProcessInstanceByKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE, variables); 
        String freigabeProzessId = freigabeProzess.getProcessInstanceId();
		auftrag.setProzessId(freigabeProzessId);
        
        return freigabeProzessId;
	 }
	 
	private VariableMap initVariablesProzesstart(Zahlungsauftrag auftrag, String userid)
	{
      VariableMap variables = Variables.createVariables();
        
      variables.put(ProzessVariablen.LON_AUFTRAGSID, Variables.longValue(auftrag.getId()));
      variables.put(ProzessVariablen.STR_AUFTRAGSERFASSERID, Variables.stringValue(userid));
      variables.put(ProzessVariablen.STR_FREIGEBER_ID, Variables.stringValue(userid));
  	  
      LocalDate prozessAblaufZum = auftrag.getAusfuehrenZum();
  	  if (prozessAblaufZum==null) {
  		  prozessAblaufZum = LocalDate.now().plusDays(7);
  	  } 
  	  else {
  		prozessAblaufZum = prozessAblaufZum.plusDays(1);
  	  }
      variables.put(ProzessVariablen.DAT_AUFTRAGSABLAUF, Variables.dateValue(Date.from(prozessAblaufZum.atStartOfDay(ZoneId.systemDefault()).toInstant())));
  	 
  	  if (auftrag.getBetrag()==null)
  		  variables.put(ProzessVariablen.DBL_BETRAG, Variables.doubleValue(new Double(0)));
  	  else
  		  variables.put(ProzessVariablen.DBL_BETRAG, Variables.doubleValue(auftrag.getBetrag().doubleValue()));

  	  // Der erste Freigeber ist der Start-User
  	  variables.put(ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(false));
      variables.put(ProzessVariablen.FLG_WEITERE_FREIGABE_ERFORDERLICH, Variables.booleanValue(true));	
      variables.put(ProzessVariablen.STR_LETZTER_AUFTRAGSSTATUS, Variables.stringValue(ProzessKonstanten.MESSAGE_AUFTRAGSTATUS_OFFEN));
      variables.put(ProzessVariablen.STR_FREIGEBER_LISTE, new ArrayList<String>());
        
      return variables;
	}

}
