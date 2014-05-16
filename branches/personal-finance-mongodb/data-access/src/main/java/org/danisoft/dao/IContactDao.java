package org.danisoft.dao;

import org.danisoft.model.Contact;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

@RepositoryDefinition(domainClass = Contact.class, idClass = String.class)
public interface IContactDao extends MongoRepository<Contact, String> {

}
