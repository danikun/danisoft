package org.danisoft.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;

import org.danisoft.model.Account;
import org.danisoft.model.Movement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class AccountDaoIT {
	
	@Autowired
	private IAccountDao accountDao;

	private Account persisted;
	
	@Before
	public void Setup(){
		List<Movement> movements = new ArrayList<Movement>();
		movements.add(new Movement(0, new Date(), new Date(), "test movement", 100.0));
		
		persisted = accountDao.save(new Account("XXXXXXXXXXX", 0.0, "Test account", movements));
		
		List<Movement> movements2 = new ArrayList<Movement>();
		movements2.add(new Movement(0, new Date(), new Date(), "test movement 2", 100.0));
		
		accountDao.save(new Account("XXXXXXXXXXX", 0.0, "Test account 2", movements2));
	}
	
	@Test
	public void TestGetSingle() {
		Account account = accountDao.findOne(persisted.getId());
		Assert.assertNotNull(account);
	}
	
	@Test
	public void TestMovementsMapping() {
		Account account = accountDao.findOne(persisted.getId());
		Assert.assertEquals(account.getMovements().size(), 1);
	}
	
	@Test
	public void TestGetAll() {
		List<Account> result = Lists.newArrayList(accountDao.findAll());
		Assert.assertEquals(2, result.size());
	}

	@After
	public void tearDown() {
		accountDao.deleteAll();
	}
}
