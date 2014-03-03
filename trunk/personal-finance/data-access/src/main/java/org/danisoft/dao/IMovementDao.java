package org.danisoft.dao;

import org.danisoft.model.Movement;

public interface IMovementDao extends IReadDao<Long, Movement>, IWriteDao<Long, Movement> {

}
