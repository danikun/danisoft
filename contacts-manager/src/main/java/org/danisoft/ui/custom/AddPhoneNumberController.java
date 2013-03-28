package org.danisoft.ui.custom;

import java.util.Map;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.danisoft.model.PhoneNumberType;
import org.danisoft.ui.base.Controller;
import org.danisoft.ui.model.UIPhoneNumber;

/**
 * 
 * @author Daniel Garcia
 * 
 */
public class AddPhoneNumberController implements Controller {
	// Constants
	/**
	 * Width of the pop-up.
	 */
	private static final double WIDTH = 300;
	/**
	 * Height of the pop-up.
	 */
	private static final double HEIGHT = 150;

	// UI elements
	/**
	 * Main layout of the component.
	 */
	@FXML
	private GridPane layout;

	/**
	 * Type of phone number selector.
	 */
	@FXML
	private ChoiceBox<String> choiceBox;

	/**
	 * Phone number text field.
	 */
	@FXML
	private TextField textField;

	// Private properties.
	/**
	 * Phone number being edited/created.
	 */
	private UIPhoneNumber phoneNumber;
	/**
	 * Stage.
	 */
	private Stage stage;
	/**
	 * Phone list.
	 */
	private ObservableList<UIPhoneNumber> items;

	@SuppressWarnings("unchecked")
	@Override
	public void init(final Map<String, Object> params) {
		// Set private properties.
		phoneNumber = (UIPhoneNumber) params.get("phoneNumber");
		items = (ObservableList<UIPhoneNumber>) params.get("phoneNumbers");

		// Setup stage to open as pop-up.
		stage = new Stage();

		if (phoneNumber == null) {
			stage.setTitle("New Phone Number");
		} else {
			stage.setTitle("Edit Phone Number");
			textField.setText(phoneNumber.getNumber());
			choiceBox.getSelectionModel().select(phoneNumber.getType());
		}

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);

		// Set bindings.
		choiceBox.prefWidthProperty().bind(layout.widthProperty());

		for (PhoneNumberType type : PhoneNumberType.values()) {
			choiceBox.getItems().add(type.getDisplayName());
		}

		// Set scene into the stage and show the stage.
		stage.setScene(new Scene(layout));
		stage.show();
	}

	/**
	 * Handles the accept button action event.
	 * 
	 * @param event the action event
	 */
	@FXML
	public void handleAcceptButton(final ActionEvent event) {
		if (phoneNumber == null) {
			phoneNumber = new UIPhoneNumber(null, null);
			items.add(phoneNumber);
		}

		phoneNumber.setNumber(textField.getText());
		phoneNumber.setType(choiceBox.getSelectionModel().getSelectedItem());
		stage.close();
	}

	/**
	 * Handles the cancel button action event.
	 * 
	 * @param event the action event
	 */
	@FXML
	public void handleCancelButton(final ActionEvent event) {
		stage.close();
	}
}
