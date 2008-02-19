package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.net.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.embed.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

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
public class TipiFrame extends TipiSwingDataComponentImpl {
	// private JFrame myFrame = null;
	private boolean fullscreen = false;
	private boolean visible = false;
	private int x = 0, y = 0, w = 0, h = 0;

	private RootPaneContainer myToplevel = null;
	private JPanel mySuperPanel = null;

	public TipiFrame() {
	}

	public Object createContainer() {
		System.err.println("CREATING FRAME OBJECT~~~");
		boolean internal = (getContext() instanceof EmbeddedContext) || ((SwingTipiContext) getContext()).getAppletRoot() != null;
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		if (internal) {
			TipiApplet ta = ((SwingTipiContext) getContext()).getAppletRoot();
			if(ta!=null) {
				myToplevel = ta;
				return ta;
			}
			RootPaneContainer or = ((SwingTipiContext) getContext()).getOtherRoot();
			myToplevel = or;
			return or;
//			if (getContext() instanceof EmbeddedContext) {
		//		EmbeddedContext ec = (EmbeddedContext) getContext();
	//			myToplevel = (RootPaneContainer) ec.getRootComponent();
//				return ec.getRootComponent();
	//		}
		} else {
			TipiSwingFrameImpl myFrame;
			myFrame = new TipiSwingFrameImpl(this);
			myToplevel = myFrame;
			mySuperPanel = new JPanel();
			myFrame.getContentPane().add(mySuperPanel ,BorderLayout.CENTER);
			mySuperPanel.setLayout(new BorderLayout());
			// myContext.setToplevel(myFrame);
			//mySuperPanel.addC
			return myFrame;
		}
	}

