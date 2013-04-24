package org.danisoft.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main application class for the UI. It includes a menu bar and a tab Panel.
 * 
 * @author Daniel Garcia
 *
 */
public class PersonalFinance extends Application {

	/**
	 * Main scene of the application.
	 */
	private Scene scene = null;

	@Override
	public void start(final Stage stage) throws Exception {
		// Spring application Context
		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

		// Window title
		stage.setTitle("Personal finance");
		Image icon = new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/cash.png"));
		stage.getIcons().add(icon);

		// Border Pane Layout (top --> menu, center --> current page area)
		double height = Screen.getPrimary().getVisualBounds().getHeight();
		double width = Screen.getPrimary().getVisualBounds().getWidth();
		scene = new Scene(new BorderPane(), width - 20, height - 50);
		scene.getStylesheets().add("personalFinance.css");

		// Load the menu from an easily editable FXML file
		TopMenuComponent topMenuComponent = new TopMenuComponent();
		((BorderPane) scene.getRoot()).setCenter(topMenuComponent);

		// Final lines
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param args command line arguments
	 */
	public static void main(final String[] args) {
		Application.launch(args);
	}
}
