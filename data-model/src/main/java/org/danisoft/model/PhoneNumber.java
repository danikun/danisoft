package org.danisoft.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * Data object to represent a phone number.
 * 
 * @author Daniel García
 *
 */
@Entity
public class PhoneNumber {
	/**
	 * Id.
	 */
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	private int id;
	/**
	 * Type of phone number.
	 */
	private PhoneNumberType type;
	/**
	 * The actual phone number.
	 */
	private String number;
	/**
	 * The contact owning this phone number.
	 */
	private Contact contact;	
	
	/**
	 * @return the type Id
	 */
	public int getTypeId() {
		return type.getId();
	}
	/**
	 * @return the type
	 */
	public PhoneNumberType getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = PhoneNumberType.fromCode(type);
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
	/**
	 * @return the contactid
	 */
	public int getContactId() {
		return contact.getId();
	}
	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}
	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
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
}
