package org.danisoft.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.danisoft.model.Company;
import org.danisoft.model.Contact;
import org.danisoft.model.Family;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;
import org.danisoft.model.PhoneNumberType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@ContextConfiguration(classes = TestConfiguration.class)
@TransactionConfiguration
public class ContactDaoIT extends AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	protected IContactDao contactDao;
	
	// Variables to check test data.
	private Person person;
	private Company company;
	private Family family;
	
	@Before
	@Transactional
	public void setup() {
		ArrayList<PhoneNumber> numbers = new ArrayList<PhoneNumber>();
		numbers.add(new PhoneNumber(PhoneNumberType.Mobile, "00000000"));
		numbers.add(new PhoneNumber(PhoneNumberType.Home, "11111111"));
		
		//Setup a person.
		person = contactDao.save(new Person(0, "Daniel", numbers, "an address", "Garcia", "Pino"));
		//Setup a company.
		company = contactDao.save(new Company(0, "Company", null, "address"));
		//Setup a family
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(0, "name", null, "address", "lastName1", "lastName2"));
		
		family = contactDao.save(new Family(0, "Garcia", null, "an address", persons));
	}
	
	@Test
	public void testGetPerson() {
		Contact contact = contactDao.findOne(person.getId());
		Assert.assertTrue(contact instanceof Person);
	}
	
	@Test
	public void testGetFamily() {
		Contact contact = contactDao.findOne(family.getId());
		Assert.assertTrue(contact instanceof Family);
	}
	
	@Test
	public void testFamilyPersonsMapping() {
		Family family2 = (Family)contactDao.findOne(family.getId());
		Assert.assertEquals(1, family2.getPersons().size());
	}
	
	@Test
	public void testPhoneNumbersMapping() {
		Contact contact = contactDao.findOne(person.getId());
		Assert.assertEquals(2, contact.getPhoneNumbers().size());
	}
	
	@Test
	public void testGetCompany() {
		Contact contact = contactDao.findOne(company.getId());
		Assert.assertTrue(contact instanceof Company);
	}
	
	@Test
	public void TestGetAll() {
		List<Contact> result = Lists.newArrayList(contactDao.findAll());
		Assert.assertEquals(4, result.size());
	}
}
