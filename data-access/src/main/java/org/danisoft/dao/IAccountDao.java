package org.danisoft.dao;

import org.danisoft.model.Account;

public interface IAccountDao extends IReadDao<Integer, Account>, IWriteDao<Integer, Account>{

}
