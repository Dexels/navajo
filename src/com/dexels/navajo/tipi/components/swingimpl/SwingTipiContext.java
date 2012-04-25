package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tipi.SwingTipiApplicationInstance;
import tipi.TipiExtension;
import tipi.TipiSwingExtension;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.TipiActivityListener;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiContext;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.animation.TipiAnimationManager;
import com.dexels.navajo.tipi.components.swingimpl.cookie.impl.JnlpCookieManager;
import com.dexels.navajo.tipi.components.swingimpl.formatters.PropertyAnimator;
import com.dexels.navajo.tipi.components.swingimpl.jnlp.WebStartProxy;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiModalInternalFrame;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDesktop;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDialog;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingSplash;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiResourceLoader;
import com.dexels.navajo.tipi.internal.cookie.CookieManager;
import com.dexels.navajo.tipi.internal.cookie.impl.TmpFileCookieManager;
import com.dexels.navajo.tipi.swingclient.UserInterface;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

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

	private static final long serialVersionUID = -538400075423823232L;

	private TipiSwingSplash splash;

	private final Set<Thread> threadSet = Collections
			.synchronizedSet(new HashSet<Thread>());
	private final Set<Thread> dialogThreadSet = Collections
			.synchronizedSet(new HashSet<Thread>());
	private final Stack<JDialog> dialogStack = new Stack<JDialog>();

	// private JDialog blockingDialog;

	private UserInterface myUserInterface;
	private boolean debugMode = false;

	private TipiApplet myAppletRoot;
	private TipiSwingDesktop defaultDesktop = null;

	private RootPaneContainer myOtherRoot;

	private static final Logger logger = LoggerFactory.getLogger(SwingTipiContext.class);
	
	public SwingTipiContext(SwingTipiApplicationInstance instance,List<TipiExtension> extensionList,
			SwingTipiContext parentContext) {
		super(instance,extensionList, parentContext);
		// Don't think it is right here
		try {
			Locale.setDefault(new Locale("nl", "NL"));
		} catch (SecurityException se) {
		}
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);

		try {

			if (WebStartProxy.hasJnlpContext()) {
				// System.err.println("JNLP DETECTED.");
				setCookieManager(new JnlpCookieManager());
				try {
					getCookieManager().loadCookies();
				} catch (FileNotFoundException e) {
					// System.err.println("No cookies (yet). No prob.");
				}
			} else {
				createTmpCookieManager();
			}
		} catch (Throwable e) {
			// System.err.println("No jnlp found");
			// e.printStackTrace();
			createTmpCookieManager();
		}
		// hasJnlpContext(
		// if(false) {
		// appendJnlpCodeBase();
		// createJnlpCookieManager();
		// } else {
		// createTmpCookieManager();
		// }

		// if(hasJnlpContext()) {
		// appendJnlpCodeBase();
		// createJnlpCookieManager();
		// } else {
		// createTmpCookieManager();
		// }
		//

	}

	private void createTmpCookieManager() {
		CookieManager cm = new TmpFileCookieManager();
		setCookieManager(cm);
		try {
			cm.loadCookies();
		} catch (IOException e) {
			System.err.println("No cookies found. Continuing");
		} catch (SecurityException e) {
			System.err
					.println("No permission for temp cookies. Cookies disabled, use JNLP Cookies!");
		}
	}

	public void runSyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			try {
				SwingUtilities.invokeAndWait(r);
			} catch (InvocationTargetException ex) {
				throw new RuntimeException(ex);
			} catch (InterruptedException ex) {
			}
		}
	}

	public void runAsyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	public void setUserInterface(UserInterface ui) {
		myUserInterface = ui;
	}

	public UserInterface getUserInterface() {
		return myUserInterface;
	}

	public synchronized void setWaiting(boolean b) {

		// if (dialogShowing) {
		// b = false;
		// }
		// System.err.println("SETWAITING: "+b+" thread: "+Thread.currentThread().getName());
		// Thread.dumpStack();
		if (getAppletRoot() != null) {

			getAppletRoot().setCursor(
					b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor
							.getDefaultCursor());
		}
		// for (int i = 0; i < rootPaneList.size(); i++) {
		// Object obj = rootPaneList.get(i);
		// if (TipiSwingComponent.class.isInstance(obj)) {
		// TipiSwingComponent tc = (TipiSwingComponent) obj;
		// tc.setWaitCursor(b);
		// } else {
		// ((Container) obj).setCursor(b ?
		// Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) :
		// Cursor.getDefaultCursor());
		// }
		// }
		for (TipiActivityListener ta : myActivityListeners) {
			ta.setActive(b);
		}
	}

	public void clearTopScreen() {
		((TipiScreen) getDefaultTopLevel()).clearTopScreen();
	}

	public void setSplashInfo(final String info) {
		// System.err.println("Splash: "+info);
		logger.info("Splash: "+info);
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
			runAsyncInEventThread(new Runnable() {
				public void run() {
					setWaiting(true);
				}
			});
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
			runAsyncInEventThread(new Runnable() {
				public void run() {
					setWaiting(false);
				}
			});
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
		if (!SwingUtilities.isEventDispatchThread()) {
			if (b) {
				dialogThreadSet.add(Thread.currentThread());
			} else {
				dialogThreadSet.remove(Thread.currentThread());
			}
		} else {
		}
		updateWaiting();
	}

	public void addTopLevel(Object toplevel) {
		// rootPaneList.add(toplevel);
	}

	public void removeTopLevel(Object toplevel) {
		// rootPaneList.remove(toplevel);
	}

	protected void clearLogFile() {
		try {
			File f = new File(System.getProperty("user.home") + "/.tipidebug");
			// System.err.println("Deleting: "+f.getAbsolutePath());
			f.delete();
		} catch (SecurityException e) {
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
			FileWriter fw = new FileWriter(System.getProperty("user.home")
					+ "/.tipidebug", true);
			fw.write(category + ", " + inputFormat1.format(d) + ", "
					+ " Thread: " + Thread.currentThread().getName() + ","
					+ event + "\n");
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
		printIndent(indent, "Debugging component with hash: " + c.hashCode()
				+ " class: " + c.getClass());
		printIndent(
				indent,
				"Minimumsite: " + c.getMinimumSize() + " max: "
						+ c.getMaximumSize());
		if (c instanceof JComponent) {
			printIndent(indent, "Preferredsize: " + c.getPreferredSize());
		}
		printIndent(indent,
				"Size: " + c.getSize() + " visible: " + c.isVisible());

		// c.setPreferredSize(null);
		if (c instanceof Container) {
			Container cc = (Container) c;
			printIndent(indent, "Container. Layout: " + cc.getLayout());
			for (int i = 0; i < cc.getComponentCount(); i++) {
				Component ccc = cc.getComponent(i);
				debugSwingTree(ccc, indent + 3);
			}
		}
		printIndent(indent, "End of debug component: " + c.hashCode()
				+ " class: " + c.getClass());

	}

	public void setAppletRoot(TipiApplet appletRoot) {
		myAppletRoot = appletRoot;
	}

	public TipiApplet getAppletRoot() {
		return myAppletRoot;
	}

	public void setOtherRoot(RootPaneContainer otherRoot) {
		myOtherRoot = otherRoot;
	}

	public RootPaneContainer getOtherRoot() {
		return myOtherRoot;
	}

	public void exit() {
		if (myAppletRoot != null) {
			myAppletRoot.reload();
		} else {
			shutdown();

			super.exit();
			System.exit(0);
		}
	}

	public void shutdown() {
		super.shutdown();
		// TODO: Move this to swingtipi or something
		if (getTopLevel() instanceof RootPaneContainer) {
			RootPaneContainer rpc = (RootPaneContainer) getTopLevel();
			System.err.println("Doing allright!");
			if (rpc instanceof Window) {
				Window w = (Window) rpc;
				w.setVisible(false);
				w.dispose();
				System.err.println("Ciao!");
			}

		} else {
			System.err
					.println("NEED REFACTOR in TIPICONTEXT, around line 2230.");
		}
	}
	public void setDefaultDesktop(TipiSwingDesktop jp) {
		defaultDesktop = jp;

	}

	public TipiSwingDesktop getDefaultDesktop() {
		return defaultDesktop;
	}

	public void showQuestion(final String text, final String title,
			final String[] options) throws TipiBreakException {
		final Set<Integer> responseSet = new HashSet<Integer>();
		runSyncInEventThread(new Runnable() {
			public void run() {
				int response = JOptionPane
						.showOptionDialog((Component) getTopDialog(), text,
								title, JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
				responseSet.add(response);
			}
		});
		int response = responseSet.iterator().next();
		if (response != 0) {
			throw new TipiBreakException(TipiBreakException.USER_BREAK);
		}
	}

	private void showInfo(final String text, final String title,
			final int messageType) {
		// swing implementation.
		runSyncInEventThread(new Runnable() {

			public void run() {
				if (getOtherRoot() != null) {
					TipiModalInternalFrame.showInternalMessage(getOtherRoot()
							.getRootPane(), getOtherRoot().getContentPane(),
							title, text, getPoolSize(), messageType);
				} else 
					JOptionPane.showMessageDialog((Component) getTopDialog(),
							text, title, messageType);

			}
		});

	}

	@Override
	public void showInfo(final String text, final String title) {
		logger.info("ShowInfo: "+text+" title: "+title);
		showInfo(text, title, JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void showError(final String text, final String title) {
		logger.error("ShowError: "+text+" title: "+title);
		showInfo(text, title, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showWarning(final String text, final String title) {
		logger.warn("ShowWarning: "+text+" title: "+title);
		showInfo(text, title, JOptionPane.WARNING_MESSAGE);
	}

	// TODO refactor into more 
	public void processProperties(Map<String, String> properties)
			throws MalformedURLException {
		appendJnlpCodeBase(properties);
		super.processProperties(properties);
		String laf = null;

		try {
			laf = System.getProperty("tipilaf");
		} catch (SecurityException e) {

		}
		if (laf == null) {
			laf = properties.get("tipilaf");
		}
		
		final String tipiLaf = laf;

		
		try {
			if (tipiLaf == null) {
				// try nimbus first:
				try {
					Class.forName("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					UIManager
							.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

				} catch (ClassNotFoundException e) {
					// revert to system
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());

				}
				// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else {
//				UIManager.setLookAndFeel(tipiLaf);
				runSyncInEventThread(new Runnable() {

					@Override
					public void run() {
						if(tipiLaf!=null) {
							TipiSwingExtension.getInstance().setLookAndFeel(tipiLaf);
//							SwingUtilities.updateComponentTreeUI(getTopLevel());
						}
					
					}});
			}
		} catch (Exception e) {
			logger.warn("Unable to load supplied look and feel: "+ tipiLaf,e);
			
		}
	}

	private void appendJnlpCodeBase(Map<String, String> properties) {
		try {
			WebStartProxy.appendJnlpProperties(properties);
		} catch (Throwable e) {
		}
	}

	public boolean useCache() {
		return "true".equals(getSystemProperty("cache"));
	}

	protected TipiResourceLoader createDefaultResourceLoader(String loaderType,
			boolean useCache) {
		if (hasJnlpContext()) {
			try {
				return WebStartProxy.createDefaultWebstartLoader(loaderType,
						useCache, getCookieManager());
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return super.createDefaultResourceLoader(loaderType, useCache);
	}

	public boolean hasJnlpContext() {
		try {
			Class.forName("javax.jnlp.ServiceManager");
			return WebStartProxy.hasJnlpContext();
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public void animateProperty(Property p, int duration, Object target) {
		Class<?> animatableClass = TipiAnimationManager.isAnimatable(
				p.getTypedValue(), target);
		if (animatableClass != null) {
			PropertyAnimator pa = new PropertyAnimator();
			System.err.println("animating...: " + animatableClass);
			pa.animateProperty(p, duration, target, animatableClass);
		} else {
			System.err.println("No, not animatable");
			super.animateProperty(p, duration, target);
		}

	}

	@Override
	public void doActions(TipiEvent te, TipiComponent comp,
			TipiExecutable executableParent, List<TipiExecutable> exe)
			throws TipiBreakException {
		String component = executableParent.getBlockParam("animationComponent");
		if (component == null) {
			super.doActions(te, comp, executableParent, exe);
			return;
		}

		TipiComponent tc = (TipiComponent) evaluate(component, comp, te).value;
		if (tc == null || !(tc instanceof TipiSwingComponent)) {
			super.doActions(te, comp, executableParent, exe);
			return;
		}
		TipiSwingComponent ttt = (TipiSwingComponent) tc;
		String durationString = executableParent.getBlockParam("duration");
		int duration = 1500;
		if (durationString == null) {
			//
		} else {
			duration = Integer.parseInt(durationString);
		}
		ttt.animateTransition(te, executableParent, exe, duration);

	}

	public void animateDefaultTransition(
			TipiSwingComponent tipiSwingComponentImpl, final TipiEvent te,
			final TipiExecutable exeParent, Container swingContainer,
			final List<TipiExecutable> exe, int duration)
			throws TipiBreakException {
		if (!(swingContainer instanceof JComponent)) {
			super.doActions(te, tipiSwingComponentImpl, exeParent, exe);
			return;
		}
		final JComponent jjj = (JComponent) swingContainer;
		// int delay = 1500;
		final Animator animator = new Animator(duration);

		final ScreenTransition transition = new ScreenTransition(jjj,
				new TransitionTarget() {

					public void setupNextScreen() {
						try {
							int i = 0;
							for (TipiExecutable current : exe) {
								current.performAction(te, exeParent, i++);
							}
						} catch (Throwable ex) {
							te.dumpStack(ex.getMessage());
							ex.printStackTrace();
						}
						// animator.resume();
						animator.getCycleElapsedTime();
						jjj.revalidate();
					}
				}, animator);
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				transition.dispose();
			}

		});
		animator.setAcceleration(.1f); // Accelerate for first 20%
		animator.setDeceleration(.4f); // Decelerate for last 40%
		transition.start();
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void injectApplication(String definition, List<String> arrrgs,
			String sandBoxPath) {
		TipiComponent sandbox = getDefaultTopLevel().getTipiComponentByPath(
				sandBoxPath);
		final XMLElement inst = new CaseSensitiveXMLElement();
		inst.setName("c.windowembed");
		inst.setAttribute("id", "app");
		if (sandbox != null) {
			System.err.println("SANDBOX FOUND!");
		}
	}

	public void showInternalError(String errorString, Throwable t) {
		super.showInternalError(errorString, t);
		if (fakeJars) {
			showInfo("Internal error: " + errorString, "Internal error");
		}
	}

	public JDialog createDialog(TipiDialog d, String title) {
		if (dialogStack.isEmpty()) {
			if (getTopLevel() instanceof JFrame) {
				// JDialog jd = new JDialog((JFrame)getTopLevel(),title);
				JDialog jd = new TipiSwingDialog((JFrame) getTopDialog(), d);
				jd.setTitle(title);

				((JComponent) jd.getContentPane()).setOpaque(false);
				dialogStack.push(jd);
				return jd;
			}
			if (getAppletRoot() != null) {
				JFrame rootFrame = (JFrame) JOptionPane
						.getFrameForComponent(getAppletRoot());
				JDialog jd = new TipiSwingDialog(rootFrame, d);
				jd.setTitle(title);
				dialogStack.push(jd);
				return jd;
			}
			if (getTopLevel() instanceof JPanel) {
				JFrame rootFrame = (JFrame) JOptionPane
						.getFrameForComponent((JPanel) getTopLevel());
				JDialog jd = new TipiSwingDialog(rootFrame, d);
				jd.setTitle(title);
				dialogStack.push(jd);
				return jd;
			}
			System.err.println("Trouble creating dialog");
			JDialog jd = new TipiSwingDialog((JFrame) null, d);
			jd.setTitle(title);
			dialogStack.push(jd);
			return jd;
		}
		JDialog parent = dialogStack.peek();

		System.err.println("Create dialog: Stack not empty: "
				+ dialogStack.size() + ", attaching to dialog with title: "
				+ parent.getTitle());

		JDialog newMotherFucka = new JDialog(parent, title);
		dialogStack.push(newMotherFucka);
		return newMotherFucka;
	}

	public void addDialog(JDialog j) {
		if (dialogStack.contains(j)) {
			return;
		}
		dialogStack.push(j);
	}

	public RootPaneContainer getTopDialog() {
		if (dialogStack.isEmpty()) {
			return (RootPaneContainer) getTopLevel();
		}
		JDialog parent = dialogStack.peek();
		return parent;
	}

	public void destroyDialog(JDialog j) {
		// System.err.println("Before: "+dialogStack);
		if (dialogStack.isEmpty()) {
			System.err.println("Already empty");
			return;
		}
		if (dialogStack.peek() == j) {
			dialogStack.pop();
			j.setVisible(false);
			// smoking gun: (this line was missing)
			j.dispose();
			// System.err.println("After: "+dialogStack);

			return;
		}
		if (!dialogStack.contains(j)) {
		} else {
			JDialog sss = dialogStack.pop();
			sss.dispose();
			destroyDialog(j);
		}
		// System.err.println("After: "+dialogStack);
		j.dispose();
	}

	@Override
	public void showFatalStartupError(String message) {
		showError(message, "Fatal startup error!");
	}



}
