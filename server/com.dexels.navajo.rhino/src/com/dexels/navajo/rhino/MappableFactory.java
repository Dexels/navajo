package com.dexels.navajo.rhino;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

@Deprecated
public class MappableFactory {
	@SuppressWarnings("unchecked")
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
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (MappableException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
		}
		return null;
	}
}
