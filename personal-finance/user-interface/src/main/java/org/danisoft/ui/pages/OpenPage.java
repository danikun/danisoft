package org.danisoft.ui.pages;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import org.danisoft.ui.base.Page;

public class OpenPage implements Page {

	HBox layout = new HBox();
	Label label = new Label();
	
	public Node load() {
		label.setText("This is only a sample page!!");
		layout.getChildren().add(label);
		return layout;
	}

	public String getName() {
		return "Open";
	}

}
