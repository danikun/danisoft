package org.danisoft.ui.custom.cells;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Image table cell factory.
 * 
 * @author Daniel Garcia
 * 
 */
public class ImageTableCellFactory<T> implements Callback<TableColumn<T, String>, TableCell<T, String>> {

	/**
	 * {@inheritDoc}
	 */
	public TableCell<T, String> call(final TableColumn<T, String> arg0) {
		return new ImageTableCell<T>();
	}
}
