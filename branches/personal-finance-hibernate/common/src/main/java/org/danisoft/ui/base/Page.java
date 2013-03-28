package org.danisoft.ui.base;

import java.io.IOException;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;

/**
 * Interface to describe the methods of the page classes.
 * 
 * @author dgarcia
 */
public class Page {
	/**
	 * Name of the page.
	 */
	private String name;

	/**
	 * Path to the FMXL file.
	 */
	private String path;

	/**
	 * Controller params.
	 */
	private Map<String, Object> params;

	/**
	 * Loads the page and all its contents.
	 * 
	 * @return a javaFX node containing the page
	 */
	public Node load() {

		FXMLLoader loader = new FXMLLoader();
		loader.setBuilderFactory(new JavaFXBuilderFactory(false));
		loader.setLocation(getClass().getResource(""));
		Node page = null;
		try {
			page = (Node) loader.load(getClass().getResourceAsStream(path));
			Controller controller = (Controller) loader.getController();
			if (controller != null) {
				controller.init(params);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return page;
	}

	/**
	 * Returns the name of the page.
	 * 
	 * @return the name of the page
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the pageName
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(final String path) {
		this.path = path;
	}

	/**
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(final Map<String, Object> params) {
		this.params = params;
	}
}
