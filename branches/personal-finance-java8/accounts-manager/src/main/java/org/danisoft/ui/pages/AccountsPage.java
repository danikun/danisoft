package org.danisoft.ui.pages;

import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

import org.danisoft.model.Account;
import org.danisoft.ui.control.CustomTableView;
import org.danisoft.ui.control.CustomTableViewBuilder;
import org.danisoft.ui.controllers.AccountsPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value="prototype")
public class AccountsPage extends BorderPane {

	@Autowired
	private AccountsPageController controller;
	private CustomTableView<Account> tAccounts;
	
	public AccountsPage() {
		super();
		initialise();
	}

	private void initialise() {
		configureTable();
		configureLayout();
	}

	private void configureLayout() {
		this.setLeft(tAccounts);
	}

	private void configureTable() {
		tAccounts = new CustomTableViewBuilder<Account>()
				.addColumn("number", "number")
				.build();
		
		//numberColumn.prefWidthProperty().bind(tAccounts.widthProperty());
		tAccounts.addDoubleClickCommand(controller.getOpenAccount());
	}

	public void bindAccounts(ObservableList<Account> results) {
		tAccounts.setItems(results);
	}
	
}
