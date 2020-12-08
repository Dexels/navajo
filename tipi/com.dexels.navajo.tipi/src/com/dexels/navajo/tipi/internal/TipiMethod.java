/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
import com.dexels.navajo.tipi.components.core.TipiSupportOverlayPane;
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
        String overlayType = "none";
        if (getBlockParam("overlay") != null) {
            overlayType = getBlockParam("overlay");
        }

        TipiSupportOverlayPane overlayComponent = null;
        boolean addedOverlay = false;
        if (!overlayType.equals("none")) {
            overlayComponent = localInstance.getComponent().getOverlayComponent();
            if (overlayComponent != null && !overlayComponent.isHidden()) {
                overlayComponent.addOverlayProgressPanel(overlayType);
                addedOverlay = true;
            }
        }

        try {
            getContext().doActions(localInstance, getComponent(), this, getExecutables());
            // not sure if this is wise
            setEvent(null);
        } catch (TipiBreakException ex) {
            if (TipiBreakException.BREAK_EVENT == ex.getType() || TipiBreakException.WEBSERVICE_BREAK == ex.getType() ) {
                throw ex;
            }
            return;
        } catch (Throwable t) {
            logger.error("Error performing method", t);
        } finally {
            if (addedOverlay && overlayComponent != null) {
                overlayComponent.removeOverlayProgressPanel();
            }
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
