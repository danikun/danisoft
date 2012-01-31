package org.danisoft.model;

import java.util.List;

/**
 * Data object to represent a person data.
 * 
 * @author Daniel García
 *
 */
public class Person extends Contact {

	/**
	 * First surname.
	 */
	private String lastName1;
	/**
	 * Second surname.
	 */
	private String lastName2;
	
	public Person() {
		super();
	}
	
	public Person(int id, String name, ContactType type,
			List<PhoneNumber> phoneNumbers, String address, String lastName1,
			String lastName2) {
		super(id, name, type, phoneNumbers, address);
		this.lastName1 = lastName1;
		this.lastName2 = lastName2;
	}
	
	/**
	 * @return the lastName1
	 */
	public String getLastName1() {
		return lastName1;
	}
	/**
	 * @param lastName1 the lastName1 to set
	 */
	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}
	/**
	 * @return the lastName2
	 */
	public String getLastName2() {
		return lastName2;
	}
	/**
	 * @param lastName2 the lastName2 to set
	 */
	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}
	
}
