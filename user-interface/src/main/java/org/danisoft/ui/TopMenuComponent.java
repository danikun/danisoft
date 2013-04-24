package org.danisoft.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

import org.danisoft.ui.command.ICommand;
import org.danisoft.ui.command.LoadTabCommand;
import org.danisoft.ui.pages.ContactsPage;

public class TopMenuComponent extends TabPane {

	/**
	 * Layout pane holding the buttons.
	 */
	@FXML private FlowPane flowButtons;
	
	/**
	 * Comands Map
	 */
	private Map<String, ICommand> buttonCommands;
	
	/**
	 * Constructor.
	 */
	public TopMenuComponent() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/danisoft/ui/Menu.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		buttonCommands = new HashMap<String, ICommand>();
		buttonCommands.put("contacts", new LoadTabCommand<ContactsPage>(this, "Contacts", ContactsPage.class));
	}
	
	/**
	 * Handles the button action event.
	 * 
	 * @param event the generated button action event
	 */
	@FXML
	protected void handleButtonAction(final ActionEvent event) {
		Button button = (Button) event.getTarget();

		String id = button.getId();
		buttonCommands.get(id).execute();
	}
}
