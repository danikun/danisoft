package org.danisoft.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * Parent class for all the entities suitable of being a contact.
 * 
 * @author Daniel García
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Contact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7142048697158184604L;
	/**
	 * Primary key.
	 */
	protected int id;
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
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name = "contact_id")
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
	@OneToMany(mappedBy = "contact", fetch=FetchType.LAZY, cascade= {CascadeType.ALL})	
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
