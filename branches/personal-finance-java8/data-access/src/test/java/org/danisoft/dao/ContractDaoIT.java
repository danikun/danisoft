package org.danisoft.dao;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.danisoft.model.Account;
import org.danisoft.model.Contract;
import org.danisoft.model.Period;
import org.hibernate.PropertyValueException;
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
public class ContractDaoIT extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private IContractDao contractDao;
	@Autowired
	private IAccountDao accountDao;
	
	private Contract persisted;
	
	@Before
	@Transactional
	public void Setup() {
		Account account = new Account(0, "number", 100, "description", null);
		account.setId(accountDao.save(account).getId());
		
		persisted = contractDao.save(new Contract(0, "Concept", new Date(), Period.MONTHLY, true, 100, account));
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
	
	@Test(expected = PropertyValueException.class)
	public void testAccountIsMandatory() {
		contractDao.save(new Contract(0, "Concept", new Date(), Period.MONTHLY, true, 100, null));
	}
	
	@Test
	public void testGetAll() {
		List<Contract> contracts = Lists.newArrayList(contractDao.findAll());
		Assert.assertFalse(contracts.isEmpty());
	}
}
