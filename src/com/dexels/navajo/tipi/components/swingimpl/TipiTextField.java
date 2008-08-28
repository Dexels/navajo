package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import javax.swing.event.*;

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
		myField = new TipiSwingTextField();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
				// System.err.println("typed: "+e.getKeyChar());
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyPressed(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "pressed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void keyReleased(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
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

			public Map<String, Object> getEventMap(KeyEvent e) {
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("code", new Integer(e.getKeyCode()));
				hm.put("modifiers", KeyEvent.getKeyModifiersText(e.getModifiers()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});
		myField.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());
					
			}

			public void insertUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());
					
			}

			public void removeUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());
						
			}});
	
		
		myField.addFocusListener(new FocusListener(){

			public void focusGained(FocusEvent e) {
				try {
					performTipiEvent("onFocusGained", null, false);
				} catch (TipiBreakException e1) {
					e1.printStackTrace();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
			}

			public void focusLost(FocusEvent e) {
				try {
					performTipiEvent("onFocusLost", null, false);
				} catch (TipiBreakException e1) {
					e1.printStackTrace();
				} catch (TipiException e1) {
					e1.printStackTrace();
				}
				
			}});
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

	//
	// public Object getComponentValue(String name) {
	// if (name.equals("text")) {
	// return myField.getText();
	// }
	// return super.getComponentValue(name);
	// }

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		if (name.equals("append")) {
			Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				myField.setText(myField.getText() + result);
			}
		}
		if (name.equals("selectAll")) {
			runSyncInEventThread(new Runnable(){
				public void run() {
					myField.selectAll();
				}});
		}
	}

}
