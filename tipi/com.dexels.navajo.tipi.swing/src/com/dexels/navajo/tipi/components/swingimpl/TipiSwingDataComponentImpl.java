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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.internal.TipiLayout;
import com.dexels.navajo.tipi.tipixml.XMLElement;


public abstract class TipiSwingDataComponentImpl extends TipiDataComponentImpl
		implements TipiSwingComponent {

	private static final long serialVersionUID = 501099327239149734L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingDataComponentImpl.class);
	protected TipiPopupMenu myPopupMenu = null;
	protected TipiGradientPaint myPaint;
	private int currentPage = -1;

	protected SwingTipiContext mySwingTipiContext;

	@Override
	public void initContainer() {
		mySwingTipiContext = (SwingTipiContext) myContext;
		if (getContainer() == null) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					setContainer(createContainer());
				}
			});
		}
	}

	public void setWaitCursor(final boolean b) {
		runSyncInEventThread(new Runnable() {

			@Override
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

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		try {
			runSyncInEventThread(new Runnable() {

				@Override
				public void run() {
					getSwingContainer().add((Component) c, constraints);
					// logger.debug("Added to container: "+Thread.currentThread().getName());
					// logger.debug("Adding component: "+c+" to: "+getSwingContainer()+" with constraint: "+constraints);
					// TODO ADDED 7/7/2009 I don't know about the performance
					// hit.
					((JComponent) getSwingContainer()).revalidate();
					// getSwingContainer().reva();
				}
			});
		} catch (Throwable e) {
			throw new RuntimeException(
					"Illegal constraint while adding object: " + c
							+ " to component: " + getPath()
							+ " with constraint: " + constraints);
		}
	}

	@Override
	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				getSwingContainer().remove((Component) c);
			}
		});
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				((Container) getContainer()).setLayout((LayoutManager) layout);
			}
		});
	}

	@Override
	protected Object getComponentValue(String name) {
		if (name != null) {
			if (name.equals("currentPage")) {
				return new Integer(currentPage);
			}
		}
		return super.getComponentValue(name);
	}

	@Override
	public void replaceLayout(TipiLayout tl) {
		super.replaceLayout(tl);
		((Container) getContainer()).repaint();
		if (JComponent.class.isInstance(getContainer())) {
			((JComponent) getContainer()).revalidate();
		}
	}

	@Override
	public void showPopup(MouseEvent e) {
		((JPopupMenu) myPopupMenu.getSwingContainer()).show(
				getSwingContainer(), e.getX(), e.getY());
	}

	@Override
	protected void doLayout() {
		if (getContainer() != null) {
			if (JComponent.class.isInstance(getContainer())) {
				runSyncInEventThread(new Runnable() {
					@Override
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

	@Override
	public Object getContainerLayout() {
		return getSwingContainer().getLayout();
	}

	@Override
	public Container getSwingContainer() {
		return (Container) getContainer();
	}

	@Override
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
					@Override
					public void run() {
						addToContainer(current.getContainer(),
								current.getConstraints());
					}
				});
			}
		}
	}

	@Override
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

	@Override
	public void runAsyncInEventThread(Runnable r) {
		if (SwingUtilities.isEventDispatchThread()) {
			r.run();
		} else {
			SwingUtilities.invokeLater(r);
		}
	}

	@Override
	protected void performComponentMethod(final String name,
			final TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
	}

	@Override
	public void setPaint(Paint p) {
		this.myPaint = (TipiGradientPaint) p;
	}

	public TipiGradientPaint getPaint() {
		return myPaint;
	}

	@Override
	public void commitToUi() {
		super.commitToUi();
	}

	@Override
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

				@Override
				public void run() {
					try {
						TipiSwingDataComponentImpl.super.loadData(n, method);
					} catch (TipiBreakException e) {
						logger.debug("Error detected",e);
					} catch (Throwable e) {
						logger.error("Error detected",e);
						myContext.showInternalError("Error loading component: "
								+ getPath() + " message: " + e.getMessage(), e);
					}

				}
			});

		} else {
			super.loadData(n, method);
		}

	}

	@Override
	protected void loadValues(final XMLElement values, final TipiEvent event)
			throws TipiException {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				try {
					TipiSwingDataComponentImpl.super.loadValues(values, event);
				} catch (TipiException e) {
					logger.error("Error detected",e);
				}
			}
		});
	}

	@Override
	public void loadStartValues(final XMLElement element, final TipiEvent event) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				TipiSwingDataComponentImpl.super
						.loadStartValues(element, event);

			}
		});
	}

	@Override
	protected void doCallSetter(final Object component,
			final String propertyName, final Object param) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				TipiSwingDataComponentImpl.super.doCallSetter(component,
						propertyName, param);
			}
		});
	}

	@Override
	protected void setComponentValue(final String name, final Object object) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				TipiSwingDataComponentImpl.super
						.setComponentValue(name, object);
			}
		});
	}

	// protected void addedToParent() {
	// // logger.debug("Boioioiooiinggggg!");
	// if(getContainer() instanceof JComponent) {
	// runAsyncInEventThread(new Runnable() {
	// public void run() {
	// Component p = getSwingContainer().getParent();
	// if(p instanceof JComponent) {
	// logger.debug("RAAAAAHHHH!!!");
	// JComponent jc = (JComponent)p;
	// jc.revalidate();
	// }
	// }
	// });
	// }
	// // ( (TipiSwingDesktop) getContainer()).paintImmediately(0, 0, 100, 100);
	// super.addedToParent();
	// }

}
