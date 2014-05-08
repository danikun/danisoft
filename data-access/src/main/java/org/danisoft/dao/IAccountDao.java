package org.danisoft.dao;

import org.danisoft.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAccountDao extends PagingAndSortingRepository<Account, Integer> {

}