	public void addToContainer(final Object c, final Object constraints) {
		if(	this.getClass().getName().contains("Embed")) {
			
			return;
		}
		// final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
		if (JMenuBar.class.isInstance(c)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					if (myToplevel instanceof JFrame) {
						((JFrame) myToplevel).setJMenuBar((JMenuBar) c);
					}
					if (myToplevel instanceof JApplet) {
						((JApplet) myToplevel).setJMenuBar((JMenuBar) c);
					}
					if (myToplevel instanceof JInternalFrame) {
						((JInternalFrame) myToplevel).setJMenuBar((JMenuBar) c);
					}
				}
			});
		} else {
			runSyncInEventThread(new Runnable() {
				public void run() {
					// System.err.println("Constraints: "+constraints);
					// if (constraints!=null) {
					// System.err.println("constraints:
					// "+constraints.getClass());
					// }
					mySuperPanel.add((Component) c, constraints);
					//myToplevel.getContentPane().add((Component) c, constraints);
					// myFrame.getContentPane().dispatchEvent(new
					// ComponentEvent(myFrame.getContentPane(),ComponentEvent.COMPONENT_RESIZED));
				}
			});
		}
	}

	public void removeFromContainer(final Object c) {
		// final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
		runSyncInEventThread(new Runnable() {
			public void run() {
				System.err.println("Beware! not working well");
				myToplevel.getContentPane().remove((Component) c);
			}
		});
	}

	protected void setBounds(Rectangle r) {
		if (myToplevel instanceof JFrame) {
			((JFrame) myToplevel).setBounds(r);
		}
	}

	protected Rectangle getBounds() {
		// final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
		if (myToplevel instanceof JFrame) {
			return ((JFrame) myToplevel).getBounds();
		}
		return null;
	}

	protected void setIcon(ImageIcon ic) {
		if (ic == null) {
			return;
		}
		if (myToplevel instanceof JFrame) {
			((JFrame) myToplevel).setIconImage(ic.getImage());
		}
	}

	protected void setTitle(String s) {
		if (myToplevel instanceof JFrame) {
			((JFrame) myToplevel).setTitle(s);
		}
	}

	public void setContainerLayout(Object layout) {
		System.err.println("Beware! not working well");

//		myToplevel.getContentPane().setLayout((LayoutManager) layout);
		mySuperPanel.setLayout((LayoutManager) layout);
	}

	private ImageIcon getIcon(URL u) {
		return new ImageIcon(u);
	}

	public void setComponentValue(String name, Object object) {
		if (name.equals("fullscreen")) {
			fullscreen = ((Boolean) object).booleanValue();
		}
		if (name.equals("visible")) {
			visible = ((Boolean) object).booleanValue();
		}
		if ("icon".equals(name)) {
			if (object instanceof URL) {
				setIcon(getIcon((URL) object));
			} else {
				System.err.println("Warning setting icon of tipiframe:");
			}
		}
		if ("title".equals(name)) {
			this.setTitle((String) object);
		}
		if (name.equals("x")) {
			x = ((Integer) object).intValue();
		}
		if (name.equals("y")) {
			y = ((Integer) object).intValue();
		}
		if (name.equals("w")) {
			w = ((Integer) object).intValue();
		}
		if (name.equals("h")) {
			h = ((Integer) object).intValue();
		}
		if (name.equals("menubar")) {
			throw new UnsupportedOperationException("Dont use the menubar attribute. Just add a menubar to the component");
			// try {
			// if (object == null || object.equals("")) {
			// System.err.println("null menu bar. Not instantiating");
			// return;
			// }
			// myMenuBar = (String) object;
			// XMLElement instance = new CaseSensitiveXMLElement();
			// instance.setName("component-instance");
			// instance.setAttribute("name", (String) object);
			// instance.setAttribute("id", (String) object);
			// TipiComponent tm = this.addComponentInstance(myContext, instance,
			// null);
			// setJMenuBar( (JMenuBar) tm.getContainer());
			// }
			// catch (TipiException ex) {
			// ex.printStackTrace();
			// setJMenuBar(null);
			// myMenuBar = "";
			// }
		}
		super.setComponentValue(name, object);
	}

	public Object getComponentValue(String name) {
		if ("visible".equals(name)) {
			if (myToplevel instanceof JFrame) {
				return new Boolean(((JFrame) myToplevel).isVisible());
			}
			if (myToplevel instanceof JApplet) {
				return new Boolean(((JFrame) myToplevel).isVisible());
			}
		}

		if (name.equals("resizable")) {
			if (myToplevel instanceof JFrame) {
				return new Boolean(((JFrame) myToplevel).isResizable());
			}
		}

		if (name.equals("fullscreen")) {
			return new Boolean(JFrame.MAXIMIZED_BOTH == ((JFrame) myToplevel).getExtendedState());

			// new Boolean(JFrame.MAXIMIZED_BOTH == myFrame.getExtendedState());
		}

		if (name.equals("title")) {
			if (myToplevel instanceof JFrame) {
				return new Boolean(((JFrame) myToplevel).getTitle());
			}
			// return myFrame.getTitle();
		}
		Rectangle r = getBounds();
		if (r == null) {
			return super.getComponentValue(name);
		}
		if (name.equals("x")) {
			return new Integer(r.x);
		}
		if (name.equals("y")) {
			return new Integer(r.y);
		}
		if (name.equals("w")) {
			return new Integer(r.width);
		}
		if (name.equals("h")) {
			return new Integer(r.height);
		}

		// Watch out: Jump exit at getBounds
		return super.getComponentValue(name);
	}

	/**
	 * componentInstantiated
	 * 
	 * @todo Implement this com.dexels.navajo.tipi.TipiComponent method
	 */
	public void componentInstantiated() {
		if (getContainer() instanceof TipiSwingFrame) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					setBounds(new Rectangle(x, y, w, h));
					if (fullscreen) {
						((TipiSwingFrame) getSwingContainer()).setExtendedState(JFrame.MAXIMIZED_BOTH);
					}
					getSwingContainer().setVisible(visible);
				}
			});

		}
	}
}
