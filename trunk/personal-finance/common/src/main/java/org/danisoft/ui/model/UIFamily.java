package org.danisoft.ui.model;

import javafx.collections.ObservableList;

import org.danisoft.model.ContactType;

/**
 * UI representation of the Family entity.
 * 
 * @author Daniel Garcia
 * 
 */
public class UIFamily extends UIContact {
	/**
	 * Members of the family.
	 */
	private ObservableList<UIPerson> members;

	/**
	 * @param id the id
	 * @param name the name
	 * @param phoneNumbers the list of phone numbers
	 * @param address the address
	 * @param members the list of members
	 */
	public UIFamily(final int id, final String name, final ObservableList<UIPhoneNumber> phoneNumbers,
			final String address, final ObservableList<UIPerson> members) {
		super(id, name, ContactType.Family, phoneNumbers, address);
		this.members = members;
	}

	/**
	 * @return the members
	 */
	public ObservableList<UIPerson> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(final ObservableList<UIPerson> members) {
		this.members = members;
	}
}
