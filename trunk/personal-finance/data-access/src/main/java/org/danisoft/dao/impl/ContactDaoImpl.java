package org.danisoft.dao.impl;

import java.util.List;

import org.danisoft.Mapper.ContactMapper;
import org.danisoft.Mapper.PersonMapper;
import org.danisoft.Mapper.PhoneNumberMapper;
import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;

/**
 * Actual implementation of the Contact DAO
 * 
 * @author Daniel Garc�a
 *
 */
public class ContactDaoImpl implements IContactDao {

	private PersonMapper personMapper;
	private ContactMapper contactMapper;
	private PhoneNumberMapper phoneNumberMapper;

	@Override
	public Contact get(int id) {
		return contactMapper.get(id);
	}

	@Override
	public List<Contact> getAll() {
		return contactMapper.getAll();
	}

	@Override
	public int save(Contact object) {
		contactMapper.save(object);

		for(PhoneNumber phoneNumber : object.getPhoneNumbers()) {
			phoneNumberMapper.save(phoneNumber);
		}

		if (object instanceof Person) {
			personMapper.save((Person) object);
		}

		return object.getId();
	}

	@Override
	public void update(Contact object) {
		contactMapper.update(object);

		for(PhoneNumber phoneNumber : object.getPhoneNumbers()) {
			if (phoneNumber.getId() > 0) {
				phoneNumberMapper.update(phoneNumber);
			} else {
				phoneNumberMapper.save(phoneNumber);
			}
		}

		if (object instanceof Person) {
			personMapper.update((Person) object);
		}
	}

	@Override
	public void delete(Contact object) {

		for (PhoneNumber phoneNumber : object.getPhoneNumbers()) {
			phoneNumberMapper.delete(phoneNumber);
		}

		if (object instanceof Person) {
			personMapper.delete((Person) object);
		}

		contactMapper.delete(object);
	}

	/**
	 * @param personMapper the personMapper to set
	 */
	public void setPersonMapper(PersonMapper personMapper) {
		this.personMapper = personMapper;
	}

	/**
	 * @param contactMapper the contactMapper to set
	 */
	public void setContactMapper(ContactMapper contactMapper) {
		this.contactMapper = contactMapper;
	}

	/**
	 * @param phoneNumberMapper the phoneNumberMapper to set
	 */
	public void setPhoneNumberMapper(PhoneNumberMapper phoneNumberMapper) {
		this.phoneNumberMapper = phoneNumberMapper;
	}
}
