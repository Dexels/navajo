package com.dexels.navajo.tipi.internal;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.TipiContext;

public class RemoteDescriptionProvider extends BaseDescriptionProvider {

	
	private final static Logger logger = LoggerFactory
			.getLogger(RemoteDescriptionProvider.class);
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
		logger.info("# of descriptions: " + myDescriptionMap.size());
	}

}
