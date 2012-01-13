package org.danisoft.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
		// Window title
		stage.setTitle("Personal finance");
		Image icon = new Image(getClass().getResourceAsStream("images/cash.png"));
		stage.getIcons().add(icon);
		
		// Border Pane Layout (top --> menu, center --> current page area)
		scene = new Scene(new BorderPane(), 1024, 768);
		
		// Load the menu from an easily editable FXML file
		MenuBar menuBar = FXMLLoader.load(getClass().getResource("TopMenu.fxml"));
		VBox sideButtons = FXMLLoader.load(getClass().getResource("SideButtons.fxml"));
		FlowPane flowButtons = FXMLLoader.load(getClass().getResource("FlowButtons.fxml"));

		// Add menubar to the top of the border pane
		//((BorderPane)scene.getRoot()).setTop(menuBar);
		//((BorderPane)scene.getRoot()).setLeft(sideButtons);
		
		Tab tab = new Tab("test tab");
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