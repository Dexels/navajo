/*
 * Created on Feb 17, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;

public class TipiScrollQuestionGroup extends TipiBaseQuestionGroup implements TipiSwingComponent {
    private JScrollPane jp;
    private JPanel jpanel;

    public Object createContainer() {
      jp = new JScrollPane();
      jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      jpanel = new TipiSwingPanel();
     
      jpanel.setLayout(new BorderLayout());
      jp.getViewport().add(jpanel);
      TipiHelper th = new TipiSwingHelper();
      th.initHelper(this);
      addHelper(th);
      return jp;
    }

    public void addToContainer(Object c, Object constraints) {
        jpanel.add((Component)c, constraints);
    }
    public void removeFromContainer(Object c) {
        jpanel.remove((Component)c);
    }

    public void setContainerLayout(Object layout) {
         jpanel.setLayout((LayoutManager)layout);
    }


	public void highLight(Component c, Graphics g) {
	
		
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
	            addToContainer(current.getContainer(), current.getConstraints());
	          }
	        });
	      }
	    }
	  }

	  public void runSyncInEventThread(Runnable r) {
	    if (SwingUtilities.isEventDispatchThread()) {
	      r.run();
	    }
	    else {
	      try {
	        SwingUtilities.invokeAndWait(r);
	      }
	      catch (InvocationTargetException ex) {
	        throw new RuntimeException(ex);
	      }
	      catch (InterruptedException ex) {
	      }
	    }
	  }

	  public void runASyncInEventThread(Runnable r) {
	    if (SwingUtilities.isEventDispatchThread()) {
	      r.run();
	    }
	    else {
	      SwingUtilities.invokeLater(r);
	    }
	  }
	
	
	
	public void setCursor(Cursor c) {
		// TODO Auto-generated method stub
		
	}

	public void setPaint(Paint p) {
		// TODO Auto-generated method stub
		
	}

	public void setWaitCursor(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void showPopup(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void animateTransition(TipiEvent te, TipiExecutable executableParent, List<TipiExecutable> exe) {
		// TODO Auto-generated method stub
		
	}

    
}
