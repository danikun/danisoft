package org.danisoft.ui.bootstrap;

import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

import org.danisoft.ui.command.ICommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class Module {
	
	@Autowired
	private ApplicationContext context;
	
	public abstract void loadModule(MenuBar menu, TabPane tabPane);
	
	protected <T extends Parent> MenuItem createMenuItem(final ICommand command, final String label) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.setOnAction(e -> executeCommand(command));
		menuItem.disableProperty().bind(command.canExecute().not());
		return menuItem;
	}
	
	private void executeCommand(ICommand command) {
		if (command.canExecute().getValue()) {
			command.execute();
		}
	}
}
