package org.danisoft.repo.impl;

import java.io.File;

import javax.jcr.Repository;

import org.apache.jackrabbit.core.TransientRepository;
import org.danisoft.repo.IRepositoryFactoryBean;

public class RepositoryFactoryBeanImpl implements IRepositoryFactoryBean {

	/**
	 * XML configuration file.
	 */
	private String xml;
	
	/**
	 * Root path of the repository.
	 */
	private String path;
	
	public Repository createRepository() {
		Repository repository = new TransientRepository(new File(xml), new File(path));
		
		return repository;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
