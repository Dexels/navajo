package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingComponent;
import com.dexels.navajo.tipi.internal.TipiEvent;


public class TipiSwingHelper implements TipiHelper {
	private static final long serialVersionUID = -1677532389917655637L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingHelper.class);
	
	private TipiSwingComponent myComponent = null;

	private java.util.List<EventListener> myListeners = new ArrayList<EventListener>();

	public void initHelper(TipiComponent tc) {
		myComponent = (TipiSwingComponent) tc;
	}

	public void setComponentValue(String name, Object object) {
	}

	public Object getComponentValue(String name) {
		return null;
	}

	public void deregisterEvent(TipiEvent e) {
		Component c = (Component) myComponent.getContainer();
		if (c == null) {
			logger.warn("Cannot deregister swing event: Container is null! Component class: "
							+ myComponent.getClass());
			return;
		}
		try {
			for (EventListener el : myListeners) {
				if (el instanceof ActionListener) {
					java.lang.reflect.Method m = c.getClass().getMethod(
							"removeActionListener",
							new Class[] { ActionListener.class });
					m.invoke(c, new Object[] { el });
				}
				if (el instanceof InternalFrameListener) {
					java.lang.reflect.Method m = c.getClass().getMethod(
							"removeInternalFrameListener",
							new Class[] { InternalFrameListener.class });
					m.invoke(c, new Object[] { el });
				}
				if (el instanceof WindowListener) {
					java.lang.reflect.Method m = c.getClass().getMethod(
							"removeWindowListener",
							new Class[] { WindowListener.class });
					m.invoke(c, new Object[] { el });
				}

				if (el instanceof MouseListener) {
					java.lang.reflect.Method m = c.getClass().getMethod(
							"removeMouseListener",
							new Class[] { MouseListener.class });
					m.invoke(c, new Object[] { el });
				}

			}
		} catch (Exception e1) {
			logger.error("Error detected",e1);
		}

	}

	/**
	 * Refactor this silly bugger. This is called from TipiComponent. Should be
	 * able to fix it using regular inheritance
	 * 
	 */
	public void registerEvent(final TipiEvent te) {
		Component c = (Component) myComponent.getContainer();
		if (c == null) {
			logger.warn("Cannot register swing event: Container is null!");
			return;
		}
		if (te.isTrigger("onActionPerformed")) {
			try {
				java.lang.reflect.Method m = c.getClass().getMethod(
						"addActionListener",
						new Class[] { ActionListener.class });
				ActionListener bert = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							myComponent.performTipiEvent("onActionPerformed",
									null, te.isSync());
						} catch (TipiException ex) {
							logger.error("Error detected",ex);
						}
					}
				};
				m.invoke(c, new Object[] { bert });
			} catch (Exception exe) {
				logger.error("Error detected",exe);
			}
		}
		// leaving this (empty) structure for the final else error.
		if (te.isTrigger("onWindowClosed")) {
			if (JInternalFrame.class.isInstance(c)) {
				// handled in TipiDialog & TipiWindow
			} else if (JFrame.class.isInstance(c)) {
				if (TipiSwingFrameImpl.class.isInstance(c))
				{
				// handled in TipiSwingFrameImpl
				}
				else
				{
					JFrame jj = (JFrame) c;
					jj.addWindowListener(new WindowAdapter() {
						public void windowClosed(WindowEvent e) {
							try {
								myComponent.performTipiEvent("onWindowClosed",
										null, te.isSync());
							} catch (TipiException ex) {
								logger.error("Error detected", ex);
							}
						}
					});
				}
			} else if (JApplet.class.isInstance(c)) {
				// do nothing.

			} else {
				throw new RuntimeException(
						"Can not fire onWindowClosed event from class: "
								+ c.getClass());
			}
		}
		if (te.isTrigger("onMouseEntered")) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("x", new Integer(e.getX()));
					m.put("y", new Integer(e.getY()));
					m.put("button", new Integer(e.getButton()));
					m.put("clickCount", new Integer(e.getClickCount()));
					try {
						myComponent.performTipiEvent("onMouseEntered", m,
								te.isSync());
					} catch (TipiException ex) {
						logger.error("Error detected",ex);
					}
				}
			});
		}
		if (te.isTrigger("onMouseExited")) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					try {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("x", new Integer(e.getX()));
						m.put("y", new Integer(e.getY()));
						myComponent.performTipiEvent("onMouseExited", m,
								te.isSync());
					} catch (TipiException ex) {
						logger.error("Error detected",ex);
					}
				}
			});
		}
		if (te.isTrigger("onMouseClicked")) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					try {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("x", new Integer(e.getX()));
						m.put("y", new Integer(e.getY()));
						m.put("button", new Integer(e.getButton()));
						m.put("clickCount", new Integer(e.getClickCount()));
						myComponent.performTipiEvent("onMouseClicked", m,
								te.isSync());
					} catch (TipiException ex) {
						logger.error("Error detected",ex);
					}
				}
			});
		}
		if (te.isTrigger("onMousePressed")) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					try {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("x", new Integer(e.getX()));
						m.put("y", new Integer(e.getY()));
						m.put("button", new Integer(e.getButton()));
						myComponent.performTipiEvent("onMousePressed", m,
								te.isSync());
					} catch (TipiException ex) {
						logger.error("Error detected",ex);
					}
				}
			});
		}
		if (te.isTrigger("onMouseReleased")) {
			c.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					try {
						Map<String, Object> m = new HashMap<String, Object>();
						m.put("x", new Integer(e.getX()));
						m.put("y", new Integer(e.getY()));
						m.put("button", new Integer(e.getButton()));
						myComponent.performTipiEvent("onMouseReleased", m,
								te.isSync());
					} catch (TipiException ex) {
						logger.error("Error detected",ex);
					}
				}
			});
		}
	}
}
