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
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;

/**
 * Mapping of the Contact data object to the data model of the UI.
 * 
 * @author Daniel Garc�a
 * 
 */
public class UIContact {
	/**
	 * Primary key.
	 */
	protected IntegerProperty id;
	/**
	 * Base name.
	 */
	protected StringProperty name;
	/**
	 * Contact type;
	 */
	protected ContactType type;
	/**
	 * Phone numbers
	 */
	protected ObservableList<UIPhoneNumber> phoneNumbers;
	/**
	 * Contact address
	 */
	protected StringProperty address;
	/**
	 * JCR Path of the photo
	 */
	protected File photo;
	/**
	 * Display Name to show in the contacts list
	 */
	protected StringProperty displayName;

	public UIContact(int id, String name, ContactType type,
			ObservableList<UIPhoneNumber> phoneNumbers, String address) {
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
	public int getId() {
		return id.get();
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id.set(id);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name.set(name);
		updateDisplayName();
	}

	/**
	 * @return the type
	 */
	public ContactType getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(ContactType type) {
		this.type = type;
	}

	/**
	 * @return the phoneNumbers
	 */
	public ObservableList<UIPhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers
	 *            the phoneNumbers to set
	 */
	public void setPhoneNumbers(ObservableList<UIPhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address.get();
	}
	
	/**
	 * @param photoPath the photoPath to set
	 */
	public void setPhoto(File file) {
		this.photo = file;
	}
	
	public File getPhoto() {
		return this.photo;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address.set(address);
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public StringProperty nameProperty() {
		return name;
	}

	public StringProperty addressProperty() {
		return address;
	}
	
	@Override
	public String toString() {
		return name.get();
	}

	public Contact toContact() {
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		
		Contact contact = new Contact(this.getId(), this.getName(), this.getType(),
				null, this.getAddress());
		
		for (UIPhoneNumber phoneNumber : phoneNumbers) {
			PhoneNumber number = phoneNumber.toPhoneNumber();
			number.setContact(contact);
			
			numbers.add(number);
		}
		contact.setPhoneNumbers(numbers);
		
		return contact;
	}

	public static UIContact fromContact(Contact contact) {

		UIContact uiContact = null;
		
		ObservableList<UIPhoneNumber> phoneNumbers = FXCollections.observableArrayList();
		
		for (PhoneNumber number : contact.getPhoneNumbers()) {
			phoneNumbers.add(UIPhoneNumber.fromPhoneNumber(number));
		}
		
		if (contact instanceof Person) {
			Person person = (Person) contact;
			uiContact = new UIPerson(person.getId(), person.getName(),
					person.getLastName1(), person.getLastName2(),
					phoneNumbers, person.getAddress());
		} else {
			uiContact = new UIContact(contact.getId(), contact.getName(),
					contact.getType(), phoneNumbers,
					contact.getAddress()); 
		}

		return uiContact;
	}

	/**
	 * @return the displayName
	 */
	public StringProperty displayNameProperty() {
		return displayName;
	}
	
	protected void updateDisplayName() {
		displayName.set(name.get());
	}
	
}
