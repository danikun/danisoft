package org.danisoft.dao;

import java.util.Date;
import java.util.List;

import org.danisoft.model.Account;
import org.danisoft.model.Contract;
import org.danisoft.model.Period;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ContractDaoIT {

	@Autowired
	private IContractDao contractDao;
	@Autowired
	private IAccountDao accountDao;
	
	private Contract persisted;
	
	@Before
	public void Setup() {
		Account account = new Account("number", 100, "description", null);
		account = accountDao.save(account);
		
		persisted = contractDao.save(new Contract("Concept", new Date(), Period.MONTHLY, true, 100, account));
	}
	
	@Test
	public void testGetSingle() {
		Contract contract = contractDao.findOne(persisted.getId());
		Assert.assertNotNull(contract);
	}
	
	@Test
	public void testAccountMapping() {
		Contract contract = contractDao.findOne(persisted.getId());
		Assert.assertNotNull(contract.getAccount());
	}
	
	@Test
	public void testGetAll() {
		List<Contract> contracts = Lists.newArrayList(contractDao.findAll());
		Assert.assertFalse(contracts.isEmpty());
	}
	
	@After
	public void tearDown() {
		contractDao.deleteAll();
		accountDao.deleteAll();
	}
}
