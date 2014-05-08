package org.danisoft.ui;

import org.springframework.stereotype.Controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

@Controller
public class TitleComponent extends AnchorPane {

	public TitleComponent() {
		super();
		
		Label appLabel = new Label("Personal Finance", new ImageView(new Image("/org/danisoft/ui/images/cash.png", 48, 48, true, true)));
		appLabel.setAlignment(Pos.CENTER);
		appLabel.getStyleClass().add("mainTitle");
		
		AnchorPane.setBottomAnchor(appLabel, 0d);
		AnchorPane.setLeftAnchor(appLabel, 0d);
		AnchorPane.setRightAnchor(appLabel, 0d);
		AnchorPane.setTopAnchor(appLabel, 0d);
		
		this.getChildren().add(appLabel);
		
	}

	
}
