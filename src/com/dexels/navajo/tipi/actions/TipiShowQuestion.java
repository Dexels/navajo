package com.dexels.navajo.tipi.actions;

import java.awt.*;
import java.lang.reflect.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
/** @todo Refactor, move to NavajoSwingTipi */
public class TipiShowQuestion extends TipiAction {
	
	int response = 0;
	
	public void execute(TipiEvent event) throws com.dexels.navajo.tipi.TipiException, com.dexels.navajo.tipi.TipiBreakException {
		final Object[] options = { "Ja", "Nee" };
		String txt = getParameter("text").getValue();
		Operand o = null;
		try {
			o = evaluate(txt, event);
		} catch (Exception ex) {
				ex.printStackTrace();
				return;
		}
		final Operand p = o;
		if(!SwingUtilities.isEventDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run() {
						response = JOptionPane.showOptionDialog((Component) myContext.getTopLevel(), p.value, "Vraag", JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					
					}});
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} else {
			response = JOptionPane.showOptionDialog((Component) myContext.getTopLevel(), o.value, "Vraag", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response != 0) {
				throw new TipiBreakException();
			}
		}
		if (response != 0) {
			throw new TipiBreakException();
		}
	}
}
