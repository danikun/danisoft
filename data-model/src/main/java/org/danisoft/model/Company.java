package org.danisoft.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Data object that represents a Company.
 * 
 * @author Daniel Garcia
 * 
 */
@Entity
@PrimaryKeyJoinColumn(name = "CompanyId")
public class Company extends Contact {

	/**
	 * Generated serial version number.
	 */
	private static final long serialVersionUID = 4621655563281153343L;

	public Company() {
		super();
	}

	public Company(int id, String name, List<PhoneNumber> phoneNumbers,
			String address) {
		super(id, name, ContactType.Company, phoneNumbers, address);
	}

}
