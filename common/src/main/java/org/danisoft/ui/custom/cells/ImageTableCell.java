package org.danisoft.ui.custom.cells;

import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Custom cell to include an image inside a table cell.
 * 
 * @author Daniel Garcia
 */
public class ImageTableCell<T> extends TableCell<T, String> {
	/**
	 * Width of the images to include in the table cell.
	 */
	private static final Integer IMAGE_WIDTH = 32;
	/**
	 * Height of the images to include in the table cell.
	 */
	private static final Integer IMAGE_HEIGTH = 32;

	/**
	 * Default constructor.
	 */
	public ImageTableCell() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final void updateItem(final String item, final boolean empty) {
		super.updateItem(item, empty);
		setText("");

		if (item != null) {
			Image image = new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/" + item + ".png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(IMAGE_WIDTH);
			imageView.setFitHeight(IMAGE_HEIGTH);

			setGraphic(imageView);
			setAlignment(Pos.CENTER);
		}
	}
}