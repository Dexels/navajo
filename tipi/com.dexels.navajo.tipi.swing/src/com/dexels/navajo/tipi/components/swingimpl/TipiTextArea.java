package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Operand;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingTextArea;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiTextArea extends TipiSwingComponentImpl {

	private static final long serialVersionUID = 2784561061508301662L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTextArea.class);
	
	private TipiSwingTextArea myTextArea;

	public TipiTextArea() {
	}

	@Override
	public Object getActualComponent() {
		return myTextArea;
	}

	@Override
	public Object createContainer() {
		myTextArea = new TipiSwingTextArea();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		JScrollPane jsp = new JScrollPane(myTextArea);
		addHelper(th);
		myTextArea.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					try {
						performTipiEvent("onEnter", m, true);
					} catch (TipiException e1) {
						logger.error("Error detected",e1);
					}
				}
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "typed");
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				Map<String, Object> m = getEventMap(e);
				m.put("mode", "released");
				try {
					performTipiEvent("onKey", m, true);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			public Map<String, Object> getEventMap(KeyEvent e) {
				Map<String, Object> hm = new HashMap<String, Object>();
				hm.put("code", new Integer(e.getKeyCode()));
				hm.put("modifiers",
						KeyEvent.getKeyModifiersText(e.getModifiers()));
				hm.put("key", KeyEvent.getKeyText(e.getKeyCode()));
				return hm;
			}
		});
		return jsp;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		logger.debug("Settting: " + name + " to " + object);
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myTextArea.setText((String) object);
				}
			});
			return;
		}
		// if(name.equals("editable")) {
		// myTextArea.setEditable((Boolean)object);
		// }
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("text")) {
			return myTextArea.getText();
		}
		return super.getComponentValue(name);
	}

	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		if (name.equals("append")) {
			final Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				runSyncInEventThread(new Runnable() {

					@Override
					public void run() {
						String result = (String) o.value;
						myTextArea.append(result);
					}
				});

			}
		}
		if (name.equals("appendLine")) {
			final Operand o = compMeth.getEvaluatedParameter("text", event);
			if (o != null) {
				runSyncInEventThread(new Runnable() {

					@Override
					public void run() {
						String result = (String) o.value;
						myTextArea
								.setText(myTextArea.getText() + result + "\n");
					}
				});
			}
		}

	}

}
