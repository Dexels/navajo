package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.LayoutManager;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JRootPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.core.TipiComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.parsers.TipiGradientPaint;
import com.dexels.navajo.tipi.internal.TipiEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;


public abstract class TipiSwingComponentImpl extends TipiComponentImpl
		implements TipiSwingComponent {

	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingComponentImpl.class);
	private static final long serialVersionUID = -8220100898776017676L;
	protected TipiGradientPaint myPaint;
	protected TipiPopupMenu myPopupMenu = null;
	// private boolean committedInUI;
	protected SwingTipiContext mySwingTipiContext;

	@Override
	public void showPopup(MouseEvent e) {
		((JPopupMenu) myPopupMenu.getSwingContainer()).show(
				getSwingContainer(), e.getX(), e.getY());
	}

	public void setWaitCursor(final boolean b, final JRootPane root) {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				if (root != null) {
					root.setCursor(b ? Cursor
							.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor
							.getDefaultCursor());
				}
			}
		});

	}

	@Override
	public void setPaint(Paint p) {
		this.myPaint = (TipiGradientPaint) p;
	}

	public TipiGradientPaint getPaint() {
		return myPaint;
	}

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

	@Override
	public Container getSwingContainer() {
		return (Container) getContainer();
	}

	@Override
	public Object getContainerLayout() {
		return getSwingContainer().getLayout();
	}

	@Override
	public void setContainerLayout(Object layout) {
		((Container) getContainer()).setLayout((LayoutManager) layout);
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				getSwingContainer().add((Component) c, constraints);
			}
		});
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
	public void animateTransition(TipiEvent te,
			TipiExecutable executableParent, List<TipiExecutable> exe,
			int duration) throws TipiBreakException {
		mySwingTipiContext.animateDefaultTransition(this, te, executableParent,
				getSwingContainer(), exe, duration);
	}

	@Override
	protected void loadValues(final XMLElement values, final TipiEvent event)
			throws TipiException {
		runSyncInEventThread(new Runnable() {

			@Override
			public void run() {
				try {
					TipiSwingComponentImpl.super.loadValues(values, event);
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
				TipiSwingComponentImpl.super.loadStartValues(element, event);

			}
		});
	}

	@Override
	protected void doCallSetter(final Object component,
			final String propertyName, final Object param) {
		runSyncInEventThread(new Runnable() {
			@Override
			public void run() {
				TipiSwingComponentImpl.super.doCallSetter(component,
						propertyName, param);
			}
		});
	}

}
