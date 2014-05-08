package org.danisoft.ui.custom.event;

import org.danisoft.ui.model.UIContact;

import javafx.event.Event;
import javafx.event.EventType;

public class ContactSaveEvent extends Event {
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -6147053970408312574L;

	public static final EventType<ContactSaveEvent> CONTACT_SAVE_EVENT = new EventType<>(
			Event.ANY, "CONTACT_SAVE_EVENT");
	
	private UIContact contact;

	public ContactSaveEvent(UIContact contact) {
		super(CONTACT_SAVE_EVENT);
		this.contact = contact;
	}

	/**
	 * @return the contact
	 */
	public UIContact getContact() {
		return contact;
	}
}
