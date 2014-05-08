package org.danisoft.ui.custom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.ui.model.UIContact;
import org.danisoft.ui.model.UIPerson;
import org.danisoft.ui.model.UIPhoneNumber;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class PersonDetailsComponent extends ContactDetailsAbstractComponent {
	/**
	 * Log.
	 */
	private final Log log = LogFactory.getLog(getClass());

	// UI elements
	/**
	 * Parent layout of the view.
	 */
	@FXML
	private GridPane layout;
	/**
	 * First name text field.
	 */
	@FXML
	private TextField firstNameText;
	/**
	 * Last name 1 text field.
	 */
	@FXML
	private TextField lastName1Text;
	/**
	 * Last name 2 text field.
	 */
	@FXML
	private TextField lastName2Text;
	/**
	 * Address text field.
	 */
	@FXML
	private TextField addressText;
	/**
	 * Photo.
	 */
	@FXML
	private ImageView photo;
	/**
	 * Phone Numbers.
	 */
	@FXML
	private PhoneNumbersComponent phoneNumbers;
	
	public PersonDetailsComponent() {
		super();
	}

	@Override
	public void setContact(UIContact contact) {
		
		if(!(contact == null || contact instanceof UIPerson))
			throw new RuntimeException("Incorrect contact Type.");
		
		if (contact == null) {
			this.contact = new UIPerson(0, "", "", "", null, "");
		} else {
			UIPerson person = (UIPerson) contact;
			this.contact = person;
		}

		firstNameText.setText(this.contact.getName());
		lastName1Text.setText(this.contact.getLastName1());
		lastName2Text.setText(this.contact.getLastName2());
		addressText.setText(this.contact.getAddress());

		// Phone numbers
		if (this.contact.getPhoneNumbers() == null) {
			ObservableList<UIPhoneNumber> numbers = FXCollections.observableArrayList();
			this.contact.setPhoneNumbers(numbers);
		}
		phoneNumbers.setPhoneNumbers(this.contact.getPhoneNumbers());

		// Photo
		InputStream stream = contactsService.getContactImage(this.contact.getId());

		if (stream != null) {
			photo.setImage(new Image(stream));
		} else {
			photo.setImage(new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/Person.png")));
		}
	}

	@Override
	protected UIPerson getContact() {
		contact.setName(firstNameText.getText());
		contact.setLastName1(lastName1Text.getText());
		contact.setLastName2(lastName2Text.getText());
		contact.setAddress(addressText.getText());
		contact.setPhoneNumbers(phoneNumbers.getPhoneNumbers());

		return contact;
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String getFxmlPath() {
		return "/org/danisoft/ui/custom/PersonDetails.fxml";
	}
	
	// Event handlers
	/**
	 * Handles the save button's action event.
	 * 
	 * @param event the action event to handle
	 */
	@FXML
	protected void handleSaveButtonAction(final ActionEvent event) {
		getContact();

		if (contactsService != null) {
			int id;
			try {
				if (contact.getPhoto() != null) {
					InputStream photoStream = new BufferedInputStream(new FileInputStream(contact.getPhoto()));
					id = contactsService.saveContact(contact.toContact(), photoStream);
				} else {
					id = contactsService.saveContact(contact.toContact(), null);
				}

				if (contact.getId() == 0) {
					contact.setId(id);
					contacts.add(contact);
				}
			} catch (FileNotFoundException e) {
				log.error("Couldn't find the file: " + contact.getPhoto());
			}
		}
	}

	/**
	 * Handle the set photo button action event.
	 * 
	 * @param event the action event to handle
	 */
	@FXML
	protected void handleSetPhotoButtonAction(final ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(layout.getScene().getWindow());
		try {
			InputStream stream = new BufferedInputStream(new FileInputStream(file));
			photo.setImage(new Image(stream));
			stream.close();
			contact.setPhoto(file);
		} catch (FileNotFoundException e) {
			log.error("Couldn't find the file: " + file);
		} catch (IOException e) {
			log.error("Error accessing the file: " + file);
		}
	}
}
