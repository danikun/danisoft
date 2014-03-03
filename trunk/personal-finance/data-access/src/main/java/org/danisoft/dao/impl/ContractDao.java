package org.danisoft.dao.impl;

import org.danisoft.dao.IContractDao;
import org.danisoft.model.Contract;
import org.springframework.stereotype.Repository;

@Repository
public class ContractDao extends GenericReadWriteDao<Integer, Contract> implements IContractDao {

	/**
	 * Default constructor.
	 */
	public ContractDao() {
		super(Contract.class);
	}
}
