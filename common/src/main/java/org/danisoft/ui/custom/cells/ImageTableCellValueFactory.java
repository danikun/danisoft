package org.danisoft.ui.custom.cells;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import org.danisoft.ui.model.UIContact;

/**
 * Image table cell value factory.
 * 
 * @author Daniel Garcia
 * 
 */
public class ImageTableCellValueFactory implements
		Callback<TableColumn.CellDataFeatures<UIContact, String>, ObservableValue<String>> {

	/**
	 * {@inheritDoc}
	 */
	public ObservableValue<String> call(final CellDataFeatures<UIContact, String> cellData) {

		SimpleStringProperty value = new SimpleStringProperty(cellData.getValue().getType().getDisplayName());
		return value;
	}

}
