package com.dexels.navajo.tipi.components.core.parsers;


import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class EventRef implements TipiReference {

	private final TipiEvent myEvent;
	private final String myKey;
	public EventRef(String key, TipiEvent myEvent) {
		this.myEvent = myEvent;
		this.myKey = key;
	}

	public void setValue(Object val, TipiComponent source) {
		TipiValue tv = new TipiValue(null);
		tv.setValue(val);
		
		myEvent.addEventParameter(myKey,tv);
		}
	
	
}
