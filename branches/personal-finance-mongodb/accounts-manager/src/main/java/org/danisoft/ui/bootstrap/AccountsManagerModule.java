package org.danisoft.ui.bootstrap;

import org.danisoft.ui.command.LoadFXMLTabCommand;
import org.danisoft.ui.pages.AccountsController;
import org.springframework.stereotype.Component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;

@Component
public class AccountsManagerModule extends Module {

	@Override
	public void loadModule(MenuBar menuBar, TabPane tabPane) {
		Menu menu = new Menu("Bank Accounts");
		menu.getItems().add(
				createMenuItem(new LoadFXMLTabCommand<>(tabPane,
						"Manage Accounts",
						"/org/danisoft/ui/pages/Accounts.fxml",
						AccountsController.class), "Manage Accounts..."));
		menuBar.getMenus().add(menu);
	}

}
