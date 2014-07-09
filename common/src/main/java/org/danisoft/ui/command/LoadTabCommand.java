package org.danisoft.ui.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import org.controlsfx.control.action.AbstractAction;
import org.danisoft.spring.ServiceLocator;

public class LoadTabCommand<T extends Node> extends AbstractAction {

	private TabPane tabPane;
	private String tabName;
	private Class<T> clazz; 
	private BooleanProperty canExecute;
	
	public LoadTabCommand(TabPane tabPane, String tabName, Class<T> clazz) {
		super(tabName);
		this.tabPane = tabPane;
		this.tabName = tabName;
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
			tab.setContent(ServiceLocator.getSingle(clazz));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		tabs.add(tab);
		tabPane.getSelectionModel().select(tab);
	}

}
