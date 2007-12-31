package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
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
public class TipiTextField extends TipiSwingComponentImpl {
	private TipiSwingTextField myField;

	public TipiTextField() {
	}

	public Object createContainer() {
		myField = new TipiSwingTextField(this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				// System.err.println("typed: "+e.getKeyChar());
				Map m = getEventMap(e);
				m.put("mode", "typed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyPressed(KeyEvent e) {
				Map m = getEventMap(e);
				m.put("mode", "pressed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent e) {
				Map m = getEventMap(e);
				m.put("mode", "released");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						performTipiEvent("onEnter", m, false);
					} catch (TipiException e1) {
						e1.printStackTrace();
					}
				}

			}

			public Map getEventMap(KeyEvent e) {
				Map hm = new HashMap();
				hm.put("code", new Integer(e.getKeyCode()));
				hm.put("modifiers", e.getKeyModifiersText(e.getModifiers()));
				hm.put("key", e.getKeyText(e.getKeyCode()));
				return hm;
			}
		});
		return myField;
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myField.setText(object.toString());
					return;
				}
			});
		}
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myField.getText();
		}
		return super.getComponentValue(name);
	}

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		if (name.equals("append")) {
			Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				myField.setText(myField.getText()+result);
			}
		}
	
	}

}
