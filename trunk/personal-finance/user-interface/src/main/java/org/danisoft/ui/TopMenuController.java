package org.danisoft.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import org.danisoft.ui.base.Page;

public class TopMenuController {
	@FXML private MenuBar menuBar;
	@FXML private FlowPane flowButtons;

	@FXML protected void handleMenuItemAction(ActionEvent event) {
		MenuItem menuItem = (MenuItem)event.getTarget();
		
		String id = menuItem.getId();
		String className = "org.danisoft.ui.pages." + id + "Page";
		
		Page page = null;
		
		try {
			page = (Page) Class.forName(className).newInstance();
			Tab tab = new Tab(page.getName());
			tab.setContent(page.load());
			
			((TabPane)((BorderPane)menuBar.getParent()).getCenter()).getTabs().add(tab);
			tab.getTabPane().getSelectionModel().select(tab);
		} catch (Exception e) {
			//TODO: log4j proper logging
			System.out.println("Could not load class");
			return;
		}
	}
	
	@FXML protected void handleButtonAction(ActionEvent event) {
		Button button = (Button)event.getTarget();
		
		String id = button.getId();
		String className = "org.danisoft.ui.pages." + id + "Page";
		
		Page page = null;
		
		try {
			TabPane pane = (TabPane)flowButtons.getParent().getParent().getParent();
			ObservableList<Tab> tabs = pane.getTabs();
			
			for (Tab t : tabs){
				if (id.equals(t.getId())) {
					pane.getSelectionModel().select(t);
					return;
				}
			}
			
			page = (Page) Class.forName(className).newInstance();
			Tab tab = new Tab(page.getName());
			tab.setId(id);
			tab.setContent(page.load());
			
			tabs.add(tab);
			pane.getSelectionModel().select(tab);
		} catch (Exception e) {
			//TODO: log4j proper logging
			System.out.println("Could not load class");
			return;
		}
	}
}
