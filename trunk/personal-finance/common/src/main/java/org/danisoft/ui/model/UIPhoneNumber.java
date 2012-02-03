package org.danisoft.ui.model;

import org.danisoft.model.PhoneNumber;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIPhoneNumber {

	private StringProperty number;
	private StringProperty type;
	private int id;
	
	/**
	 * @param number
	 * @param type
	 */
	public UIPhoneNumber(String number, String type) {
		super();
		this.number = new SimpleStringProperty(number);
		this.type = new SimpleStringProperty(type);
		this.id = 0;
	}

	public UIPhoneNumber(int id, String number, String type) {
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
	public void setNumber(String number) {
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
	public void setType(String type) {
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
	public void setId(int id) {
		this.id = id;
	}

	public PhoneNumber toPhoneNumber() {
		PhoneNumber phoneNumber = new PhoneNumber();
		
		phoneNumber.setId(id);
		phoneNumber.setNumber(getNumber());
		phoneNumber.setType(getType().substring(0,1));
		
		return phoneNumber;
	}
	
	public static UIPhoneNumber fromPhoneNumber(PhoneNumber phoneNumber) {
		UIPhoneNumber uiPhoneNumber = new UIPhoneNumber(phoneNumber.getId(), phoneNumber.getNumber(), phoneNumber.getType().getDisplayName());
		
		return uiPhoneNumber;
	}
}
