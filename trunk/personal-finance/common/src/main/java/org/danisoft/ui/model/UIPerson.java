package org.danisoft.ui.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;

/**
 * Mapping of the Person class of the data object to the UI data model.
 * 
 * @author dgarcia
 * 
 */
public class UIPerson extends UIContact {

	/**
	 * First surname.
	 */
	private StringProperty lastName1;
	/**
	 * Second surname.
	 */
	private StringProperty lastName2;

	public UIPerson(int id, String name, String lastName1, String lastName2,
			ObservableList<UIPhoneNumber> phoneNumbers, String address) {
		super(id, name, ContactType.Person, phoneNumbers, address);
		this.lastName1 = new SimpleStringProperty(lastName1);
		this.lastName2 = new SimpleStringProperty(lastName2);
		
		updateDisplayName();
	}

	/**
	 * @return the lastName1
	 */
	public String getLastName1() {
		return lastName1.get();
	}

	/**
	 * @param lastName1
	 *            the lastName1 to set
	 */
	public void setLastName1(String lastName1) {
		this.lastName1.set(lastName1);
		updateDisplayName();
	}

	/**
	 * @return the lastName2
	 */
	public String getLastName2() {
		return lastName2.get();
	}

	/**
	 * @param lastName2
	 *            the lastName2 to set
	 */
	public void setLastName2(String lastName2) {
		this.lastName2.set(lastName2);
		updateDisplayName();
	}

	public StringProperty lastName1Property() {
		return lastName1;
	}

	public StringProperty lastName2Property() {
		return lastName2;
	}

	@Override
	public Contact toContact() {
		Person person = new Person(getId(), getName(), getType(), null,
				getAddress(), getLastName1(), getLastName2());
		
		List<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		
		for (UIPhoneNumber phoneNumber : getPhoneNumbers()) {
			PhoneNumber number = phoneNumber.toPhoneNumber();
			number.setContact(person);
			
			numbers.add(number);
		}
		person.setPhoneNumbers(numbers);
		
		return person;
	}
	
	@Override
	protected void updateDisplayName() {
		StringBuilder displayName = new StringBuilder();
		
		if (lastName1 != null && lastName1.isNotNull().get() && !lastName1.get().isEmpty()) {
			displayName.append(lastName1.get());
			
			if (lastName2 != null && lastName2.isNotNull().get() && !lastName2.get().isEmpty()) {
				displayName.append(" " + lastName2.get());
			}
			displayName.append(", ");
		}
		
		displayName.append(name.get());
		
		this.displayName.set(displayName.toString());
	}
}
