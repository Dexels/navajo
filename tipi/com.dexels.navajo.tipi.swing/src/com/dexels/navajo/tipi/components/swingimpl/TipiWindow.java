/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
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
import com.dexels.navajo.tipi.components.core.TipiSupportOverlayPane;
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
public final class TipiWindow extends TipiSwingDataComponentImpl implements TipiSupportOverlayPane {

	private static final long serialVersionUID = -4916285139918344888L;
	private final static Logger logger = LoggerFactory.getLogger(TipiWindow.class);
	
	private JInternalFrame myWindow;
	private boolean hideOnClose = true;
    private boolean isHidden = false;
    private int overlayCounter = 0;
    
	private JInternalFrame constructWindow() {
		clearContainer();
		myWindow = new TipiSwingWindow();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		myWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		myWindow.setResizable(true);
		myWindow.setSize(100, 40);
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
			    // Nothing to do anymore - we are already disposed!
			}

			@Override
			public void internalFrameClosing(final InternalFrameEvent e) {
			    // On closing the window, request the focus to myWindow. This should trigger 
			    // any onFocusLost events for active inputs which might trigger other events or isDirty states
                myWindow.requestFocusInWindow();
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						try {
							performTipiEvent("onWindowClosed", null, true);
							hideComponent();
						} catch (Exception e1) {
							logger.error("Error detected",e1);
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
	
	@Override
    protected void setComponentValue(String name, Object object) {
        if (name.equals("hideOnClose")) {
           this.hideOnClose = (Boolean) object;
           return;
        }
        super.setComponentValue(name, object);
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
    protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
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
	    TipiWindow.super.disposeComponent();
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
		

	}
	
	
	   
    public boolean isHideOnClose() {
        return hideOnClose;
    }

    public void setHideOnClose(boolean hideOnClose) {
        this.hideOnClose = hideOnClose;
    }

    @Override
    public synchronized void hideComponent() {
        if (myWindow == null || isHidden) {
            // already hidden/disposed
            return;
        }
        if (hideOnClose) {
            this.getSwingContainer().setVisible(false);
            isHidden = true;
        } else {
            myContext.disposeTipiComponent(this);
        }
    }
    
    
    @Override
    public void unhideComponent() {

        super.unhideComponent();
        if (hideOnClose) {
            isHidden = false;
            addOverlayProgressPanel("opaque");

            SwingUtilities.invokeLater(new Runnable() {

                @Override
                public void run() {
                    getSwingContainer().setVisible(true);
                }
            });
                    
  
        }

    }

    @Override
    public void postOnInstantiate() {
        if (hideOnClose) {
            removeOverlayProgressPanel();
        }
    }
    
    @Override 
    public void addOverlayProgressPanel(String type) {
        synchronized(this){
            if (overlayCounter == 0) {
                ((TipiSwingWindow) myWindow).addGlass(type);
            }
            overlayCounter++;
        }
    } 
    
    @Override
    public void removeOverlayProgressPanel() {
        synchronized(this){
            overlayCounter--;
            if (overlayCounter < 1) {
                ((TipiSwingWindow) myWindow).hideGlass();
                overlayCounter = 0;
            }
           
        }
    }
    
    @Override
    public Container getOverlayContainer(){
        return myWindow;
    }
    

	@Override
	public void reUse() {
		// if (myParent!=null) {
		// myParent.addToContainer();
		// }
		// myWindow.setVisible(true);
	}
	
    @Override
    public boolean isHidden() {
        return isHidden;
    }
}
