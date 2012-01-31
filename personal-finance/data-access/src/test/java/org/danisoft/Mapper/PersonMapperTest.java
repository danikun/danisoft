package org.danisoft.Mapper;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.danisoft.model.Contact;
import org.danisoft.model.ContactType;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PersonMapperTest {

	SqlSession session = null;
	Person person = new Person(0, "Dani", ContactType.Person, null, "address", "Garcia", "Pino");
	PersonMapper personMapper = null;
	ContactMapper contactMapper = null;
	Log log = LogFactory.getLog(this.getClass());
	
	@Before
	public void setup() {
		String driver = "org.apache.derby.jdbc.EmbeddedDriver";
		String connectionURL = "jdbc:derby:directory:../personalFinanceDB;create=true";
		DataSource dataSource = new PooledDataSource(driver, connectionURL, "", "");
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		
		Environment environment = new Environment("test", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		configuration.getTypeAliasRegistry().registerAlias("Contact", Contact.class);
		configuration.getTypeAliasRegistry().registerAlias("Person", Person.class);
		configuration.getTypeAliasRegistry().registerAlias("PhoneNumber", PhoneNumber.class);
		configuration.addMapper(ContactMapper.class);
		configuration.addMapper(PersonMapper.class);
		
		SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		session = sessionFactory.openSession();
		
		personMapper = session.getMapper(PersonMapper.class);
		contactMapper = session.getMapper(ContactMapper.class);
	}
	
	@Test
	public void testMapperContact() {
		contactMapper.save(person);
		
		Assert.assertTrue(person.getId() > 0);
		personMapper.save(person);
		session.commit();
		
		Person person2 = null;
		person2 = (Person) personMapper.get(person.getId());
		
		Assert.assertEquals("Garcia", person2.getLastName1());
		Assert.assertEquals("Pino", person2.getLastName2());
		
		person.setName("David");
		person.setLastName1("Gonzalez");
		person.setLastName2("Pardeza");
		
		contactMapper.update(person);
		personMapper.update(person);
		session.commit();
		person2 = personMapper.get(person.getId());
		
		Assert.assertEquals("David", person2.getName());
		Assert.assertEquals("Gonzalez", person2.getLastName1());
		Assert.assertEquals("Pardeza", person2.getLastName2());
		
		personMapper.delete(person);
		contactMapper.delete(person);
		person = personMapper.get(person.getId());
		Assert.assertNull(person);
	}
	
	@After
	public void tearDown() {
		session.close();
	}
}
