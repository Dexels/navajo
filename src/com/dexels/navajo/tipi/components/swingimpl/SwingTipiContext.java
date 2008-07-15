package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.transitions.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.animation.*;
import com.dexels.navajo.tipi.components.swingimpl.formatters.*;
import com.dexels.navajo.tipi.components.swingimpl.jnlp.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.swingclient.*;
import com.dexels.navajo.tipi.tipixml.*;

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

	private final Set<Thread> threadSet = Collections.synchronizedSet(new HashSet<Thread>());
	private final Set<Thread> dialogThreadSet = Collections.synchronizedSet(new HashSet<Thread>());
	private final Map<String, String> cookieMap = new HashMap<String, String>();
	private final Stack<JDialog> dialogStack = new Stack<JDialog>();
	private boolean dialogShowing = false;

	// private JDialog blockingDialog;

	private UserInterface myUserInterface;
	private boolean debugMode = false;

	private TipiApplet myAppletRoot;
	private TipiSwingDesktop defaultDesktop = null;

	private RootPaneContainer myOtherRoot;

	public SwingTipiContext(SwingTipiContext parentContext) {
		super(parentContext);
		// Don't think it is right here
		try {
			Locale.setDefault(new Locale("nl", "NL"));
		} catch (SecurityException se) {
		}
		// JFrame.setDefaultLookAndFeelDecorated(true);
		// JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			loadCookies();
		} catch (FileNotFoundException e) {
			// no cookies, no prob
		} catch (IOException e) {
			e.printStackTrace();
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

	public List<String> getRequiredIncludes() {
		List<String> s = super.getRequiredIncludes();
		s.add("com/dexels/navajo/tipi/components/swingimpl/swingclassdef.xml");
		return s;
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
		if (getAppletRoot() != null) {

			getAppletRoot().setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
		}
		for (int i = 0; i < rootPaneList.size(); i++) {
			Object obj = rootPaneList.get(i);
			// if (TipiSwingComponent.class.isInstance(obj)) {
			// TipiSwingComponent tc = (TipiSwingComponent) obj;
			// tc.setWaitCursor(b);
			// } else {
			// ((Container) obj).setCursor(b ?
			// Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) :
			// Cursor.getDefaultCursor());
			// }
		}
		for (TipiActivityListener ta : myActivityListeners) {
			ta.setActive(b);
		}
	}

	public void clearTopScreen() {
		((TipiScreen) getDefaultTopLevel()).clearTopScreen();
	}

	public void setSplashInfo(final String info) {
		// System.err.println("Splash: "+info);
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
			printIndent(indent, "Preferredsize: " + c.getPreferredSize());
		}
		printIndent(indent, "Size: " + c.getSize() + " visible: " + c.isVisible());

		// c.setPreferredSize(null);
		if (c instanceof Container) {
			Container cc = (Container) c;
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
			System.exit(0);
		}
	}

	public void setDefaultDesktop(TipiSwingDesktop jp) {
		defaultDesktop = jp;

	}

	public TipiSwingDesktop getDefaultDesktop() {
		return defaultDesktop;
	}

	public void showQuestion(final String text, final String title) {
	}

	public void showInfo(final String text, final String title) {
		// swing implementation.
		runSyncInEventThread(new Runnable() {

			public void run() {
				if (getOtherRoot() != null) {
					TipiModalInternalFrame.showInternalMessage(getOtherRoot().getRootPane(), getOtherRoot().getContentPane(), title, text,
							getPoolSize());
				} else if (getAppletRoot() != null && getDefaultDesktop() != null) {

					TipiModalInternalFrame.showInternalMessage(getAppletRoot().getRootPane(), getDefaultDesktop(), title, text,
							getPoolSize());
				} else {
					JOptionPane.showMessageDialog((Component) getTopDialog(), text, title, JOptionPane.PLAIN_MESSAGE);

				}
			}
		});

	}

	public void processProperties(Map<String, String> properties) throws MalformedURLException {
		// appendJnlpCodeBase(properties);
		super.processProperties(properties);
		String tipiLaf = null;

		try {
			tipiLaf = System.getProperty("tipilaf");
		} catch (SecurityException e) {

		}
		if (tipiLaf == null) {
			tipiLaf = properties.get("tipilaf");
		}

		try {
			if (tipiLaf == null) {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} else {
				UIManager.setLookAndFeel(tipiLaf);

			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	private void appendJnlpCodeBase(Map<String, String> properties) {

		try {
			Class c = Class.forName("javax.jnlp.ServiceManager");
			WebStartProxy.appendJnlpCodeBase(properties);

		} catch (ClassNotFoundException e) {
			System.err.println("Not running in WebStart");
		}

	}

	public void animateProperty(Property p, int duration, Object target) {
		if (TipiAnimationManager.isAnimatable(p.getTypedValue(), target)) {
			PropertyAnimator pa = new PropertyAnimator();
			pa.animateProperty(p, duration, target);
		} else {
			super.animateProperty(p, duration, target);
		}

	}

	@Override
	public void doActions(TipiEvent te, TipiComponent comp, TipiExecutable executableParent, List<TipiExecutable> exe)
			throws TipiBreakException {
		te.getEventKeySet();

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

		ttt.animateTransition(te, executableParent, exe);

	}

	public void animateDefaultTransition(TipiSwingComponent tipiSwingComponentImpl, final TipiEvent te, final TipiExecutable exeParent,
			Container swingContainer, final List<TipiExecutable> exe) throws TipiBreakException {
		if (!(swingContainer instanceof JComponent)) {
			super.doActions(te, tipiSwingComponentImpl, exeParent, exe);
			return;
		}
		final JComponent jjj = (JComponent) swingContainer;
		int delay = 1500;
		final Animator animator = new Animator(delay);
		final int iii = jjj.getComponentListeners().length;
		// try {
		// SwingUtilities.invokeAndWait(new Runnable(){
		//
		// public void run() {
		//					

		final ScreenTransition transition = new ScreenTransition(jjj, new TransitionTarget() {

			public void setupNextScreen() {
				// System.err.println(">>>>>ThreaD: " +
				// Thread.currentThread().getName()+" hash: "+this.hashCode());

				// animator.pause();
				try {
					int i = 0;
					for (TipiExecutable current : exe) {
						current.performAction(te, exeParent, i++);
					}
				} catch (Throwable ex) {
					ex.printStackTrace();
				}
				// animator.resume();
				long ll = animator.getCycleElapsedTime();
				// System.err.println("Elapsed during setup: " + ll);
				// System.err.println("Free memory:
				// "+Runtime.getRuntime().freeMemory());
				jjj.revalidate();
			}
		}, animator);
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				// System.err.println("Finished:
				// "+jjj.getComponentListeners().length+" before: "+iii);
				transition.dispose();
			}

		});
		animator.setAcceleration(.1f); // Accelerate for first 20%
		animator.setDeceleration(.4f); // Decelerate for last 40%
		transition.start();
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.gc();
		// TODO only continue when the animation has finished
		// }});
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (InvocationTargetException e) {
		// e.printStackTrace();
		// }
	}

	public void injectApplication(String definition, List<String> arrrgs, String sandBoxPath) {
		TipiComponent sandbox = getDefaultTopLevel().getTipiComponentByPath(sandBoxPath);
		final XMLElement inst = new CaseSensitiveXMLElement();
		inst.setName("c.windowembed");

		// <instantiateClass id="'club'" location="{component://init/desktop}"
		// class="'windowembed'"
		// tipiCodeBase="'c:/projecten/SportlinkClubStudio/tipi/'"
		// resourceCodeBase="'c:/projecten/SportlinkClubStudio/resource/'" />

		// inst.setAttribute("resourceCodeBase", "app");
		// inst.setAttribute("tipiCodeBase", "app");
		inst.setAttribute("id", "app");
		//
		// try {
		// sandbox.addComponentInstance(this, inst, null);
		// } catch (TipiException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// TipiComponent application = sandbox.getTipiComponent("app");
		// <performTipiMethod path="{component://init/desktop/club}"
		// name="'loadDefinition'" location="'start.xml'" />
		// <performTipiMethod path="{component://init/desktop/club}"
		// name="'switch'" definition="'init'" />

		if (sandbox != null) {
			System.err.println("SANDBOX FOUND!");
		}
	}

	@Override
	public String getCookie(String key) {
		return cookieMap.get(key);
	}

	@Override
	public void setCookie(String key, String value) {
		cookieMap.put(key, value);
		try {
			saveCookies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void saveCookies() throws IOException {
		File tmp = new File(System.getProperty("java.io.tmpdir"));
		File f = new File(tmp, "tipi.cookie");
		PrintWriter fw = new PrintWriter(new FileWriter(f, false));
		Set<String> ss = cookieMap.keySet();
		for (String key : ss) {
			fw.println(key + "|" + cookieMap.get(key));
		}
		fw.flush();
		fw.close();
	}

	private void loadCookies() throws IOException {
		File tmp = new File(System.getProperty("java.io.tmpdir"));
		File f = new File(tmp, "tipi.cookie");
		BufferedReader fw = new BufferedReader(new FileReader(f));
		String line = fw.readLine();
		while (line != null) {
			StringTokenizer st = new StringTokenizer(line, "|");
			String key = st.nextToken();
			String value = st.nextToken();
			cookieMap.put(key, value);
			line = fw.readLine();
		}
	}

	public void showInternalError(String errorString, Throwable t) {
		super.showInternalError(errorString, t);
		if (fakeJars) {
			showInfo("Internal error: " + errorString, "Internal error");
		}
	}

	public JDialog createDialog(String title) {
		if(dialogStack.isEmpty()) {
			System.err.println("Create dialog: Stack empty, attaching to frame");
			JDialog jd = new JDialog((JFrame)getTopLevel(),title);
			dialogStack.push(jd);
			return jd;
		}
		JDialog parent = (JDialog)dialogStack.peek();

		System.err.println("Create dialog: Stack not empty: "+dialogStack.size()+", attaching to dialog with title: "+parent.getTitle());

		JDialog newMotherFucka = new JDialog(parent,title);
		dialogStack.push(newMotherFucka);
		return newMotherFucka;
	}
	
	public void addDialog(JDialog j) {
		if(dialogStack.contains(j)) {
			System.err.println("WTF? Double registration of a dialog?");
			return;
		} 
		dialogStack.push(j);
	}

	public RootPaneContainer getTopDialog() {
		if(dialogStack.isEmpty()) {
			System.err.println("No registered dialogs. Returning frame");
			return (RootPaneContainer) getTopLevel();
		}
		JDialog parent = dialogStack.peek();
		System.err.println("Returning dialog with name: "+parent.getTitle());
		return parent;
	}
	
	public void destroyDialog(JDialog j) {
		System.err.println("Disposing dialog. Current stack size: "+dialogStack.size());
		if(dialogStack.isEmpty()) {
			System.err.println("Already popped?! Whatever...");
			return;
		}
		if(dialogStack.peek() == j) {
			System.err.println("Disposing dialog: Popping the last ");
				dialogStack.pop();
			j.setVisible(false);
			return;
		}
		if(!dialogStack.contains(j)) {
			System.err.println("Unknown dialog!!!!");
		}
		
	}
}
