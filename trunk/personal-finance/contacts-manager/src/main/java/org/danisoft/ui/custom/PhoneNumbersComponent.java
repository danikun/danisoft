package org.danisoft.ui.custom;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import org.danisoft.ui.model.UIPhoneNumber;

public class PhoneNumbersComponent extends HBox {
	
	private TableView<UIPhoneNumber> phoneNumbersTable = new TableView<UIPhoneNumber>();
	private VBox buttonsBox = new VBox();
	private Button addButton = new Button("Add");
	private Button removeButton = new Button("Remove");
	private Button editButton = new Button("Edit");
	
	public PhoneNumbersComponent(List<UIPhoneNumber> phoneNumbers) {
		super();
		
		buttonsBox.getChildren().addAll(addButton, removeButton, editButton);
		
		ObservableList<UIPhoneNumber> items = FXCollections.observableArrayList(phoneNumbers);
		phoneNumbersTable.setItems(items);
		
		TableColumn<UIPhoneNumber, String> numberColumn = new TableColumn<UIPhoneNumber, String>("Number");
		numberColumn.setCellValueFactory(new PropertyValueFactory<UIPhoneNumber, String>("number"));
		numberColumn.prefWidthProperty().bind(phoneNumbersTable.widthProperty());
		
		phoneNumbersTable.getColumns().add(numberColumn);
		phoneNumbersTable.setPrefSize(100, 300);
		phoneNumbersTable.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		phoneNumbersTable.prefWidthProperty().bind(this.widthProperty().multiply(0.5));
		
		buttonsBox.setSpacing(10);
		this.setSpacing(10);
		
		this.getChildren().addAll(phoneNumbersTable, buttonsBox);
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Stage stage = new Stage();
				stage.setTitle("New Phone Number");
				stage.initModality(Modality.WINDOW_MODAL);
				
				stage.setWidth(400);
				stage.setHeight(200);
				
				stage.setScene(new Scene(new GridPane()));
				stage.show();
			}
		});
	}
	
	public void setPhoneNumbers(List<UIPhoneNumber> phoneNumbers) {
		ObservableList<UIPhoneNumber> items = FXCollections.observableArrayList(phoneNumbers);
		phoneNumbersTable.setItems(items);
	}

	/**
	 * @return the phoneNumbersTable
	 */
	public TableView<UIPhoneNumber> getPhoneNumbersTable() {
		return phoneNumbersTable;
	}

	/**
	 * @return the buttonsBox
	 */
	public VBox getButtonsBox() {
		return buttonsBox;
	}
}
