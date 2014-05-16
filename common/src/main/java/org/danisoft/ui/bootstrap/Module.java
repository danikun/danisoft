package org.danisoft.ui.bootstrap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
		menuItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (command.canExecute()) {
					command.execute();
				}
			}
		});
		return menuItem;
	}
}
