package org.danisoft.repo;

import javax.jcr.Repository;

/**
 * Interface to implement the Content repository.
 * 
 * @author Daniel Garcia
 *
 */
public interface IRepositoryFactoryBean {

	/**
	 * Creates a new JCR repository.
	 * 
	 * @return the created repository
	 */
	Repository createRepository();
}
