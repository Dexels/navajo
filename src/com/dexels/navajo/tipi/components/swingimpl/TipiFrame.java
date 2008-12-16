package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.beans.*;
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
		boolean internal = (getContext() instanceof SwingEmbeddedContext) || ((SwingTipiContext) getContext()).getAppletRoot() != null;
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		
		addHelper(th);
		if (internal) {
			TipiApplet ta = ((SwingTipiContext) getContext()).getAppletRoot();
			if (ta != null) {
				myToplevel = ta;
				ta.getContentPane().setLayout(new BorderLayout());
				mySuperPanel = new JPanel();
				mySuperPanel.setLayout(new BorderLayout());
//				mySuperPanel.addPropertyChangeListener(new PropertyChangeListener(){
//					public void propertyChange(PropertyChangeEvent evt) {
//						System.err.println("Changed: "+evt.getPropertyName()+" old: "+evt.getOldValue()+" new: "+evt.getNewValue());
//					}});
				ta.getContentPane().add(mySuperPanel, BorderLayout.CENTER);
				mySuperPanel.setOpaque(false);
				return ta;
			}

			RootPaneContainer or = ((SwingTipiContext) getContext()).getOtherRoot();
			myToplevel = or;
			mySuperPanel = new JPanel();
			mySuperPanel.setLayout(new BorderLayout());
			or.getContentPane().add(mySuperPanel, BorderLayout.CENTER);
			return or;
			// if (getContext() instanceof EmbeddedContext) {
			// EmbeddedContext ec = (EmbeddedContext) getContext();
			// myToplevel = (RootPaneContainer) ec.getRootComponent();
			// return ec.getRootComponent();
			// }
		} else {
			TipiSwingFrameImpl myFrame;
			myFrame = new TipiSwingFrameImpl(this);
			myToplevel = myFrame;
			mySuperPanel = new JPanel();
			myFrame.setExtendedState(myFrame.getExtendedState() | JFrame.MAXIMIZED_HORIZ);
			myFrame.getContentPane().add(mySuperPanel, BorderLayout.CENTER);
			mySuperPanel.setLayout(new BorderLayout());
			((SwingTipiContext) myContext).addTopLevel(myFrame);
			// mySuperPanel.addC
			return myFrame;
		}
	}

	public void addToContainer(final Object c, final Object constraints) {
		if (this.getClass().getName().contains("Embed")) {

			return;
		}
		// final TipiSwingFrame myFrame = (TipiSwingFrame) getContainer();
		if (JMenuBar.class.isInstance(c)) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					if (myToplevel instanceof JFrame) {
						((JFrame) myToplevel).getRootPane().setJMenuBar((JMenuBar) c);
						((JFrame) myToplevel).repaint();
						((JMenuBar) c).revalidate();
					}
					if (myToplevel instanceof JApplet) {
						((JApplet) myToplevel).getRootPane().setJMenuBar((JMenuBar) c);
					}
					if (myToplevel instanceof JInternalFrame) {
						((JInternalFrame) myToplevel).getRootPane().setJMenuBar((JMenuBar) c);
					}
					
				}
			});
		} else {
			runSyncInEventThread(new Runnable() {
				public void run() {

					runSyncInEventThread(new Runnable() {
						public void run() {
							mySuperPanel.add((Component) c, constraints);
//							mySuperPanel.doLayout();
							System.err.println("Size: "+mySuperPanel.getLayout());
							
						}
					});

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
		mySuperPanel.setLayout((LayoutManager) layout);
	}

	private ImageIcon getIcon(URL u) {
		return new ImageIcon(u);
	}

	public void setComponentValue(final String name, final Object object) {

		runSyncInEventThread(new Runnable() {

			public void run() {
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
					TipiFrame.this.setTitle((String) object);
				}

				if ("background".equals(name)) {
					setBackground((Color) object);
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
			}
		});

		super.setComponentValue(name, object);
	}

	
	
	protected void setBackground(Color object) {
		if(myToplevel instanceof JFrame) {
			((JFrame) myToplevel).setBackground(object);
			return;
		}
		if(myToplevel instanceof JApplet) {
			((JApplet) myToplevel).setBackground(object);
			// not pretty. eat me.
			((JApplet) myToplevel).getContentPane().setBackground(object);
			return;
		}
		if(myToplevel instanceof Container) {
			((Container) myToplevel).setBackground(object);
		}
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

	public final void performTipiMethod(final String name, TipiComponentMethod compMeth) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				if (name.equals("iconify")) {
					if (getContainer() instanceof TipiSwingFrameImpl) {
						try {
							TipiSwingFrameImpl jj = (TipiSwingFrameImpl) getContainer();
							jj.setExtendedState(jj.getExtendedState() | JFrame.ICONIFIED);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				if (name.equals("maximize")) {
					if (getContainer() instanceof TipiSwingFrameImpl) {
						try {
							TipiSwingFrameImpl jj = (TipiSwingFrameImpl) getContainer();
							jj.setExtendedState(jj.getExtendedState() | JFrame.MAXIMIZED_BOTH);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
				if (name.equals("restore")) {
					// if(getContainer() instanceof TipiSwingFrameImpl) {
					// try {
					// TipiSwingFrameImpl jj = (TipiSwingFrameImpl)
					// getContainer();
					//jj.setExtendedState(jj.getExtendedState()|JFrame.ICONIFIED
					// );
					// } catch (Exception ex) {
					// ex.printStackTrace();
					// }
					// }
				}
			}
		});

	}

	public static void main(String[] args) {
		JFrame j = new JFrame("aap");

		// j.setSize(200,100);
		j.setExtendedState(j.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		j.setVisible(true);
	}
}
