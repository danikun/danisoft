package org.danisoft.ui.pages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.danisoft.ui.base.FormController;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class AccountsFormController extends FormController<Account> {
    //Movements Table.
	@FXML
    private TableView<Movement> movementsTable;
    
	//Form Fields.
    @FXML
    private TextField tNumber;
    @FXML
    private TextField tBalance;
    @FXML
    private TextArea tDescription;
	
    //Variables
    private ObservableList<Movement> movements;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	super.initialize(location, resources);
    	
        //Create movements observable list
        movements = FXCollections.observableArrayList();   
    	
        //Setup movements table
        movementsTable.setItems(movements);
        movementsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        movementsTable.disableProperty().bind(current.isNull().or(isNew));
        
        //Control Binding
        tNumber.disableProperty().bind(current.isNull());
        tNumber.editableProperty().bind(isNew);
        tNumber.textProperty().addListener(e -> isModified.set(true));
        
        tBalance.editableProperty().bind(isNew);
        
        tDescription.disableProperty().bind(current.isNull());
        tDescription.textProperty().addListener(e -> isModified.set(true));
        
        tBalance.disableProperty().bind(current.isNull());
	}

	protected void setCurrentFormFields() {
		isNew.set(current.get().getId() == null);
		
		if (!isNew()) {
	        //Set the fields values
	        tNumber.setText(current.get().getNumber());
	        tDescription.setText(current.get().getDescription());
	        tBalance.setText(String.valueOf(current.get().getBalance()));
	        movements.clear();
	        movements.addAll(current.get().getMovements());
		} else {
			clearFields();
		}
	}
	
	protected void clearFields() {
        tNumber.clear();
        tDescription.clear();
        tBalance.clear();
        movements.clear();
    }

	public void addMovements(List<Movement> result) {
		movements.addAll(result);
	}

	public void updateCurrent() {
		getCurrent().setNumber(tNumber.getText());
        getCurrent().setDescription(tDescription.getText());
        getCurrent().setMovements(new ArrayList<Movement>(movements));
	}

	public ObservableValue<? extends Boolean> canLoadMovements() {
		return current.isNull().or(isNew);
	}
}
