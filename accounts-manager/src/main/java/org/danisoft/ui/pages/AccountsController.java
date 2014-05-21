/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.danisoft.ui.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.danisoft.services.IAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author dgarcia
 */
@Controller
@Scope("prototype")
public class AccountsController implements Initializable {
	
    private static final Logger log = LoggerFactory.getLogger(AccountsController.class);
	
	//Service
	@Autowired
	private IAccountsService accountsService;
	
    //Tables
    @FXML
    private TableView<Account> accountsTable;
    @FXML
    private TableView<Movement> movementsTable;
    
    //Form controls
    @FXML
    private TextField tNumber;
    @FXML
    private TextField tBalance;
    @FXML
    private TextArea tDescription;
    
    //Action Buttons.
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadMovementsButton;
    
    //Variables
    private BooleanProperty isNew;
    private BooleanProperty isModified;
    private ObservableList<Account> accounts;
    private ObservableList<Movement> movements;
    
    private ObjectProperty<Account> current;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Setup variables
        isNew = new SimpleBooleanProperty(false);
        isModified = new SimpleBooleanProperty(false);
        current = new SimpleObjectProperty<>();
        
        //Load account List
        accounts = FXCollections.observableArrayList();
        accounts.addAll(accountsService.getAllAccounts());
        
        //Create movements observable list
        movements = FXCollections.observableArrayList();
        
        //Setup Accounts table
        accountsTable.setItems(accounts);
        accountsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        //Setup movements table
        movementsTable.setItems(movements);
        movementsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        movementsTable.disableProperty().bind(current.isNull().or(isNew));
        
        //Button bindings
        deleteButton.disableProperty().bind(accountsTable.getSelectionModel().selectedItemProperty().isNull());
        saveButton.disableProperty().bind(current.isNotNull().and(isNew.or(isModified)).not());
        loadMovementsButton.disableProperty().bind(current.isNull());
        
        //Control Binding
        tNumber.disableProperty().bind(current.isNull());
        tNumber.editableProperty().bind(isNew);
        tNumber.textProperty().addListener(e -> isModified.set(true));
        
        tDescription.disableProperty().bind(current.isNull());
        tDescription.textProperty().addListener(e -> isModified.set(true));
        
        tBalance.disableProperty().bind(current.isNull());
    }    
    
    @FXML
    public void openAccountDetails(MouseEvent e) {
        if (e.getClickCount() != 2) return;
        
        //We are opening an existing account
        isNew.set(false);
        
        //Obtain the selected item from the table
        current.set(accountsTable.getSelectionModel().getSelectedItem());
        
        if (current.isNull().get()) {
            clearFields();
            return;
        }
        
        //Set the fields values
        tNumber.setText(current.get().getNumber());
        tDescription.setText(current.get().getDescription());
        tBalance.setText(String.valueOf(current.get().getBalance()));
        movements.clear();
        movements.addAll(current.get().getMovements());
        
        //Resetting the modified account
        isModified.set(false);
    }
    
    @FXML
    public void addAccount(ActionEvent e) {
        //We are adding a new account
        isNew.set(true);
        current.set(new Account());
        clearFields();
        
        //Clear account table selection
        accountsTable.getSelectionModel().clearSelection();
        
        //Reset the isModified value
        isModified.set(false);
    }

    private void clearFields() {
        tNumber.clear();
        tDescription.clear();
        tBalance.clear();
        movements.clear();
    }
    
    @FXML
    public void deleteAccount(ActionEvent e) {
        //Obtain the selected item from the table
        current.set(accountsTable.getSelectionModel().getSelectedItem());
        clearFields();
        
        accountsService.deleteAccount(current.get());
        
        //Remove it from the list
        accounts.remove(current.get());
        current.set(null);
    }
    
    @FXML
    public void loadMovements(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(accountsTable.getScene().getWindow());
        
        if (file != null) {
        	try {
        		//call service to load and save Movements from file;
        		List<Movement> result = 
        				accountsService.loadMovementsFromFile(current.get(), new FileInputStream(file));
        		movements.addAll(result);
			} catch (FileNotFoundException e1) {
				log.error("File not found", e);
			}
        }
    }
    
    @FXML
    public void saveAccount(ActionEvent e) {
    	current.get().setNumber(tNumber.getText());
        current.get().setDescription(tDescription.getText());
        current.get().setMovements(new ArrayList<Movement>(movements));
        
        //Call service to persist the account
        accountsService.saveAccount(current.get());
        
        //Select The saved account in the accounts table
        accountsTable.getSelectionModel().select(current.get());
        
        if (isNew.get()) {
            accounts.add(current.get());
        }
        
        //Reset variables
        isNew.set(false);
        isModified.set(false);
    }
}
