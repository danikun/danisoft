package org.danisoft.dao;

import org.danisoft.model.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IContactDao extends PagingAndSortingRepository<Contact, Integer> {

}
