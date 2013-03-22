package com.dexels.navajo.rhino;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Access;

@Deprecated
public class MappableFactory {
	
	private final static Logger logger = LoggerFactory
			.getLogger(MappableFactory.class);
	
	public Mappable createMappable(String className, Navajo input,
			Navajo output, Message currentOutMessage) {
		try {
			Class<? extends Mappable> mclass = (Class<? extends Mappable>) Class
					.forName(className);
			Mappable obj = mclass.newInstance();
			Access ac = new Access();
			ac.setInDoc(input);
			ac.setOutputDoc(output);
			ac.setCurrentOutMessage(currentOutMessage);
			obj.load(ac);
			return obj;
		} catch (Throwable e) {
			logger.error("Error: ", e);
		}
		return null;
	}
}
