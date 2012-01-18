package org.danisoft.dao;

import java.util.List;

/**
 * Generic interface for DAO's that are able to read data.
 *  
 * @author Daniel García
 *
 * @param <T> the type of data object that the implementation can read
 */
public interface IReadDao<T> {
	
	/**
	 * Retrieve a single object given its id.
	 * 
	 * @param id the id of the object to retrieve
	 * @return the requested object
	 */
	T get(int id);
	
	/**
	 * Retrieve all the data objects of the type <T>
	 * 
	 * @return the list of retrieved objects
	 */
	List<T> getAll();
}
