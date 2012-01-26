package org.danisoft.ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * Main application class for the UI. It includes a menu bar and a tab Panel.
 * 
 * @author Daniel Garcia
 *
 */
public class PersonalFinance extends Application {

	private Scene scene = null;
	
	@Override
	public void start(Stage stage) throws Exception {
		// Spring application Context
		ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		
		// Window title
		stage.setTitle("Personal finance");
		Image icon = new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/cash.png"));
		stage.getIcons().add(icon);
		
		// Border Pane Layout (top --> menu, center --> current page area)
		scene = new Scene(new BorderPane(), 1024, 768);
		
		// Load the menu from an easily editable FXML file
		FXMLLoader loader = new FXMLLoader();
		loader.setBuilderFactory(new JavaFXBuilderFactory(false));
		loader.setLocation(getClass().getResource(""));
		FlowPane flowButtons = 
				(FlowPane) loader.load(getClass().getResourceAsStream("FlowButtons.fxml"));
		TopMenuController controller = (TopMenuController) loader.getController();
		controller.setContext(context);

		// Add flow buttons to the main tab	
		Tab tab = new Tab("Main menu");
		tab.setClosable(false);
		tab.setContent(flowButtons);
		TabPane tabPane = new TabPane();
		tabPane.getTabs().add(tab);
		((BorderPane)scene.getRoot()).setCenter(tabPane);

		// Final lines
		stage.setScene(scene);
		stage.show();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
}
