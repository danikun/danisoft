package org.danisoft.dao;

/**
 * Generic interface for DAO's that are able to write data.
 * 
 * @author Daniel García
 *
 * @param <T> the type of objects to write
 */
public interface IWriteDao<T> {

	/**
	 * Saves a new data object.
	 * 
	 * @param object the object to save
	 * @return the id of the newly persisted object
	 */
	int save(T object);
	
	/**
	 * Updates an already persisted object.
	 * 
	 * @param object the object to update
	 */
	void update(T object);
	
	/**
	 * Deletes the given object
	 * 
	 * @param object the object to delete
	 */
	void delete(T object);
}
