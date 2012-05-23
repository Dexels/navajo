package com.dexels.navajo.tipi.internal;

import java.util.List;
import java.util.Set;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.TipiContext;

public class RemoteDescriptionProvider extends BaseDescriptionProvider {

	public RemoteDescriptionProvider(TipiContext myContext) {
		super(myContext);
	}

	public Set<String> getDescriptionNames() {
		return myDescriptionMap.keySet();
	}

	public void setMessage(Message m) {
		List<Message> al = m.getAllMessages();
		for (int i = 0; i < al.size(); i++) {
			Message current = al.get(i);
			String name = current.getProperty("Name").getValue();
			String value = current.getProperty("Description").getValue();
			value = myContext.XMLUnescape(value);
			addDescription(name, value);
		}
		System.err.println("# of descriptions: " + myDescriptionMap.size());
	}

}
