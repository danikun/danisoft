package org.danisoft.dao;

import org.danisoft.model.Contact;

public interface IContactDao extends IReadDao<Integer, Contact>, IWriteDao<Integer, Contact>{

}
