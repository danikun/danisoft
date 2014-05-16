package org.danisoft.services;

import java.io.InputStream;
import java.util.List;

import org.danisoft.model.Contact;

/**
 * Service to perform operations with contacts.
 * 
 * @author Daniel García
 *
 */
public interface IContactsService {
	/**
	 * Retrieve all the available contacts.
	 * 
	 * @return list of retrieved contacts
	 */
	List<Contact> getAllContacts();
	
	/**
	 * Search contacts based on the given keyword.
	 * 
	 * @param keyword the keyword to search for
	 * @return the list of found results
	 */
	List<Contact> searchContacts(String keyword);
	
	/**
	 * Saves a new contact
	 * 
	 * @param contact the contact to save
	 * @param stream the photo stream of the contact
	 * @return the id of the newly created contact
	 */
	String saveContact(Contact contact, InputStream stream);
	
	/**
	 * Deletes a contact
	 * 
	 * @param contact the contact to delete
	 */
	void deleteContact(Contact contact);

	/**
	 * @param id contact id
	 * @return the image that represents a contact
	 */
	InputStream getContactImage(String id);
}
