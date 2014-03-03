package org.danisoft.dao.impl;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;
import org.springframework.stereotype.Repository;

@Repository
public class ContactDaoImpl extends GenericReadWriteDao<Integer, Contact> implements IContactDao {

	public ContactDaoImpl() {
		super(Contact.class);
	}
}
