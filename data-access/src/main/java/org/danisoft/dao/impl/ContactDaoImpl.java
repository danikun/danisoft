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
 * Actual implementation of the Contact DAO.
 * 
 * @author Daniel Garc�a
 * 
 */
public class ContactDaoImpl implements IContactDao {

	/**
	 * MyBatis mapper for person objects.
	 */
	private PersonMapper personMapper;
	/**
	 * MyBatis mapper for contact objects.
	 */
	private ContactMapper contactMapper;
	/**
	 * MyBatis mapper for phone number objects.
	 */
	private PhoneNumberMapper phoneNumberMapper;

	@Override
	public Contact get(Integer id) {
		return contactMapper.get(id);
	}

	@Override
	public List<Contact> getAll() {
		return contactMapper.getAll();
	}

	@Override
	public Integer save(final Contact object) {
		contactMapper.save(object);

		for (PhoneNumber phoneNumber : object.getPhoneNumbers()) {
			phoneNumberMapper.save(phoneNumber);
		}

		if (object instanceof Person) {
			personMapper.save((Person) object);
		}

		return object.getId();
	}

	@Override
	public void update(final Contact object) {
		contactMapper.update(object);

		for (PhoneNumber phoneNumber : object.getPhoneNumbers()) {
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
	public void delete(final Contact object) {

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
	public void setPersonMapper(final PersonMapper personMapper) {
		this.personMapper = personMapper;
	}

	/**
	 * @param contactMapper the contactMapper to set
	 */
	public void setContactMapper(final ContactMapper contactMapper) {
		this.contactMapper = contactMapper;
	}

	/**
	 * @param phoneNumberMapper the phoneNumberMapper to set
	 */
	public void setPhoneNumberMapper(final PhoneNumberMapper phoneNumberMapper) {
		this.phoneNumberMapper = phoneNumberMapper;
	}
}
