package org.danisoft.repo.impl;

import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.danisoft.repo.IJcrSessionFactory;
import org.danisoft.repo.IRepositoryFactoryBean;

public class JcrSessionFactoryImpl implements IJcrSessionFactory {

	private IRepositoryFactoryBean repositoryFactory;
	private Repository repository;
	private Credentials credentials;
	private Session session;
	
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
	public void setRepositoryFactory(IRepositoryFactoryBean repositoryFactory) {
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
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void openSession() {
		try {
			repository = repositoryFactory.createRepository();
			session = repository.login(credentials);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeSession() {
		if (session != null)
			session.logout();
	}
}
