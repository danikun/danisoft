package org.danisoft.ui.pages;

import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.model.Contact;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.base.Controller;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.model.UIContact;

/**
 * 
 * @author dgarcia
 * 
 */
public class ContactsController implements Controller {
	// Constants
	/**
	 * Type column width.
	 */
	private static final double TYPE_COLUMN_WIDTH = 42;
	/**
	 * Contacts table percentage width.
	 */
	private static final double CONTACTS_PERCENTAGE_WIDTH = 0.2;

	/**
	 * Log.
	 */
	private final Log log = LogFactory.getLog(getClass());

	// Properties
	/**
	 * Person Details Page.
	 */
	private Map<String, Page> detailPages;
	/**
	 * Contacts Service.
	 */
	private IContactsService contactsService;

	// UI elements.
	/**
	 * Contact list component.
	 */
	@FXML
	private TableView<UIContact> contactList;
	/**
	 * Main layout of the page.
	 */
	@FXML
	private BorderPane layout;
	/**
	 * Delete contact button.
	 */
	@FXML
	private Button deleteButton;
	/**
	 * Name column of the contacts table.
	 */
	@FXML
	private TableColumn<UIContact, String> nameColumn;

	/**
	 * List of contacts.
	 */
	private ObservableList<UIContact> contacts;

	/**
	 * Creates a new person.
	 * 
	 * @param event an action event
	 */
	@FXML
	protected void newContact(final ActionEvent event) {
		MenuItem target = (MenuItem) event.getTarget();

		Page detailsPage = detailPages.get(target.getText());

		if (detailsPage != null) {
			detailsPage.getParams().remove("contact");
			detailsPage.getParams().put("contacts", contacts);

			layout.setCenter(detailsPage.load());
			contactList.getSelectionModel().clearSelection();
		}
	}

	/**
	 * Deletes a contact from the system.
	 * 
	 * @param event an action event
	 */
	@FXML
	protected void deleteContact(final ActionEvent event) {
		UIContact contact = contactList.getSelectionModel().getSelectedItem();
		contacts.remove(contact);
		contactList.getSelectionModel().clearSelection();
		layout.setCenter(null);

		contactsService.deleteContact(contact.toContact());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init(final Map<String, Object> params) {
		// init private properties
		detailPages = (Map<String, Page>) params.get("detailPages");
		contactsService = (IContactsService) params.get("contactsService");

		// Initialise contacts list
		contacts = FXCollections.observableArrayList();

		List<Contact> dbContacts = contactsService.getAllContacts();

		for (Contact contact : dbContacts) {
			contacts.add(UIContact.fromContact(contact));
		}

		contactList.setItems(contacts);

		// Bindings
		deleteButton.disableProperty().bind(contactList.getSelectionModel().selectedItemProperty().isNull());
		contactList.prefWidthProperty().bind(layout.widthProperty().multiply(CONTACTS_PERCENTAGE_WIDTH));
		nameColumn.prefWidthProperty().bind(contactList.widthProperty().subtract(TYPE_COLUMN_WIDTH));

		// Event handling
		contactList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UIContact>() {

			@Override
			public void changed(final ObservableValue<? extends UIContact> observable, final UIContact prev,
					final UIContact curr) {

				if (curr != null) {
					Page detailsPage = detailPages.get(curr.getType().getDisplayName());
					detailsPage.getParams().put("contact", curr);
					detailsPage.getParams().put("contacts", contacts);
					GridPane center = (GridPane) detailsPage.load();
					layout.setCenter(center);
				}
			}
		});
	}
}
