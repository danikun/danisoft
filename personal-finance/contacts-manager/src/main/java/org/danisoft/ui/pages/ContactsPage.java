package org.danisoft.ui.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import org.danisoft.model.Contact;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.custom.ContactDetailsAbstractComponent;
import org.danisoft.ui.custom.cells.ImageTableCell;
import org.danisoft.ui.custom.event.ContactSaveEvent;
import org.danisoft.ui.model.UIContact;
import org.danisoft.ui.model.UIPerson;

/**
 * Main view of the contacts manager.
 * 
 * @author Daniel Garcia
 * 
 */
public class ContactsPage implements Page {

	// Service
	IContactsService contactsService = null;

	// Action Constants
	public static final String NEW = "new";
	public static final String DELETE = "delete";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";

	// UI elements of the view
	private final TableView<UIContact> contactList = new TableView<UIContact>();
	private final BorderPane layout = new BorderPane();
	private final HBox actionButtons = new HBox();
	private final MenuButton newButton = new MenuButton("New Contact...");
	private final Button deleteButton = new Button("Delete Contact");

	// Event handlers
	private final ButtonEventHandler buttonEventHandler = new ButtonEventHandler();
	private final SaveEventHandler saveEventHandler = new SaveEventHandler();

	// Contacts list
	private ObservableList<UIContact> contacts = null;
	private Boolean initialised = false;

	// Detail components cache
	Map<String, ContactDetailsAbstractComponent> detailComponents = null;

	public Node load() {
		contacts = FXCollections.observableArrayList();

		List<Contact> dbContacts = contactsService.getAllContacts();

		for (Contact contact : dbContacts) {
			contacts.add(UIContact.fromContact(contact));
		}

		if (!initialised) {
			init();
			initialised = true;
		} else {
			layout.setLeft(null);
		}

		contactList.setItems(contacts);

		contactList.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<UIContact>() {

					public void changed(
							ObservableValue<? extends UIContact> observable,
							UIContact prev, UIContact curr) {

						if (curr != null) {
							generateContactDataPane(curr);
						}
					}
				});

		return layout;
	}

	@SuppressWarnings("unchecked")
	private void init() {
		detailComponents = new HashMap<String, ContactDetailsAbstractComponent>();

		// List of Contacts (Center)
		layout.setCenter(contactList);

		TableColumn<UIContact, String> nameColumn = new TableColumn<UIContact, String>(
				"Name");
		nameColumn
				.setCellValueFactory(new PropertyValueFactory<UIContact, String>(
						"name"));
		nameColumn.prefWidthProperty().bind(
				contactList.widthProperty().subtract(42));

		TableColumn<UIContact, String> typeColumn = new TableColumn<UIContact, String>(
				"");
		typeColumn
				.setCellFactory(new Callback<TableColumn<UIContact, String>, TableCell<UIContact, String>>() {

					public TableCell<UIContact, String> call(
							TableColumn<UIContact, String> arg0) {
						return new ImageTableCell();
					}
				});

		typeColumn
				.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UIContact, String>, ObservableValue<String>>() {

					public ObservableValue<String> call(
							CellDataFeatures<UIContact, String> cellData) {
						SimpleStringProperty value = new SimpleStringProperty(
								cellData.getValue().getType().getDisplayName());
						return value;
					}
				});
		typeColumn.setPrefWidth(40);
		typeColumn.setMaxWidth(40);
		typeColumn.setMinWidth(40);

		contactList.getColumns().addAll(typeColumn, nameColumn);

		// Action buttons (Down)
		actionButtons.setAlignment(Pos.CENTER_RIGHT);
		actionButtons.setPadding(new Insets(10));
		actionButtons.setSpacing(5);
		actionButtons
				.setStyle("-fx-border-style: solid; -fx-border-color: black");
		actionButtons.getChildren().addAll(newButton, deleteButton);

		layout.setBottom(actionButtons);

		// New button
		MenuItem personItem = new MenuItem("Person");
		personItem.setOnAction(buttonEventHandler);
		personItem.setId(NEW);
		personItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
		newButton.getItems().add(personItem);

		// Delete button
		deleteButton.setOnAction(buttonEventHandler);
		deleteButton.setId(DELETE);
	}

	private void generateContactDataPane(UIContact contact) {
		String type = contact.getType().getDisplayName();
		ContactDetailsAbstractComponent detailsComponent = detailComponents
				.get(type);

		if (detailsComponent == null) {
			String detailsClass = "org.danisoft.ui.custom." + type + "DetailsComponent";

			try {
				detailsComponent = (ContactDetailsAbstractComponent) Class.forName(detailsClass).newInstance();
				detailsComponent.setSaveEventHandler(saveEventHandler);
				detailComponents.put(type, detailsComponent);
			} catch (Exception e) {
				//TODO: Log
				System.out.print("Invalid class name");
			}
		}
		
		if (detailsComponent != null) {
			detailsComponent.setContact(contact);
			detailsComponent.setEditable(true);
		}
		layout.setLeft(detailsComponent);
		detailsComponent.setFocus();
	}

	public String getName() {
		return "Contacts";
	}

	private class ButtonEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			String actionId = null;
			
			if (event.getTarget() instanceof MenuItem) {
				actionId = ((MenuItem)event.getTarget()).getId();
			} else {
				Control node = (Control) event.getTarget();
				actionId = node.getId();
			}
			
			switch (actionId) {
			case NEW:
				generateContactDataPane(new UIPerson(0, "", "", "", null, "", null));
				break;
			case DELETE:
				UIContact contact = contactList.getSelectionModel()
						.getSelectedItem();
				contactsService.deleteContact(contact.toContact());

				contacts.remove(contact);
				layout.setLeft(null);
				break;
			}
		}
	}
	
	private class SaveEventHandler implements EventHandler<ContactSaveEvent> {

		@Override
		public void handle(ContactSaveEvent event) {
			UIContact contact = event.getContact();
			int id = contactsService.saveContact(contact.toContact());
			
			if (contact.getId() == 0) {
				contact.setId(id);
				contacts.add(contact);
			}
		}
		
	}

	/**
	 * @param contactsService
	 *            the contactsService to set
	 */
	public void setContactsService(IContactsService contactsService) {
		this.contactsService = contactsService;
	}
}
