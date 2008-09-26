package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiMenuItem
    extends TipiSwingComponentImpl {
  private TipiSwingMenuItem myItem;

  private boolean iAmEnabled = true;


  public Object createContainer() {
    myItem = new TipiSwingMenuItem();
//    TipiHelper th = new TipiSwingHelper();
//    th.initHelper(this);
//    addHelper(th);
    myItem.addActionListener(new ActionListener(){

		public void actionPerformed(ActionEvent e) {
			try {
				
				setWaitCursor(true);
				
				performTipiEvent("onActionPerformed", null, false
				, new Runnable(){
					public void run() {
						setWaitCursor(false);
					}});
			} catch (TipiException e1) {
				e1.printStackTrace();
			}
			
		}});
    return myItem;
  }

  
  
	@Override
protected void addedToParent() {
		JMenuItem swingContainer = (JMenuItem)getSwingContainer();
		System.err.println("Toplevel:" +swingContainer.getTopLevelAncestor());
		super.addedToParent();
}



	public void setWaitCursor(final boolean b) {
		runSyncInEventThread(new Runnable() {

			public void run() {
				Container cc = getSwingContainer();
				if (!(cc instanceof JComponent)) {
					return;
				}
				JMenuItem jj = (JMenuItem) cc;
//				jj.getTopLevelAncestor()
				System.err.println("My toplevel: "+jj.getTopLevelAncestor());
				Thread.dumpStack();
				if (jj.getTopLevelAncestor() != null) {
					
					jj.getTopLevelAncestor().setCursor(b ? Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) : Cursor.getDefaultCursor());
				}
			}
		});

	}
  
  /**
   * Sort of legacy. Don't really know what to do with this one.
   */
   public void eventStarted(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
//          enabled = ( (Container) getContainer()).isEnabled();
          getSwingContainer().setEnabled(false);
        }
      });
    }
  }
  public void eventFinished(TipiExecutable te, Object event) {
    if (Container.class.isInstance(getContainer())) {
      runSyncInEventThread(new Runnable() {
        public void run() {
          ( (Container) getContainer()).setEnabled(iAmEnabled);
        }
      });
    }
  }
}
