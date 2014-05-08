package org.danisoft.ui.handler;

import java.util.List;

import org.danisoft.ui.command.ICommand;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Envent handler for double click mouse events;
 * 
 * @author dgarcia
 *
 */
public class DoubleClickEventHandler implements EventHandler<MouseEvent> {

	/**
	 * Commands to execute on double click event.
	 */
	private List<ICommand> commands;
	
	public DoubleClickEventHandler(List<ICommand> commands) {
		this.commands = commands;
	}
	
	public void handle(MouseEvent event) {
		if(event.getClickCount() == 2) {
			execute();
		}		
	}

	protected void execute() {
		for (ICommand command : commands) {
			if (command.canExecute()) {
				command.execute();
			}
		}	
	}
}
