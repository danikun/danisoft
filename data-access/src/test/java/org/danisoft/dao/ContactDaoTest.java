package org.danisoft.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.danisoft.dao.impl.ContactDaoImpl;
import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;
import org.danisoft.model.PhoneNumberType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

@ContextConfiguration(locations="classpath:/applicationContext.xml")
@TransactionConfiguration
public class ContactDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	protected ContactDaoImpl contactDao;
	
	@Test
	public void TestGetSingle() {
		
		ArrayList<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		numbers.add(new PhoneNumber(PhoneNumberType.Mobile, "00000000"));
		numbers.add(new PhoneNumber(PhoneNumberType.Home, "11111111"));
		
		int id = contactDao.save(new Person(0, "Daniel", ContactType.Person, numbers, "an address", "Garcia", "Pino"));
		Contact contact = contactDao.get(id);
		
		Assert.assertNotNull(contact);
		Assert.assertEquals(ContactType.contactTypeByCode(contact.getType()), ContactType.Person);
		Assert.assertEquals(contact.getPhoneNumbers().size(), 2);
		Assert.assertEquals(contact.getClass(), Person.class);
	}
	
	@Test
	public void TestGetAll() {
		ArrayList<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		numbers.add(new PhoneNumber(PhoneNumberType.Mobile, "00000000"));
		numbers.add(new PhoneNumber(PhoneNumberType.Home, "11111111"));
		
		contactDao.save(new Person(0, "Daniel", ContactType.Person, numbers, "an address", "Garcia", "Pino"));
		
		numbers = new ArrayList<PhoneNumber>();
		numbers.add(new PhoneNumber(PhoneNumberType.Mobile, "2222222"));
		numbers.add(new PhoneNumber(PhoneNumberType.Home, "3333333"));
		
		contactDao.save(new Person(0, "name", ContactType.Person, numbers, "an address", "surname1", "surname2"));
		
		List<Contact> result = contactDao.getAll();
		
		Assert.assertEquals(result.size(), 2);
		Assert.assertEquals(result.get(0).getClass(), Person.class);
	}
	
}
