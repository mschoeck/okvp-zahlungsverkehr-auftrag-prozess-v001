package de.fiduciagad.okvp.zahlungsverkehr.uiprozess;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProzessController {
	
	private final static Logger LOGGER = Logger.getLogger(ProzessController.class.getName());
	
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private FormService formService;
		
	 @RequestMapping("/zahlungsauftraege/uiprozess/start")
	 public ResponseEntity<Bedienerschritt> starteProzess(@RequestParam String prozesskey, @RequestParam String userid) {
		 
		VariableMap map = Variables.createVariables();
		map.put(ProzessVariablen.VAR_START_USERID, Variables.stringValue(userid));
		
		ProcessInstance prozess = runtimeService.startProcessInstanceByKey(prozesskey, map); 
        String prozessId = prozess.getProcessInstanceId();
        
        Bedienerschritt schritt = new Bedienerschritt();
        schritt.setProzessid(prozessId);
       
        return ResponseEntity.ok(schritt);
	 }
	 
	 @RequestMapping("/zahlungsauftraege/uiprozess/{prozessid}/schritt")
	 public ResponseEntity<Bedienerschritt> getNaechsterSchritt (@PathVariable String prozessid, @RequestParam String userid) {
		 if (prozessid == null)
		 {
			 return ResponseEntity.noContent().build();
		 }
		 LOGGER.info("Task suchen:" + prozessid);
		 Task task = taskService.createTaskQuery().processInstanceId(prozessid).
				             taskAssignee(userid).
				             initializeFormKeys().singleResult();
	
		 Bedienerschritt schritt = null;
		 if (task == null) {
			 schritt = new Bedienerschritt(prozessid, ProzessVariablen.SCHRITT_LETZTER, ProzessVariablen.SCHRITT_LETZTER);
		 }
		 else {
			 schritt = new Bedienerschritt(prozessid, task.getId(), task.getFormKey());
			 schritt.setUiserviceName(task.getName());
			 
			 // Form-Eigenschaften lesen
			 TaskFormData taskFormData = formService.getTaskFormData(task.getId());
			 List<FormField> formFields = taskFormData.getFormFields();
			 ArrayList<String> stringListButtonnames= new ArrayList<String>();
			 ArrayList<String> stringListButtonvariables= new ArrayList<String>();
			 for (FormField field : formFields)
			 {
				 if (field.getId().startsWith("button_")) {
					 stringListButtonnames.add(field.getLabel());
					 stringListButtonvariables.add((String) field.getValue().getValue());
				 }
			 }
			 schritt.setButtonnames( stringListButtonnames.toArray(new String[stringListButtonnames.size()])); 
			 schritt.setButtonvariables( stringListButtonvariables.toArray(new String[stringListButtonvariables.size()])); 
		 }
		 return ResponseEntity.ok(schritt);
	 }
		 
	 @RequestMapping("/zahlungsauftraege/uiprozess/{prozessid}/schritt/{schrittid}/complete")
	 public ResponseEntity<Bedienerschritt> completeSchritt (@PathVariable String prozessid, @PathVariable String schrittid, @RequestParam String aktion, @RequestParam String userid) {
		 if (prozessid == null || schrittid == null)
		 {
			 return ResponseEntity.noContent().build();
		 }
		 
		 if (aktion == null || aktion.equals("")) aktion = ProzessVariablen.AKTION_DEFAULT;
		 runtimeService.setVariable(prozessid, ProzessVariablen.VAR_AKTION, Variables.stringValue(aktion));
		 taskService.complete(schrittid);
		 
		 Bedienerschritt schritt = new Bedienerschritt();
	        schritt.setProzessid(prozessid);
		 
		 return ResponseEntity.ok(schritt);
	 }
}
