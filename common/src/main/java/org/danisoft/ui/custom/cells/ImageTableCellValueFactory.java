package org.danisoft.ui.custom.cells;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

/**
 * Image table cell value factory.
 * 
 * @author Daniel Garcia
 * 
 */
public class ImageTableCellValueFactory<T> implements
		Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>> {

	private Callback<T, String> imageNameCallback;
	
	public ImageTableCellValueFactory(Callback<T, String> imageNameCallback) {
		this.imageNameCallback = imageNameCallback;
	}

	/**
	 * {@inheritDoc}
	 */
	public ObservableValue<String> call(final CellDataFeatures<T, String> cellData) {
		return new SimpleStringProperty(imageNameCallback.call(cellData.getValue()));
	}

}
