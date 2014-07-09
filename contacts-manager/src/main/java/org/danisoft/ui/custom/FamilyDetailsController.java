package org.danisoft.ui.custom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.base.Controller;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.model.UIContact;
import org.danisoft.ui.model.UIFamily;
import org.danisoft.ui.model.UIPhoneNumber;

/**
 * Controller for the family details page.
 * 
 * @author Daniel Garcia
 * 
 */
public class FamilyDetailsController implements Controller {
	/**
	 * Log.
	 */
	private final Log log = LogFactory.getLog(getClass());

	// Private properties
	/**
	 * Family contact.
	 */
	private UIFamily family;
	/**
	 * List of contacts.
	 */
	private ObservableList<UIContact> contacts;
	/**
	 * Phone numbers component.
	 */
	private Page phoneNumbers;
	/**
	 * Contacts service.
	 */
	private IContactsService contactsService;

	// UI components
	/**
	 * Parent layout of the view.
	 */
	@FXML
	private GridPane layout;
	/**
	 * Name text field.
	 */
	@FXML
	private TextField nameText;
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

	@SuppressWarnings("unchecked")
	@Override
	public void init(final Map<String, Object> params) {
		// Set private properties
		phoneNumbers = (Page) params.get("phoneNumbers");
		contacts = (ObservableList<UIContact>) params.get("contacts");
		contactsService = (IContactsService) params.get("contactsService");

		// Set the contact
		setContact((UIFamily) params.get("contact"));

		// Page setup
		// TODO: firstNameText.requestFocus();
		layout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		// Add phone numbers component
		phoneNumbers.getParams().put("items", family.getPhoneNumbers());
		layout.add(phoneNumbers.load(), 1, 6);
	}

	/**
	 * Set the contact to show the details.
	 * 
	 * @param contact the contact to set
	 */
	private void setContact(final UIFamily contact) {
		this.family = contact;

		nameText.setText(family.getName());
		addressText.setText(family.getAddress());

		// Phone numbers
		if (contact.getPhoneNumbers() == null) {
			ObservableList<UIPhoneNumber> numbers = FXCollections.observableArrayList();
			contact.setPhoneNumbers(numbers);
		}

		// Photo
		InputStream stream = contactsService.getContactImage(contact.getId());

		if (stream != null) {
			photo.setImage(new Image(stream));
		} else {
			photo.setImage(new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/Person.png")));
		}
	}

	/**
	 * Retrieve the person using the information typed in the text fields.
	 * 
	 * @return the person
	 */
	private UIFamily getContact() {
		family.setName(nameText.getText());
		family.setAddress(addressText.getText());

		return family;
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
			String id;
			try {
				if (family.getPhoto() != null) {
					InputStream photoStream = new BufferedInputStream(new FileInputStream(family.getPhoto()));
					id = contactsService.saveContact(family.toContact(), photoStream).getId();
				} else {
					id = contactsService.saveContact(family.toContact(), null).getId();
				}

				if (family.getId() == null) {
					family.setId(id);
					contacts.add(family);
				}
			} catch (FileNotFoundException e) {
				log.error("Couldn't find the file: " + family.getPhoto());
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
			family.setPhoto(file);
		} catch (FileNotFoundException e) {
			log.error("Couldn't find the file: " + file);
		} catch (IOException e) {
			log.error("Error accessing the file: " + file);
		}
	}
}
