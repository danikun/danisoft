package org.danisoft.model;

/**
 * Data object to represent a phone number.
 * 
 * @author Daniel García
 *
 */
public class PhoneNumber {
	/**
	 * Type of phone number.
	 */
	private PhoneNumberType type;
	/**
	 * The actual phone number.
	 */
	private String number;
	
	
	/**
	 * @return the type
	 */
	public PhoneNumberType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(PhoneNumberType type) {
		this.type = type;
	}
	/**
	 * @return the number
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setNumber(String number) {
		this.number = number;
	}
}
