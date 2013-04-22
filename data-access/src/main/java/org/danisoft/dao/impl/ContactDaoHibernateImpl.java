package org.danisoft.dao.impl;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;

public class ContactDaoHibernateImpl extends GenericDaoHibernateImpl<Integer, Contact> implements IContactDao{

	public ContactDaoHibernateImpl() {
		super(Contact.class);
	}
}
