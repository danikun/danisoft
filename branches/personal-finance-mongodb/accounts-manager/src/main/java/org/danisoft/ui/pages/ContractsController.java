package org.danisoft.ui.pages;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.danisoft.model.Account;
import org.danisoft.model.Contract;
import org.danisoft.model.Period;
import org.danisoft.services.IAccountsService;
import org.danisoft.services.IContractService;
import org.danisoft.ui.command.SimpleAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 
 * @author dgarcia
 *
 */
@Controller
@Scope("prototype")
public class ContractsController implements Initializable {

	//Dependencies
	@Autowired
	private IContractService contractService;
	@Autowired
	private IAccountsService accountsService;
	
	//UI Controls
	@FXML
	private TableView<Contract> contractsTable;
	@FXML
	private ButtonBar buttonBar;
	
	//Variables
	private ObservableList<Contract> contracts;
	private ObservableList<Account> accounts;
	private BooleanPropertyBase isModified;
	
	//Actions
	private Action addContract;
	private Action deleteContract;
	private Action saveContracts;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		isModified = new SimpleBooleanProperty();
		isModified.set(false);
		
		loadData();
		setupTable();
		setupActions();
	}
	
	protected void setupActions() {
		addContract = new SimpleAction("Add", () -> addContract());
		
		deleteContract = new SimpleAction("Delete", () -> deleteContract());
		deleteContract.disabledProperty().bind(
				contractsTable.getSelectionModel().selectedItemProperty().isNull());
		
		saveContracts = new SimpleAction("Save", () -> saveContracts());
		saveContracts.disabledProperty().bind(isModified.not());
		
		buttonBar.addButton(ActionUtils.createButton(saveContracts), ButtonType.APPLY);
		buttonBar.addButton(ActionUtils.createButton(addContract), ButtonType.APPLY);
		buttonBar.addButton(ActionUtils.createButton(deleteContract), ButtonType.CANCEL_CLOSE);
	}

	private void saveContracts() {
		contractService.saveAll(contracts);
		isModified.set(false);
	}

	private void deleteContract() {
		Action result = Dialogs.create()
				.message("Are you sure you want to delete the selected contract?")
				.showConfirm();
		
		if (result != Dialog.Actions.YES) return;
		
		Contract c = contractsTable.getSelectionModel().getSelectedItem();
		contracts.remove(c);
		isModified.set(true);
	}

	private void addContract() {
		contracts.add(new Contract());
	}

	@SuppressWarnings("unchecked")
	protected void setupTable() {
		TableColumn<Contract, Account> accountColumn = 
				new TableColumn<Contract, Account>("Account");
		accountColumn.setEditable(true);
		accountColumn.setCellValueFactory(
				new PropertyValueFactory<Contract, Account>("account"));
		accountColumn.setCellFactory(
				ComboBoxTableCell.<Contract, Account>forTableColumn(new AccountStringConverter(), accounts));
		accountColumn.setOnEditCommit(
				(CellEditEvent<Contract, Account> e) -> {
					e.getRowValue().setAccount(e.getNewValue());
					isModified.set(true);
				});
		
		TableColumn<Contract, String> conceptColumn =
				new TableColumn<Contract, String>("Concept");
		conceptColumn.setEditable(true);
		conceptColumn.setCellValueFactory(
				new PropertyValueFactory<>("concept"));
		conceptColumn.setCellFactory(
				TextFieldTableCell.<Contract>forTableColumn());
		conceptColumn.setOnEditCommit(
				(CellEditEvent<Contract, String> e) -> {
					e.getRowValue().setConcept(e.getNewValue());
					isModified.set(true);
				});
		
		TableColumn<Contract, Period> periodColumn =
				new TableColumn<Contract, Period>("Period");
		periodColumn.setEditable(true);
		periodColumn.setCellValueFactory(
				new PropertyValueFactory<>("period"));
		periodColumn.setCellFactory(
				ComboBoxTableCell.<Contract, Period>forTableColumn(FXCollections.observableArrayList(Period.values())));
		periodColumn.setOnEditCommit(
				(CellEditEvent<Contract, Period> e) -> {
					e.getRowValue().setPeriod(e.getNewValue().getCode());
					isModified.set(true);
				});
		
		TableColumn<Contract, Boolean> fixedAmountColumn =
				new TableColumn<Contract, Boolean>("Is fixed Amount?");
		fixedAmountColumn.setEditable(true);
		fixedAmountColumn.setCellValueFactory(
				new PropertyValueFactory<>("fixedAmount"));
		fixedAmountColumn.setCellFactory(
				CheckBoxTableCell.<Contract>forTableColumn(fixedAmountColumn));
		fixedAmountColumn.setOnEditCommit(
				(CellEditEvent<Contract, Boolean> e) -> {
					e.getRowValue().setFixedAmount(e.getNewValue());
					isModified.set(true);
				});

		TableColumn<Contract, Double> amountColumn =
				new TableColumn<Contract, Double>("Amount");
		amountColumn.setEditable(true);
		amountColumn.setCellValueFactory(
				new PropertyValueFactory<>("amount"));
		amountColumn.setCellFactory(
				TextFieldTableCell.<Contract, Double>forTableColumn(new DoubleStringConverter()));
		amountColumn.setOnEditCommit(
				(CellEditEvent<Contract, Double> e) -> {
					e.getRowValue().setAmount(e.getNewValue());
					isModified.set(true);
				});
		//		this.lastPaymentDate = lastPaymentDate;
		//		this.amount = amount;
	
		contractsTable.getColumns().addAll(conceptColumn, periodColumn, accountColumn, amountColumn, fixedAmountColumn);
		contractsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		contractsTable.setItems(contracts);
	}
	
	private class AccountStringConverter extends StringConverter<Account> {

		@Override
		public String toString(Account account) {
			if (account == null)
				return null;
			return account.getDescription();
		}

		@Override
		public Account fromString(String description) {
			return null;
		}
		
	}

	private void loadData() {
		contracts = FXCollections.observableArrayList(contractService.getAll());
		accounts = FXCollections.observableArrayList(accountsService.getAllAccounts());
	}

}
