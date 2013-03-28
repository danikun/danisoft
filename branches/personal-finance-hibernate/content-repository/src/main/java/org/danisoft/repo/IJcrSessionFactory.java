package org.danisoft.repo;

import javax.jcr.Session;

/**
 * Factory for JCR sessions.
 * 
 * @author Daniel Garcia
 * 
 */
public interface IJcrSessionFactory {

	/**
	 * @return the created session
	 */
	Session getSession();

	/**
	 * Opens a new session in the repository.
	 */
	void openSession();

	/**
	 * close the existing session.
	 */
	void closeSession();
}
