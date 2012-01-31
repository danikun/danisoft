package org.danisoft.model;

import java.util.List;

/**
 * Parent class for all the entities suitable of being a contact.
 * 
 * @author Daniel García
 *
 */
public class Contact {
	/**
	 * Primary key.
	 */
	private int id;
	/**
	 * Base name.
	 */
	private String name;
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
	private String address;
	
	public Contact(int id, String name, ContactType type,
			List<PhoneNumber> phoneNumbers, String address) {
		super();
		this.id = id;
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
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
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
	public ContactType getType() {
		return type;
	}
	
	public int getTypeId() {
		return type.getId();
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
