package org.danisoft.repo;

import java.io.InputStream;

/**
 * Methods to access the content repository.
 * 
 * @author Daniel Garcia
 *
 */
public interface IJcrTemplate {

	/**
	 * Adds a binary content to the repository.
	 *  
	 * @param name name of the node to create
	 * @param mimeType mime type of the stream
	 * @param path the path to store the content
	 * @param stream byte stream to save
	 */
	void addBinary(String name, String mimeType, String path, InputStream stream);
	
	/**
	 * Retrieve a binary content from the repository
	 * 
	 * @param path path of the content to retrieve
	 * @return the byte stream of the content
	 */
	InputStream getBinary(String path);
	
	/**
	 * @param path the path of the node to delete
	 */
	void deleteNode(String path);
}
