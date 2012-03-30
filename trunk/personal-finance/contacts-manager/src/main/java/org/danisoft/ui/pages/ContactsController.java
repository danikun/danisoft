package org.danisoft.ui.pages;

import java.util.List;
import java.util.Map;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
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

	/**
	 * Log.
	 */
	private final Log log = LogFactory.getLog(getClass());

	// Properties
	/**
	 * Person Details Page.
	 */
	private Page personDetailsPage;
	/**
	 * Contacts Service.
	 */
	private IContactsService contactsService;

	// UI elements.
	@FXML
	private TableView<UIContact> contactList;

	@FXML
	private BorderPane layout;

	@FXML
	private Button deleteButton;

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
	protected void newPerson(final ActionEvent event) {
		FXMLLoader loader = new FXMLLoader();
		loader.setBuilderFactory(new JavaFXBuilderFactory(false));
		loader.setLocation(getClass().getResource(""));
		personDetailsPage.getParams().remove("contact");
		personDetailsPage.getParams().put("contacts", contacts);

		GridPane center = (GridPane) personDetailsPage.load();
		layout.setCenter(center);
		contactList.getSelectionModel().clearSelection();
	}

	/**
	 * Creates a new company.
	 * 
	 * @param event an action event
	 */
	@FXML
	protected void newCompany(final ActionEvent event) {
		// TODO
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

	@Override
	public void init(final Map<String, Object> params) {
		// init private properties
		personDetailsPage = (Page) params.get("personDetailsPage");
		contactsService = (IContactsService) params.get("contactsService");

		// Initialise contacts list
		// TODO: obtain list from database
		contacts = FXCollections.observableArrayList();

		List<Contact> dbContacts = contactsService.getAllContacts();

		for (Contact contact : dbContacts) {
			contacts.add(UIContact.fromContact(contact));
		}

		contactList.setItems(contacts);

		// Bindings
		deleteButton.disableProperty().bind(contactList.getSelectionModel().selectedItemProperty().isNull());
		contactList.prefWidthProperty().bind(layout.widthProperty().multiply(0.2));
		nameColumn.prefWidthProperty().bind(contactList.widthProperty().subtract(42));

		// Event handling
		contactList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UIContact>() {

			@Override
			public void changed(final ObservableValue<? extends UIContact> observable, final UIContact prev,
					final UIContact curr) {

				if (curr != null) {
					personDetailsPage.getParams().put("contact", curr);
					personDetailsPage.getParams().put("contacts", contacts);
					GridPane center = (GridPane) personDetailsPage.load();
					layout.setCenter(center);
					center.prefWidthProperty().bind(layout.getScene().widthProperty().multiply(0.3));
				}
			}
		});
	}
}
