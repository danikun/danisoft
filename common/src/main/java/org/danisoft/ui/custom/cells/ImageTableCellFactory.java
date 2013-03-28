package org.danisoft.ui.custom.cells;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import org.danisoft.ui.model.UIContact;

/**
 * Image table cell factory.
 * 
 * @author Daniel Garcia
 * 
 */
public class ImageTableCellFactory implements Callback<TableColumn<UIContact, String>, TableCell<UIContact, String>> {

	/**
	 * {@inheritDoc}
	 */
	public TableCell<UIContact, String> call(final TableColumn<UIContact, String> arg0) {
		return new ImageTableCell();
	}
}
