package com.dexels.navajo.tipi.components.swingimpl;


import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.document.*;

import com.dexels.navajo.tipi.actions.*;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiQuestionGroup extends TipiBaseQuestionGroup {

    public Object createContainer() {
        JPanel j = new JPanel();
        j.setLayout(new BorderLayout());
        return j;
    }

     public void runSyncInEventThread(Runnable r) {
        if (SwingUtilities.isEventDispatchThread() ) {
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
            System.err.println("Interrupted");
          }
        }
      }

    public void addToContainer(final Object c, final Object constraints) {
        System.err.println("Adding to TipiTabbedQuestionList container:   "+c+" constraints: "+constraints);
        runSyncInEventThread(new Runnable(){
            public void run() {
                ((Container)getContainer()).add((Component)c, BorderLayout.CENTER);
            }});
       }
    
}
