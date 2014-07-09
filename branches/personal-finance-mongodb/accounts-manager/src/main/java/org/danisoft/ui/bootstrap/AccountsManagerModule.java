package org.danisoft.ui.bootstrap;

import javafx.scene.control.TabPane;

import org.controlsfx.control.action.ActionGroup;
import org.danisoft.ui.command.LoadFXMLTabCommand;
import org.danisoft.ui.pages.AccountsController;
import org.danisoft.ui.pages.ContractsController;
import org.springframework.stereotype.Component;

@Component
public class AccountsManagerModule implements Module {

	@Override
	public ActionGroup loadModule(TabPane tabPane) {
		ActionGroup actionGroup = new ActionGroup("Bank Accounts", 
				new LoadFXMLTabCommand<>(tabPane,
						"Manage Accounts",
						"/org/danisoft/ui/base/ListDetailsView.fxml",
						AccountsController.class),
				new LoadFXMLTabCommand<>(tabPane,
						"Manage Contracts", 
						"/org/danisoft/ui/pages/Contracts.fxml", 
						ContractsController.class));
		return actionGroup;
	}

}
