package org.danisoft.ui.custom;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.danisoft.ui.base.Controller;
import org.danisoft.ui.base.Page;
import org.danisoft.ui.model.UIPhoneNumber;

/**
 * Controller for the phone numbers component.
 * 
 * @author Daniel Garcia
 * 
 */
public class PhoneNumbersController implements Controller {

	// Private properties
	/**
	 * Phone list.
	 */
	private ObservableList<UIPhoneNumber> items = FXCollections.observableArrayList();

	// UI elements
	/**
	 * Phone numbers table.
	 */
	@FXML
	private TableView<UIPhoneNumber> phoneNumbersTable;
	/**
	 * number column.
	 */
	@FXML
	private TableColumn<UIPhoneNumber, String> numberColumn;
	/**
	 * Layout of the component.
	 */
	@FXML
	private HBox layout;
	/**
	 * Buttons layout VBox.
	 */
	@FXML
	private VBox buttonsBox;

	@SuppressWarnings("unchecked")
	@Override
	public void init(final Map<String, Object> params) {
		// Private properties
		if (params.get("items") != null) {
			items = (ObservableList<UIPhoneNumber>) params.get("items");
		}

		// Set bindings
		numberColumn.prefWidthProperty().bind(phoneNumbersTable.widthProperty());
		phoneNumbersTable.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		phoneNumbersTable.prefWidthProperty().bind(layout.widthProperty().multiply(0.9));
		buttonsBox.prefWidthProperty().bind(layout.widthProperty().multiply(0.1));

		phoneNumbersTable.setItems(items);
	}

	/**
	 * Handles the add button action event.
	 * 
	 * @param event add button action event
	 */
	@FXML
	public void handleAddButton(final ActionEvent event) {
		// TODO: add page to application context
		Page addPhoneNumberComponent = new Page();
		addPhoneNumberComponent.setName("Add phone number");
		addPhoneNumberComponent.setPath("/org/danisoft/ui/custom/AddPhoneNumber.fxml");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phoneNumbers", items);

		addPhoneNumberComponent.setParams(params);
		addPhoneNumberComponent.load();
	}

	/**
	 * Handles the edit button action event.
	 * 
	 * @param event edit button action event
	 */
	@FXML
	public void handleEditButton(final ActionEvent event) {
		if (phoneNumbersTable.getSelectionModel().getSelectedItem() != null) {
			Page addPhoneNumberComponent = new Page();
			addPhoneNumberComponent.setName("Add phone number");
			addPhoneNumberComponent.setPath("/org/danisoft/ui/custom/AddPhoneNumber.fxml");

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("phoneNumbers", items);
			params.put("phoneNumber", phoneNumbersTable.getSelectionModel().getSelectedItem());

			addPhoneNumberComponent.setParams(params);
			addPhoneNumberComponent.load();
		}
	}

	/**
	 * Handles the remove button action event.
	 * 
	 * @param event remove button action event
	 */
	@FXML
	public void handleRemoveButton(final ActionEvent event) {
		items.remove(phoneNumbersTable.getSelectionModel().getSelectedItem());
	}
}
