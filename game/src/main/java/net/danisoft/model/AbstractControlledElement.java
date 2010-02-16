package net.danisoft.model;

import net.danisoft.engine.controllers.Controller;

/**
 * Abstract class to implement elements managed by a controller.
 * 
 * @author Daniel García Pino
 *
 */
public abstract class AbstractControlledElement implements BaseElement {

	private Controller controller;
	
	public AbstractControlledElement(Controller controller){
		this.controller = controller;
	}
	
	public void logic() {
		controller.logic(this);
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
