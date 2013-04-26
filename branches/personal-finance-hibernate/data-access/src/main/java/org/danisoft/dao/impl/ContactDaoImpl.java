package org.danisoft.dao.impl;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;

public class ContactDaoImpl extends GenericReadWriteDao<Integer, Contact> implements IContactDao {

	public ContactDaoImpl() {
		super(Contact.class);
	}
}
