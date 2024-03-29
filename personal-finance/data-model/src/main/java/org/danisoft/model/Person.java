package org.danisoft.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Data object to represent a person data.
 * 
 * @author Daniel Garc�a
 *
 */
@Entity
@PrimaryKeyJoinColumn(name = "ContactId")
public class Person extends Contact {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3941791824743960357L;
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
	
	public Person(int id, String name, List<PhoneNumber> phoneNumbers, 
			String address, String lastName1, String lastName2) {
		super(id, name, ContactType.Person, phoneNumbers, address);
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
	
	@Override
	public String toString() {
		StringBuilder displayName = new StringBuilder();
		
		if (lastName1 != null && !lastName1.isEmpty()) {
			displayName.append(lastName1);
			
			if (lastName2 != null && !lastName2.isEmpty()) {
				displayName.append(" " + lastName2);
			}
			displayName.append(", ");
		}
		
		displayName.append(name);
				
		return displayName.toString();
	}
}
