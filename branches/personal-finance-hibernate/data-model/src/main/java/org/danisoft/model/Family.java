package org.danisoft.model;

import java.util.List;

/**
 * Data class that represents a Family.
 * 
 * @author Daniel Garcia
 *
 */
public class Family extends Contact {
	/**
	 * List of family members.
	 */
	private List<Person> persons;

	/**
	 * @param id the id
	 * @param name the name
	 * @param type the type of contact
	 * @param phoneNumbers the list of phone numbers
	 * @param address the address
	 * @param persons list of member of the family
	 */
	public Family(final int id, final String name, final ContactType type, final List<PhoneNumber> phoneNumbers,
			final String address, final List<Person> persons) {
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
	public void setPersons(final List<Person> persons) {
		this.persons = persons;
	}
}
