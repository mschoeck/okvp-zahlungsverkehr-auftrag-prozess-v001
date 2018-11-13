package xx.de.fiduciagad.okvp.zahlungsauftrag.prozess.auftragsfreigabe.taskworker.v1;

import java.util.logging.Logger;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.v001.ProzessKonstanten;

@Component
public class FreigeberErmittelnWorker implements ApplicationRunner {

	private final static Logger LOGGER = Logger.getLogger(FreigeberErmittelnWorker.class.getName());

	//Todo anders!
    private String processEngineBaseUrl = "http://localhost:8091/rest";
    
    @Autowired
    private FreigeberErmittelnTaskHandler handler;
    
    @Value("${processengine.serviceworker.freigeber_ermitteln.lockduration:60000}")
    private long lockDuration;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception
    {
        LOGGER.info("Subscribing to TOPIC_FREIGEBER_ERMITTLEN");
        ExternalTaskClient client = ExternalTaskClient.create().baseUrl(processEngineBaseUrl).build();
        client.subscribe(ProzessKonstanten.TOPIC_FREIGEBER_ERMITTELN).lockDuration(lockDuration).handler(handler).open();
    }



}
