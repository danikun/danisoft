package org.danisoft.ui.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.services.IContactsService;
import org.danisoft.spring.ServiceLocator;
import org.danisoft.ui.custom.ContactDetailsAbstractComponent;
import org.danisoft.ui.custom.PersonDetailsComponent;
import org.danisoft.ui.model.UIContact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ContactsPage extends BorderPane {
	// Constants
	/**
	 * Type column width.
	 */
	private static final double TYPE_COLUMN_WIDTH = 42;
	/**
	 * Contacts table percentage width.
	 */
	private static final double CONTACTS_PERCENTAGE_WIDTH = 0.2;

	// Properties
	/**
	 * Person Details Page.
	 */
	private Map<String, ContactDetailsAbstractComponent> detailComponents;
	/**
	 * Contacts Service.
	 */
	@Autowired
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
	
	@FXML
	private ProgressIndicator progressIndicator;

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
		ContactDetailsAbstractComponent detailsPage = detailComponents.get(target.getText());

		if (detailsPage != null) {
			detailsPage.setContact(null);
			detailsPage.setContacts(contacts);
			layout.setCenter(detailsPage);
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
	
	@FXML
	protected void onMouseClicked(final MouseEvent event) {
		UIContact contact = contactList.getSelectionModel().getSelectedItem();
		
		if(event.getButton().equals(MouseButton.PRIMARY) && contact != null){
			ContactDetailsAbstractComponent detailsPage = detailComponents.get(contact.getType().getDisplayName());
			detailsPage.setContact(contact);
			detailsPage.setContacts(contacts);
			layout.setCenter(detailsPage);
        }
	}

	/**
	 * Constructor.
	 */
	@Autowired
	public ContactsPage(IContactsService _contactsService) {
		this.contactsService = _contactsService;
		
		//Load FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/danisoft/ui/pages/Contacts.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		// Initialise contacts list
		contacts = FXCollections.observableArrayList();
		contactList.setItems(contacts);
		
		Task<Void> task = new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				
				while(contactsService == null) {}
				
				List<Contact> dbContacts = contactsService.getAllContacts();
				
				int i = 1;
				for (Contact contact : dbContacts) {
					contacts.add(UIContact.fromContact(contact));
					updateProgress(i, dbContacts.size());
					i++;
				}
				this.updateProgress(i, i);
				return null;
			}
		};
				
		// Bindings
		deleteButton.disableProperty().bind(contactList.getSelectionModel().selectedItemProperty().isNull());
		contactList.prefWidthProperty().bind(layout.widthProperty().multiply(CONTACTS_PERCENTAGE_WIDTH));
		nameColumn.prefWidthProperty().bind(contactList.widthProperty().subtract(TYPE_COLUMN_WIDTH));
		progressIndicator.progressProperty().bind(task.progressProperty());
		
		//Register Detail Components.
		detailComponents = new HashMap<String, ContactDetailsAbstractComponent>();
		detailComponents.put(ContactType.Person.getDisplayName(), ServiceLocator.getSingle(PersonDetailsComponent.class));
		
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
}
