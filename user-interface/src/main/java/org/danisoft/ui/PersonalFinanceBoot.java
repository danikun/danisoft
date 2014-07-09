package org.danisoft.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.controlsfx.control.action.ActionGroup;
import org.controlsfx.control.action.ActionMap;
import org.controlsfx.control.action.ActionProxy;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.dialog.Dialogs;
import org.danisoft.ui.bootstrap.Module;
import org.danisoft.ui.command.CloseAction;
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
		ActionMap.register(this);
		
		//Starting spring application
		ApplicationContext context = SpringApplication.run(PersonalFinanceBoot.class, new String[0]);
		modules = new ArrayList<>(context.getBeansOfType(Module.class).values());
		
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

		TabPane tabPane = new TabPane();
		
		//Load Actions.
		List<ActionGroup> actions = new ArrayList<ActionGroup>();
		
		addFileMenu(stage, actions);
		loadModules(actions, tabPane);
		addHelpMenu(actions);
		
		// Load the top menu
		MenuBar menuBar = ActionUtils.createMenuBar(actions);

		mainLayout.setTop(menuBar);
		mainLayout.setCenter(tabPane);
		
		//Load main tab.
		addMainTab(context, tabPane);
		
		// Final lines
		stage.setScene(scene);
		stage.show();
		stage.setMinWidth(1024);
		stage.setMinHeight(768);
	}

	private void addMainTab(ApplicationContext context, TabPane tabPane) {
		Tab title = new Tab("Personal Finance");
		title.setClosable(false);
		title.setContent(context.getBean(TitleComponent.class));
		tabPane.getTabs().add(title);
	}

	private void addFileMenu(Stage stage, List<ActionGroup> actions) {
		ActionGroup group = new ActionGroup("File", 
				new CloseAction("Exit", stage));
		actions.add(group);
	}
	
	private void addHelpMenu(List<ActionGroup> actions) {
		ActionGroup group = 
				new ActionGroup("Help",	ActionUtils.ACTION_SEPARATOR, ActionMap.action("showHelp"));
		actions.add(group);
	}

	private void loadModules(List<ActionGroup> actions, TabPane tabPane) {
		for (Module module : modules) {
			actions.add(module.loadModule(tabPane));
		}
	}
	
	@ActionProxy(text="About...")
	private void showHelp() {
		Dialogs
			.create()
			.masthead("About...")
			.message("Personal Finance, Developed by Daniel Garcia.")
			.showInformation();
	}
}
