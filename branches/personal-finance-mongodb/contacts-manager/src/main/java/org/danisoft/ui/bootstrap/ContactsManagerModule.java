package org.danisoft.ui.bootstrap;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;

import org.danisoft.ui.command.LoadTabCommand;
import org.danisoft.ui.pages.ContactsPage;
import org.springframework.stereotype.Component;

@Component
public class ContactsManagerModule extends Module {

	@Override
	public void loadModule(MenuBar menuBar, TabPane tabPane) {
		Menu menu = new Menu("Contacts");
		menu.getItems().add(createMenuItem(new LoadTabCommand<>(tabPane, "Manage Contacts", ContactsPage.class), "Manage Contacts..."));
		menuBar.getMenus().add(menu);
	}
}
