package com.dexels.navajo.tipi.components.swingimpl.actions;

import java.awt.Component;

import javax.swing.JOptionPane;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.internal.TipiAction;
import com.dexels.navajo.tipi.internal.TipiEvent;

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

	private static final long serialVersionUID = 8276253817431008950L;

	public TipiAskValue() {
	}

	protected void execute(TipiEvent event)
			throws com.dexels.navajo.tipi.TipiBreakException,
			com.dexels.navajo.tipi.TipiException {
		final Operand text = getEvaluatedParameter("text", event);
		final Operand globalvalue = getEvaluatedParameter("value", event);
		final Operand initialValue = getEvaluatedParameter("initialValue",
				event);

		myContext.runSyncInEventThread(new Runnable() {

			public void run() {
				String initVal = "";
				if (initialValue != null) {
					if (initialValue.value != null) {
						initVal = "" + initialValue.value;
					}
				}
				String response = JOptionPane.showInputDialog(
						(Component) myContext.getTopLevel(), text.value,
						initVal);
				if (response != null) {
					myContext.setGlobalValue("" + globalvalue.value, response);
				} else {
					throw new TipiBreakException(TipiBreakException.USER_BREAK);

				}
			}
		});
	}

}
