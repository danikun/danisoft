package org.danisoft.repo;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

/**
 * Factory for JCR sessions
 * 
 * @author Daniel Garcia
 *
 */
public interface IJcrSessionFactory {

	/**
	 * @return the created session
	 * @throws RepositoryException 
	 * @throws LoginException 
	 */
	Session getSession();
	
	/**
	 * Opens a new session in the repository
	 */
	void openSession();
	
	/**
	 * close the existing session
	 */
	void closeSession();
}
