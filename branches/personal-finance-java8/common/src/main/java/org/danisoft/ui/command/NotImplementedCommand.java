package org.danisoft.ui.command;

import org.danisoft.ui.dialog.DialogProvider;

public class NotImplementedCommand implements ICommand {

	public void execute() {
		DialogProvider.showWarning("Not Implemented", "This command is not currently implemented.");
	}

	public boolean canExecute() {
		return true;
	}
}
