/**
 * 
 */
package net.danidoft.engine.controllers.test;

import org.lwjgl.input.Keyboard;

import net.danisoft.engine.controllers.Controller;
import net.danisoft.model.AbstractControlledElement;
import net.danisoft.model.test.Square;

/**
 * @author danielg.pino
 *
 */
public class KeyboardSquareController implements Controller {

	/**
	 * Process I/O of the controllers.
	 * 
	 * @param element element to control.
	 */
	public void logic(AbstractControlledElement element) {

		Square square = (Square)element;
		
		//Se Controlan las teclas que permiten pulsación continua
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			square.setHeight(square.getHeight() + 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			square.setHeight(square.getHeight() - 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
			square.setWidth(square.getWidth() + 1);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
			square.setWidth(square.getWidth() - 1);
		}
		
		Keyboard.poll();
		while(Keyboard.next()){
			//Se controlan las teclas que no permiten pulsación continua
			switch(Keyboard.getEventKey()){
				case Keyboard.KEY_F:
					if(Keyboard.getEventKeyState()){
						square.setFrame(square.getFrame() + 1);
						if (square.getFrame() == 4){
							square.setFrame(0);
						}
					}
				break;
			}
		}
	}

}
