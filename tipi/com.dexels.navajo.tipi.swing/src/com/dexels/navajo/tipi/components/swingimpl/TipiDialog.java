package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.notifier.SerializablePropertyChangeListener;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.JExtendedInternalFrame;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiModalInternalFrame;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDialog;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingPanel;
import com.dexels.navajo.tipi.internal.TipiEvent;
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
public class TipiDialog extends TipiSwingDataComponentImpl{

	private static final long serialVersionUID = 8645510349158311190L;
	private boolean modal = false;
	@SuppressWarnings("unused")
	private boolean decorated = true;
	private boolean showing = false;
	private String title = "";
	private Object iconUrl = null;
	private JMenuBar myBar = null;
	private Rectangle myBounds = new Rectangle(-1, -1, -1, -1);
	private boolean ignoreClose = false;
	private Point myOffset;
	private RootPaneContainer myRootPaneContainer;
	private boolean forceInternal = false;

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDialog.class);
	
	TipiSwingPanel tp;

	public TipiDialog() {
	}

	public Object createContainer() {
		runSyncInEventThread(new Runnable() {

			public void run() {
				tp = new TipiSwingPanel(TipiDialog.this);
				TipiHelper th = new TipiSwingHelper();
				th.initHelper(TipiDialog.this);
				addHelper(th);

			}
		});
		return tp;
	}

	public void instantiateComponent(XMLElement instance, XMLElement classdef)
			throws TipiException {
		super.instantiateComponent(instance, classdef);
		getAttributeProperty("h").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = -6759912965035092081L;

					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							public void run() {
								setComponentValue("h", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("w").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = -328558635748778593L;

					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							public void run() {
								setComponentValue("w", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("x").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = 1768826439432626245L;

					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							public void run() {
								setComponentValue("x", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("y").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = 6549320305985952129L;

					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							public void run() {
								setComponentValue("y", evt.getNewValue());
							}
						});
					}
				});

	}

	// }
	private final void dialog_windowClosing() {
		try {
			if (!ignoreClose) {
				// beware of the true here:
				// only sync events can respond to breaks
				performTipiEvent("onWindowClosed", null, true);
			}
		} catch (TipiException ex) {
			logger.error("Error detected",ex);
		}
	}

	protected void createWindowListener(final JDialog d) {
		d.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// logger.debug("Dialog closing");
				// logger.debug("Defaultop: "+d.getDefaultCloseOperation()+" : "+JDialog.DO_NOTHING_ON_CLOSE+" :: "+JDialog.DISPOSE_ON_CLOSE+" ::: "+JDialog.HIDE_ON_CLOSE);
				try {
					dialog_windowClosing();
					((SwingTipiContext) myContext).destroyDialog(d);
					myRootPaneContainer = null;

				} catch (TipiBreakException e1) {
					// logger.debug("Break in window listener");
					// logger.error("Error detected",e1);
					if (e1.getType() == TipiBreakException.COMPONENT_DISPOSED) {
						// if the break is because the component is disposing
						// (that can happen while closing)
						// destroy anyway.
						((SwingTipiContext) myContext).destroyDialog(d);
					}
				}
			}

			public void windowClosed(WindowEvent e) {
				// logger.debug("Dialog closed");
				// dialog_windowClosing();
				// ((SwingTipiContext)myContext).destroyDialog(d);
			}
		});
	}

	protected void createWindowListener(final JInternalFrame d) {
		// d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.addInternalFrameListener(new InternalFrameAdapter() {

			public void internalFrameClosing(InternalFrameEvent e) {
				// logger.debug("Dialog closing");
				try {
					dialog_windowClosing();
					d.dispose();

				} catch (TipiBreakException e1) {
					// logger.debug("Break in window listener");
					logger.error("Error detected",e1);
					if (e1.getType() == TipiBreakException.COMPONENT_DISPOSED) {
						// if the break is because the component is disposing
						// (that can happen while closing)
						// destroy anyway.
						// ((SwingTipiContext) myContext).destroyDialog(d);
						d.dispose();
					}
				}
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
				if (name.equals("opacity")) {
//					opacity = ((Double) object).floatValue();
					if (myRootPaneContainer != null) {
						if (myRootPaneContainer instanceof JDialog) {
							JDialog jd = (JDialog) myRootPaneContainer;
							jd.setTitle(title);

							// com.sun.awt.AWTUtilities.setWindowOpacity(jd,
							// opacity);

						}
					}
					return;
				}
				if (name.equals("title")) {
					title = object.toString();
					if (myRootPaneContainer != null) {
						if (myRootPaneContainer instanceof JDialog) {
							JDialog jd = (JDialog) myRootPaneContainer;
							jd.setTitle(title);
						}
						if (myRootPaneContainer instanceof JInternalFrame) {
							JInternalFrame jd = (JInternalFrame) myRootPaneContainer;
							jd.setTitle(title);
						}
					}
					return;
				}
				if (name.equals("icon")) {
					iconUrl = object; 
					possiblySetIconAtContainer();
					return;
				}
				if (name.equals("x")) {
					myBounds.x = ((Integer) object).intValue();
					resetDialogBounds();
					return;
				}
				if (name.equals("y")) {
					myBounds.y = ((Integer) object).intValue();
					resetDialogBounds();
					return;
				}
				if (name.equals("w")) {
					myBounds.width = ((Integer) object).intValue();
					resetDialogBounds();
					return;
				}
				if (name.equals("h")) {
					myBounds.height = ((Integer) object).intValue();
					// logger.debug("Setting height: "+myBounds.height);
					resetDialogBounds();
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
		if ("icon".equals(name))
		{
			return iconUrl;
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

		runSyncInEventThread(new Runnable() {
			public void run() {
				if (getDialogContainer() != null) {
					if (getDialogContainer() instanceof JDialog) {
						((SwingTipiContext) myContext)
								.destroyDialog(((JDialog) getDialogContainer()));
					}
					if (getDialogContainer() instanceof JInternalFrame) {
						((JInternalFrame) getDialogContainer())
								.setVisible(false);
					}
				}
				myRootPaneContainer = null;
				TipiDialog.super.disposeComponent();
			}
		});

	}

	private RootPaneContainer getDialogContainer() {
		return myRootPaneContainer;
	}



	@SuppressWarnings("unused")
	private final void constructDialog() {
		// logger.debug("Constructing: studio? "+isStudioElement());

		// FIXME DISabled now::::
		if (false
				&& mySwingTipiContext.getAppletRoot() != null
				|| mySwingTipiContext.getOtherRoot() != null
				|| (mySwingTipiContext.getDefaultDesktop() != null && forceInternal == true)) {
			// logger.debug("Applet root");
			constructAppletDialog();
		} else {
			// logger.debug("Standard dialog mode");
			constructStandardDialog();
		}
		possiblySetIconAtContainer();
	}

	private void constructAppletDialog() {
		if (mySwingTipiContext.getDefaultDesktop() == null) {
			// logger.debug("No default desktop found. Reverting to normal.");
			constructStandardDialog();
			return;
		}
		Rectangle bnds = getDialogBounds();
		if (bnds == null) {
			// Not at all pretty, will look into it soon
			bnds = new Rectangle(10, 10, 200, 200);
		}
		JInternalFrame myDialog = createInternalFrame(modal,
				mySwingTipiContext.getDefaultDesktop(), bnds.getSize());
		myRootPaneContainer = myDialog;
		ignoreClose = false;
		myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		createWindowListener(myDialog);
		myDialog.setTitle(title);
		myDialog.toFront();
		if (myBar != null) {
			myDialog.setJMenuBar(myBar);
		}
		myDialog.getContentPane().add(getSwingContainer(), BorderLayout.CENTER);
		myDialog.setBounds(myBounds);
		if (myDialog instanceof JExtendedInternalFrame) {
			JExtendedInternalFrame jef = (JExtendedInternalFrame) myDialog;
			jef.setVisible(true);
			// jef.startModal();
		} else {
			myDialog.setVisible(true);
		}
	}

	/**
	 * @param isModal modal, but disabled 
	 */
	private JInternalFrame createInternalFrame(boolean isModal,
			JDesktopPane desktop, Dimension size) {
		// logger.debug("Creating modal: "+isModal);
		logger.info("HACKED TO NONMODAL!");
		isModal = false;
		JRootPane myRootPane = null;
		if (myContext.getTopLevel() instanceof TipiApplet) {
			myRootPane = ((TipiApplet) myContext.getTopLevel()).getRootPane();
		}
		if (myContext.getTopLevel() instanceof JFrame) {
			myRootPane = ((JFrame) myContext.getTopLevel()).getRootPane();
		}
		if (myContext.getTopLevel() instanceof JInternalFrame) {
			myRootPane = ((JInternalFrame) myContext.getTopLevel())
					.getRootPane();
		}

		if (!modal) {
			JInternalFrame jif = new JInternalFrame();
			jif.setVisible(true);
			myRootPaneContainer = jif;

			mySwingTipiContext.getDefaultDesktop().add(jif);
			// logger.debug("Adding: "+jif+" to "+mySwingTipiContext.getDefaultDesktop());
			return jif;
		} else {
			TipiModalInternalFrame tmif = new TipiModalInternalFrame("",
					myRootPane, desktop, getSwingContainer(), size);
			tmif.setPreferredSize(size);
			tmif.setSize(size);
			tmif.setResizable(false);
			tmif.setClosable(true);
			// mySwingTipiContext.getDefaultDesktop().add(tmif);
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
		// logger.debug("Creating dialog with root: "+rootObject);
		RootPaneContainer r = null;
		// JDialog d = null;
		JDialog myDialog = (JDialog) getDialogContainer();
		ignoreClose = false;
		if (rootObject == null) {
			logger.debug("Null root. Bad, bad, bad.");
			myDialog = ((SwingTipiContext) myContext).createDialog(this, title);
			// myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		} else {
			if (rootObject instanceof RootPaneContainer) {
				r = (RootPaneContainer) rootObject;
				if (JFrame.class.isInstance(r)) {
					// logger.debug("Creating with frame root");
					// myDialog = new TipiSwingDialog((JFrame) r,this);
					myDialog = ((SwingTipiContext) myContext).createDialog(
							this, title);
					// myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					// myDialog.setUndecorated(true);
					myRootPaneContainer = myDialog;
				} else {
					if (rootObject instanceof TipiApplet) {
						JApplet jap = (JApplet) rootObject;
						myDialog = ((SwingTipiContext) myContext).createDialog(
								this, title);
						// logger.debug("Root bounds: " +
						// jap.getBounds());

						// TODO All use the dialog factory in the
						// SwingTipiContext
						// myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						myOffset = jap.getLocationOnScreen();
						myDialog.setLocation(jap.getLocationOnScreen());
					} else if (rootObject instanceof JInternalFrame) {
						myDialog = new TipiSwingDialog(
								(JFrame) ((JInternalFrame) rootObject)
										.getTopLevelAncestor(),
								this);
						// myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						myRootPaneContainer = myDialog;
					}
				}
			} else {
				logger.debug("R is strange... a: "
						+ rootObject.getClass());
				myDialog = ((SwingTipiContext) myContext).createDialog(this,
						title);
				// myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			}
		}
		// logger.debug("Dialog class: "+myDialog);
		// Beware of this one: It messes up with some LnFs:
		// myDialog.setUndecorated(!decorated);
		// myDialog.setUndecorated(!decorated);
		createWindowListener(myDialog);
		myDialog.setTitle(title);
		myDialog.toFront();
		if (myBar != null) {
			myDialog.setJMenuBar(myBar);
		}
		if (!(rootObject instanceof TipiApplet)) {
			myDialog.setModal(modal);
		}

		// com.sun.awt.AWTUtilities.setWindowOpacity(myDialog, opacity);
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
			// logger.debug("Setting bounds: " + bnds + " offset: " +
			// myOffset);

		} else {
			logger.debug("Null bounds for dialog.");
		}

		mySwingTipiContext.addDialog(myDialog);
	}
	
	private void possiblySetIconAtContainer()
	{
		if (iconUrl != null && myRootPaneContainer != null && myRootPaneContainer instanceof TipiSwingDialog)
		{
			((TipiSwingDialog) myRootPaneContainer).setIconUrl(iconUrl);
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

	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		final TipiDialog me = this;
		super.performComponentMethod(name, compMeth, event);

		// final JDialog myDialog = (JDialog) getDialogContainer();
		if (name.equals("show")) {
			// logger.debug("ENTERING SHOW\n=================");
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					if (getDialogContainer() == null) {
						constructDialog();
					}
					if (getDialogContainer() != null) {
						mySwingTipiContext.addTopLevel(getDialogContainer()
								.getContentPane());
						mySwingTipiContext.dialogShowing(true);
						mySwingTipiContext.updateWaiting();
						if (myBounds.x >= 0 && myBounds.y >= 0) {
							// logger.debug("Entering bounssss...");
							// will show NOW, after this bounds will not matter
							if (myContext.getTopLevel() instanceof TipiApplet
									|| myContext.getTopLevel() instanceof JInternalFrame) {
								TipiApplet jap = (TipiApplet) myContext
										.getTopLevel();
								Point p = jap
										.getCenteredPoint(((JComponent) getDialogContainer())
												.getSize());
								((JComponent) getDialogContainer())
										.setLocation(p);
								((JComponent) getDialogContainer())
										.setVisible(true);
							} else {
								showDialogAt((JDialog) getDialogContainer(),
										myBounds.x, myBounds.y);
							}
							// logger.debug("Setting to location: " +
							// myBounds);
						} else {
							// will show NOW, after this bounds will not matter
							if (myContext.getTopLevel() instanceof TipiApplet) {
								TipiApplet jap = (TipiApplet) myContext
										.getTopLevel();
								Point p = jap
										.getCenteredPoint(((JComponent) getDialogContainer())
												.getSize());
								((JComponent) getDialogContainer())
										.setLocation(p);

								((JComponent) getDialogContainer())
										.setVisible(true);

							} else {
								if (myContext.getTopLevel() instanceof JInternalFrame) {
									// JInternalFrame jap = (JInternalFrame)
									// myContext.getTopLevel();
									Point p = new Point(50, 50);
									((JComponent) getDialogContainer())
											.setLocation(p);
									((JComponent) getDialogContainer())
											.setVisible(true);

								} else {
									if (getDialogContainer() instanceof JDialog) {
										JDialog g = (JDialog) getDialogContainer();
										showCenteredDialog(g);
									} else {
										Point p = new Point(50, 50);
										((JComponent) getDialogContainer())
												.setLocation(p);

										((JComponent) getDialogContainer())
												.setVisible(true);
									}

								}
							}
						}
						if (myContext != null) {
							((SwingTipiContext) myContext).dialogShowing(false);
							if (getDialogContainer() != null) {
								mySwingTipiContext
										.removeTopLevel(getDialogContainer()
												.getContentPane());
								mySwingTipiContext.updateWaiting();
								// myDialog.toFront();
							}
						} else {
							logger.warn("Null CONTEXT, in TipiDialog.");
						}

						if (getDialogContainer() instanceof JExtendedInternalFrame) {
							JExtendedInternalFrame x = (JExtendedInternalFrame) getDialogContainer();
							x.startModal();
							mySwingTipiContext.dialogShowing(false);
							mySwingTipiContext.updateWaiting();
						}

					}
				}
			});
		}
		if (name.equals("hide")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					// logger.debug("Hiding dialog!!!\n\n\n\n");
					if (getDialogContainer() != null
							&& getDialogContainer() instanceof JDialog) {
						JDialog j = (JDialog) getDialogContainer();
						ignoreClose = true;

						j.setVisible(false);
						j.dispose();
						mySwingTipiContext.destroyDialog(j);
						myRootPaneContainer = null;

					}
					if (getDialogContainer() instanceof JInternalFrame) {
						JInternalFrame j = (JInternalFrame) getDialogContainer();
						ignoreClose = true;
						if (j instanceof JExtendedInternalFrame) {
							JExtendedInternalFrame jef = (JExtendedInternalFrame) j;
							jef.stopModal();
							jef.setVisible(false);
						} else {
							j.setVisible(true);
							j.dispose();
						}
						myRootPaneContainer = null;

					}

				}
			});
		}
		if (name.equals("dispose")) {
			runSyncInEventThread(new Runnable() {

				public void run() {
					// logger.debug("Hide dialog: Disposing dialog!");
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
								JExtendedInternalFrame jef = (JExtendedInternalFrame) j;
								jef.stopModal();
								jef.setVisible(false);
							} else {
								j.setVisible(true);
								j.dispose();
							}
						}
						myRootPaneContainer = null;

					}
					// huh?
					myContext.disposeTipiComponent(me);
					myRootPaneContainer = null;

				}
			});
		}
	}

	private void resetDialogBounds() {
		if (getDialogContainer() != null) {
			if (getDialogContainer() instanceof JDialog) {
				JDialog g = (JDialog) getDialogContainer();
				g.setBounds(getDialogBounds());
			}
			if (getDialogContainer() instanceof JInternalFrame) {
				JInternalFrame g = (JInternalFrame) getDialogContainer();
				g.setBounds(getDialogBounds());
			}
		}
	}

	// public void setContainerVisible(boolean b) {
	// }
	public void reUse() {
	}

	// public void addDialog(JDialog d) {
	// // d.setLocationRelativeTo(getMainFrame());
	// d.pack();
	// showCenteredDialog(d);
	// }

	public void showDialogAt(JDialog dlg, int x, int y) {
		Dimension dlgSize = dlg.getPreferredSize();
		dlg.setLocation(x, y);

		if (dlgSize.height > (Toolkit.getDefaultToolkit().getScreenSize().height)) {
			dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height;
			dlg.setSize(dlgSize);
		}

		if (dlgSize.width > Toolkit.getDefaultToolkit().getScreenSize().width) {
			dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			dlg.setSize(dlgSize);
		}

		dlg.setModal(true);
		dlg.setVisible(true);

	}

	public RootPaneContainer getRootPaneContainer() {
		return (RootPaneContainer) myContext.getTopLevel();
	}

	public void showCenteredDialog(JDialog dlg) {
		Dimension dlgSize = dlg.getBounds().getSize();
		Rectangle r = getRootPaneContainer().getRootPane().getBounds();
		Dimension frmSize = new Dimension(r.width, r.height);
		Point loc = getRootPaneContainer().getRootPane().getLocation();
		int x = Math.max(0, (frmSize.width - dlgSize.width) / 2 + loc.x + r.x);
		int y = Math
				.max(0, (frmSize.height - dlgSize.height) / 2 + loc.y + r.y);
		dlg.setLocation(x, y);

		if (dlgSize.height > (Toolkit.getDefaultToolkit().getScreenSize().height)) {
			dlgSize.height = Toolkit.getDefaultToolkit().getScreenSize().height;
			dlg.setSize(dlgSize);
		}

		if (dlgSize.width > Toolkit.getDefaultToolkit().getScreenSize().width) {
			dlgSize.width = Toolkit.getDefaultToolkit().getScreenSize().width;
			dlg.setSize(dlgSize);
		}

		dlg.setModal(true);
		dlg.setVisible(true);
	}

	// public boolean showQuestionDialog(String s) {
	// int response = JOptionPane.showConfirmDialog( (Component)
	// myContext.getTopLevel(),
	// s,"",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
	// return response==0;
	// }

	// public void showInfoDialog(String s) {
	// JOptionPane.showConfirmDialog( (Component) myContext.getTopLevel(),
	// s,"",JOptionPane.OK_OPTION,JOptionPane.INFORMATION_MESSAGE);
	// }
	//
	// public boolean areYouSure() {
	// return showQuestionDialog("Are you sure?");
	// }
}
