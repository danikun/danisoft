package org.danisoft.model;

import java.util.List;

/**
 * Data class that represents a Family.
 * 
 * @author Daniel Garcia
 *
 */
public class Family extends Contact {
	protected List<Person> persons;

	/**
	 * @param id
	 * @param name
	 * @param type
	 * @param phoneNumbers
	 * @param address
	 * @param persons
	 */
	public Family(int id, String name, ContactType type,
			List<PhoneNumber> phoneNumbers, String address, List<Person> persons) {
		super(id, name, type, phoneNumbers, address);
		this.persons = persons;
	}

	/**
	 * @return the persons
	 */
	public List<Person> getPersons() {
		return persons;
	}

	/**
	 * @param persons the persons to set
	 */
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
