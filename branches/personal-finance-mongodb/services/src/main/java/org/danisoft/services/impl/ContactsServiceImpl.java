package org.danisoft.services.impl;

import java.io.InputStream;
import java.util.List;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;
import org.danisoft.repo.IJcrTemplate;
import org.danisoft.services.IContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

/**
 * Contacts service implementation
 * 
 * @author Daniel Garcia
 *
 */
@Service("contactsService")
public class ContactsServiceImpl implements IContactsService {

	@Autowired
	private IContactDao contactDao = null;
	@Autowired
	private IJcrTemplate jcrTemplate = null;

	@Override
	public List<Contact> getAllContacts() {
		return Lists.newArrayList(contactDao.findAll());
	}

	@Override
	public List<Contact> searchContacts(String keyword) {
		return null;
	}

	@Override
	public Contact saveContact(Contact contact, InputStream stream) {
		Contact persisted = contactDao.save(contact);
		if (stream != null) {
			jcrTemplate.addBinary("image.png", "image/png", "contacts/" + persisted.getId() + "/", stream);
		}
		return persisted;
	}

	/**
	 * @param contactDao the contactDao to set
	 */
	public void setContactDao(final IContactDao contactDao) {
		this.contactDao = contactDao;
	}

	@Override
	public void deleteContact(final Contact contact) {
		contactDao.delete(contact);
		jcrTemplate.deleteNode("/contacts/" + contact.getId());
	}

	/**
	 * @param jcrTemplate the jcrTemplate to set
	 */
	public void setJcrTemplate(final IJcrTemplate jcrTemplate) {
		this.jcrTemplate = jcrTemplate;
	}

	@Override
	public InputStream getContactImage(String id) {
		return jcrTemplate.getBinary("/contacts/" + id + "/image.png");
	}
}
