package org.danisoft.services.impl;

import java.util.List;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;
import org.danisoft.services.IContactsService;

public class ContactsServiceImpl implements IContactsService {
	
	private IContactDao contactDao = null;

	public List<Contact> getAllContacts() {
		return contactDao.getAll();
	}

	public List<Contact> searchContacts(String keyword) {
		return null;
	}

	public int saveContact(Contact contact) {
		if (contact.getId() > 0) {
			contactDao.update(contact);
			return contact.getId();
		}
		return contactDao.save(contact);
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}

	@Override
	public void deleteContact(Contact contact) {
		contactDao.delete(contact);
	}
}
