package org.danisoft.ui.pages;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import org.controlsfx.dialog.Dialogs;
import org.danisoft.model.Person;
import org.danisoft.model.PhoneNumber;
import org.danisoft.services.IContactsService;
import org.danisoft.ui.base.FormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class PersonFormController extends FormController<Person> {
	private static Logger log = LoggerFactory.getLogger(PersonFormController.class);
	
	@Autowired
	private IContactsService contactsService;
	
	@FXML
	private GridPane layout;
	@FXML
	private ImageView photo;
	@FXML
	private TextField firstNameText;
	@FXML
	private TextField lastName1Text;
	@FXML
	private TextField lastName2Text;
	@FXML
	private TextField addressText;
	@FXML
	private TableView<PhoneNumber> phoneNumbersTable;
	
	//Variable
	private ObservableList<PhoneNumber> phoneNumbers;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	super.initialize(location, resources);
    	
        //Control Binding
        firstNameText.textProperty().addListener(e -> isModified.set(true));
        firstNameText.disableProperty().bind(current.isNull());
            
        lastName1Text.disableProperty().bind(current.isNull());
        lastName1Text.textProperty().addListener(e -> isModified.set(true));
        
        lastName2Text.disableProperty().bind(current.isNull());
        lastName2Text.textProperty().addListener(e -> isModified.set(true));
        
        addressText.disableProperty().bind(current.isNull());
        addressText.textProperty().addListener(e -> isModified.set(true));
        
        //Phone numbers table bindings
        phoneNumbers = FXCollections.observableArrayList();
        
        phoneNumbersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        phoneNumbersTable.disableProperty().bind(current.isNull().or(isNew));
        phoneNumbersTable.setItems(phoneNumbers);
	}
	
	@Override
	protected void setCurrentFormFields() {
		isNew.set(current.get().getId() == null);
		
		if (!isNew()) {
			firstNameText.setText(current.get().getName());
			lastName1Text.setText(current.get().getLastName1());
			lastName2Text.setText(current.get().getLastName2());
			addressText.setText(current.get().getAddress());
			
			//Phone Numbers
			if (current.get().getPhoneNumbers() != null) {
				phoneNumbers.addAll(current.get().getPhoneNumbers());
			}
			
			// Photo
			InputStream stream = contactsService.getContactImage(current.get().getId());
	
			if (stream != null) {
				photo.setImage(new Image(stream));
			} else {
				photo.setImage(new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/Person.png")));
			}
		} else {
			clearFields();
		}
	}

	@Override
	protected void clearFields() {
		firstNameText.clear();
		lastName1Text.clear();
		lastName2Text.clear();
		addressText.clear();
		phoneNumbers.clear();
		
		photo.setImage(new Image(getClass().getResourceAsStream("/org/danisoft/ui/images/Person.png")));
	}

	@Override
	public void updateCurrent() {
		current.get().setName(firstNameText.getText());
		current.get().setLastName1(lastName1Text.getText());
		current.get().setLastName2(lastName2Text.getText());
		current.get().setAddress(addressText.getText());
		
		if (current.get().getPhoneNumbers() != null) {
			current.get().getPhoneNumbers().clear();
			current.get().getPhoneNumbers().addAll(phoneNumbers);
		} else {
			current.get().setPhoneNumbers(new ArrayList<>(phoneNumbers));
		}
	}

	/**
	 * Handle the set photo button action event.
	 * 
	 * @param event the action event to handle
	 */
	@FXML
	protected void handleSetPhotoButtonAction(final ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(layout.getScene().getWindow());
		try {
			InputStream stream = new BufferedInputStream(new FileInputStream(file));
			photo.setImage(new Image(stream));
			stream.close();
			current.get().setPhoto(file);
			isModified.set(true);
		} catch (FileNotFoundException e) {
			log.error("Couldn't find the file: " + file);
			Dialogs.create()
				.message("Couldn't find the file: ")
				.showException(e);
		} catch (IOException e) {
			log.error("Error accessing the file: " + file);
			Dialogs.create()
				.message("Error accessing the file: ")
				.showException(e);
		}
	}
}
