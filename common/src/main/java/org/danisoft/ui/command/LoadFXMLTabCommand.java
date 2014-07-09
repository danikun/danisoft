package org.danisoft.ui.command;

import org.controlsfx.control.action.AbstractAction;
import org.danisoft.spring.ServiceLocator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LoadFXMLTabCommand<T> extends AbstractAction {

	private TabPane tabPane;
	private String tabName;
	private String fxmlFile; 
	private Class<T> clazz; 
	private BooleanProperty canExecute;
	
	public LoadFXMLTabCommand(TabPane tabPane, String tabName, String fxmlFile, Class<T> clazz) {
		super(tabName);
		
		this.tabPane = tabPane;
		this.tabName = tabName;
		this.fxmlFile = fxmlFile;
		this.clazz = clazz;
		canExecute = new SimpleBooleanProperty(true);
	}

	public BooleanProperty canExecute() {
		return canExecute;
	}

	@Override
	public void execute(ActionEvent arg0) {
		ObservableList<Tab> tabs = tabPane.getTabs();

		for (Tab t : tabs) {
			if (tabName.equals(t.getId())) {
				tabPane.getSelectionModel().select(t);
				return;
			}
		}
		
		Tab tab = new Tab(tabName);
		tab.setId(tabName);
		try {
			FXMLLoader loader = new FXMLLoader(clazz.getResource(fxmlFile));
			loader.setController(ServiceLocator.getSingle(clazz));
			tab.setContent(loader.load());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		tabs.add(tab);
		tabPane.getSelectionModel().select(tab);
	}

}
