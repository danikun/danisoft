package org.danisoft.model;

import java.io.Serializable;
import java.util.List;

/**
 * Parent class for all the entities suitable of being a contact.
 * 
 * @author Daniel García
 *
 */
public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7142048697158184604L;
	/**
	 * Primary key.
	 */
	protected String id;
	/**
	 * Base name.
	 */
	protected String name;
	/**
	 * Contact type;
	 */
	protected ContactType type;
	/**
	 * Phone numbers
	 */
	protected List<PhoneNumber> phoneNumbers;
	/**
	 * Contact address
	 */
	protected String address;
	
	public Contact(String name, ContactType type,
			List<PhoneNumber> phoneNumbers, String address) {
		super();
		this.name = name;
		this.type = type;
		this.phoneNumbers = phoneNumbers;
		this.address = address;
	}
	
	public Contact() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type.getCode();
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String code) {
		this.type = ContactType.contactTypeByCode(code);
	}
	/**
	 * @return the phoneNumbers
	 */
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}
	/**
	 * @param phoneNumbers the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
