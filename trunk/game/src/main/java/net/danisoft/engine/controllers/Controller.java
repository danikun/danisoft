package net.danisoft.engine.controllers;

import net.danisoft.model.AbstractControlledElement;
import net.danisoft.model.BaseElement;

/**
 * Interface to implement controllers.
 * 
 * @author Daniel Garc�a Pino
 *
 */
public interface Controller {
	
	/**
	 * Process I/O of the controllers.
	 * 
	 * @param element element to control.
	 */
	public void logic(AbstractControlledElement element);

}
