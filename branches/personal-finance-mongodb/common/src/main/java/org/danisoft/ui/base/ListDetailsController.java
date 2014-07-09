package org.danisoft.ui.base;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import org.controlsfx.control.ButtonBar;
import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ListDetailsController<T> implements Initializable {
	private static final Logger log = LoggerFactory.getLogger(ListDetailsController.class);
	
	@FXML
	protected TableView<T> listTable;
	@FXML
	protected AnchorPane formPane;
	@FXML
	protected ButtonBar listButtonBar;
	@FXML
	protected ButtonBar formButtonBar;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			initTableColumns();
			initForm();
			createActions();
			loadData();
			bindView();
		} catch (Exception e) {
			log.error("An unexpected exception ocurred", e);
			Dialogs.create().message("An unexpected exception ocurred")
					.showException(e);
		}
	}
	
	protected void attachForm(Node form) {
		formPane.getChildren().clear();
		
		if (form == null) return;
		
		AnchorPane.setBottomAnchor(form, 0.0);
		AnchorPane.setTopAnchor(form, 0.0);
		AnchorPane.setLeftAnchor(form, 0.0);
		AnchorPane.setRightAnchor(form, 0.0);
		
		formPane.getChildren().add(form);
	}

	protected abstract void initTableColumns();

	protected abstract void initForm() throws Exception;

	protected abstract void loadData();

	protected abstract void bindView();

	protected abstract void createActions();
}
