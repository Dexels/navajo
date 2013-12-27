package com.dexels.navajo.tipi.internal;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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
 * @author Marte Koning
 * @version 1.0
 */
public class TipiMethod extends TipiAbstractExecutable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -744796253414327619L;
	private static final Logger logger = LoggerFactory.getLogger(TipiMethod.class);
	
	public TipiMethod(TipiContext tc) {
		super(tc);
	}

	@Override
	public void performAction(TipiEvent te, TipiExecutable parent, int index)
			throws TipiBreakException, TipiException {
		TipiEvent localInstance = (TipiEvent) te.clone();
		localInstance.setComponent(getComponent());
		setEvent(localInstance);
		try {
				getContext().doActions(localInstance, getComponent(), this,
						getExecutables());
			// not sure if this is wise
			setEvent(null);
		} catch (TipiBreakException ex) {
			if (TipiBreakException.BREAK_EVENT == ex.getType()) {
				throw ex;
			}
			return;
		} catch (Throwable t) {
			logger.error("Error performing method",t);
		}
	}

	public void load(XMLElement elm, TipiComponent parent,
			TipiExecutable parentExe) {
		setComponent(parent);
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator
				.hasNext();) {
			String n = iterator.next();
			setBlockParam(n, elm.getStringAttribute(n));
		}

		// myEvent = event;

		if (elm.getName().equals("localmethod") || elm.getName().equals("globalmethod")) {
			parseActions(elm.getChildren());
		} else {
			logger.error("WTF?! WHAT IS THIS ELEMENT?!");
		}
	}

	private final void parseActions(List<XMLElement> temp) {
		try {
			for (XMLElement current : temp) {
				if (current.getName().equals("block")) {
					TipiActionBlock con = getContext()
							.instantiateTipiActionBlock(current,
									getComponent(), this);

					appendTipiExecutable(con);
				} else {
					parseActions(getContext(), current);
				}
			}
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

}
