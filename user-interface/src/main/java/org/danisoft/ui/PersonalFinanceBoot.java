package org.danisoft.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.danisoft.ui.bootstrap.Module;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("org.danisoft")
public class PersonalFinanceBoot extends Application implements CommandLineRunner {

	private List<Module> modules;
	
	public static void main(String[] args) {
		Application.launch(args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		ApplicationContext context = SpringApplication.run(PersonalFinanceBoot.class, new String[0]);
		this.modules = new ArrayList<>(context.getBeansOfType(Module.class).values());
		
		// Window title
		stage.setTitle("Personal finance");
		Image icon = new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/cash.png"));
		stage.getIcons().add(icon);

		// Border Pane Layout (top --> menu, center --> current page area)
		double height = Screen.getPrimary().getVisualBounds().getHeight();
		double width = Screen.getPrimary().getVisualBounds().getWidth();
		BorderPane mainLayout = new BorderPane();
		Scene scene = new Scene(mainLayout, width - 20, height - 50);
		scene.getStylesheets().add("personalFinance.css");

		// Load the top menu
		MenuBar menuBar = new MenuBar();
		TabPane tabPane = new TabPane();
		
		mainLayout.setTop(menuBar);
		mainLayout.setCenter(tabPane);

		loadModules(menuBar, tabPane);
		
		//Add system menu
		Menu system = new Menu("System");
		MenuItem exit = new MenuItem("Exit");
		
		system.getItems().add(exit);
		menuBar.getMenus().add(system);
		
		// Final lines
		stage.setScene(scene);
		stage.show();
		stage.setMinWidth(640);
		stage.setMinHeight(480);
	}

	private void loadModules(MenuBar menuBar, TabPane tabPane) {
		for (Module module : modules) {
			module.loadModule(menuBar, tabPane);
		}
	}
}
