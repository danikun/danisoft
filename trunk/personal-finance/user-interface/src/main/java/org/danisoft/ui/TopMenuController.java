package org.danisoft.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;

import org.danisoft.ui.base.Page;
import org.springframework.context.ApplicationContext;

/**
 * Controller for the top menu of the application.
 * 
 * @author dgarcia
 * 
 */
public class TopMenuController {
	/**
	 * Layout pane holding the buttons.
	 */
	@FXML private FlowPane flowButtons;

	/**
	 * Spring application context.
	 */
	private ApplicationContext context;

	/**
	 * Handles the button action event.
	 * 
	 * @param event the generated button action event
	 */
	@FXML
	protected void handleButtonAction(final ActionEvent event) {
		Button button = (Button) event.getTarget();

		String id = button.getId();
		String pageName = id + "Page";

		Page page = null;

		TabPane pane = (TabPane) flowButtons.getParent().getParent().getParent();
		ObservableList<Tab> tabs = pane.getTabs();

		for (Tab t : tabs) {
			if (id.equals(t.getId())) {
				pane.getSelectionModel().select(t);
				return;
			}
		}

		page = (Page) context.getBean(pageName);
		Tab tab = new Tab(page.getName());
		tab.setId(id);
		tab.setContent(page.load());

		tabs.add(tab);
		pane.getSelectionModel().select(tab);
	}

	/**
	 * Sets the spring application context.
	 * 
	 * @param context the spring application context
	 */
	public void setContext(final ApplicationContext context) {
		this.context = context;
	}
}
