package org.danisoft.ui.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import org.controlsfx.control.ButtonBar.ButtonType;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.action.ActionUtils;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;
import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.base.FormController;
import org.danisoft.ui.base.ListDetailsController;
import org.danisoft.ui.command.SimpleAction;
import org.danisoft.ui.custom.cells.ImageTableCellFactory;
import org.danisoft.ui.custom.cells.ImageTableCellValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ContactsController extends ListDetailsController<Contact> {
	
	private static final Logger LOG = LoggerFactory.getLogger(ContactsController.class);
	
	//Required components
	@Autowired
	private FormController<Person> personFormController;
	@Autowired
	private IContactsService contactsService;
	
	//Private variables
	private FormController<? extends Contact> currentFormController;
	
	//Action Buttons.
    private MenuButton addButton;
    private Button deleteButton;
    private Button saveButton;
	
	private Node personForm;
	
	/**
	 * List of contacts.
	 */
	private ObservableList<Contact> contacts;

	@SuppressWarnings("unchecked")
	@Override
	protected void initTableColumns() {
		TableColumn<Contact, String> columnImage = new TableColumn<Contact, String>();
		columnImage.setPrefWidth(40);
		columnImage.setResizable(false);
		columnImage.setCellFactory(new ImageTableCellFactory<>());
		columnImage.setCellValueFactory(
				new ImageTableCellValueFactory<Contact>(
						c -> ContactType.contactTypeByCode(c.getType()).getDisplayName()));
		
		TableColumn<Contact, String> columnName = new TableColumn<Contact, String>("Name");
		columnName.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().toString()));
		columnName.setResizable(true);
		
		listTable.getColumns().addAll(columnName);
		listTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		listTable.setOnMouseClicked(e -> openContactDetails(e));
	}

	@FXML
	private void openContactDetails(MouseEvent e) {
		if (e.getClickCount() != 2) return;
        
        //Obtain the selected item from the table
		Contact selected = listTable.getSelectionModel().getSelectedItem(); 
		if (selected instanceof Person) {
			currentFormController = personFormController;
			personFormController.setCurrent((Person)selected);
	        saveButton.disableProperty().bind(personFormController.canSave());
			attachForm(personForm);
		}
	}

	@Override
	protected void initForm() throws Exception {
		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/org/danisoft/ui/custom/PersonDetails.fxml"));
		loader.setController(personFormController);
		
		personForm = loader.load();
	}

	@Override
	protected void loadData() {
		//Load contacts List
		contacts = FXCollections.observableArrayList();
		contacts.addAll(contactsService.getAllContacts());
	}

	@Override
	protected void bindView() {
		listTable.setItems(contacts);
		
		deleteButton.disableProperty().bind(listTable.getSelectionModel().selectedItemProperty().isNull());
	}

	@Override
	protected void createActions() {
		addButton = new MenuButton("Add");
		addButton.getItems().add(ActionUtils.createMenuItem(new SimpleAction("Person", () -> addPerson())));
		deleteButton = ActionUtils.createButton(new SimpleAction("Delete", () -> deleteContact()));
		saveButton = ActionUtils.createButton(new SimpleAction("Save", () -> saveContact()));
		
		listButtonBar.addButton(addButton, ButtonType.YES);
		listButtonBar.addButton(deleteButton, ButtonType.NO);
		
		formButtonBar.addButton(saveButton, ButtonType.RIGHT);
	}

	private void saveContact() {
		//Update instance from currently entered values.
    	currentFormController.updateCurrent();

    	//Call service to persist the account
    	FileInputStream fileInputStream = null;
    	
    	if (currentFormController.getCurrent().getPhoto() != null) {
    		try {
    			fileInputStream = new FileInputStream(currentFormController.getCurrent().getPhoto());
    		} catch (FileNotFoundException e) {
    			fileInputStream = null;
    			LOG.error("The specified file doesn't exist or can't be accessed.");
    		}
    	}
    	
        Contact persisted = contactsService.saveContact(currentFormController.getCurrent(), fileInputStream);
        
        //Select The saved account in the accounts table
        listTable.getSelectionModel().select(currentFormController.getCurrent());
        
        if (currentFormController.isNew()) {
            contacts.add(persisted);
        } else {
        	contacts = FXCollections.observableArrayList(contactsService.getAllContacts());
        	listTable.setItems(contacts);
        }
        
        //Reset variables
        if (persisted instanceof Person) 
        	personFormController.setCurrent((Person) persisted);
	}

	private void deleteContact() {
		Contact toDelete = listTable.getSelectionModel().getSelectedItem();
		Action response = Dialogs
			.create()
			.message(String.format("Are you sure you want to delete %s?", toDelete))
			.showConfirm();
		
		if (response != Dialog.Actions.YES) return;
		
		contactsService.deleteContact(toDelete);
		contacts.remove(toDelete);
		
		if (currentFormController != null 
				&& currentFormController.getCurrent().getId() == toDelete.getId()) {
			currentFormController.setCurrent(null);
			attachForm(null);
		}
	}

	private void addPerson() {
		//Adding a new person
		currentFormController = personFormController;
		personFormController.setCurrent(new Person());
		//setting the person form to the form pane
		attachForm(personForm);
		//Clear selection
		listTable.getSelectionModel().clearSelection();
	}

}
