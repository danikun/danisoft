package org.danisoft.ui.custom;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import org.danisoft.services.IContactsService;
import org.danisoft.ui.custom.event.ContactSaveEvent;
import org.danisoft.ui.model.UIContact;

/**
 * Abstract base class to create contact detail pages.
 * 
 * @author dgarcia
 *
 */
public abstract class ContactDetailsAbstractComponent extends GridPane {
	
	// UI elements
	protected final Button saveButton = new Button("Save");
	
	// Save Event handler
	protected EventHandler<ContactSaveEvent> saveEventHandler = null;
	
	/**
	 * Constructor.
	 * 
	 * @param contact the contact to show
	 * @param editable if the controls of the component are editable or not
	 */
	public ContactDetailsAbstractComponent() {
		super();
		
		// Save button eventHandler
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ContactSaveEvent e = new ContactSaveEvent(getContact());
				
				if (saveEventHandler != null) {
					saveEventHandler.handle(e);
				}
			}
		});
	}
	
	/**
	 * Setter for the contact represented in this details page.
	 * 
	 */
	public abstract void setContact(UIContact contact);
	
	/**
	 * Getter for the contact represented in this details page.
	 * 
	 */
	protected abstract UIContact getContact();
	
	/**
	 * Setter for the editable property.
	 * 
	 */
	public void setEditable(boolean editable) {
		saveButton.setVisible(editable);
	}

	/**
	 * @return the saveEventHandler
	 */
	public EventHandler<ContactSaveEvent> getSaveEventHandler() {
		return saveEventHandler;
	}

	/**
	 * @param saveEventHandler the saveEventHandler to set
	 */
	public void setSaveEventHandler(EventHandler<ContactSaveEvent> saveEventHandler) {
		this.saveEventHandler = saveEventHandler;
	}
	
	public abstract void setFocus();
	
	public abstract void setContactsService(IContactsService contactsService);
		
}
