package net.danisoft.engine.controllers.impl;

import org.lwjgl.input.Keyboard;

import net.danisoft.engine.controllers.Controller;
import net.danisoft.model.AbstractControlledElement;

/**
 * Keyboard controller implementation.
 * 
 * @author Daniel Garc�a Pino
 *
 */
public class KeyboardController implements Controller {

	public void logic(AbstractControlledElement element) {
		
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			
		}

	}

}
