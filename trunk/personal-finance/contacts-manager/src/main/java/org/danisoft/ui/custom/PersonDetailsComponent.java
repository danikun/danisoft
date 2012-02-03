package org.danisoft.ui.custom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.constants.StyleClass;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.model.UIContact;
import org.danisoft.ui.model.UIPerson;

/**
 * Component to show/edit the contact details of a person contact.
 * 
 * @author Daniel Garcia
 *
 */
public class PersonDetailsComponent extends ContactDetailsAbstractComponent {

	// Log.
	Log log = LogFactory.getLog(getClass());
	
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
	private final Label addressLabel = new Label("Address");
	private final TextField addressText = new TextField();
	private final ImageView photo = new ImageView();
	private final Button setPhoto = new Button("set...");
	private final PhoneNumbersComponent  phoneNumbers = new PhoneNumbersComponent();
	
	// Contacts service
	private IContactsService contactsService = null;
	
	public PersonDetailsComponent() {
		super();
		
		this.add(title, 0, 0);
		GridPane.setColumnSpan(title, 2);
		this.add(photo, 0, 1);
		this.add(setPhoto, 1, 1);
		setPhoto.setAlignment(Pos.BOTTOM_LEFT);
		setPhoto.setOnAction(new PersonButtonsEventHandler());
		photo.setFitHeight(64);
		photo.setFitWidth(64);
		
		this.add(firstNameLabel, 0, 2);
		this.add(firstNameText, 1, 2);
		
		this.add(lastName1Label, 0, 3);
		this.add(lastName1Text, 1, 3);
		
		this.add(lastName2Label, 0, 4);
		this.add(lastName2Text, 1, 4);
		
		this.add(addressLabel, 0, 5);
		this.add(addressText, 1, 5);
		
		this.add(phoneNumbers, 1, 6);
		this.add(saveButton, 1, 7);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(30);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(70);
		
		this.getColumnConstraints().addAll(column1, column2);
		this.setPrefSize(300, Double.MAX_VALUE);
		this.setMaxSize(Region.USE_COMPUTED_SIZE,
				Region.USE_COMPUTED_SIZE);
		
		this.getStyleClass().add(StyleClass.CONTACT_DETAILS);
		firstNameLabel.getStyleClass().add(StyleClass.FORM_FIELD);
		lastName1Label.getStyleClass().add(StyleClass.FORM_FIELD);
		lastName2Label.getStyleClass().add(StyleClass.FORM_FIELD);
		addressLabel.getStyleClass().add(StyleClass.FORM_FIELD);
		title.getStyleClass().add(StyleClass.CONTACT_DETAILS_TITLE);
	}

	@Override
	public void setContact(UIContact contact) {
		person = (UIPerson) contact;
		
		firstNameText.setText(person.getName());
		lastName1Text.setText(person.getLastName1());
		lastName2Text.setText(person.getLastName2());
		addressText.setText(person.getAddress());
		phoneNumbers.setItems(person.getPhoneNumbers());
		
		InputStream stream = contactsService.getContactImage(contact.getId());
		
		if (stream != null) {
			photo.setImage(new Image(stream));
		} else {
			photo.setImage(new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/Person.png")));
		}
	}

	@Override
	protected UIContact getContact() {
		person.setName(firstNameText.getText());
		person.setLastName1(lastName1Text.getText());
		person.setLastName2(lastName2Text.getText());
		person.setAddress(addressText.getText());
		
		return person;
	}

	@Override
	public void setFocus() {
		firstNameText.requestFocus();
		prefWidthProperty().bind(getScene().widthProperty().multiply(0.3));
	}
	
	private class PersonButtonsEventHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(getScene().getWindow());
			try {
				InputStream stream = new BufferedInputStream(new FileInputStream(file));
				photo.setImage(new Image(stream));
				stream.close();
				person.setPhoto(file);
			} catch (FileNotFoundException e) {
				log.error("Couldn't find the file: " + file);
			} catch (IOException e) {
				log.error("Error accessing the file: " + file);
			}
		}
	}

	/**
	 * @param contactsService the contactsService to set
	 */
	public void setContactsService(IContactsService contactsService) {
		this.contactsService = contactsService;
	}
	
	
}
