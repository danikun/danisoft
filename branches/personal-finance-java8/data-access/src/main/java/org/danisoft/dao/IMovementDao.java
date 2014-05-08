package org.danisoft.dao;

import org.danisoft.model.Movement;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IMovementDao extends PagingAndSortingRepository<Movement, Integer> {

}
