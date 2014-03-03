package org.danisoft.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * Data class that represents a Family.
 * 
 * @author Daniel Garcia
 *
 */
@Entity
@PrimaryKeyJoinColumn(name = "ContactId")
public class Family extends Contact {
	/**
	 * 
	 */
	private static final long serialVersionUID = 929300636252602001L;
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
	public Family(final int id, final String name, final List<PhoneNumber> phoneNumbers,
			final String address, final List<Person> persons) {
		super(id, name, ContactType.Family, phoneNumbers, address);
		this.persons = persons;
	}
	
	public Family() {
		super();
	}

	/**
	 * @return the persons
	 */
	@OneToMany (cascade = CascadeType.ALL)
	@JoinTable(
			name = "FamilyPersons",
			joinColumns = @JoinColumn(name = "FamilyId"),
			inverseJoinColumns = @JoinColumn(name = "PersonId")
	)
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
