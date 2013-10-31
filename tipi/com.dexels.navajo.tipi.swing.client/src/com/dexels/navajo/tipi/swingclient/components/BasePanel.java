package com.dexels.navajo.tipi.swingclient.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;

/**
 * <p>
 * Title: SportLink Client:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author unascribed
 * @version 1.0
 * @deprecated
 */

public class BasePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403054920085605764L;
	private Message loadMessage = null;
	private Message initMessage = null;
	private Message rootMessage = null;
	protected BasePanel hostingPanel = null;
	protected StandardWindow myWindow = null;
	private String panelTitle = "no name";
	
	private final static Logger logger = LoggerFactory
			.getLogger(BasePanel.class);
	
	private int panelState = NO_STATE;

	public static final int NO_STATE = -1;
	public static final int INITIALIZED = 1;
	public static final int LOADED = 2;
	public static final int UPDATED = 3;
	public static final int INSERTED = 4;
	public static final int DELETED = 5;
	private boolean hasExceptions = false;
	public static final int FOCUS_REQUEST = 0;
	public static final int FOCUS_GAINED = 1;
	public static final int FOCUS_LOST = 2;
	private int focusState = FOCUS_LOST;

	//
	// private static int busyPanelCount = 0;
	// private static Object busyPanelSemaphore = new Object();
	// private Paint myPaint = SystemColor.getColor("control");

	protected void init(Message msg) {
		initMessage = msg;
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			if (BasePanel.class.isInstance(c)) {
				((BasePanel) c).init(msg);
			}
		}
	}

	protected void load(Message msg) {
		loadMessage = msg;
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			if (BasePanel.class.isInstance(c)) {
				// logger.info("Loading BasePanel");
				((BasePanel) c).load(msg);
			}
		}
	}

	public void focusUpdate() {
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			if (BasePanel.class.isInstance(c)) {
				((BasePanel) c).focusChange();
			}
		}
		focusChange();
	}

	private final void focusChange() {
		switch (focusState) {
		case FOCUS_REQUEST:
			setFocusState(FOCUS_GAINED);
			logger.info("request -> gained");
			setBorder(new LineBorder(Color.white, 2));
			break;
		case FOCUS_LOST:
			setFocusState(FOCUS_LOST);
			setBorder(null);
			break;
		case FOCUS_GAINED:
			setFocusState(FOCUS_LOST);
			setBorder(null);
			break;
		}
	}

	private final void setFocusState(int state) {
		focusState = state;
	}

	public void load() {

		if (loadMessage != null) {
			load(loadMessage);
		} else {
			// logger.info("Warning: Attempting to load without loadMessage");
		}
	}

	public void store() {
		if (loadMessage != null) {
			Navajo n = store(loadMessage);
			if (n != null) {
				Message exception = n.getMessage("error");
				if (exception != null) {
					hasExceptions = true;
				}
			}
		} else {
			// logger.info("Error: Attempting to store without loadMessage");
		}
	}

	public void init() {

		if (SwingUtilities.isEventDispatchThread()) {
			if (initMessage != null) {
				init(initMessage);
			}
		} else {
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					@Override
					public void run() {
						if (initMessage != null) {
							init(initMessage);
						} else {
							// logger.info("Warning: Attempting to init without initMessage");
						}
					}
				});
			} catch (InterruptedException e) {
				logger.error("Error: ",e);
			} catch (InvocationTargetException e) {
				logger.error("Error: ",e);
			}
		}
	}

	public void insert() {
		if (initMessage != null) {
			Navajo n = insert(initMessage);
			Message exception = n.getMessage("error");
			if (exception != null) {
				hasExceptions = true;
			}
		} else {
			// logger.info("Error: Attempting to insert without initMessage");
		}
	}

	public void delete() {
		if (loadMessage != null) {
			Navajo n = delete(loadMessage);
			Message exception = n.getMessage("error");
			if (exception != null) {
				hasExceptions = true;
			}
		} else {
			// logger.info("Error: Attempting to delete without loadMessage");
		}
	}

	public void initializePanel(Message init, Message load) {
		setInitMessage(init);
		init();
		setLoadMessage(load);
		load();
	}

	public void initializePanel(Message root, Message init, Message load) {
		setRootMessage(root);
		initializePanel(init, load);
	}

	public int getPanelState() {
		return panelState;
	}

	private final void setPanelState(int newState) {
		panelState = newState;
		revalidate();
		// setStateRecursive(newState);
	}

	public void setRootMessage(Message msg) {
		if (rootMessage != null) {
			// logger.info("WARNING: Resetting root message");
		}
		rootMessage = msg;
	}

	public Message getRootMessage() {
		return rootMessage;
	}

	public void setLoadMessage(Message msg) {
		// switch (getPanelState()) {
		// case NO_STATE:
		// logger.info("WARNING: Loading panel without initializing first");
		// break;
		// case INSERTED:
		// logger.info("WARNING: Loading newly inserted panel");
		// break;
		// case LOADED:
		// logger.info("Reloading panel");
		// break;
		// case UPDATED:
		// logger.info("Reverting updated panel");
		// break;
		// }
		setPanelState(LOADED);
		loadMessage = msg;
		// load(loadMessage);
	}

	public void setInitMessage(Message msg) {
		/*
		 * switch (getPanelState()) { case INITIALIZED:
		 * logger.info("Reinitializing panel"); break; case INSERTED:
		 * logger.info("OK: Initializing newly inserted panel"); break;
		 * case LOADED: logger.info("Reinitializing loaded panel");
		 * break; case UPDATED:
		 * logger.info("Reinitializing updated panel, discarding changes"
		 * ); break; }
		 */
		setPanelState(INITIALIZED);
		initMessage = msg;
		// init(initMessage);
	}

	public Message getLoadMessage() {
		return loadMessage;
	}

	public Message getInitMessage() {
		return initMessage;
	}

	protected Navajo insert(Message msg) {
		logger.info("Insert called in BasePanel. Not implemented. "
				+ getClass().getName());
		return null;
	}

	protected Navajo store(Message msg) {
		logger.info("this: " + this.getClass());
		logger.info("Store called in BasePanel. Not implemented.");
		return null;
	}

	protected Navajo delete(Message msg) {
		logger.info("Delete called in BasePanel. Not implemented.");
		return null;
	}

	public void setStateRecursive(int state) {
		panelState = state;
		for (int i = 0; i < getComponentCount(); i++) {
			Component current = getComponent(i);
			if (BasePanel.class.isInstance(current)) {
				((BasePanel) current).setStateRecursive(state);
			}
		}
	}

	public void setStateDeleted() {
		switch (panelState) {
		case LOADED:
			logger.info("Deleting loaded panel");
			setPanelState(DELETED);
			break;
		case UPDATED:
			logger.info("Deleting updated panel");
			setPanelState(DELETED);
			break;
		case INSERTED:
			logger.info("Deleting inserted panel. No action.");
			setPanelState(INITIALIZED);
			break;
		case INITIALIZED:
		case NO_STATE:
			logger.info("Ignoring delete for current panel.");
			break;
		}
	}

	public void setStateInserted() {

		switch (panelState) {
		case INSERTED:
			logger.info("Setting inserted panel to inserted. Ignoring.");
			break;
		case INITIALIZED:
			setPanelState(INSERTED);
			break;
		default:
			System.err
					.println("Attempting to set to inserted from illegal state. Ignoring. State: "
							+ getPanelState());
			// panelState = UPDATED;
		}
	}

	public void setStateInitialized() {
		setPanelState(INITIALIZED);
	}

	public void setStateUpdated() {
		switch (panelState) {
		case LOADED:
			setPanelState(UPDATED);
			break;
		case INSERTED:
			logger.info("Setting inserted panel to updated. Ignoring.");
			break;
		case UPDATED:
			break;
		default:
			System.err
					.println("Attempting to set to updated from illegal state. Ignoring");
			// panelState = UPDATED;
		}
	}

	public void commit() {
		hasExceptions = false;
		switch (panelState) {
		case INITIALIZED:
			logger.info("Store: Discarding initialized panel");
			break;
		case INSERTED:
			insert();
			break;
		case LOADED:
			logger.info("Ignoring unchanged panel");
			break;
		case NO_STATE:
			logger.info("Warning: Updating panel without state");
			break;
		case UPDATED:
			store();
			break;
		case DELETED:
			delete();
			break;
		}
		ArrayList<Component> al = getAllPanels();
		if (al.size() > 0) {
			// logger.info("End of commit panel. Now committing subpanels.");
			for (int i = 0; i < al.size(); i++) {
				BasePanel aw = (BasePanel) al.get(i);
				aw.commit();
			}
			// logger.info("Committed subpanels.");
		}
		if (!hasExceptions()) {
			setPanelState(LOADED);
		} else {
			logger.info("This panel had errors: " + this);
		}
	}

	public void setHostingWindow(StandardWindow w) {
		myWindow = w;
	}

	public void setHostingPanel(BasePanel p) {
		hostingPanel = p;
	}

	protected Component findParentComponent(Class<?> type) {
		Component c = getParent();
		while (c != null) {
			if (type.isInstance(c)) {
				return c;
			}
			c = c.getParent();
		}
		return null;
	}

	public ArrayList<Component> getAllPanels() {
		ArrayList<Component> al = new ArrayList<Component>();
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			if (BasePanel.class.isInstance(c)) {
				BasePanel current = (BasePanel) c;
				al.add(current);
			}
		}
		return al;
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			c.setEnabled(b);
		}
	}

	public void setFocus() {
		focusState = FOCUS_REQUEST;
		if (hostingPanel != null) {
			hostingPanel.focusUpdate();
		}
	}

	public void setWaiting(boolean b) {
		// if (getRootPane()!=null) {
		// getRootPane().setCursor(b?new
		// Cursor(Cursor.WAIT_CURSOR):Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		// }
	}

	public boolean hasExceptions() {
		if (hasExceptions) {
			return true;
		}
		for (int i = 0; i < getComponentCount(); i++) {
			Component c = getComponent(i);
			if (BasePanel.class.isInstance(c)) {
				BasePanel handler = (BasePanel) c;
				// logger.info("Checking: " + c.getClass() + ", errors: "
				// + handler.hasConditionErrors());
				if (handler.hasExceptions()) {
					// logger.info("HasConditionErrors");
					return true;
				}
			}
		}
		return false;
	}

	public String getPanelTitle() {
		return panelTitle;
	}

	public void setPanelTitle(String title) {
		panelTitle = title;
	}

	public void setPanelEnabled(boolean state) {
		Component[] components = getComponents();
		for (int i = 0; i < components.length; i++) {
			Component c = components[i];
			c.setEnabled(state);
			if (BasePanel.class.isInstance(c)) {
				BasePanel cc = (BasePanel) c;
				cc.setPanelEnabled(state);
			}
		}
		setEnabled(state);
	}

	public BasePanel() {
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	private final void jbInit() throws Exception {
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				this_mouseClicked(e);
			}
		});
		this.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				this_focusGained(e);
			}
		});
	}

	void this_focusGained(FocusEvent e) {
		setFocus();
		if (hostingPanel != null) {
			this.hostingPanel.focusUpdate();
			logger.info("BasePanel Focus gained");
		}
	}

	void this_mouseClicked(MouseEvent e) {
		setFocus();
	}

	public StandardWindow getHostingWindow() {
		return this.myWindow;
	}

}
