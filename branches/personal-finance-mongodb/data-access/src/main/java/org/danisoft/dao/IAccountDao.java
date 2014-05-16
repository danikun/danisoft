package org.danisoft.dao;

import org.danisoft.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IAccountDao extends MongoRepository<Account, String> {

}
