package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.swingclient.*;

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
public class TipiDialog extends TipiSwingDataComponentImpl {
	private boolean disposed = false;
	// private JDialog myDialog = null;
	private boolean modal = false;
	private boolean decorated = true;
	private boolean showing = false;
	private String title = "";
	private JMenuBar myBar = null;
	private Rectangle myBounds = new Rectangle(-1, -1, -1, -1);
	private boolean studioMode = false;
	private boolean ignoreClose = false;
	private Point myOffset;
	private RootPaneContainer myRootPaneContainer;

	public TipiDialog() {
	}

	public Object createContainer() {
		TipiSwingDesktop tp = new TipiSwingDesktop(this);
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return tp;
	}

	// }
	private final void dialog_windowClosing() {
		try {
			if (!ignoreClose) {
				performTipiEvent("onWindowClosed", null, true);
			}
		} catch (TipiException ex) {
			ex.printStackTrace();
		}
		disposed = true;
	}

	protected void createWindowListener(JDialog d) {
		d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dialog_windowClosing();
			}

			public void windowClosed(WindowEvent e) {
				dialog_windowClosing();
			}
		});
	}

	protected void createWindowListener(JInternalFrame d) {
		// d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.addInternalFrameListener(new InternalFrameAdapter() {

			public void internalFrameClosing(WindowEvent e) {
				dialog_windowClosing();
			}

			public void internalFrameClosing(InternalFrameEvent e) {
				dialog_windowClosing();
			}
		});
	}

	// public void removeFromContainer(Object c) {
	// getSwingContainer().remove( (Component) c);
	// }
	public void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				if (name.equals("modal")) {
					modal = ((Boolean) object).booleanValue();
					return;
				}
				if (name.equals("decorated")) {
					decorated = ((Boolean) object).booleanValue();
					return;
				}
				if (name.equals("title")) {
					title = object.toString();
					return;
				}
				if (name.equals("x")) {
					myBounds.x = ((Integer) object).intValue();
					return;
				}
				if (name.equals("y")) {
					myBounds.y = ((Integer) object).intValue();
					return;
				}
				if (name.equals("w")) {
					myBounds.width = ((Integer) object).intValue();
					return;
				}
				if (name.equals("h")) {
					myBounds.height = ((Integer) object).intValue();
					return;
				}
			}
		});
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		/** @todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method */
		if ("isShowing".equals(name)) {
			// return new Boolean( ( (JDialog)
			// getDialogContainer()).isVisible());
			return new Boolean(showing);
		}
		if ("title".equals(name)) {
			// return ( (JDialog) getDialogContainer()).getTitle();
			return title;
		}
		if (name.equals("x")) {
			return new Integer(myBounds.x);
		}
		if (name.equals("y")) {
			return new Integer(myBounds.y);
		}
		if (name.equals("w")) {
			return new Integer(myBounds.width);
		}
		if (name.equals("h")) {
			return new Integer(myBounds.height);
		}
		return super.getComponentValue(name);
	}

	public void disposeComponent() {
		if (getDialogContainer() != null) {
			if (getDialogContainer() instanceof JDialog) {
				((JDialog) getDialogContainer()).setVisible(false);
			}
			if (getDialogContainer() instanceof JInternalFrame) {
				((JInternalFrame) getDialogContainer()).setVisible(false);
			}
		}
		super.disposeComponent();
	}

	private RootPaneContainer getDialogContainer() {
		// TODO Auto-generated method stub
		return myRootPaneContainer;
	}

	private final void constructDialog() {
		// System.err.println("Constructing: studio? "+isStudioElement());
		if (mySwingTipiContext.getAppletRoot() != null) {
//			System.err.println("Applet root");
			constructAppletDialog();
		} else {
//			System.err.println("Standard dialog mode");
			constructStandardDialog();
		}
	}

	private void constructAppletDialog() {
		if (mySwingTipiContext.getDefaultDesktop() == null) {
			System.err.println("No default desktop found. Reverting to normal.");
			constructStandardDialog();
			return;
		}
		final Rectangle bnds = getDialogBounds();
		if (bnds != null) {
			// if (myOffset != null) {
			// bnds.translate(myOffset.x, myOffset.y);
			// }
			// myDialog.setSize(bnds.getSize());
//			System.err.println("Setting bounds: " + bnds + " offset: " + myOffset);
		}

		JInternalFrame myDialog = createInternalFrame(modal, mySwingTipiContext.getDefaultDesktop(), bnds.getSize());

		myRootPaneContainer = myDialog;
		ignoreClose = false;
		myDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// myDialog.setUndecorated(!decorated);
		createWindowListener(myDialog);
		myDialog.setTitle(title);
		myDialog.toFront();
		if (myBar != null) {
			myDialog.setJMenuBar(myBar);
		}
//		myDialog.getContentPane().setLayout(new BorderLayout());
//		myDialog.getContentPane().add(getSwingContainer(), BorderLayout.CENTER);
//		myDialog.pack();
		// always the case
		// myDialog.setLocationRelativeTo((Component) myContext.getTopLevel());

		// mySwingTipiContext.getDefaultDesktop().add(myDialog);
//		myDialog.setVisible(true);
		if (myDialog instanceof JExtendedInternalFrame) {
			JExtendedInternalFrame jef = (JExtendedInternalFrame)myDialog;
			jef.setVisible(true);
			jef.startModal();
		} else {
			myDialog.setVisible(true);
		}
	}

	private JInternalFrame createInternalFrame(boolean isModal, JDesktopPane desktop, Dimension size) {
		JRootPane myRootPane = null;
		if (myContext.getTopLevel() instanceof TipiApplet) {
			myRootPane = ((TipiApplet) myContext.getTopLevel()).getRootPane();
		}
		if (myContext.getTopLevel() instanceof JFrame) {
			myRootPane = ((JFrame) myContext.getTopLevel()).getRootPane();
		}

		if (!modal) {
			JInternalFrame jif = new JInternalFrame();
			jif.setVisible(true);
			myRootPaneContainer = jif;

			mySwingTipiContext.getDefaultDesktop().add(jif);
			return jif;
		} else {
			TipiModalInternalFrame tmif = new TipiModalInternalFrame("",myRootPane,desktop,getSwingContainer(),size);
			tmif.setPreferredSize(size);
			tmif.setSize(size);
			tmif.setResizable(false);
			tmif.setClosable(true);
//			mySwingTipiContext.getDefaultDesktop().add(tmif);
			// TipiModalInternalFrame tmif = new
			// TipiModalInternalFrame("",myRootPane,desktop,size);
			myRootPaneContainer = tmif;
			tmif.add(getSwingContainer());
			desktop.add(tmif);
			tmif.setVisible(true);
			return tmif;
		}

	}

	private final void constructStandardDialog() {
		Object rootObject = getContext().getTopLevel();
		RootPaneContainer r = null;
		// JDialog d = null;
		JDialog myDialog = (JDialog) getDialogContainer();
		ignoreClose = false;
		if (rootObject == null) {
			System.err.println("Null root. Bad, bad, bad.");
			myDialog = new JDialog(new JFrame());
			myDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		} else {
			if (rootObject instanceof RootPaneContainer) {
				r = (RootPaneContainer) rootObject;
				if (Frame.class.isInstance(r)) {
					// System.err.println("Creating with frame root");
					myDialog = new JDialog((Frame) r);
					myRootPaneContainer = myDialog;
				} else {
					if (rootObject instanceof TipiApplet) {
						JApplet jap = (JApplet) rootObject;
						myDialog = new JDialog();
//						System.err.println("Root bounds: " + jap.getBounds());
						myDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						myOffset = jap.getLocationOnScreen();
						myDialog.setLocation(jap.getLocationOnScreen());
					} else {
//						System.err.println("Creating with dialog root. This is quite surpising, actually.");
						myDialog = new JDialog((Dialog) r);
						myDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
						myRootPaneContainer = myDialog;

					}
				}
			} else {
				System.err.println("R is strange... a: " + rootObject.getClass());
				myDialog = new JDialog();
				myDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		}
		myDialog.setUndecorated(!decorated);
		createWindowListener(myDialog);
		myDialog.setTitle(title);
		myDialog.toFront();
		if (myBar != null) {
			myDialog.setJMenuBar(myBar);
		}
		if (!(rootObject instanceof TipiApplet)) {
			myDialog.setModal(modal);
		}

//		myDialog.setVisible(true);
		myDialog.getContentPane().setLayout(new BorderLayout());
		myDialog.getContentPane().add(getSwingContainer(), BorderLayout.CENTER);
		myDialog.pack();
		// always the case
		final Rectangle bnds = getDialogBounds();
		myDialog.setLocationRelativeTo((Component) myContext.getTopLevel());
		if (bnds != null) {
			if (myOffset != null) {
				bnds.translate(myOffset.x, myOffset.y);
			}
			myDialog.setBounds(bnds);
			System.err.println("Setting bounds: " + bnds + " offset: " + myOffset);

		} else {
			System.err.println("Null bounds for dialog.");
		}

	}

	private synchronized Rectangle getDialogBounds() {
		return myBounds;
	}

	protected void helperRegisterEvent(TipiEvent te) {
		if (te != null && te.getEventName().equals("onWindowClosed")) {
			// overridden.. should be ok.
		} else {
			super.helperRegisterEvent(te);
		}
	}

	protected synchronized void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		final TipiDialog me = this;
		final Thread currentThread = Thread.currentThread();
		final boolean amIEventThread = SwingUtilities.isEventDispatchThread();
		super.performComponentMethod(name, compMeth, event);

		// final JDialog myDialog = (JDialog) getDialogContainer();
		if (name.equals("show")) {
//			System.err.println("ENTERING SHOW\n=================");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (getDialogContainer() == null) {
//						System.err.println("CONSTRUCTING DIALOG: "+getId());
//						System.err.print(store());
						constructDialog();
					}
					if (getDialogContainer() != null) {
						mySwingTipiContext.addTopLevel(getDialogContainer().getContentPane());
						mySwingTipiContext.dialogShowing(true);
						mySwingTipiContext.updateWaiting();
						if (myBounds.x >= 0 && myBounds.y >= 0) {
//							System.err.println("Entering bounssss...");
							// will show NOW, after this bounds will not matter
							if (myContext.getTopLevel() instanceof TipiApplet) {
								TipiApplet jap = (TipiApplet) myContext.getTopLevel();
								Point p = jap.getCenteredPoint(((JComponent) getDialogContainer()).getSize());
								((JComponent) getDialogContainer()).setLocation(p);
								((JComponent) getDialogContainer()).setVisible(true);
							} else {
								SwingClient.getUserInterface().showDialogAt((JDialog) getDialogContainer(), myBounds.x, myBounds.y);
							}
//							System.err.println("Setting to location: " + myBounds);
						} else {
//							System.err.println("Centering...");
							// will show NOW, after this bounds will not matter
							if (myContext.getTopLevel() instanceof TipiApplet) {
								TipiApplet jap = (TipiApplet) myContext.getTopLevel();
							} else {
								JDialog g = (JDialog) getDialogContainer();
//								System.err.println("NEARLY SHOWING NOW>>>>>>>>>>>>");
								SwingClient.getUserInterface().showCenteredDialog(g);
//								System.err.println("SHOWING NOW>>>>>>>>>>>>");
							}
						}
						final Rectangle bnds = getDialogBounds();
						if (myContext != null) {
							((SwingTipiContext) myContext).dialogShowing(false);
							if (getDialogContainer() != null) {
								mySwingTipiContext.removeTopLevel(getDialogContainer().getContentPane());
								mySwingTipiContext.updateWaiting();
								// myDialog.toFront();
							} else {
								System.err.println("Null DIALOG, in TipiDialog.");
							}
						} else {
							System.err.println("Null CONTEXT, in TipiDialog.");
						}

					}
				}
			});
		}
		if (name.equals("hide")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					// System.err.println("Hiding dialog!!!\n\n\n\n");
					if (getDialogContainer() != null && getDialogContainer() instanceof JDialog) {
						JDialog j = (JDialog) getDialogContainer();
						ignoreClose = true;
						j.setVisible(false);
						j.dispose();
					}
					if (getDialogContainer() instanceof JInternalFrame) {
						JInternalFrame j = (JInternalFrame) getDialogContainer();
						ignoreClose = true;
						if (j instanceof JExtendedInternalFrame) {
							JExtendedInternalFrame jef = (JExtendedInternalFrame)j;
							jef.stopModal();
							jef.setVisible(false);
						} else {
							j.setVisible(true);
							j.dispose();
						}

						
					}

				}
			});
		}
		if (name.equals("dispose")) {
			runSyncInEventThread(new Runnable() {

				public void run() {
					// System.err.println("Hide dialog: Disposing dialog!");
					if (getDialogContainer() != null) {
						if (getDialogContainer() instanceof JDialog) {
							JDialog j = (JDialog) getDialogContainer();
							ignoreClose = true;
							j.setVisible(false);
							j.dispose();
						}
						if (getDialogContainer() instanceof JInternalFrame) {
							JInternalFrame j = (JInternalFrame) getDialogContainer();
							ignoreClose = true;
							if (j instanceof JExtendedInternalFrame) {
								JExtendedInternalFrame jef = (JExtendedInternalFrame)j;
								jef.stopModal();
								jef.setVisible(false);
							} else {
								j.setVisible(true);
								j.dispose();
							}
						}

					}
					myContext.disposeTipiComponent(me);
					disposed = true;
				}
			});
		}
	}

	//
	// private void centerDialog(JDialog dlg, JFrame toplevel) {
	// Dimension dlgSize = dlg.getPreferredSize();
	// Rectangle r = toplevel.getBounds();
	// Dimension frmSize = new Dimension(r.width, r.height);
	// Point loc = toplevel.`getLocation();
	// int x_loc = (frmSize.width - dlgSize.width) / 2 + loc.x + r.x;
	// int y_loc = (frmSize.height - dlgSize.height) / 2 + loc.y + r.y;
	// System.err.println("Adding at: " + x_loc + ", " + y_loc);
	// if(y_loc < 0) {
	// y_loc = 0;
	// }
	// dlg.setLocation(x_loc, y_loc);
	// dlg.setModal(true);
	// dlg.show();
	// }

	// public void setContainerVisible(boolean b) {
	// }
	public void reUse() {
	}
}
