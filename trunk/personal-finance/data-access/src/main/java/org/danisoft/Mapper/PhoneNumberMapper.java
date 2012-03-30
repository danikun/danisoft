package org.danisoft.Mapper;

import org.danisoft.model.PhoneNumber;

/**
 * Mapper for the PhoneNumber objects.
 * 
 * @author Daniel Garcia
 *
 */
public interface PhoneNumberMapper {

	/**
	 * @param phoneNumbers the PhoneNumber to save
	 */
	void save(PhoneNumber phoneNumber);

	/**
	 * @param phoneNumber the PhoneNumber to update
	 */
	void update(PhoneNumber phoneNumber);

	/**
	 * @param phoneNumber the phoneNumber to delete
	 */
	void delete(PhoneNumber phoneNumber);
}
