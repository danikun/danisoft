package org.danisoft.ui.custom;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;

import org.danisoft.ui.model.UIPhoneNumber;

public class PhoneNumbersComponent extends HBox {
	
	private TableView<UIPhoneNumber> phoneNumbersTable = new TableView<UIPhoneNumber>();
	private VBox buttonsBox = new VBox();
	private Button addButton = new Button("Add");
	private Button removeButton = new Button("Remove");
	private Button editButton = new Button("Edit");
	private ObservableList<UIPhoneNumber> items = FXCollections.observableArrayList();
	
	public PhoneNumbersComponent() {
		super();
		
		buttonsBox.getChildren().addAll(addButton, removeButton, editButton);
		
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
				final AddPhoneNumberComponent addPhoneNumberComponent = new AddPhoneNumberComponent();
				addPhoneNumberComponent.show(null);
				addPhoneNumberComponent.setOnHiding(new EventHandler<WindowEvent>() {
					
					@Override
					public void handle(WindowEvent arg0) {
						if (AddPhoneNumberComponent.ACTION_ADD.equals(addPhoneNumberComponent.getAction())) {
							items.add(addPhoneNumberComponent.getPhoneNumber());
						}
					}
				});
			}
		});
		
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (phoneNumbersTable.getSelectionModel().getSelectedItem() != null) {
					final AddPhoneNumberComponent addPhoneNumberComponent = new AddPhoneNumberComponent();
					addPhoneNumberComponent.show(phoneNumbersTable.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		removeButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				items.remove(phoneNumbersTable.getSelectionModel().getSelectedItem());
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

	/**
	 * @return the items
	 */
	public ObservableList<UIPhoneNumber> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ObservableList<UIPhoneNumber> items) {
		this.items = items;
		phoneNumbersTable.setItems(items);
	}
	
	
}
