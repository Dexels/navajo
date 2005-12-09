package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Color;
import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Row;
import nextapp.echo2.app.Window;
import nextapp.echo2.app.WindowPane;
import nextapp.echo2.app.event.WindowPaneEvent;
import nextapp.echo2.app.event.WindowPaneListener;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiDialog extends TipiEchoDataComponentImpl {
	private boolean disposed = false;

	private WindowPane myDialog = null;

	private boolean modal = false;

	private boolean decorated = true;

	private boolean showing = false;

	private String title = "";

	// private Rectangle myBounds = new Rectangle(0, 0, 0, 0);
	private boolean studioMode = false;

	public TipiDialog() {
	}

	public Object createContainer() {
		Component tp = new Row();
		TipiHelper th = new EchoTipiHelper();
		th.initHelper(this);
		tp.setBackground(new Color(200, 100, 100));
		addHelper(th);
		return tp;
	}

	// }
	private final void dialog_windowClosing(WindowPaneEvent e) {
		WindowPane d = (WindowPane) e.getSource();
		try {
			performTipiEvent("onWindowClosed", null, true);
		} catch (TipiException ex) {
			ex.printStackTrace();
		}
		myContext.disposeTipiComponent(this);
		disposed = true;
	}

	protected void createWindowListener(WindowPane d) {
		d.setDefaultCloseOperation(WindowPane.DO_NOTHING_ON_CLOSE);
		d.addWindowPaneListener(new WindowPaneListener() {
			public void windowPaneClosing(WindowPaneEvent arg0) {
				dialog_windowClosing(arg0);
			}
		});
	}

	// public void removeFromContainer(Object c) {
	// getSwingContainer().remove( (Component) c);
	// }
	public void setComponentValue(final String name, final Object object) {
		// runSyncInEventThread(new Runnable() {
		// public void run() {
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
		// if (name.equals("x")) {
		// myBounds.x = ( (Integer) object).intValue();
		// return;
		// }
		// if (name.equals("y")) {
		// myBounds.y = ( (Integer) object).intValue();
		// return;
		// }
		// if (name.equals("w")) {
		// myBounds.width = ( (Integer) object).intValue();
		// return;
		// }
		// if (name.equals("h")) {
		// myBounds.height = ( (Integer) object).intValue();
		// return;
		// }
		// }
		// });
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		/** @todo Override this com.dexels.navajo.tipi.impl.DefaultTipi method */
		if ("isShowing".equals(name)) {
			// return new Boolean( ( (JDialog) getContainer()).isVisible());
			return new Boolean(showing);
		}
		if ("title".equals(name)) {
			// return ( (JDialog) getContainer()).getTitle();
			return title;
		}
		// if (name.equals("x")) {
		// return new Integer(myBounds.x);
		// }
		// if (name.equals("y")) {
		// return new Integer(myBounds.y);
		// }
		// if (name.equals("w")) {
		// return new Integer(myBounds.width);
		// }
		// if (name.equals("h")) {
		// return new Integer(myBounds.height);
		// }
		return super.getComponentValue(name);
	}

	public void disposeComponent() {
		if (myDialog != null) {
			myDialog.setVisible(false);
		}
		super.disposeComponent();
	}

	private final void constructDialog() {
		// System.err.println("Constructing: studio? "+isStudioElement());
		if (myContext.isStudioMode() && !isStudioElement()) {
			//
			System.err.println("studio");
			studioMode = true;
		} else {
			constructStandardDialog();
			studioMode = false;
		}
	}

	private final void constructStandardDialog() {
		TipiScreen s = (TipiScreen) getContext().getDefaultTopLevel();
		final Window w = (Window) s.getTopLevel();
		myDialog = new WindowPane(title, new Extent(400, Extent.PX),
				new Extent(150, Extent.PX));
		// myDialog.setUndecorated(!decorated);
		createWindowListener(myDialog);
		myDialog.setTitle(title);
		// myDialog.toFront();
		// if (myBar != null) {
		// myDialog.setJMenuBar(myBar);
		// }

		w.getContent().add(myDialog);
		// myDialog.setModal(true);
		myDialog.add((Component) getContainer());

		myDialog.setModal(modal);
		// myDialog.setHeight(new Extent(300,Extent.PX));
		// myDialog.setWidth(new Extent(300,Extent.PX));
		// myDialog.setPositionX(new Extent(300,Extent.PX));
		// myDialog.setPositionY(new Extent(300,Extent.PX));
		myDialog.setVisible(true);
		System.err.println("Showin!");
		// myDialog.getContentPane().setLayout(new BorderLayout());
		// myDialog.getContentPane().add(getSwingContainer(),
		// BorderLayout.CENTER);
		// myDialog.pack();
		// if (myBounds != null) {
		// myDialog.setBounds(myBounds);
		// System.err.println("Setting bounds: "+myBounds);
		// } else {
		// System.err.println("Null bounds for dialog.");
		// }
		//
		// myDialog.setLocationRelativeTo( (Component) myContext.getTopLevel());
	}

	protected void helperRegisterEvent(TipiEvent te) {
		if (te != null && te.getEventName().equals("onWindowClosed")) {
			// overridden.. should be ok.
		} else {
			super.helperRegisterEvent(te);
		}
	}

	public void addToContainer(Object c, Object constraints) {
		System.err.println("Adding to dialog: " + c);
		super.addToContainer(c, constraints);
	}

	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		final TipiComponent me = this;
		final Thread currentThread = Thread.currentThread();
		// final boolean amIEventThread =
		// SwingUtilities.isEventDispatchThread();
		super.performComponentMethod(name, compMeth, event);
		if (name.equals("show")) {
			// runASyncInEventThread(new Runnable() {
			// public void run() {

			System.err.println("Component method found!");
			constructDialog();
			if (myDialog == null) {
			}
			if (myDialog != null) {
				// ( (TipiContext)
				// myContext).addTopLevel(myDialog.getContentPane()); (
				// (SwingTipiContext) myContext).dialogShowing(true);
				// ( (TipiContext) myContext).updateWaiting();
				// SwingClient.getUserInterface().addDialog(myDialog);
				if (myContext != null) {
					// ( (TipiContext) myContext).dialogShowing(false); if
					// (myDialog != null) {
					// ( (TipiContext)
					// myContext).removeTopLevel(myDialog.getContentPane()); (
					// (SwingTipiContext) myContext).updateWaiting();
				} else {
					System.err.println("Null DIALOG, in TipiDialog.");
				}
			} else {
				System.err.println("Null CONTEXT, in TipiDialog.");
			}
		}
		// }
		// });
		if (name.equals("hide")) {
			System.err.println("Hiding dialog!!!\n\n\n\n");
			if (myDialog != null) {
				myDialog.setVisible(false);
			}
		}
		if (name.equals("dispose")) {
			// System.err.println("Hide dialog: Disposing dialog!");
			if (myDialog != null) {
				myDialog.setVisible(false);
				myDialog = null;
			}
			myContext.disposeTipiComponent(me);
			disposed = true;
		}
	}

	// public void setContainerVisible(boolean b) {
	// }
	public void reUse() {
	}
}
