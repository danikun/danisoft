package org.danisoft.ui.command;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.danisoft.spring.ServiceLocator;

public class OpenWindowCommand<T extends Parent> implements ICommand {

	private Scene parent;
	private Class<T> clazz;
	private String title;
	private BooleanProperty canExecute;
	
	public OpenWindowCommand(Scene parent, Class<T> clazz, String title) {
		super();
		this.parent = parent;
		this.clazz = clazz;
		this.title = title;
		canExecute = new SimpleBooleanProperty(true);
	}

	@Override
	public void execute() {
		Stage stage = new Stage();
		stage.setTitle(title);

		Scene scene = new Scene(ServiceLocator.getSingle(clazz));
		scene.getStylesheets().addAll(parent.getStylesheets());
		stage.setScene(scene);
		
		stage.show();
	}

	@Override
	public BooleanProperty canExecute() {
		return canExecute;
	}

}
