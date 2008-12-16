package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
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
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiAskValue extends TipiAction {
	public TipiAskValue() {
	}

	protected void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiBreakException, com.dexels.navajo.tipi.TipiException {
		final Operand text = getEvaluatedParameter("text", event);
		final Operand globalvalue = getEvaluatedParameter("value", event);
		final Operand initialValue = getEvaluatedParameter("initialValue", event);
		
		myContext.runSyncInEventThread(new Runnable() {

			public void run() {
				String initVal = "";
				if (initialValue != null) {
					if (initialValue.value != null) {
						initVal = "" + initialValue.value;
					}
				}
				String response = JOptionPane.showInputDialog((Component) myContext.getTopLevel(), text.value, initVal);
				if (response != null) {
					myContext.setGlobalValue("" + globalvalue.value, response);
				} else {
					throw new TipiBreakException(TipiBreakException.USER_BREAK);

				}
			}
		});
	}

}
