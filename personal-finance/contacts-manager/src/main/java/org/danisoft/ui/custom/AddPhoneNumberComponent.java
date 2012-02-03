package org.danisoft.ui.custom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.apache.ibatis.builder.xml.dynamic.ChooseSqlNode;
import org.danisoft.constants.StyleClass;
import org.danisoft.model.PhoneNumberType;
import org.danisoft.ui.model.UIPhoneNumber;

public class AddPhoneNumberComponent extends Stage {

	public static final String ACTION_ADD = "ADD";
	public static final String ACTION_EDIT = "EDIT";
	public static final String ACTION_CANCEL = "CANCEL";
	
	private UIPhoneNumber phoneNumber;
	private String action;
	
	public void show(UIPhoneNumber number) {
		this.phoneNumber = number;
		
		this.setTitle("New Phone Number");
		this.initModality(Modality.APPLICATION_MODAL);
		this.setResizable(false);
		
		this.setWidth(300);
		this.setHeight(150);
		
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));
		
		Label typeLbl = new Label("Type");
		typeLbl.getStyleClass().add(StyleClass.FORM_FIELD);
		Label numberLbl = new Label("Number");
		numberLbl.getStyleClass().add(StyleClass.FORM_FIELD);
		final ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.prefWidthProperty().bind(gridPane.widthProperty());
		
		for (PhoneNumberType type : PhoneNumberType.values()) {
			choiceBox.getItems().add(type.getDisplayName());
		}
		
		final TextField textField = new TextField();
		
		Button acceptButton = new Button("Accept");
		Button cancelButton = new Button("Cancel");
		HBox buttons = new HBox();
		buttons.setAlignment(Pos.CENTER_RIGHT);
		buttons.setSpacing(10);
		buttons.getChildren().addAll(acceptButton, cancelButton);
		
		gridPane.add(typeLbl, 0, 0);
		gridPane.add(choiceBox, 1, 0);
		gridPane.add(numberLbl, 0, 1);
		gridPane.add(textField, 1, 1);
		gridPane.add(buttons, 0, 2);
		GridPane.setColumnSpan(buttons, 2);
		
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(30);
		column1.setHalignment(HPos.RIGHT);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(70);
		
		RowConstraints row1 = new RowConstraints();
		row1.setPercentHeight(33);
		row1.setValignment(VPos.CENTER);
		RowConstraints row2 = new RowConstraints();
		row2.setPercentHeight(33);
		row2.setValignment(VPos.CENTER);
		RowConstraints row3 = new RowConstraints();
		row3.setPercentHeight(33);
		row3.setValignment(VPos.CENTER);
		
		gridPane.getColumnConstraints().addAll(column1, column2);
		gridPane.getRowConstraints().addAll(row1, row2, row3);
		
		this.setScene(new Scene(gridPane));
		acceptButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (phoneNumber == null) {
					action = ACTION_ADD;
					phoneNumber = new UIPhoneNumber(null, null);
				} else {
					action = ACTION_EDIT;
				}
				
				phoneNumber.setNumber(textField.getText());
				phoneNumber.setType(choiceBox.getSelectionModel().getSelectedItem());
				close();
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				action = ACTION_CANCEL;
				close();
			}
		});
		
		if (phoneNumber != null) {
			textField.setText(phoneNumber.getNumber());
			choiceBox.getSelectionModel().select(phoneNumber.getType());
		}
		
		show();
	}

	/**
	 * @return the phoneNumber
	 */
	public UIPhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(UIPhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
