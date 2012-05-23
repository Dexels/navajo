package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.core.TipiMessageDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiLayout;
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
public abstract class TipiSwingMessageDataComponentImpl extends
		TipiMessageDataComponentImpl implements TipiSwingComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = -733008132063779783L;
	protected TipiPopupMenu myPopupMenu = null;
	protected TipiGradientPaint myPaint;
	private int currentPage = -1;

	protected SwingTipiContext mySwingTipiContext;

	public void initContainer() {
		mySwingTipiContext = (SwingTipiContext) myContext;
		if (getContainer() == null) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					setContainer(createContainer());
				}
			});
		}
	}

	public void setWaitCursor(final boolean b) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				Container cc = getSwingContainer();
				if (!(cc instanceof JComponent)) {
					return;
				}
				JComponent jj = (JComponent) cc;
				if (jj.getRootPane() != null) {
					jj.getRootPane().setCursor(
							b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
									: Cursor.getDefaultCursor());
				}
			}
		});

	}

	public void addToContainer(final Object c, final Object constraints) {
		try {
			runSyncInEventThread(new Runnable() {

				public void run() {
					getSwingContainer().add((Component) c, constraints);
				}
			});
		} catch (Throwable e) {
			throw new RuntimeException(
					"Illegal constraint while adding object: " + c
							+ " to component: " + getPath()
							+ " with constraint: " + constraints);
		}
	}

	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				getSwingContainer().remove((Component) c);
			}
		});
	}

	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				((Container) getContainer()).setLayout((LayoutManager) layout);
			}
		});
	}

	protected Object getComponentValue(String name) {
		if (name != null) {
			if (name.equals("currentPage")) {
				return new Integer(currentPage);
			}
		}
		return super.getComponentValue(name);
	}

	public void replaceLayout(TipiLayout tl) {
		super.replaceLayout(tl);
		((Container) getContainer()).repaint();
		if (JComponent.class.isInstance(getContainer())) {
			((JComponent) getContainer()).revalidate();
		}
	}

	public void showPopup(MouseEvent e) {
		((JPopupMenu) myPopupMenu.getSwingContainer()).show(
				getSwingContainer(), e.getX(), e.getY());
	}

	protected void doLayout() {
		if (getContainer() != null) {
			if (JComponent.class.isInstance(getContainer())) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						((JComponent) getContainer()).revalidate();
						((JComponent) getContainer()).repaint();
						getContext().debugLog("data    ",
								"Exiting doLayout in tipi: " + getId());
					}
				});
			}
		}
	}

	public Object getContainerLayout() {
		return getSwingContainer().getLayout();
	}

	public Container getSwingContainer() {
		return (Container) getContainer();
	}

	public void refreshLayout() {
		List<TipiComponent> elementList = new ArrayList<TipiComponent>();
		for (int i = 0; i < getChildCount(); i++) {
			TipiComponent current = getTipiComponent(i);
			if (current.isVisibleElement()) {
				removeFromContainer(current.getContainer());
			}
			elementList.add(current);
		}
		for (int i = 0; i < elementList.size(); i++) {
			final TipiComponent current = elementList.get(i);
			if (current.isVisibleElement()) {
				runSyncInEventThread(new Runnable() {
					public void run() {
						addToContainer(current.getContainer(),
								current.getConstraints());
					}
				});
			}
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

	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
	}

	public void setPaint(Paint p) {
		this.myPaint = (TipiGradientPaint) p;
	}

	public TipiGradientPaint getPaint() {
		return myPaint;
	}

	public void commitToUi() {
		super.commitToUi();
	}

	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this, te, executableParent,
				getSwingContainer(), exe, duration);
	}

	@Override
	public void loadData(final Navajo n, final String method)
			throws TipiException, TipiBreakException {
		if (getContainer() != null && getContainer() instanceof JComponent) {
			// SwingUtilities.invokeAndWait
			runSyncInEventThread(new Runnable() {

				public void run() {
					try {
						TipiSwingMessageDataComponentImpl.super.loadData(n,
								method);
					} catch (TipiBreakException e) {
						e.printStackTrace();
					} catch (Throwable e) {
						e.printStackTrace();
						myContext.showInternalError("Error loading component: "
								+ getPath() + " message: " + e.getMessage(), e);
					}

				}
			});

		} else {
			super.loadData(n, method);
		}

	}

	protected void loadValues(final XMLElement values, final TipiEvent event)
			throws TipiException {
		runSyncInEventThread(new Runnable() {

			public void run() {
				try {
					TipiSwingMessageDataComponentImpl.super.loadValues(values,
							event);
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void loadStartValues(final XMLElement element, final TipiEvent event) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				TipiSwingMessageDataComponentImpl.super.loadStartValues(
						element, event);

			}
		});
	}

	protected void doCallSetter(final Object component,
			final String propertyName, final Object param) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				TipiSwingMessageDataComponentImpl.super.doCallSetter(component,
						propertyName, param);
			}
		});
	}

}
