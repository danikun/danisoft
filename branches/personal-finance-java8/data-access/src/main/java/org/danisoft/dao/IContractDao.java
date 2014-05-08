package org.danisoft.dao;

import org.danisoft.model.Contract;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IContractDao extends PagingAndSortingRepository<Contract, Integer> {

}
