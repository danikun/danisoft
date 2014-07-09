package org.danisoft.ui.bootstrap;

import javafx.scene.control.TabPane;

import org.controlsfx.control.action.ActionGroup;
import org.danisoft.ui.command.LoadFXMLTabCommand;
import org.danisoft.ui.pages.ContactsController;
import org.springframework.stereotype.Component;

@Component
public class ContactsManagerModule implements Module {

	@Override
	public ActionGroup loadModule(TabPane tabPane) {
		ActionGroup actionGroup = new ActionGroup("Contacts",
				new LoadFXMLTabCommand<>(tabPane, "Manage Contacts", 
						"/org/danisoft/ui/base/ListDetailsView.fxml", ContactsController.class));
		return actionGroup;
	}
}
