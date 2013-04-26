package org.danisoft.ui.custom;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.danisoft.ui.model.UIPhoneNumber;

public class PhoneNumbersComponent extends HBox {
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
	 * Buttons layout VBox.
	 */
	@FXML
	private VBox buttonsBox;
	/**
	 * Edit button.
	 */
	@FXML
	private Button editButton;
	/**
	 * Remove button.
	 */
	@FXML
	private Button removeButton;

	public PhoneNumbersComponent() {
		//Load FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/danisoft/ui/custom/PhoneNumbers.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		// Set bindings
		numberColumn.prefWidthProperty().bind(phoneNumbersTable.widthProperty());
		phoneNumbersTable.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		phoneNumbersTable.prefWidthProperty().bind(this.widthProperty().multiply(0.9));
		buttonsBox.prefWidthProperty().bind(this.widthProperty().multiply(0.1));
		editButton.disableProperty().bind(phoneNumbersTable.getSelectionModel().selectedItemProperty().isNull());
		removeButton.disableProperty().bind(phoneNumbersTable.getSelectionModel().selectedItemProperty().isNull());
	}

	/**
	 * Handles the add button action event.
	 * 
	 * @param event add button action event
	 */
	@FXML
	public void handleAddButton(final ActionEvent event) {
		AddPhoneNumberComponent addPhoneNumber = new AddPhoneNumberComponent(null, items);
	}

	/**
	 * Handles the edit button action event.
	 * 
	 * @param event edit button action event
	 */
	@FXML
	public void handleEditButton(final ActionEvent event) {
		if (phoneNumbersTable.getSelectionModel().getSelectedItem() != null) {
			AddPhoneNumberComponent addPhoneNumber = new AddPhoneNumberComponent(phoneNumbersTable.getSelectionModel().getSelectedItem(), items);
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

	public ObservableList<UIPhoneNumber> getPhoneNumbers() {
		return items;
	}

	public void setPhoneNumbers(ObservableList<UIPhoneNumber> phoneNumbers) {
		items = phoneNumbers;
		phoneNumbersTable.setItems(items);
	}
}
