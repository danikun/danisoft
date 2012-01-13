package org.danisoft.ui.pages;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.ui.base.Page;

/**
 * Main view of the contacts manager.
 * 
 * @author Daniel Garcia
 *
 */
public class ContactsPage implements Page {

	//TODO: this is only a test contact list
	ObservableList<Person> contacts = 
			FXCollections.observableArrayList(
					new Person(1, "Daniel", ContactType.Person, null, "C/Sepulveda, 34, 08015, Barcelona", "García", "Pino"), 
					new Person(1, "Lourdes", ContactType.Company, null, "C/Sepulveda, 34, 08015, Barcelona", "de Lamo", "Valverde"));
	
	public Node load() {
		BorderPane layout = new BorderPane();
		
		//List of Contacts (Center)
		final ListView<Person> listView = new ListView<Person>();
		listView.setItems(contacts);
		
		
		layout.setCenter(listView);
		
		//Contact Data (Left)
		GridPane contactData = new GridPane();
		contactData.setStyle("-fx-hgap: 10; -fx-padding: 20;");
		
		Label nameLabel = new Label("Name:");
		nameLabel.setStyle("-fx-font-weight: bold;");
		contactData.getChildren().add(nameLabel);
		GridPane.setColumnIndex(nameLabel, 0);
		GridPane.setRowIndex(nameLabel, 0);
		
		final Label nameText = new Label();
		contactData.getChildren().add(nameText);
		GridPane.setColumnIndex(nameText, 1);
		GridPane.setRowIndex(nameText, 0);
		
		layout.setLeft(contactData);
		
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent event) {
				Person person = listView.getSelectionModel().getSelectedItem();
				
				if (person != null) {
					nameText.setText(person.getName());
				}
			}
		});
		
		return layout;
	}

	public String getName() {
		return "Contacts";
	}

}
