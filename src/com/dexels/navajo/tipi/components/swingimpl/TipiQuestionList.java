package com.dexels.navajo.tipi.components.swingimpl;

import com.dexels.navajo.tipi.components.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import java.util.*;
import com.dexels.navajo.tipi.internal.*;
import java.io.*;
import java.lang.reflect.*;
import java.awt.*;
import java.awt.print.*;

import javax.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class TipiQuestionList
    extends TipiBaseQuestionList {
//  private String messagePath = null;
//  private String questionDefinitionName = null;
//  private String questionGroupDefinitionName = null;
//  
  private static final String MODE_TABS = "tabs";
  private static final String MODE_PANEL = "panel";
  
  private String groupMode = MODE_PANEL;
  public TipiQuestionList() {
  }

  protected Object getGroupConstraints(Message groupMessage) {
      return null;
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
  
  
  
 }
