/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.danisoft.ui.pages;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.dialog.Dialogs;
import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.danisoft.services.IAccountsService;
import org.danisoft.ui.base.ListDetailsController;
import org.danisoft.ui.command.SimpleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Controller for the accounts view.
 *
 * @author dgarcia
 */
@Controller
@Scope("prototype")
public class AccountsController extends ListDetailsController<Account> {	
	//Service
	@Autowired
	private IAccountsService accountsService;
	//Form Controller
	@Autowired
	private AccountsFormController formController;
    
    //Action Buttons.
    private Button addButton;
    private Button deleteButton;
    private Button saveButton;
    private Button loadMovementsButton;
    
    private ObservableList<Account> accounts;
    
    @FXML
    private void openAccountDetails(MouseEvent e) {
        if (e.getClickCount() != 2) return;
        
        //Obtain the selected item from the table
        formController.setCurrent(listTable.getSelectionModel().getSelectedItem());
    }
    
    private void addAccount() {
        //We are adding a new account
    	formController.setCurrent(new Account());
        //Clear account table selection
        listTable.getSelectionModel().clearSelection();
    }
    
    private void deleteAccount() {
        //Obtain the selected item from the table
        Account toDelete = listTable.getSelectionModel().getSelectedItem();
        accountsService.deleteAccount(toDelete);
        
        //Remove it from the list
        accounts.remove(toDelete);
        formController.setCurrent(null);
    }
    
    private void loadMovements() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(listTable.getScene().getWindow());
        
        if (file != null) {
        	Task<Void> load = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					List<Movement> result = 
	        				accountsService.loadMovementsFromFile(formController.getCurrent(), new FileInputStream(file));
					formController.addMovements(result);
					return null;
				}
			};
			
        	Dialogs.create()
        		.message("Loading Movements...")
        		.showWorkerProgress(load);
        	
        	Thread th = new Thread(load);
            th.setDaemon(true);
            th.start();
        }
    }
    
    private void saveAccount() {
    	//Update instance from currently entered values.
    	formController.updateCurrent();

    	//Call service to persist the account
        Account persisted = accountsService.saveAccount(formController.getCurrent());
        
        //Select The saved account in the accounts table
        listTable.getSelectionModel().select(formController.getCurrent());
        
        if (formController.isNew()) {
            accounts.add(persisted);
        }
        
        //Reset variables
        formController.setCurrent(persisted);
    }

	@Override
	protected void initTableColumns() {
		TableColumn<Account, String> column = new TableColumn<Account, String>("Number");
		column.setCellValueFactory(a -> new SimpleStringProperty(a.getValue().getNumber()));
		listTable.getColumns().add(column);
		listTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		listTable.setOnMouseClicked(e -> openAccountDetails(e));
	}

	@Override
	protected void initForm() throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/org/danisoft/ui/pages/AccountsForm.fxml"));
		loader.setController(formController);
		
		Node form = loader.load();
		attachForm(form);
	}

	@Override
	protected void loadData() {
		//Load account List
        accounts = FXCollections.observableArrayList();
        accounts.addAll(accountsService.getAllAccounts());
	}

	@Override
	protected void bindView() {
		listTable.setItems(accounts);
		
		//Button bindings
        deleteButton.disableProperty().bind(listTable.getSelectionModel().selectedItemProperty().isNull());
        saveButton.disableProperty().bind(formController.canSave());
        loadMovementsButton.disableProperty().bind(formController.canLoadMovements());
	}

	@Override
	protected void createActions() {
		addButton = ActionUtils.createButton(new SimpleAction("Add", () -> addAccount()));
		deleteButton = ActionUtils.createButton(new SimpleAction("Delete", () -> deleteAccount()));
		saveButton = ActionUtils.createButton(new SimpleAction("Save", () -> saveAccount()));
		loadMovementsButton = ActionUtils.createButton(new SimpleAction("Load Movements...", () -> loadMovements()));
		
		listButtonBar.addButton(addButton, ButtonType.YES);
		listButtonBar.addButton(deleteButton, ButtonType.NO);
		
		formButtonBar.addButton(saveButton, ButtonType.RIGHT);
		formButtonBar.addButton(loadMovementsButton, ButtonType.RIGHT);
	}
	
}
