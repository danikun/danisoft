package org.danisoft.ui.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Family;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;

/**
 * Mapping of the Contact data object to the data model of the UI.
 * 
 * @author Daniel Garcia
 * 
 */
public class UIContact {
	/**
	 * Primary key.
	 */
	private final IntegerProperty id;
	/**
	 * Base name.
	 */
	private final StringProperty name;
	/**
	 * Contact type.
	 */
	private ContactType type;
	/**
	 * Phone numbers.
	 */
	private ObservableList<UIPhoneNumber> phoneNumbers;
	/**
	 * Contact address.
	 */
	private final StringProperty address;
	/**
	 * JCR Path of the photo.
	 */
	private File photo;
	/**
	 * Display Name to show in the contacts list.
	 */
	private final StringProperty displayName;

	/**
	 * Constructor.
	 * 
	 * @param id an id
	 * @param name a name
	 * @param type a type
	 * @param phoneNumbers a list of phone numbers
	 * @param address an address
	 */
	public UIContact(final int id, final String name, final ContactType type,
			final ObservableList<UIPhoneNumber> phoneNumbers,
			final String address) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.type = type;
		this.phoneNumbers = phoneNumbers;
		this.address = new SimpleStringProperty(address);
		this.displayName = new SimpleStringProperty();

		updateDisplayName();
	}

	/**
	 * @return the id
	 */
	public final int getId() {
		return id.get();
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(final int id) {
		this.id.set(id);
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name.get();
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(final String name) {
		this.name.set(name);
		updateDisplayName();
	}

	/**
	 * @return the type
	 */
	public final ContactType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(final ContactType type) {
		this.type = type;
	}

	/**
	 * @return the phoneNumbers
	 */
	public final ObservableList<UIPhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public final void setPhoneNumbers(final ObservableList<UIPhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	/**
	 * @return the address
	 */
	public final String getAddress() {
		return address.get();
	}

	/**
	 * @param file the file to set
	 */
	public final void setPhoto(final File file) {
		this.photo = file;
	}

	/**
	 * @return the photo
	 */
	public final File getPhoto() {
		return this.photo;
	}

	/**
	 * @param address the address to set
	 */
	public final void setAddress(final String address) {
		this.address.set(address);
	}

	/**
	 * @return the idProperty
	 */
	public final IntegerProperty idProperty() {
		return id;
	}

	/**
	 * @return the nameProperty
	 */
	public final StringProperty nameProperty() {
		return name;
	}

	/**
	 * @return the addressProperty
	 */
	public final StringProperty addressProperty() {
		return address;
	}

	@Override
	public final String toString() {
		return name.get();
	}

	/**
	 * Transform this UI contact to a database contact.
	 * 
	 * @return the database contact
	 */
	public Contact toContact() {
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();

		Contact contact = new Contact(this.getId(), this.getName(), this.getType(), null, this.getAddress());

		for (UIPhoneNumber phoneNumber : phoneNumbers) {
			PhoneNumber number = phoneNumber.toPhoneNumber();
			number.setContact(contact);

			numbers.add(number);
		}
		contact.setPhoneNumbers(numbers);

		return contact;
	}

	/**
	 * Generates a UI Contact with the data of a database contact.
	 * 
	 * @param contact a database contact
	 * @return the generated UI contact
	 */
	public static UIContact fromContact(final Contact contact) {

		UIContact uiContact = null;

		ObservableList<UIPhoneNumber> phoneNumbers = FXCollections.observableArrayList();

		for (PhoneNumber number : contact.getPhoneNumbers()) {
			phoneNumbers.add(UIPhoneNumber.fromPhoneNumber(number));
		}

		if (contact instanceof Person) {
			Person person = (Person) contact;
			uiContact = new UIPerson(person.getId(), person.getName(), person.getLastName1(), person.getLastName2(),
					phoneNumbers, person.getAddress());
		} else if (contact instanceof Family) {
			Family family = (Family) contact;
			ObservableList<UIPerson> members = FXCollections.observableArrayList();

			for (Person person : family.getPersons()) {
				members.add((UIPerson) UIContact.fromContact(person));
			}
			uiContact = new UIFamily(family.getId(), family.getName(), phoneNumbers, family.getAddress(), members);
		} else {
			uiContact = new UIContact(contact.getId(), contact.getName(), ContactType.contactTypeByCode(contact.getType()), phoneNumbers,
					contact.getAddress());
		}

		return uiContact;
	}

	/**
	 * @return the displayName
	 */
	public final StringProperty displayNameProperty() {
		return displayName;
	}

	/**
	 * Updates the display name.
	 */
	protected void updateDisplayName() {
		displayName.set(name.get());
	}

}
