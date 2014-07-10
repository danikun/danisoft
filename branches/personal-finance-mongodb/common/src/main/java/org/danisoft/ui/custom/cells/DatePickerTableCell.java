package org.danisoft.ui.custom.cells;

import java.text.DateFormat;
import java.time.ZoneId;
import java.util.Date;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DatePickerTableCell<S> extends TableCell<S, Date> {

	//Field
	private DatePicker datePicker;
	private DateFormat dateFormat;
	
	public DatePickerTableCell(DateFormat dateFormat) {
		super();
		this.dateFormat = dateFormat;
	}


	public static <S> Callback<TableColumn<S,Date>, TableCell<S,Date>> forTableColumn() {
		return forTableColumn(DateFormat.getDateInstance(DateFormat.MEDIUM));
	}
	
	public static <S> Callback<TableColumn<S,Date>, TableCell<S,Date>> forTableColumn(DateFormat dateFormat) {
		return c -> new DatePickerTableCell<S>(dateFormat);
	}
	
	
	 /** {@inheritDoc} */
    @Override public void startEdit() {
        if (! isEditable() 
                || ! getTableView().isEditable() 
                || ! getTableColumn().isEditable()) {
            return;
        }
        super.startEdit();

        if (isEditing()) {
            if (datePicker == null) {
            	datePicker = new DatePicker();
            	datePicker.setOnAction(e -> {
            		datePicker.hide();
            		commitEdit(Date.from(
            				datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            	});
            }
            setGraphic(datePicker);
            setText(null);
            datePicker.show();
        }
    }

    /** {@inheritDoc} */
    @Override public void cancelEdit() {
        super.cancelEdit();
        setGraphic(null);
    }
    
    @Override
    protected void updateItem(Date item, boolean empty) {
    	super.updateItem(item, empty);
    	if (item == null) return;
    	setText(dateFormat.format(item));
    	setGraphic(null);
    }
}
