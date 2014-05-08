package org.danisoft.ui.controllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.danisoft.ui.command.ICommand;
import org.danisoft.ui.command.NotImplementedCommand;
import org.danisoft.ui.pages.AccountsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class AccountsPageController {

	@Autowired
	private AccountsPage accountsPage;
	private ICommand openAccount;
	ObservableList<Account> results;
	
	public AccountsPageController() {
		results = FXCollections.observableArrayList();
		createCommands();
		loadData();
		bindView();
	}

	private void bindView() {
		accountsPage.bindAccounts(results);
		
	}

	private void loadData() {
		//TODO: Call to a service to get this data from DB.
		results.add(new Account(1, "1223112344", 1000d, "Cuenta nomina", new ArrayList<Movement>()));
		results.add(new Account(1, "1223112345", 2000d, "Cuenta naranja 1", new ArrayList<Movement>()));
		results.add(new Account(1, "1223112346", 3000d, "Cuenta naranja 2", new ArrayList<Movement>()));
		results.add(new Account(1, "1223112347", 4000d, "Cuenta naranja 3", new ArrayList<Movement>()));
		results.add(new Account(1, "1223112348", 5000d, "Cuenta naranja 4", new ArrayList<Movement>()));
	}

	private void createCommands() {
		openAccount = new NotImplementedCommand();
	}

	/**
	 * @return the accountsPage
	 */
	public AccountsPage getAccountsPage() {
		return accountsPage;
	}

	/**
	 * @param accountsPage the accountsPage to set
	 */
	public void setAccountsPage(AccountsPage accountsPage) {
		this.accountsPage = accountsPage;
	}

	/**
	 * @return the openAccount
	 */
	public ICommand getOpenAccount() {
		return openAccount;
	}
}
