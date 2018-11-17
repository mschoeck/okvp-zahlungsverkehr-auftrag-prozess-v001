package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class DelegateErrorHandler implements ExecutionListener {

	private final static Logger LOGGER = Logger.getLogger(DelegateErrorHandler.class.getName());

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		  LOGGER.log(Level.SEVERE, this.getClass().getName() + " gerufen.");
	}

}
