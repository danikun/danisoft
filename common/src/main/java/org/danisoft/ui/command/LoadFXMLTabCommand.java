package org.danisoft.ui.command;

import org.danisoft.spring.ServiceLocator;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class LoadFXMLTabCommand<T> implements ICommand {

	private TabPane tabPane;
	private String tabName;
	private String fxmlFile; 
	private Class<T> clazz; 
	private BooleanProperty canExecute;
	
	public LoadFXMLTabCommand(TabPane tabPane, String tabName, String fxmlFile, Class<T> clazz) {
		this.tabPane = tabPane;
		this.tabName = tabName;
		this.fxmlFile = fxmlFile;
		this.clazz = clazz;
		canExecute = new SimpleBooleanProperty(true);
	}
	
	public void execute() {
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

	public BooleanProperty canExecute() {
		return canExecute;
	}

}
