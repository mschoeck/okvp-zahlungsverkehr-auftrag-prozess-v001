package xx.de.fiduciagad.okvp.zahlungsauftrag.prozess.auftragsfreigabe.taskworker.v1;

import java.util.logging.Logger;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

@Component
public class FreigeberErmittelnTaskHandler implements ExternalTaskHandler
{
	private final static Logger LOGGER = Logger.getLogger(FreigeberErmittelnTaskHandler.class.getName());
	
	@Override
	public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
		LOGGER.info("execute gerufen für: " + externalTask.getId());
		externalTask.getAllVariables().entrySet().stream().forEach(entry -> LOGGER.info(entry.toString()));
		//"ycm1010"
	}

}
