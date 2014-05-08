package org.danisoft.ui.dialog;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DialogProvider {
	
	public static Stage showModal(Parent parent, String title, double width, double height) {
		// Setup stage to open as pop-up.
		Stage stage = new Stage();

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		stage.setTitle(title);
		stage.setWidth(width);
		stage.setHeight(height);
			
		// Set scene into the stage and show the stage.
		stage.setScene(new Scene(parent));
		stage.show();
		
		return stage;
	}

	public static void showWarning(String caption, String message) {
		AnchorPane pane = new AnchorPane();
		pane.getChildren().add(new Label(message));
		
		showModal(pane, caption, 100, 50);
	}
}
