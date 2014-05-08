package org.danisoft.ui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.danisoft.model.PhoneNumber;

/**
 * Phone number UI representation.
 * 
 * @author Daniel Garcia
 * 
 */
public class UIPhoneNumber {

	/**
	 * Number property.
	 */
	private final StringProperty number;
	/**
	 * Type Property.
	 */
	private final StringProperty type;
	/**
	 * Entity id.
	 */
	private int id;

	/**
	 * @param number the number
	 * @param type the type
	 */
	public UIPhoneNumber(final String number, final String type) {
		super();
		this.number = new SimpleStringProperty(number);
		this.type = new SimpleStringProperty(type);
		this.id = 0;
	}

	/**
	 * @param id the id
	 * @param number the number
	 * @param type the type
	 */
	public UIPhoneNumber(final int id, final String number, final String type) {
		super();
		this.number = new SimpleStringProperty(number);
		this.type = new SimpleStringProperty(type);
		this.id = id;
	}

	/**
	 * @return the number
	 */
	public StringProperty numberProperty() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(final String number) {
		this.number.set(number);
	}

	/**
	 * @return the type
	 */
	public StringProperty typeProperty() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type.set(type);
	}

	/**
	 * @return the number
	 */
	public String getNumber() {
		return number.get();
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type.get();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * Converts the object into a database phone number.
	 * 
	 * @return the converted object
	 */
	public PhoneNumber toPhoneNumber() {
		PhoneNumber phoneNumber = new PhoneNumber();

		phoneNumber.setId(id);
		phoneNumber.setNumber(getNumber());
		phoneNumber.setType(getType().substring(0, 1));

		return phoneNumber;
	}

	/**
	 * Creates a UI phone number from a database phone number.
	 * 
	 * @param phoneNumber the database phone number
	 * @return the UI phone number
	 */
	public static UIPhoneNumber fromPhoneNumber(final PhoneNumber phoneNumber) {
		UIPhoneNumber uiPhoneNumber = new UIPhoneNumber(phoneNumber.getId(), phoneNumber.getNumber(), phoneNumber.getType().getDisplayName());

		return uiPhoneNumber;
	}
}
