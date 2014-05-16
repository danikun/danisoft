package org.danisoft.dao;

import org.danisoft.model.Movement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IMovementDao extends MongoRepository<Movement, Integer> {

}
