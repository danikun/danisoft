package org.danisoft.ui.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.danisoft.spring.ServiceLocator;

public class LoadTabCommand<T extends Node> implements ICommand {

	private TabPane tabPane;
	private String tabName;
	private Class<T> clazz; 
	private BooleanProperty canExecute;
	
	public LoadTabCommand(TabPane tabPane, String tabName, Class<T> clazz) {
		this.tabPane = tabPane;
		this.tabName = tabName;
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
			tab.setContent(ServiceLocator.getSingle(clazz));
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
