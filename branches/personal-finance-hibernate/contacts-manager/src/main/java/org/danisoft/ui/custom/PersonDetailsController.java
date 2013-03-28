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
import org.danisoft.ui.model.UIPerson;
import org.danisoft.ui.model.UIPhoneNumber;

/**
 * Controller of the person details screen.
 * 
 * @author Daniel García
 * 
 */
public class PersonDetailsController implements Controller {
	/**
	 * Log.
	 */
	private final Log log = LogFactory.getLog(getClass());

	// Private properties
	/**
	 * The person to show/edit the details.
	 */
	private UIPerson contact;
	/**
	 * List of contacts.
	 */
	private ObservableList<UIContact> contacts;
	/**
	 * Contacts service.
	 */
	private IContactsService contactsService;
	/**
	 * Phone numbers page.
	 */
	private Page phoneNumbers;

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

	@SuppressWarnings("unchecked")
	@Override
	public void init(final Map<String, Object> params) {
		// Private properties
		contactsService = (IContactsService) params.get("contactsService");
		contacts = (ObservableList<UIContact>) params.get("contacts");
		phoneNumbers = (Page) params.get("phoneNumbers");

		// Load contact
		setContact((UIPerson) params.get("contact"));

		// Page setup
		// TODO: firstNameText.requestFocus();
		layout.setPrefSize(300, Double.MAX_VALUE);
		layout.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

		// Add phone numbers component
		phoneNumbers.getParams().put("items", contact.getPhoneNumbers());
		layout.add(phoneNumbers.load(), 1, 6);
	}

	/**
	 * Sets the person data into the form.
	 * 
	 * @param person the person to set
	 */
	private void setContact(final UIPerson person) {

		if (person == null) {
			contact = new UIPerson(0, "", "", "", null, "");
		} else {
			contact = person;
		}

		firstNameText.setText(contact.getName());
		lastName1Text.setText(contact.getLastName1());
		lastName2Text.setText(contact.getLastName2());
		addressText.setText(contact.getAddress());

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
	private UIPerson getContact() {
		contact.setName(firstNameText.getText());
		contact.setLastName1(lastName1Text.getText());
		contact.setLastName2(lastName2Text.getText());
		contact.setAddress(addressText.getText());

		return contact;
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
