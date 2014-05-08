package org.danisoft.repo.impl;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.repo.IJcrSessionFactory;
import org.danisoft.repo.IRepositoryFactoryBean;

/**
 * JCR session factory implementation.
 * 
 * @author Daniel Garcia
 *
 */
public class JcrSessionFactoryImpl implements IJcrSessionFactory {

	private final Log log = LogFactory.getLog(getClass());
	private IRepositoryFactoryBean repositoryFactory;
	private Repository repository;
	private Credentials credentials;
	private Session session;

	/**
	 * @return the current JCR session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @return the repositoryFactory
	 */
	public IRepositoryFactoryBean getRepositoryFactory() {
		return repositoryFactory;
	}

	/**
	 * @param repositoryFactory the repositoryFactory to set
	 */
	public void setRepositoryFactory(final IRepositoryFactoryBean repositoryFactory) {
		this.repositoryFactory = repositoryFactory;
	}

	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @param credentials the credentials to set
	 */
	public void setCredentials(final Credentials credentials) {
		this.credentials = credentials;
	}

	/**
	 * Opens a new JCR session.
	 */
	public void openSession() {
		try {
			repository = repositoryFactory.createRepository();
			session = repository.login(credentials);
		} catch (LoginException e) {
			log.error("Unable to log into the JCR", e);
		} catch (RepositoryException e) {
			log.error("Error executing a JCR operation", e);
		}
	}

	/**
	 * Closes the current JCR session.
	 */
	public void closeSession() {
		if (session != null) {
			session.logout();
		}
	}
}