package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Aufgabe;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.DelegateAuftragskompetenz;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Prozesssauftrag;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Prozessreferenz;
import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.delegates.v001.Prozessauftragsliste;

/**
 * Rest-API für den Freigabeprozess
 * @author xcm1030
 *
 */
@RestController
@RequestMapping("/freigabeprozess")
public class ProzessServiceProviderDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(ProzessServiceProviderDelegate.class.getName());
	
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;
    
    @Autowired
    HistoryService historyService;
    
    /**
     * Methode zum Start eines neuen Prozesses
     * @param auftrag
     * @param userid
     * @return
     */
	@RequestMapping(value = "/start", method = RequestMethod.POST, produces = "application/json")
	 public Prozessreferenz starteFreigabe(@RequestBody Prozesssauftrag auftrag, @RequestParam String userid) {
        LOGGER.info("starteFreigabe aufgerufen");
		VariableMap variables = init(auftrag, userid);
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE, variables); 
		LOGGER.info("id: " + processInstance.getProcessInstanceId());
		return new Prozessreferenz(processInstance.getProcessInstanceId());
	 }
	
	/**
	 * Methode zur Rückgabe der aktuellen (User-) Aufgabe des Prozesses.
	 * Dem aufrufende Bediener muss diese zugeordnet sein.
	 * @param prozessid
	 * @param userid
	 * @return
	 */
	 @RequestMapping("/{prozessid}/aufgabe")
	 public Aufgabe findeAufgabeZurFreigabe (@PathVariable String prozessid, @RequestParam String userid) {
	    LOGGER.info("findeAufgabeZurFreigabe aufgerufen: " + prozessid);
		Task task = taskService.createTaskQuery().processInstanceId(prozessid).
				             taskAssignee(userid).
				             initializeFormKeys().singleResult();
		LOGGER.info("Task: " + task);
        Aufgabe aufgabe = null;
        if (task!= null) {
          aufgabe = new Aufgabe(prozessid, task.getId(), task.getFormKey());
		  LOGGER.info("Aufgabe: " + aufgabe);
        }
		return aufgabe;
	 }
	 
	 /**
	  * Freigabe eines Auftrags durch den Bediener.
	  * Die Aufgabe muss dem Bediener zugeordnet sein. Ferner wird die Kompetenz zur Freigabe geprüft.
	  * @param aufgabeid
	  * @param userid
	  */
	 @RequestMapping("/aufgabe/{aufgabeid}/freigeben")
     public void auftragFreigeben(@PathVariable String aufgabeid, @RequestParam String userid) {
		LOGGER.info("auftragFreigeben aufgerufen: " + aufgabeid);
		Task task = taskService.createTaskQuery().taskId(aufgabeid).singleResult();
		if (task != null && userid.equals(task.getAssignee())) {
		  String prozessid = task.getProcessInstanceId();
		  if (DelegateAuftragskompetenz.getDelegateKompetez().hatUserKompetenzFuerFreigabe(userid)) {
		     runtimeService.setVariable(prozessid, ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(true));

		     ArrayList<String> freigeberListe = ((ArrayList<String>) runtimeService.getVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE));
		     freigeberListe.add(userid);
		     runtimeService.setVariable(prozessid, ProzessVariablen.STR_FREIGEBER_LISTE, freigeberListe);

		     taskService.complete(task.getId());
		  }
		  else
				LOGGER.info("Aufgabe konnte nicht für User " + userid + "abgeschlossen werden, keine Kompetenz");
		}
		else
			LOGGER.info("Aufgabe konnte nicht für User " + userid + "abgeschlossen werden");
	}
	 
	 /**
	  * Ablehnung eines Auftrags durch den Bediener.
	  * Die Aufgabe muss dem Bediener zugeordnet sein.
	  * @param aufgabeid
	  * @param userid
	  */
	 @RequestMapping("/aufgabe/{aufgabeid}/ablehnen")
     public void auftragAblehnen(@PathVariable String aufgabeid, @RequestParam String userid) {
		LOGGER.info("auftragAblehnen: " + aufgabeid);
		Task task = taskService.createTaskQuery().taskId(aufgabeid).singleResult();
		if (task != null && userid.equals(task.getAssignee())) {
	      String prozessid = task.getProcessInstanceId();
		  runtimeService.setVariable(prozessid, ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(false));
		  taskService.complete(task.getId());
		}
		else
		{
			LOGGER.info("Aufgabe konnte nicht für User " + userid + "abgeschlossen werden");
		}
     }

	 /**
	  * Liefert die Liste aller offenen Aufträge, die einem Bediener zur Freigabe zugewiesen sind.
	  * @param userid
	  * @return
	  */
	 @RequestMapping("/liste/bediener/offen")
     public Prozessauftragsliste gibOffeneAuftraege(@RequestParam String userid) {
		 LOGGER.info("gibOffeneAuftraege aufgerufen: " + userid);
		 List<Task> tasklist = taskService.createTaskQuery().processDefinitionKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE).
					             taskAssignee(userid).list();
		 Prozessauftragsliste auftraege = new Prozessauftragsliste();
		 ArrayList<Prozesssauftrag> auftragidListe = new ArrayList<>();
		 String processInstanceId;
		 Long auftragsId;
		 String erfasserId;
		 Date ablaufDatum;
		 Double betrag;
		 for(int i = 0; i < tasklist.size(); i++) {
			 processInstanceId = tasklist.get(i).getProcessInstanceId();
			 auftragsId = (Long) runtimeService.getVariable(processInstanceId, ProzessVariablen.LON_AUFTRAGSID);
			 erfasserId = (String) runtimeService.getVariable(processInstanceId, ProzessVariablen.STR_AUFTRAGSERFASSERID);
			 ablaufDatum = (Date) runtimeService.getVariable(processInstanceId, ProzessVariablen.DAT_AUFTRAGSABLAUF);
 			 betrag = (Double) runtimeService.getVariable(processInstanceId, ProzessVariablen.DBL_BETRAG);
			 auftragidListe.add(new Prozesssauftrag(auftragsId, erfasserId, ablaufDatum, betrag));
		 }
		 auftraege.setAuftragsliste(auftragidListe);
		 return auftraege;
	 }

	 /**
	  * Liefert die Liste aller vom Bediener eingereichten Aufträge, die noch auf Freigabe eimes oder mehrerer anderer Bediener warten.
	  * @param userid
	  * @return
	  */
	 @RequestMapping("/liste/bediener/eingereicht")
     public Prozessauftragsliste gibEingereichteAuftraege(@RequestParam String userid) {
		 List<Task> tasklist = taskService.createTaskQuery().processDefinitionKey(ProzessKonstanten.KEY_PROZESS_AUFTRAGSFREIGABE).list();
		 Prozessauftragsliste auftraege = new Prozessauftragsliste();
		 ArrayList<Prozesssauftrag> auftragidListe = new ArrayList<>();
		 Task task;
		 String processInstanceId;
		 Long auftragsId;
		 String erfasserId;
		 Date ablaufDatum;
		 Double betrag;
		 for(int i = 0; i < tasklist.size(); i++) {
			 task = tasklist.get(i);
			 if (!userid.equals(task.getAssignee())) {
				 processInstanceId = task.getProcessInstanceId();
				 erfasserId = (String) runtimeService.getVariable(processInstanceId, ProzessVariablen.STR_AUFTRAGSERFASSERID);
				 if (userid.equals(erfasserId)) {
					 auftragsId = (Long) runtimeService.getVariable(processInstanceId, ProzessVariablen.LON_AUFTRAGSID);				 
					 ablaufDatum = (Date) runtimeService.getVariable(processInstanceId, ProzessVariablen.DAT_AUFTRAGSABLAUF);
					 betrag = (Double) runtimeService.getVariable(processInstanceId, ProzessVariablen.DBL_BETRAG);			 
					 auftragidListe.add(new Prozesssauftrag(auftragsId, erfasserId, ablaufDatum, betrag));
				 }	 
			 }
		 }
		 auftraege.setAuftragsliste(auftragidListe);
		 return auftraege;		 
	 }
	 
	 /** 
	  * Liefert die Liste aller abgelehnten Aufträge (eines gewissen Zeitraums) von Aufträgen, die ein Bediener eingereicht hat.
	  * @param userid
	  */
	 @RequestMapping("/liste/bediener/abglehnt")
     public Prozessauftragsliste gibAbgelehnteAuftraege(@RequestParam String einreicherUserId) {
		 //not implemented yet -> history Service
		 return new Prozessauftragsliste();
	 }
	 
	 /**
	  * Liefert die Liste aller freigegebenen Aufträge (eines gewissen Zeitraums) von Aufträgen, die ein Bediener eingereicht hat.
	  * @param userid
	  */
	 @RequestMapping("/liste/bediener/freigegeben")
     public Prozessauftragsliste gibFreigegebeneAuftraege(@RequestParam String einreicherUserId) {
		 //not implemented yet -> history Service	
		 return new Prozessauftragsliste();
	 }
	 
	private VariableMap init(Prozesssauftrag auftrag, String userid)
	{
      VariableMap variables = Variables.createVariables();
        
      /* Variablen, die eigentlich schon von außen kommen müssten */
  	  if (auftrag == null || auftrag.getId()==null)
  		  variables.put(ProzessVariablen.LON_AUFTRAGSID, Variables.stringValue(""));
  	  else
  		  variables.put(ProzessVariablen.LON_AUFTRAGSID, Variables.longValue(auftrag.getId()));
  	 
  	  if (auftrag == null || auftrag.getErfasserid()==null)
  		  variables.put(ProzessVariablen.STR_AUFTRAGSERFASSERID, Variables.stringValue(userid));
  	  else
  		variables.put(ProzessVariablen.STR_AUFTRAGSERFASSERID, Variables.stringValue(auftrag.getErfasserid()));
  	  
  	  if (auftrag == null || auftrag.getAblaufdatum()==null) {
  		  Calendar calendar = Calendar.getInstance();
  		  calendar.add(Calendar.DATE, 7);  
 		  Date auftragsablauf = calendar.getTime();
  		  variables.put(ProzessVariablen.DAT_AUFTRAGSABLAUF, Variables.dateValue(auftragsablauf));
  	  }
  	  else
  		  variables.put(ProzessVariablen.DAT_AUFTRAGSABLAUF, Variables.dateValue(auftrag.getAblaufdatum()));
  	  
  	  if (auftrag == null || auftrag.getBetrag()==null)
  		  variables.put(ProzessVariablen.DBL_BETRAG, Variables.doubleValue(new Double(0)));
  	  else
  		  variables.put(ProzessVariablen.DBL_BETRAG, Variables.doubleValue(auftrag.getBetrag()));

  	  // Der erste Freigeber ist der Start-User
      variables.put(ProzessVariablen.STR_FREIGEBER_ID, Variables.stringValue(userid));
  	  variables.put(ProzessVariablen.FLG_FREIGABE_ERFOLGT, Variables.booleanValue(false));
      variables.put(ProzessVariablen.FLG_WEITERE_FREIGABE_ERFORDERLICH, Variables.booleanValue(true));	
      variables.put(ProzessVariablen.STR_LETZTE_STATUS_MSG, Variables.stringValue(ProzessKonstanten.MESSAGE_AUFTRAGSTATUS_OFFEN));
      variables.put(ProzessVariablen.STR_FREIGEBER_LISTE, new ArrayList<String>());
      
      LOGGER.info(variables.toString());
        
      return variables;
	}
}