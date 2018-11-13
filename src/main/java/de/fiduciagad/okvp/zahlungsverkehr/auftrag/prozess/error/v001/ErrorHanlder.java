package de.fiduciagad.okvp.zahlungsverkehr.auftrag.prozess.error.v001;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

public class ErrorHanlder implements ExecutionListener {

	private final static Logger LOGGER = Logger.getLogger(ErrorHanlder.class.getName());

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		  LOGGER.log(Level.SEVERE, this.getClass().getName() + " gerufen.");
	
	}

}
