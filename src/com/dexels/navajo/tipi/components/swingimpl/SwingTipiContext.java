package com.dexels.navajo.tipi.components.swingimpl;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.*;
import java.util.*;

import java.awt.*;

import javax.swing.*;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.swingclient.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

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
public class SwingTipiContext extends TipiContext {
	private TipiSwingSplash splash;

	private final Set threadSet = Collections.synchronizedSet(new HashSet());
	private final Set dialogThreadSet = Collections.synchronizedSet(new HashSet());
	private boolean dialogShowing = false;

	private JDialog blockingDialog;

	private UserInterface myUserInterface;
	private boolean debugMode = false;

	private TipiApplet myAppletRoot;
	private TipiSwingDesktop defaultDesktop = null;

	public SwingTipiContext() {
	}

	public Set getRequiredIncludes() {
		Set s = super.getRequiredIncludes();
		s.add("com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml");
		return s;
	}

	protected final void loadTipiMethod(final Navajo reply, final String tipiDestinationPath, final String method, final String server)
			throws TipiException {
		TipiDataComponent tt;
		ArrayList tipiList;
		// System.err.println("Loading method");
		if ("-".equals(tipiDestinationPath)) {
			System.err.println("Destination blocked");
			return;
		}
		if (SwingUtilities.isEventDispatchThread()) {
			// System.err.println("EVENT THREAD!");
			deliverData(reply, method, server);
		} else {
			// System.err.println("NON EVENT THREAD!");
			try {
				SwingUtilities.invokeAndWait(new Runnable() {

					public void run() {
						try {
							deliverData(reply, method, server);
						} catch (TipiException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void deliverData(Navajo reply, String method, String server) throws TipiException {
		ArrayList tipiList;
		tipiList = getTipiInstancesByService(method);
		if (tipiList != null) {
			for (int i = 0; i < tipiList.size(); i++) {
				TipiDataComponent t = (TipiDataComponent) tipiList.get(i);
				debugLog("data    ", "delivering data from method: " + method + " to tipi: " + t.getId());
				try {
					t.loadData(reply, this, method, server);
				} catch (TipiBreakException e) {
					System.err.println("Data refused by component");
				}
				if (t.getContainer() != null) {
					t.tipiLoaded();
				}
			}
		}
	}

	public void setUserInterface(UserInterface ui) {
		myUserInterface = (UserInterface) ui;
	}

	public UserInterface getUserInterface() {
		return myUserInterface;
	}

	public synchronized void setWaiting(boolean b) {

		if (dialogShowing) {
			b = false;
		}
		if(getAppletRoot()!=null) {
			
			getAppletRoot().setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
		}
		for (int i = 0; i < rootPaneList.size(); i++) {
			Object obj = rootPaneList.get(i);
			if (TipiSwingComponent.class.isInstance(obj)) {
				TipiSwingComponent tc = (TipiSwingComponent) obj;
				tc.setWaitCursor(b);
			} else {
				((Container) obj).setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
			}
		}
		for (int j = 0; j < myActivityListeners.size(); j++) {
			TipiActivityListener tal = (TipiActivityListener) myActivityListeners.get(j);
			tal.setActive(b);
		}
	}

	public void clearTopScreen() {
		((TipiScreen) getDefaultTopLevel()).clearTopScreen();
	}

	public void setSplashInfo(final String info) {
		if (getAppletRoot() != null) {
			getAppletRoot().showStatus(info);
		} else {
			if (splash != null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						splash.setInfoText(info);
					}
				});
			}

		}

	}

	public void setSplashVisible(boolean b) {
		if (splash != null) {
			splash.setVisible(b);
		}
	}

	public void setSplash(Object s) {
		splash = (TipiSwingSplash) s;
	}

	public void threadStarted(Thread workThread) {
		if (threadSet == null) {
			return;
		}
		super.threadStarted(workThread);
		threadSet.add(workThread);
		setActiveThreads(threadSet.size());
		if (!threadSet.isEmpty()) {
			setWaiting(true);
		}
	}

	public final void threadEnded(Thread workThread) {
		super.threadEnded(workThread);

		if (threadSet == null) {
			return;
		}
		threadSet.remove(workThread);
		setActiveThreads(threadSet.size());
		if (threadSet.isEmpty()) {
			setWaiting(false);
		}
	}

	public final void updateWaiting() {
		if (threadSet == null || threadSet.size() == 0) {
			// System.err.println("No waiting threads");
			setWaiting(false);
			return;
		}
		setWaiting(!dialogThreadSet.containsAll(threadSet));
	}

	public void dialogShowing(boolean b) {
		dialogShowing = b;
		if (!SwingUtilities.isEventDispatchThread()) {
			if (b) {
				dialogThreadSet.add(Thread.currentThread());
			} else {
				dialogThreadSet.remove(Thread.currentThread());
			}
			dialogShowing = b;
		} else {
			dialogShowing = false;
		}
		updateWaiting();
	}

	public void addTopLevel(Object toplevel) {
		rootPaneList.add(toplevel);
	}

	public void removeTopLevel(Object toplevel) {
		rootPaneList.remove(toplevel);
	}

	protected void clearLogFile() {
		try {
			File f = new File(System.getProperty("user.home") + "/.tipidebug");
			// System.err.println("Deleting: "+f.getAbsolutePath());
			f.delete();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void debugLog(String category, String event) {
		if (!debugMode) {
			return;
		}
		long stamp = System.currentTimeMillis() - startTime;
		SimpleDateFormat inputFormat1 = new SimpleDateFormat("HH:mm:ss S");

		// Calendar c = Calendar.getInstance();
		Date d = new Date(stamp);
		// c.setTimeInMillis(stamp);
		try {
			FileWriter fw = new FileWriter(System.getProperty("user.home") + "/.tipidebug", true);
			fw.write(category + ", " + inputFormat1.format(d) + ", " + " Thread: " + Thread.currentThread().getName() + "," + event + "\n");
			fw.flush();
			fw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (SecurityException ee) {
			ee.printStackTrace();
		}
	}

	public void setDebugMode(boolean b) {
		if (System.getSecurityManager() != null) {
			System.err.println("No debugging. Securitymanager found.");
			debugMode = false;
			return;
		}
		// System.err.println("Debugmode = "+b);
		debugMode = b;
		clearLogFile();
	}

	public static void debugSwingTree(Component c, int indent) {
		printIndent(indent, "Debugging component with hash: " + c.hashCode() + " class: " + c.getClass());
		printIndent(indent, "Minimumsite: " + c.getMinimumSize() + " max: " + c.getMaximumSize());
		if (c instanceof JComponent) {
			printIndent(indent, "Preferredsize: " + ((Component) c).getPreferredSize());
		}
		printIndent(indent, "Size: " + c.getSize() + " visible: " + c.isVisible());

		// c.setPreferredSize(null);
		if (c instanceof Container) {
			Container cc = (Container) c;
			LayoutManager lm = cc.getLayout();
			printIndent(indent, "Container. Layout: " + cc.getLayout());
			for (int i = 0; i < cc.getComponentCount(); i++) {
				Component ccc = cc.getComponent(i);
				debugSwingTree(ccc, indent + 3);
			}
		}
		printIndent(indent, "End of debug component: " + c.hashCode() + " class: " + c.getClass());

	}

	public void setAppletRoot(TipiApplet appletRoot) {
		myAppletRoot = appletRoot;
	}

	public TipiApplet getAppletRoot() {
		return myAppletRoot;
	}

	public void exit() {
		if (myAppletRoot != null) {
			myAppletRoot.reload();
		} else {
			System.exit(0);
		}
	}

	public void setDefaultDesktop(TipiSwingDesktop jp) {
		defaultDesktop = jp;

	}

	public TipiSwingDesktop getDefaultDesktop() {
		return defaultDesktop;
	}

	public void showQuestion(final String text, final String title) throws TipiBreakException {
	}
	
	public void showInfo(final String text, final String title) {
		// swing implementation.
		if (getAppletRoot() != null && getDefaultDesktop() != null) {
			TipiModalInternalFrame.showInternalMessage(getAppletRoot().getRootPane(), getDefaultDesktop(), title, text, getPoolSize());
		} else {
			if (SwingUtilities.isEventDispatchThread()) {
				JOptionPane.showMessageDialog((Component) getTopLevel(), text, title, JOptionPane.PLAIN_MESSAGE);
			} else {
				try {
					SwingUtilities.invokeAndWait(new Runnable() {
						public void run() {
							JOptionPane.showMessageDialog((Component) getTopLevel(), text, title, JOptionPane.PLAIN_MESSAGE);
						}
					});
				} catch (InvocationTargetException ex1) {
					ex1.printStackTrace();
				} catch (InterruptedException ex1) {
				}
			}
		}

	}
}
