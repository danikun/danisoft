package org.danisoft.ui.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

public abstract class FormController<T> implements Initializable {

	//Variables
    protected BooleanProperty isNew;
    protected BooleanProperty isModified;
    protected ObjectProperty<T> current;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	//Setup variables
        isNew = new SimpleBooleanProperty(false);
        isModified = new SimpleBooleanProperty(false);
        current = new SimpleObjectProperty<>();
	}
	
    public boolean isNew() {
    	return isNew.get(); 
    }
    
    public void setIsNew(boolean value) {
    	isNew.set(value);
    }

	public void setCurrent(T currentValue) {
		current.set(currentValue);
		
		if (current.isNull().get()) {
            clearFields();
            return;
        }
        
		setCurrentFormFields();
        
        //Resetting the modified account
        isModified.set(false);
	}

	protected abstract void setCurrentFormFields();
	protected abstract void clearFields();
	public abstract void updateCurrent();
	
	public T getCurrent() {
		return current.get();
	}

	public ObservableValue<? extends Boolean> canSave() {
		return current.isNotNull().and(isNew.or(isModified)).not();
	}
}
