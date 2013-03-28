package org.danisoft.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
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
		FXMLLoader loader = new FXMLLoader();
		loader.setBuilderFactory(new JavaFXBuilderFactory(false));
		loader.setLocation(getClass().getResource(""));
		FlowPane flowButtons = (FlowPane) loader.load(getClass().getResourceAsStream("/org/danisoft/ui/Menu.fxml"));
		TopMenuController controller = (TopMenuController) loader.getController();
		controller.setContext(context);

		// Add flow buttons to the main tab
		Tab tab = new Tab("Main menu");
		tab.setClosable(false);
		tab.setContent(flowButtons);
		TabPane tabPane = new TabPane();
		tabPane.getTabs().add(tab);
		((BorderPane) scene.getRoot()).setCenter(tabPane);

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