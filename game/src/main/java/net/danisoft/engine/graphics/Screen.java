package net.danisoft.engine.graphics;

/**
 * Interface to implement visualization screens and displays.
 * 
 * @author Daniel García Pino
 *
 */
public interface Screen {
	/**
	 * Method used to print the current frame.
	 */
	public void print();
	
	/**
	 * Method that initialize the screen.
	 */
	public void init();
}
