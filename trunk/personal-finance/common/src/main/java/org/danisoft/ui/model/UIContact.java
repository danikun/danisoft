package org.danisoft.ui.model;

import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;

/**
 * Mapping of the Contact data object to the data model of the UI.
 * 
 * @author Daniel García
 * 
 */
public class UIContact {
	/**
	 * Primary key.
	 */
	private IntegerProperty id;
	/**
	 * Base name.
	 */
	private StringProperty name;
	/**
	 * Contact type;
	 */
	private ContactType type;
	/**
	 * Phone numbers
	 */
	private List<PhoneNumber> phoneNumbers;
	/**
	 * Contact address
	 */
	private StringProperty address;
	/**
	 * JCR Path of the photo
	 */
	private StringProperty photoPath;

	public UIContact(int id, String name, ContactType type,
			List<PhoneNumber> phoneNumbers, String address, String photoPath) {
		super();
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.type = type;
		this.phoneNumbers = phoneNumbers;
		this.address = new SimpleStringProperty(address);
		this.photoPath = new SimpleStringProperty(photoPath);
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
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers
	 *            the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
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
	public void setPhotoPath(String photoPath) {
		this.photoPath.set(photoPath);
	}
	
	public String getPhotoPath() {
		return this.photoPath.get();
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
	
	public StringProperty photoPathProperty() {
		return photoPath;
	}

	@Override
	public String toString() {
		return name.get();
	}

	public Contact toContact() {
		return new Contact(this.getId(), this.getName(), this.getType(),
				this.getPhoneNumbers(), this.getAddress());
	}

	public static UIContact fromContact(Contact contact) {

		UIContact uiContact = null;
		
		String photoPath = null; 
		
		if (contact.getId() > 0) {
			photoPath = "ContactData/" + contact.getId() + "/photo.png";
		}

		if (contact instanceof Person) {
			Person person = (Person) contact;
			uiContact = new UIPerson(person.getId(), person.getName(),
					person.getLastName1(), person.getLastName2(),
					person.getPhoneNumbers(), person.getAddress(), photoPath);
		} else {
			uiContact = new UIContact(contact.getId(), contact.getName(),
					contact.getType(), contact.getPhoneNumbers(),
					contact.getAddress(), photoPath); 
		}

		return uiContact;
	}
}
