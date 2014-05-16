package org.danisoft.model;

import java.util.List;

/**
 * Data object that represents a Company.
 * 
 * @author Daniel Garcia
 * 
 */
public class Company extends Contact {

	/**
	 * Generated serial version number.
	 */
	private static final long serialVersionUID = 4621655563281153343L;

	public Company() {
		super();
	}

	public Company(String name, List<PhoneNumber> phoneNumbers,
			String address) {
		super(name, ContactType.Company, phoneNumbers, address);
	}

}
