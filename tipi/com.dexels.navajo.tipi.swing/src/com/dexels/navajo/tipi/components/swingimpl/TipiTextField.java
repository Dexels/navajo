package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingTextField;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiTextField extends TipiSwingComponentImpl {

	private static final long serialVersionUID = 6379192303536111124L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTextField.class);
	private TipiSwingTextField myField;

	public TipiTextField() {
	}

	@Override
	public Object createContainer() {
		myField = new TipiSwingTextField(this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// logger.debug("typed: "+e.getKeyChar());
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "pressed");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "released");
				try {
					performTipiEvent("onKey", m, false);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						performTipiEvent("onEnter", m, false);
					} catch (TipiException e1) {
						logger.error("Error detected",e1);
					}
				}

			}

			public Map<String, Object> getEventMap(KeyEvent e) {
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("code", Integer.valueOf(e.getKeyCode()));
				hm.put("modifiers",
						KeyEvent.getKeyModifiersText(e.getModifiers()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});
		myField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				getAttributeProperty("text").setAnyValue(myField.getText());

			}
		});

		myField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				try {
					performTipiEvent("onFocusGained", null, false);
				} catch (TipiBreakException e1) {
					logger.debug("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				try {
					performTipiEvent("onFocusLost", null, false);
				} catch (TipiBreakException e1) {
					logger.debug("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}

			}
		});
		return myField;
	}

	//
	// public void setComponentValue(final String name, final Object object) {
	// if (name.equals("text")) {
	// runSyncInEventThread(new Runnable() {
	// public void run() {
	// myField.setText(object.toString());
	// return;
	// }
	// });
	// }
	// super.setComponentValue(name, object);
	// }

	//
	// public Object getComponentValue(String name) {
	// if (name.equals("text")) {
	// return myField.getText();
	// }
	// return super.getComponentValue(name);
	// }

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		// TODO Place in runSync
		if (name.equals("append")) {
			Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				String result = (String) o.value;
				myField.setText(myField.getText() + result);
			}
		}
		if (name.equals("selectAll")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myField.selectAll();
				}
			});
		}
	}

}
