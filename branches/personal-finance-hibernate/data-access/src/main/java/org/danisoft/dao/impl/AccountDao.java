package org.danisoft.dao.impl;

import org.danisoft.dao.IAccountDao;
import org.danisoft.model.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends GenericReadWriteDao<Integer, Account> implements
		IAccountDao {

	protected AccountDao() {
		super(Account.class);
	}
}
