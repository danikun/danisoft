package org.danisoft.repo.impl;

import java.io.InputStream;

import javax.jcr.Binary;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.danisoft.repo.IJcrSessionFactory;
import org.danisoft.repo.IJcrTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JcrTemplateImpl implements IJcrTemplate {

	private Log log = LogFactory.getLog(getClass());
	
	@Autowired
	private IJcrSessionFactory sessionFactory;
	
	public void addBinary(String name, String mimeType, String path,
			InputStream stream) {
		
		Session session = null;
		
		try {
			session = sessionFactory.getSession();
			Node dir = null;
			String[] folders = path.split("/");
			Node prev = session.getRootNode();
			
			for (String folder : folders) {
				try {
					dir = prev.getNode(folder);
					prev = dir;
				} catch (PathNotFoundException e) {
					dir = prev.addNode(folder);
				}
			}
			
			try {
				Node fileNode = dir.getNode(name);
				fileNode.remove();
				session.save();
			} catch (PathNotFoundException e) {
				// Do nothing it is OK
				log.info("The node doesn't exist, proceed adding the new node.");
			}
			
			Node fileNode = dir.addNode(name, NodeType.NT_FILE);
			Node content = fileNode.addNode(Property.JCR_CONTENT, NodeType.NT_RESOURCE);
			Binary binary = session.getValueFactory().createBinary(stream);
			content.setProperty(Property.JCR_DATA, binary);
			content.setProperty(Property.JCR_MIMETYPE, mimeType);
			
			session.save();
			
			System.out.println("hola");
			
		} catch (LoginException e) {
			log.error("Unable to log into the JCR", e);
		} catch (RepositoryException e) {
			log.error("Error executing a JCR operation", e);
		}
	}

	public InputStream getBinary(String path) {
		
		Session session = null;
		InputStream stream = null; 
		
		try {
			session = sessionFactory.getSession();
			try {
				Node node = session.getNode(path);
				Node content = node.getNode(Property.JCR_CONTENT);
				Binary data = content.getProperty(Property.JCR_DATA).getBinary();
				stream = data.getStream();
			} catch (PathNotFoundException e) {
				return null;
			}
			
		} catch (LoginException e) {
			log.error("Unable to log into the JCR", e);
		} catch (RepositoryException e) {
			log.error("Error executing a JCR operation", e);
		}
		
		return stream;
	}

	/**
	 * @return the sessionFactory
	 */
	public IJcrSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(IJcrSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void deleteNode(String path) {
		Session session = sessionFactory.getSession();
		
		try {
			Node node = session.getNode(path);
			node.remove();
			session.save();
		} catch (PathNotFoundException e) {
			log.error("The path to delete doesn't exist");
		} catch (RepositoryException e) {
			log.error("Error executing a JCR operation", e);
		}
	}
}
