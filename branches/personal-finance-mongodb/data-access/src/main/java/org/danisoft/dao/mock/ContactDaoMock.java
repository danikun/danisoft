package org.danisoft.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.danisoft.dao.IContactDao;
import org.danisoft.model.Contact;

public class ContactDaoMock  implements IContactDao {
	
	private Map<Integer, Contact> data = new HashMap<Integer, Contact>();
	private int idSequence = 0;

	public Contact get(Integer id) {
		return data.get(id);
	}

	public List<Contact> getAll() {
		return new ArrayList<Contact>(data.values());
	}

	public Integer save(Contact object) {
		idSequence++;
		object.setId(idSequence);
		data.put(idSequence, object);
		
		return idSequence;
	}

	public void update(Contact object) {
		data.put(object.getId(), object);
	}

	public void delete(Contact object) {
		data.remove(object.getId());
	}

}
