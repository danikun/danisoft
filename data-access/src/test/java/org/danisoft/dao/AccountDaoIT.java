package org.danisoft.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration(locations="classpath:/applicationContext.xml")
@TransactionConfiguration
public class AccountDaoIT extends
		AbstractTransactionalJUnit4SpringContextTests {
	
	@Autowired
	private IAccountDao accountDao;
	
	@Autowired
	protected SessionFactory sessionFactory;

	private Integer id;
	
	@Before
	@Transactional
	public void Setup(){
		List<Movement> movements = new ArrayList<Movement>();
		movements.add(new Movement(0, new Date(), new Date(), "test movement", 100.0));
		
		id = accountDao.save(new Account(0, "XXXXXXXXXXX", 0.0, "Test account", movements));
		
		List<Movement> movements2 = new ArrayList<Movement>();
		movements2.add(new Movement(0, new Date(), new Date(), "test movement 2", 100.0));
		
		accountDao.save(new Account(0, "XXXXXXXXXXX", 0.0, "Test account 2", movements2));
	}
	
	@Test
	public void TestGetSingle() {
		Account account = accountDao.get(id);
		Assert.assertNotNull(account);
	}
	
	@Test
	public void TestMovementsMapping() {
		Account account = accountDao.get(id);
		Assert.assertEquals(account.getMovements().size(), 1);
	}
	
	@Test
	public void TestGetAll() {
		List<Account> result = accountDao.getAll();
		Assert.assertEquals(2, result.size());
	}

	@After
	public void tearDown(){
		this.deleteFromTables("account", "movement");
	}
}
