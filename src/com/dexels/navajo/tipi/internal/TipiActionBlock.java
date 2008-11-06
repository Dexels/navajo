package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.parser.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.tipixml.*;

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
public class TipiActionBlock extends TipiAbstractExecutable {
	private boolean multithread = false;
	
	public TipiActionBlock(TipiContext tc) {
		super(tc);
	}



	public void performAction(TipiEvent te, TipiExecutable parent, int index) throws TipiBreakException, TipiException {
		setEvent(te);
		boolean evaluated;
		evaluated = checkCondition(te);

		if (!evaluated) {
			return;
		}
		try {
			if (multithread) {
				for (int i = 0; i < getExecutables().size(); i++) {
					TipiExecutable current = getExecutables().get(i);
					getContext().debugLog("thread", " multithread . Performing now");
					System.err.println("In multithread block enqueueing: " + current.toString());
					getContext().enqueueExecutable(current);
				}
			} else {
				getContext().doActions(te, getComponent(), this, getExecutables());
			}
			// not sure if this is wise
			setEvent(null);
		} catch (TipiBreakException ex) {
			System.err.println("Break encountered!");
			if (TipiBreakException.BREAK_EVENT == ex.getType()) {
				throw ex;
			}
			return;
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}



	public void load(XMLElement elm, TipiComponent parent, TipiExecutable parentExe) {
		setComponent(parent);
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator.hasNext();) {
			String n = iterator.next();
			if (!n.equals("expression") && !n.equals("condition")) {
				setBlockParam(n, elm.getStringAttribute(n));
			}
		}

		// myEvent = event;

		if (elm.getName().equals("block")) {
			setExpression((String) elm.getAttribute("expression"));
			String condition = (String) elm.getAttribute("condition");
			if(condition!=null) {
				setExpression(condition);
			}
			String multi = elm.getStringAttribute("multithread");
			if ("true".equals(multi)) {
				System.err.println("Load multithread block!");
				multithread = true;
			}
			setStackElement(new TipiStackElement("if: (" + getExpression() + ")", elm, parentExe.getStackElement()));
			parseActions(elm.getChildren());
		} else {
			System.err.println("WTF?! WHAT IS THIS ELEMENT?!");
		}
	}

	private final void parseActions(List<XMLElement> temp) {
		try {
			for (XMLElement current : temp) {
				if (current.getName().equals("block")) {
					TipiActionBlock con = getContext().instantiateTipiActionBlock(current, getComponent(), this);

					appendTipiExecutable(con);
				} else {
					parseActions(getContext(), current);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
