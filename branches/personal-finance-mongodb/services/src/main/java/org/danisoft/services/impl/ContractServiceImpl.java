package org.danisoft.services.impl;

import java.util.List;

import org.danisoft.dao.IContractDao;
import org.danisoft.model.Contract;
import org.danisoft.services.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements IContractService {

	@Autowired
	private IContractDao contractDao;
	
	@Override
	public List<Contract> getAll() {
		return contractDao.findAll();
	}

	@Override
	public void saveAll(List<Contract> contracts) {
		contractDao.deleteAll();
		contractDao.save(contracts);
	}

}
