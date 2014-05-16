package org.danisoft.ui.command;

import javafx.beans.property.BooleanProperty;

public interface ICommand {
	void execute();
	BooleanProperty canExecute();
}
