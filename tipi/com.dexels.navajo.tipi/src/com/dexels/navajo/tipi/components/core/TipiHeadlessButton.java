package com.dexels.navajo.tipi.components.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiHeadlessButton extends TipiComponentImpl {


	private static final long serialVersionUID = 1303880032767792070L;
	private boolean enabled = true;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiHeadlessButton.class);
	
	@Override
	protected Object getComponentValue(String name) {
		if (name.equals("enabled")) {
			return enabled;
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		if (name.equals("enabled")) {
			enabled = (Boolean) object;
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object createContainer() {
		return null;
	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event) {
		if ("fireAction".equals(name)) {
			for (int i = 0; i < getEventList().size(); i++) {
				final int j = i;
				TipiEvent current = getEventList().get(j);
				if (current.isTrigger("onActionPerformed")) {
					try {
						doFireAction();
					} catch (TipiBreakException e) {
						logger.debug("Error: ",e);
					} catch (TipiException e) {
						logger.error("Error: ",e);
					}

				}
			}
		}
	}

	private void doFireAction() throws TipiBreakException,
			TipiException {
		performTipiEvent("onActionPerformed", null, false);

	}
}
