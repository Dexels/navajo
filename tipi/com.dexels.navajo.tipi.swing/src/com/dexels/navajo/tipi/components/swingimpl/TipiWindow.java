package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.beans.PropertyVetoException;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingDesktop;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingWindow;
import com.dexels.navajo.tipi.internal.TipiEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

/**
 * @todo Need to refactor menus in internalframes. Now still uses the old mode
 *       Frank
 */
public final class TipiWindow
// extends DefaultTipi {
		extends TipiSwingDataComponentImpl{

	private static final long serialVersionUID = -4916285139918344888L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiWindow.class);
	
	private JInternalFrame myWindow;

	private JInternalFrame constructWindow() {
		// isDisposing = false;
		clearContainer();
		myWindow = new TipiSwingWindow();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		myWindow.setResizable(true);
		myWindow.setSize(100, 40);
		// JLabel label = new JLabel("Monkey");
		// myWindow.getLayeredPane().add(label, 40000);
		// label.setBounds(10,10,50,20);
		myWindow.setVisible(true);
		myWindow.addInternalFrameListener(new InternalFrameAdapter() {

			@Override
			public void internalFrameDeiconified(InternalFrameEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							performTipiEvent("onDeminimize", null, true);
						} catch (TipiException e1) {
							logger.error("Error detected",e1);
						} catch (TipiBreakException e2) {
							logger.debug("Breakie breakie", e2);
						}

					}
				});
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							performTipiEvent("onMinimize", null, true);
						} catch (TipiException e1) {
							logger.error("Error detected",e1);
						} catch (TipiBreakException e2) {
							logger.debug("Breakie breakie", e2);
						}

					}
				});
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent e) {
				if (myWindow != null) {
					// will re-enter this event, so its a bit defensive
					JInternalFrame w = myWindow;
					myWindow = null;
					w.dispose();
					myContext.disposeTipiComponent(TipiWindow.this);
				}
			}

			@Override
			public void internalFrameClosing(final InternalFrameEvent e) {
//				Component cc = ((JInternalFrame) e.getSource()).getFocusOwner();
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							performTipiEvent("onWindowClosed", null, true);
							((JInternalFrame) e.getSource()).dispose();
						} catch (TipiException e1) {
							((JInternalFrame) e.getSource()).dispose();
							logger.error("Error detected",e1);
						} catch (TipiBreakException e2) {
							logger.debug("Breakie breakie", e2);
							if (e2.getType() == TipiBreakException.COMPONENT_DISPOSED) {
								// a component disposed event should still close
								// the window
								((JInternalFrame) e.getSource()).dispose();
							}
						}

					}
				});

			}

		});

		return myWindow;
	}

	@Override
	public Object createContainer() {
		return constructWindow();
	}

	@Override
	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this, te, executableParent,
				myWindow.getContentPane(), exe, duration);
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				JInternalFrame internalFrame = ((JInternalFrame) getContainer());
				internalFrame.getContentPane().add((Component) c, constraints);
			}
		});
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					((JInternalFrame) getContainer()).setSelected(true);
					((JInternalFrame) getContainer()).requestFocus();
				} catch (PropertyVetoException e) {
					logger.error("Error detected",e);
				}
			}
		});
	}

	@Override
	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((JInternalFrame) getContainer()).getContentPane().remove(
						(Component) c);
				((JInternalFrame) getContainer()).repaint();
			}
		});
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				checkContainerInstance();
				((JInternalFrame) getContainer()).getContentPane().setLayout(
						(LayoutManager) layout);
			}
		});
	}

	@Override
	public Container getSwingContainer() {
		checkContainerInstance();
		return super.getSwingContainer();
	}

	private void checkContainerInstance() {
		if (getContainer() == null) {
			setContainer(constructWindow());
		}
	}

	private final void doPerformMethod(String name, TipiComponentMethod compMeth) {

		if (name.equals("iconify")) {
			checkContainerInstance();

			try {
				// myWindow.setIcon(true);
				JInternalFrame jj = (JInternalFrame) getContainer();
				TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
				jj.setIcon(true);
				if (tt != null && tt.getDesktopManager() != null)
				{
					tt.getDesktopManager().iconifyFrame(jj);
				}
			} catch (Exception ex) {
				logger.error("Error detected",ex);
			}
		}
		if (name.equals("maximize")) {
			checkContainerInstance();

			JInternalFrame jj = (JInternalFrame) getContainer();
			TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
			if (!jj.isMaximum())
			{
				try {
					jj.setMaximum(true);
					if (tt != null && tt.getDesktopManager() != null)
					{
						tt.getDesktopManager().maximizeFrame(jj);
					}
				} catch (Error ex1) {
					logger.error("Error detected",ex1);
				} catch (Exception ex1) {
					logger.error("Error detected",ex1);
				}
			}
			if (jj.isIcon())
			{
				try {
					jj.setIcon(false);
					if (tt != null && tt.getDesktopManager() != null)
					{
						tt.getDesktopManager().deiconifyFrame(jj);
					}
				} catch (Error ex1) {
					logger.error("Error detected",ex1);
				} catch (Exception ex1) {
					logger.error("Error detected",ex1);
				}
			}
		}
		if (name.equals("restore")) {
			checkContainerInstance();
			JInternalFrame jj = (JInternalFrame) getContainer();
			TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
			if (jj.isMaximum())
			{
				tt.getDesktopManager().minimizeFrame(jj);
				try {
					jj.setMaximum(false);
					// // This might give an exception.. don't worry.. can't help
					// it.
				} catch (PropertyVetoException ex1) {
					logger.error("Error detected",ex1);
				}
			}
			if (jj.isIcon())
			{
				try {
					jj.setIcon(false);
					if (tt != null && tt.getDesktopManager() != null)
					{
						tt.getDesktopManager().deiconifyFrame(jj);
					}
				} catch (Error ex1) {
					logger.error("Error detected",ex1);
				} catch (Exception ex1) {
					logger.error("Error detected",ex1);
				}
			}
		}
		if (name.equals("toFront")) {
			checkContainerInstance();

			JInternalFrame jj = (JInternalFrame) getContainer();
			TipiSwingDesktop tt = (TipiSwingDesktop) jj.getParent();
			if (tt != null) {
				jj.toFront();
			}
			if (jj.isIcon())
			{
				try {
					jj.setIcon(false);
					if (tt != null && tt.getDesktopManager() != null)
					{
						tt.getDesktopManager().deiconifyFrame(jj);
					}
				} catch (Error ex1) {
					logger.error("Error detected",ex1);
				} catch (Exception ex1) {
					logger.error("Error detected",ex1);
				}
			}
			if (tt != null && tt.getDesktopManager() != null)
			{
				tt.getDesktopManager().activateFrame(jj);
			}
		}
	}

	@Override
	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				doPerformMethod(name, compMeth);
			}
		});
	}

	@Override
	public boolean isReusable() {
		return true;
	}

	@Override
	public void disposeComponent() {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				JInternalFrame jj = (JInternalFrame) getContainer();
				if (jj != null) {
					jj.dispose();
					Container parent = jj.getParent();
					if (parent != null) {
						parent.remove(jj);
					}
				}

				clearContainer();
				myWindow = null;
			}
		});
		TipiWindow.super.disposeComponent();

	}

	@Override
	public void reUse() {
		// if (myParent!=null) {
		// myParent.addToContainer();
		// }
		// myWindow.setVisible(true);
	}
}
