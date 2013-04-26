package org.danisoft.ui.custom;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.danisoft.model.PhoneNumberType;
import org.danisoft.ui.model.UIPhoneNumber;

public class AddPhoneNumberComponent extends GridPane {
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

	public AddPhoneNumberComponent(UIPhoneNumber phoneNumber, ObservableList<UIPhoneNumber> phoneNumbers) {
		//Load FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/danisoft/ui/custom/AddPhoneNumber.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		
		// Setup stage to open as pop-up.
		stage = new Stage();		

		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);

		stage.setWidth(WIDTH);
		stage.setHeight(HEIGHT);

		// Set bindings.
		choiceBox.prefWidthProperty().bind(this.widthProperty());

		for (PhoneNumberType type : PhoneNumberType.values()) {
			choiceBox.getItems().add(type.getDisplayName());
		}
		
		//Private properties
		this.phoneNumber = phoneNumber;
		
		if (phoneNumber == null) {
			stage.setTitle("New Phone Number");
		} else {
			stage.setTitle("Edit Phone Number");
			textField.setText(phoneNumber.getNumber());
			choiceBox.getSelectionModel().select(phoneNumber.getType());
		}
		
		this.items = phoneNumbers;

		// Set scene into the stage and show the stage.
		stage.setScene(new Scene(this));
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
	
	public void setPhoneNumber(UIPhoneNumber phoneNumber) {
		
	}
	
	public void setPhoneNumbers(ObservableList<UIPhoneNumber> phoneNumbers) {
		
	}
}
