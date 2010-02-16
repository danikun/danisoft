package net.danisoft.engine.controllers.impl;

import net.danisoft.engine.controllers.Controller;
import net.danisoft.model.AbstractControlledElement;

/**
 * Controller for static elements.
 * 
 * @author Daniel García Pino
 *
 */
public class NullController implements Controller {

	/**
	 * Process I/O of the controllers.
	 * 
	 * @param element element to control.
	 */
	public void logic(AbstractControlledElement element) {
		//do nothing
	}

}
