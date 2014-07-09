package org.danisoft.ui.command;

import javafx.event.ActionEvent;
import javafx.stage.Stage;

import org.controlsfx.control.action.AbstractAction;

/**
 * Action to close a given FX stage.
 * 
 * @author Daniel Garcia
 *
 */
public class CloseAction extends AbstractAction {

	private Stage stage;
	
	/**
	 * @param text text of the action
	 * @param stage stage to close
	 */
	public CloseAction(String text, Stage stage) {
		super(text);
		this.stage = stage;
	}

	@Override
	public void execute(ActionEvent arg0) {
		stage.close();
	}

}
