package org.danisoft.Mapper;

import java.util.List;

/**
 * Generic mapper to represent the base database operations.
 * 
 * @author Daniel Garcia
 *
 * @param <T> A data class
 */
public interface GenericMapper<T> {
	/**
	 * Retrieve all the values of a type
	 * 
	 * @return
	 */
	List<T> getAll();
	/**
	 * Gets a single object from the database provided its id.
	 * 
	 * @param id the id of the data object to retrieve
	 * @return the data object required
	 */
	T get(int id);
	/**
	 * Persists a data object into the database.
	 * 
	 * @param object the object to persist
	 * @return the generated object id
	 */
	int save(T object);
	/**
	 * Updates an existing data object.
	 * 
	 * @param object the object to update
	 */
	void update(T object);
	/**
	 * Deletes a persisted object.
	 * 
	 * @param object the object to delete
	 */
	void delete(T object);
}
