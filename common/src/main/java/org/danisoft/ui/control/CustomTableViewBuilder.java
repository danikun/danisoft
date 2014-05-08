package org.danisoft.ui.control;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Builder for custom table views.
 * 
 * @author dgarcia
 *
 * @param <S>
 */
public class CustomTableViewBuilder<S> {
	
	private List<TableColumn<S, Object>> columns;
	private ObservableList<S> items;

	public CustomTableViewBuilder() {
		columns = new ArrayList<TableColumn<S,Object>>();
	}
	
	public CustomTableView<S> build() {
		CustomTableView<S> table = new CustomTableView<S>();
		
		table.getColumns().addAll(columns);
		if (items != null) {
			table.setItems(items);
		}
		
		return table;
	}
	
	public CustomTableViewBuilder<S> addColumn(String caption, String property) {
		TableColumn<S, Object> column = new TableColumn<S, Object>(caption);
		column.setCellValueFactory(new PropertyValueFactory<S, Object>(property));		
		columns.add(column);
		return this;
	}
	
	public CustomTableViewBuilder<S> items(ObservableList<S> items) {
		this.items = items;
		return this;
	}
}
