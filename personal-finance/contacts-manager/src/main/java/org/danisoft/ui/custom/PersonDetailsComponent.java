package org.danisoft.ui.custom;

import org.danisoft.ui.model.UIContact;
import org.danisoft.ui.model.UIPerson;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * Component to show/edit the contact details of a person contact.
 * 
 * @author Daniel Garcia
 *
 */
public class PersonDetailsComponent extends ContactDetailsAbstractComponent {

	// Properties
	private UIPerson person;
		
	// UI components
	private final Label title = new Label("Person Details");
	private final Label firstNameLabel = new Label("First Name");
	private final TextField firstNameText = new TextField();
	private final Label lastName1Label = new Label("Last Name 1");
	private final TextField lastName1Text = new TextField();
	private final Label lastName2Label = new Label("Last Name 2");
	private final TextField lastName2Text = new TextField();
	private final ImageView photo = new ImageView();
	
	public PersonDetailsComponent() {
		super();
		
		this.add(title, 0, 0);
		GridPane.setColumnSpan(title, 2);
		this.add(photo, 0, 1);
		
		this.add(firstNameLabel, 0, 2);
		this.add(firstNameText, 1, 2);
		
		this.add(lastName1Label, 0, 3);
		this.add(lastName1Text, 1, 3);
		
		this.add(lastName2Label, 0, 4);
		this.add(lastName2Text, 1, 4);
		
		this.add(saveButton, 1, 5);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(30);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(70);
		
		this.getColumnConstraints().addAll(column1, column2);
		this.setPrefSize(300, Double.MAX_VALUE);
		this.setMaxSize(Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE);
		
		// Style of controls TODO: move to css
		this.setStyle("-fx-hgap: 10; -fx-padding: 20;");
		firstNameLabel.setStyle("-fx-font-weight: bold;");
		lastName1Label.setStyle("-fx-font-weight: bold;");
		lastName2Label.setStyle("-fx-font-weight: bold;");
	}

	@Override
	public void setContact(UIContact contact) {
		person = (UIPerson) contact;
		
		firstNameText.setText(person.getName());
		lastName1Text.setText(person.getLastName1());
		lastName2Text.setText(person.getLastName2());
	}

	@Override
	protected UIContact getContact() {
		person.setName(firstNameText.getText());
		person.setLastName1(lastName1Text.getText());
		person.setLastName2(lastName2Text.getText());
		
		return person;
	}
}
