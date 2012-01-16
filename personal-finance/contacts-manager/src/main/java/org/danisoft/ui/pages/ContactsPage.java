package org.danisoft.ui.pages;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import org.danisoft.model.ContactType;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.model.UIContact;

/**
 * Main view of the contacts manager.
 * 
 * @author Daniel Garcia
 * 
 */
public class ContactsPage implements Page {

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

	// TODO: this is only a test contact list
	ObservableList<UIContact> contacts = FXCollections.observableArrayList(
			new UIContact(1, "Daniel", ContactType.Person, null,
					"C/Sepulveda, 34, 08015, Barcelona"), new UIContact(1,
					"Lourdes", ContactType.Person, null,
					"C/Sepulveda, 34, 08015, Barcelona"));

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
						}
					}
				});

		return layout;
	}
	
	private void init() {
		// List of Contacts (Center)
		layout.setCenter(contactList);
		TableColumn<UIContact, String> nameColumn = new TableColumn<UIContact, String>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<UIContact, String>("name"));
		contactList.getColumns().add(nameColumn);
		
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
		
		nameText.setText(contact.getName());
		typeText.setText(contact.getType().getDisplayName());
		addressText.setText(contact.getAddress());
	}

	public String getName() {
		return "Contacts";
	}

}
