package org.danisoft.ui.command;

import javafx.event.ActionEvent;

import org.controlsfx.control.action.AbstractAction;

public class SimpleAction extends AbstractAction {

	Command command;
	
	public SimpleAction(String text, Command command) {
		super(text);
		this.command = command;
	}

	@Override
	public void execute(ActionEvent arg0) {
		command.execute();
	}

	public interface Command {
		public void execute();
	}
}
