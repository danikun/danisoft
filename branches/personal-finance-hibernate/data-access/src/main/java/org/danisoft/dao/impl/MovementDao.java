package org.danisoft.dao.impl;

import org.danisoft.dao.IMovementDao;
import org.danisoft.model.Movement;
import org.springframework.stereotype.Repository;

@Repository
public class MovementDao extends GenericReadWriteDao<Long, Movement> implements IMovementDao {
	public MovementDao() {
		super(Movement.class);
	}
}
