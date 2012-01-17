package org.danisoft.ui.custom.cells;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import org.danisoft.ui.model.UIContact;

public class ImageTableCell extends TableCell<UIContact, String> {

	public ImageTableCell() {}
	
	@Override protected void updateItem(String item, boolean empty) {
		super.updateItem(item, empty);
		setText("");
		
		if (item != null) {
			Image image = 
					new Image(
							getClass().getResourceAsStream(
									"/org/danisoft/ui/images/" + item + ".png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(32);
			imageView.setFitHeight(32);
			
			setGraphic(imageView);
			setAlignment(Pos.CENTER);
		}
	}
}
