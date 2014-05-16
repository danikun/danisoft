package org.danisoft.dao;

import org.danisoft.model.Contract;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IContractDao extends MongoRepository<Contract, String> {

}
