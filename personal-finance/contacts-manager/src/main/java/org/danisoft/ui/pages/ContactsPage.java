package org.danisoft.ui.pages;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import org.danisoft.model.ContactType;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.custom.cells.ImageTableCell;
import org.danisoft.ui.model.UIContact;

/**
 * Main view of the contacts manager.
 * 
 * @author Daniel Garcia
 * 
 */
public class ContactsPage implements Page {
	
	// Action Constants
	public static final String NEW = "new";
	public static final String DELETE = "delete";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";

	// UI elements of the view
	private final TableView<UIContact> contactList = new TableView<UIContact>();
	private final BorderPane layout = new BorderPane();
	private final GridPane contactData = new GridPane();
	private final Label nameLabel = new Label("Name:");
	private final Label typeLabel = new Label("Type:");
	private final Label addressLabel = new Label("Address:");
	private final TextField nameText = new TextField();
	private final TextField typeText = new TextField();
	private final TextField addressText = new TextField();
	private final Button saveButton = new Button("Save");
	private final HBox actionButtons = new HBox();
	private final Button newButton = new Button("New Contact");
	private final Button deleteButton = new Button("Delete Contact");

	// TODO: this is only a test contact list
	ObservableList<UIContact> contacts = FXCollections.observableArrayList(
			new UIContact(1, "Daniel", ContactType.Person, null,
					"C/Sepulveda, 34, 08015, Barcelona"), new UIContact(1,
					"Rule Financial", ContactType.Company, null,
					"C/Consell de Cent, 333, 08007, Barcelona"));

	public Node load() {
		init();
		contactList.setItems(contacts);

		contactList.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<UIContact>() {

					public void changed(
							ObservableValue<? extends UIContact> observable,
							UIContact prev, UIContact curr) {

						if (curr != null) {
							generateContactDataPane(curr);
							saveButton.setId(UPDATE);
						}
					}
				});

		return layout;
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		// Button event handler
		ButtonEventHandler buttonEventHandler = new ButtonEventHandler();
		
		// List of Contacts (Center)
		layout.setCenter(contactList);
		
		TableColumn<UIContact, String> nameColumn = new TableColumn<UIContact, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<UIContact, String>("name"));
		nameColumn.prefWidthProperty().bind(contactList.widthProperty().subtract(42));
		
		TableColumn<UIContact, String> typeColumn = new TableColumn<UIContact, String>("");
		typeColumn.setCellFactory(new Callback<TableColumn<UIContact,String>, TableCell<UIContact,String>>() {
			
			public TableCell<UIContact, String> call(TableColumn<UIContact, String> arg0) {
				return new ImageTableCell();
			}
		});
		
		typeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<UIContact,String>, ObservableValue<String>>() {

			public ObservableValue<String> call(
					CellDataFeatures<UIContact, String> cellData) {
				SimpleStringProperty value = 
						new SimpleStringProperty(cellData.getValue().getType().getDisplayName());
				return value;
			}
		});
		typeColumn.setPrefWidth(40);
		typeColumn.setMaxWidth(40);
		typeColumn.setMinWidth(40);
		
		contactList.getColumns().addAll(typeColumn, nameColumn);
		
		// Configure contact data pane (Left)
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(30);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(70);
		contactData.getColumnConstraints().addAll(column1, column2);
		contactData.setPrefSize(300, Double.MAX_VALUE);
		contactData.setMaxSize(Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE);
		contactData.setStyle("-fx-hgap: 10; -fx-padding: 20;");
		
		// Labels TODO: move it to a css
		nameLabel.setStyle("-fx-font-weight: bold;");
		typeLabel.setStyle("-fx-font-weight: bold;");
		addressLabel.setStyle("-fx-font-weight: bold;");
		
		layout.setLeft(contactData);
		
		// Action buttons (Down)
		actionButtons.setAlignment(Pos.CENTER_RIGHT);
		actionButtons.setPadding(new Insets(10));
		actionButtons.setSpacing(5);
		actionButtons.setStyle("-fx-border-style: solid; -fx-border-color: black");
		actionButtons.getChildren().addAll(newButton, deleteButton);
		
		layout.setBottom(actionButtons);
		
		// New button
		newButton.setOnAction(buttonEventHandler);
		newButton.setId(NEW);
		
		// Delete button
		deleteButton.setOnAction(buttonEventHandler);
		deleteButton.setId(DELETE);
		
		// Save button
		saveButton.setOnAction(buttonEventHandler);
		saveButton.setId(SAVE);
	}

	private void generateContactDataPane(UIContact contact) {
		contactData.getChildren().clear();
		
		// Labels
		contactData.getChildren().add(nameLabel);
		GridPane.setColumnIndex(nameLabel, 0);
		GridPane.setRowIndex(nameLabel, 0);
		
		contactData.getChildren().add(typeLabel);
		GridPane.setColumnIndex(typeLabel, 0);
		GridPane.setRowIndex(typeLabel, 1);

		contactData.getChildren().add(addressLabel);
		GridPane.setColumnIndex(addressLabel, 0);
		GridPane.setRowIndex(addressLabel, 2);

		// Text fields
		contactData.getChildren().add(nameText);
		GridPane.setColumnIndex(nameText, 1);
		GridPane.setRowIndex(nameText, 0);
		
		contactData.getChildren().add(typeText);
		GridPane.setColumnIndex(typeText, 1);
		GridPane.setRowIndex(typeText, 1);
		
		contactData.getChildren().add(addressText);
		GridPane.setColumnIndex(addressText, 1);
		GridPane.setRowIndex(addressText, 2);
		
		contactData.getChildren().add(saveButton);
		GridPane.setColumnIndex(saveButton, 1);
		GridPane.setRowIndex(saveButton, 3);
		GridPane.setHalignment(saveButton, HPos.RIGHT);
		
		nameText.setText(contact.getName());
		typeText.setText(contact.getType().getDisplayName());
		addressText.setText(contact.getAddress());
	}

	public String getName() {
		return "Contacts";
	}

	private class ButtonEventHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent event) {
			Button button = (Button) event.getTarget();
			String actionId = button.getId();
			
			switch (actionId) {
				case NEW:
					generateContactDataPane(new UIContact(0, "", ContactType.Person, null, ""));
					saveButton.setId(SAVE);
					break;
				case SAVE:
					UIContact newContact = new UIContact(0, nameText.getText(), ContactType.Person, null, addressText.getText()); 
					contacts.add(newContact);
					contactList.getSelectionModel().select(newContact);
					break;
				case UPDATE:
					UIContact update = contactList.getSelectionModel().getSelectedItem();
					update.setName(nameText.getText());
					update.setAddress(addressText.getText());
					break;
				case DELETE:
					UIContact contact = contactList.getSelectionModel().getSelectedItem();
					contacts.remove(contact);
					contactData.getChildren().clear();
					break;
			}
		}
	}
}
