package org.danisoft.services;

import java.util.List;

import org.danisoft.model.Contract;

public interface IContractService {

	List<Contract> getAll();

	void saveAll(List<Contract> contracts);

}
