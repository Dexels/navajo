/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuBar;
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
import com.dexels.navajo.tipi.components.core.TipiSupportOverlayPane;
import com.dexels.navajo.tipi.components.swingimpl.swing.JExtendedInternalFrame;
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
public class TipiDialog extends TipiSwingDataComponentImpl implements TipiSupportOverlayPane {

	private static final long serialVersionUID = 8645510349158311190L;
	private boolean modal = false;
	@SuppressWarnings("unused")
	private boolean decorated = true;
	private boolean showing = false;
	private String title = "";
	private Object iconUrl = null;
	private JMenuBar myBar = null;
	private Rectangle myBounds = new Rectangle(-1, -1, -1, -1);
	private Rectangle myBoundsByDefinition = new Rectangle(-1, -1, -1, -1);
	private boolean ignoreClose = false;
	private Point myOffset;
	private RootPaneContainer myRootPaneContainer;
	 private int overlayCounter = 0;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiDialog.class);
	
	TipiSwingPanel tp;

	public TipiDialog() {
	}

	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				tp = new TipiSwingPanel(TipiDialog.this);
				TipiHelper th = new TipiSwingHelper();
				th.initHelper(TipiDialog.this);
				addHelper(th);

			}
		});
		return tp;
	}

	@Override
	public void instantiateComponent(XMLElement instance, XMLElement classdef)
			throws TipiException {
		super.instantiateComponent(instance, classdef);
		getAttributeProperty("h").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = -6759912965035092081L;

					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							@Override
							public void run() {
								setComponentValue("h", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("w").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = -328558635748778593L;

					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							@Override
							public void run() {
								setComponentValue("w", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("x").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = 1768826439432626245L;

					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							@Override
							public void run() {
								setComponentValue("x", evt.getNewValue());
							}
						});
					}
				});
		getAttributeProperty("y").addPropertyChangeListener(
				new SerializablePropertyChangeListener() {
					private static final long serialVersionUID = 6549320305985952129L;

					@Override
					public void propertyChange(final PropertyChangeEvent evt) {
						runSyncInEventThread(new Runnable() {
							@Override
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
			@Override
			public void windowClosing(WindowEvent e) {
				// logger.debug("Dialog closing");
				// logger.debug("Defaultop: "+d.getDefaultCloseOperation()+" : "+JDialog.DO_NOTHING_ON_CLOSE+" :: "+JDialog.DISPOSE_ON_CLOSE+" ::: "+JDialog.HIDE_ON_CLOSE);
				try {
					dialog_windowClosing();
					((SwingTipiContext) myContext).destroyDialog(d);
					myRootPaneContainer = null;

				} catch (TipiBreakException e1) {
					// logger.debug("Break in window listener");
					logger.debug("Error detected",e1);
					if (e1.getType() == TipiBreakException.COMPONENT_DISPOSED) {
						// if the break is because the component is disposing
						// (that can happen while closing)
						// destroy anyway.
						((SwingTipiContext) myContext).destroyDialog(d);
					}
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// logger.debug("Dialog closed");
				// dialog_windowClosing();
				// ((SwingTipiContext)myContext).destroyDialog(d);
			}
		});
	}

	protected void createWindowListener(final JInternalFrame d) {
		d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		d.addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				// logger.debug("Dialog closing");
				try {
					dialog_windowClosing();
					d.dispose();

				} catch (TipiBreakException e1) {
					// logger.debug("Break in window listener");
					logger.debug("Error detected",e1);
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

	@Override
	public void loadStartValues(XMLElement element, TipiEvent event) {
		super.loadStartValues(element, event);
		myBoundsByDefinition = new Rectangle(myBounds.x, myBounds.y, myBounds.width, myBounds.height);
	}

	// public void removeFromContainer(Object c) {
	// getSwingContainer().remove( (Component) c);
	// }
	@Override
	public void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {

			@Override
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
					if (getDialogContainer() == null)
					{
						myBoundsByDefinition.x = myBounds.x; 
					}
					resetDialogBounds();
					return;
				}
				if (name.equals("y")) {
					myBounds.y = ((Integer) object).intValue();
					if (getDialogContainer() == null)
					{
						myBoundsByDefinition.y = myBounds.y; 
					}
					resetDialogBounds();
					return;
				}
				if (name.equals("w")) {
					myBounds.width = ((Integer) object).intValue();
					if (getDialogContainer() == null)
					{
						myBoundsByDefinition.width = myBounds.width; 
					}
					resetDialogBounds();
					return;
				}
				if (name.equals("h")) {
					myBounds.height = ((Integer) object).intValue();
					// logger.debug("Setting height: "+myBounds.height);
					if (getDialogContainer() == null)
					{
						myBoundsByDefinition.height = myBounds.height; 
					}
					resetDialogBounds();
					return;
				}
			}
		});
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		/** @todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method */
		if ("isShowing".equals(name)) {
			// return Boolean.valueOf( ( (JDialog)
			// getDialogContainer()).isVisible());
			return Boolean.valueOf(showing);
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
			return Integer.valueOf(myBounds.x);
		}
		if (name.equals("y")) {
			return Integer.valueOf(myBounds.y);
		}
		if (name.equals("w")) {
			return Integer.valueOf(myBounds.width);
		}
		if (name.equals("h")) {
			return Integer.valueOf(myBounds.height);
		}
		return super.getComponentValue(name);
	}

	@Override
	public void disposeComponent() {

		runSyncInEventThread(new Runnable() {
			@Override
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
		constructStandardDialog();
		possiblySetIconAtContainer();
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
			myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		} else {
			if (rootObject instanceof RootPaneContainer) {
				r = (RootPaneContainer) rootObject;
				if (JFrame.class.isInstance(r)) {
					// logger.debug("Creating with frame root");
					// myDialog = new TipiSwingDialog((JFrame) r,this);
					myDialog = ((SwingTipiContext) myContext).createDialog(
							this, title);
					myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
					// myDialog.setUndecorated(true);
					myRootPaneContainer = myDialog;
				} else {
					if (rootObject instanceof JInternalFrame) {
						myDialog = new TipiSwingDialog(
								(JFrame) ((JInternalFrame) rootObject)
										.getTopLevelAncestor(),
								this);
						myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
						myRootPaneContainer = myDialog;
					}
				}
			} else {
				logger.debug("R is strange... a: "
						+ rootObject.getClass());
				myDialog = ((SwingTipiContext) myContext).createDialog(this,
						title);
				myDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
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
		myDialog.setModal(modal);

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

	private Rectangle getDialogBounds() {
		return myBounds;
	}

	@Override
	protected void helperRegisterEvent(TipiEvent te) {
		if (te != null && te.getEventName().equals("onWindowClosed")) {
			// overridden.. should be ok.
		} else {
			super.helperRegisterEvent(te);
		}
	}

	@Override
	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		final TipiDialog me = this;
		super.performComponentMethod(name, compMeth, event);

		// final JDialog myDialog = (JDialog) getDialogContainer();
		if (name.equals("show")) {
			// logger.debug("ENTERING SHOW\n=================");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					setValue("x", myBoundsByDefinition.x);
					setValue("y", myBoundsByDefinition.y);
					setValue("w", myBoundsByDefinition.width);
					setValue("h", myBoundsByDefinition.height);
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
							if (myContext.getTopLevel() instanceof JInternalFrame) {
								logger.info("Internal frame version, not implemented");
//								JInternalFrame internal = (JInternalFrame) myContext.getTopLevel();
//								Point p = internal
//										.getCenteredPoint(((JComponent) getDialogContainer())
//												.getSize());
//								((JComponent) getDialogContainer())
//										.setLocation(p);
//								((JComponent) getDialogContainer())
//										.setVisible(true);
							} else {
								showDialogAt((JDialog) getDialogContainer(),
										myBounds.x, myBounds.y);
							}
							// logger.debug("Setting to location: " +
							// myBounds);
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
				@Override
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

				@Override
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
/* 
// Doesn't seem to work if called directly after dialog.show was called. Doesn't give the expected size on TipiTables (probably should be fixed there).
		if (name.equals("resize")) {
			runSyncInEventThread(new Runnable() {

				public void run() {
					if (getDialogContainer() != null && getDialogContainer() instanceof JDialog)
					{
						JDialog dlg = (JDialog) getDialogContainer();
						logger.debug("Resizing dialog " + this + " to size " + dlg.getPreferredSize());
						setDialogSize(dlg, dlg.getPreferredSize());
					}
					else
					{
						logger.warn("Trying to resize a dialog before it is shown!");
					}
				}
			});
		} */
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
	@Override
	public void reUse() {
	}

	// public void addDialog(JDialog d) {
	// // d.setLocationRelativeTo(getMainFrame());
	// d.pack();
	// showCenteredDialog(d);
	// }

	public void showDialogAt(JDialog dlg, int x, int y) {
		Dimension dlgSize = dlg.getBounds().getSize();
		dlg.setLocation(x, y);
		setDialogSize(dlg, dlgSize);
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
		setDialogSize(dlg, dlgSize);
		dlg.setModal(true);
		dlg.setVisible(true);
	}
	
	private void setDialogSize(JDialog dlg, Dimension dlgSize)
	{
		Rectangle r = getRootPaneContainer().getRootPane().getBounds();
		Dimension frmSize = new Dimension(r.width, r.height);

		if (dlgSize.height > frmSize.height) {
			dlgSize.height = frmSize.height;
		}

		if (dlgSize.width > frmSize.width) {
			dlgSize.width = frmSize.width;
		}
		dlg.setSize(dlgSize);
	}
	
	  
    @Override
    public void addOverlayProgressPanel(String type) {
        if (myRootPaneContainer instanceof TipiSwingDialog) {
            synchronized(this){
                if (overlayCounter == 0) {
                    TipiSwingDialog dia = (TipiSwingDialog) myRootPaneContainer;
                    dia.addGlass(type);
                }
                overlayCounter++;
            }
            
            
        }

    }

    @Override
    public void removeOverlayProgressPanel() {
        if (myRootPaneContainer instanceof TipiSwingDialog) {
            synchronized(this){
                overlayCounter--;
                if (overlayCounter < 1) {
                    TipiSwingDialog dialog = (TipiSwingDialog) myRootPaneContainer;
                    dialog.hideGlass();
                }
                overlayCounter = 0;
            }
          
        }

    }
    
    @Override
    public Container getOverlayContainer(){
        if (myRootPaneContainer instanceof TipiSwingDialog) {
            return (Container) myRootPaneContainer;
        }
        return null;
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
