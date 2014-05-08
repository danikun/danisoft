package org.danisoft.ui.control;

import java.util.ArrayList;
import java.util.List;

import org.danisoft.ui.command.ICommand;
import org.danisoft.ui.handler.DoubleClickEventHandler;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * TableView with extended functionality.
 * 
 * @author dgarcia
 *
 * @param <S> Type of values in the table view.
 */
public class CustomTableView<S> extends TableView<S> {

	private List<ICommand> doubleClickCommands;

	public CustomTableView() {
		super();
		doubleClickCommands = new ArrayList<ICommand>();
		this.setOnMouseClicked(new DoubleClickEventHandler(doubleClickCommands));
	}

	public CustomTableView(ObservableList<S> values) {
		super(values);
		doubleClickCommands = new ArrayList<ICommand>();
		this.setOnMouseClicked(new DoubleClickEventHandler(doubleClickCommands));
	}
	
	public void addDoubleClickCommand(ICommand command) {
		doubleClickCommands.add(command);
	}
}
