package org.danisoft.ui.base;

import java.util.Map;

/**
 * Interface for controllers.
 * 
 * @author dgarcia
 * 
 */
public interface Controller {
	/**
	 * Initialise the controller.
	 * 
	 * @param params parameters to configure the controller
	 */
	void init(Map<String, Object> params);
}
