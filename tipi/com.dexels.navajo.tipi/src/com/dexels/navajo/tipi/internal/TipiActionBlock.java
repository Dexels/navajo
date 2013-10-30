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
 * @author not attributable
 * @version 1.0
 */
public class TipiActionBlock extends TipiAbstractExecutable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -744796253414327618L;
	private boolean multithread = false;
	private static final Logger logger = LoggerFactory.getLogger(TipiActionBlock.class);
	public TipiActionBlock(TipiContext tc) {
		super(tc);
	}

	@Override
	public void performAction(TipiEvent te, TipiExecutable parent, int index)
			throws TipiBreakException, TipiException {
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
					getContext().debugLog("thread",
							" multithread . Performing now");
					logger.info("In multithread block enqueueing: "
							+ current.toString());
					getContext().enqueueExecutable(current);
				}
			} else {
				getContext().doActions(te, getComponent(), this,
						getExecutables());
			}
			// not sure if this is wise
			setEvent(null);
		} catch (TipiBreakException ex) {
			if (TipiBreakException.BREAK_EVENT == ex.getType()) {
				throw ex;
			}
			return;
		} catch (Throwable t) {
			logger.error("Error performing action block",t);
		}
	}

	public void load(XMLElement elm, TipiComponent parent,
			TipiExecutable parentExe) {
		setComponent(parent);
		for (Iterator<String> iterator = elm.enumerateAttributeNames(); iterator
				.hasNext();) {
			String n = iterator.next();
			if (!n.equals("expression") && !n.equals("condition")) {
				setBlockParam(n, elm.getStringAttribute(n));
			}
		}

		// myEvent = event;

		if (elm.getName().equals("block")) {
			setExpression((String) elm.getAttribute("expression"));
			String condition = (String) elm.getAttribute("condition");
			if (condition != null) {
				setExpression(condition);
			}
			String multi = elm.getStringAttribute("multithread");
			if ("true".equals(multi)) {
				logger.warn("Load multithread block!");
				multithread = true;
			}
			setStackElement(new TipiStackElement("if: (" + getExpression()
					+ ")", elm, parentExe.getStackElement()));
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
