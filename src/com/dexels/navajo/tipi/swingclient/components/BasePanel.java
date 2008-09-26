package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BasePanel
    extends JPanel
      {

  private Message loadMessage = null;
  private Message initMessage = null;
  private Message rootMessage = null;
  protected BasePanel hostingPanel = null;
  protected StandardWindow myWindow = null;
  private String panelTitle = "no name";

  private int panelState = NO_STATE;

  public static final int NO_STATE = -1;
  public static final int INITIALIZED = 1;
  public static final int LOADED = 2;
  public static final int UPDATED = 3;
  public static final int INSERTED = 4;
  public static final int DELETED = 5;
  private boolean hasExceptions = false;
  public static final int FOCUS_REQUEST = 0;
  public static final int FOCUS_GAINED = 1;
  public static final int FOCUS_LOST = 2;
  private int focusState = FOCUS_LOST;
//
//  private static int busyPanelCount = 0;
//  private static Object busyPanelSemaphore = new Object();
 // private Paint myPaint = SystemColor.getColor("control");

  protected void init(Message msg) {
    initMessage = msg;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
         ( (BasePanel) c).init(msg);
      }
    }
  }


  protected void load(Message msg) {
    loadMessage = msg;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
//        System.err.println("Loading BasePanel");
         ( (BasePanel) c).load(msg);
      }
    }
  }

  public void focusUpdate() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
         ( (BasePanel) c).focusChange();
      }
    }
    focusChange();
  }

  private final void focusChange() {
    switch (focusState) {
      case FOCUS_REQUEST:
        setFocusState(FOCUS_GAINED);
        System.err.println("request -> gained");
        setBorder(new LineBorder(Color.white, 2));
        break;
      case FOCUS_LOST:
        setFocusState(FOCUS_LOST);
        setBorder(null);
        break;
      case FOCUS_GAINED:
        setFocusState(FOCUS_LOST);
        setBorder(null);
        break;
    }
  }

  private final void setFocusState(int state) {
    focusState = state;
  }



  public void load() {
	
		  if (loadMessage != null) {
			  load(loadMessage);
		  }
		  else {
			  //System.err.println("Warning: Attempting to load without loadMessage");
		  }
  }

  public void store() {
    if (loadMessage != null) {
      Navajo n = store(loadMessage);
      if (n != null) {
        Message exception = n.getMessage("error");
        if (exception != null) {
          hasExceptions = true;
        }
      }
    }
    else {
      //System.err.println("Error: Attempting to store without loadMessage");
    }
  }

  public void init() {

	  if ( SwingUtilities.isEventDispatchThread() ) {
		  if (initMessage != null) {
			  init(initMessage);
		  }
	  } else {
		  try {
			  SwingUtilities.invokeAndWait(new Runnable(){

				  public void run() {
					  if (initMessage != null) {
						  init(initMessage);
					  }
					  else {
						  //System.err.println("Warning: Attempting to init without initMessage");
					  }
				  }
			  }
			  );
		  } catch (InterruptedException e) {
			  e.printStackTrace();
		  } catch (InvocationTargetException e) {
			  e.printStackTrace();
		  }
	  }
  }

  public void insert() {
    if (initMessage != null) {
      Navajo n = insert(initMessage);
      Message exception = n.getMessage("error");
        if (exception != null) {
        hasExceptions = true;
      }
    }
    else {
      //System.err.println("Error: Attempting to insert without initMessage");
    }
  }

  public void delete() {
    if (loadMessage != null) {
      Navajo n = delete(loadMessage);
      Message exception = n.getMessage("error");
        if (exception != null) {
        hasExceptions = true;
      }
    }
    else {
      //System.err.println("Error: Attempting to delete without loadMessage");
    }
  }

  public void initializePanel(Message init, Message load) {
    setInitMessage(init);
    init();
    setLoadMessage(load);
    load();
  }

  public void initializePanel(Message root, Message init, Message load) {
    setRootMessage(root);
    initializePanel(init, load);
  }

  public int getPanelState() {
    return panelState;
  }

  private final void setPanelState(int newState) {
    panelState = newState;
    revalidate();
//    setStateRecursive(newState);
  }

  public void setRootMessage(Message msg) {
    if (rootMessage != null) {
      //System.err.println("WARNING: Resetting root message");
    }
    rootMessage = msg;
  }

  public Message getRootMessage() {
    return rootMessage;
  }

  public void setLoadMessage(Message msg) {
//    switch (getPanelState()) {
//      case NO_STATE:
//        System.err.println("WARNING: Loading panel without initializing first");
//        break;
//      case INSERTED:
//        System.err.println("WARNING: Loading newly inserted panel");
//        break;
//      case LOADED:
//        System.err.println("Reloading panel");
//        break;
//      case UPDATED:
//        System.err.println("Reverting updated panel");
//        break;
//    }
    setPanelState(LOADED);
    loadMessage = msg;
//    load(loadMessage);
  }

  public void setInitMessage(Message msg) {
    /*switch (getPanelState()) {
      case INITIALIZED:
        System.err.println("Reinitializing panel");
        break;
      case INSERTED:
        System.err.println("OK: Initializing newly inserted panel");
        break;
      case LOADED:
        System.err.println("Reinitializing loaded panel");
        break;
      case UPDATED:
        System.err.println("Reinitializing updated panel, discarding changes");
        break;
    }*/
    setPanelState(INITIALIZED);
    initMessage = msg;
//    init(initMessage);
  }

  public Message getLoadMessage() {
    return loadMessage;
  }

  public Message getInitMessage() {
    return initMessage;
  }

  protected Navajo insert(Message msg) {
    System.err.println("Insert called in BasePanel. Not implemented. " + getClass().getName());
    return null;
  }

  protected Navajo store(Message msg) {
    System.err.println("this: " + this.getClass());
    System.err.println("Store called in BasePanel. Not implemented.");
    return null;
  }

  protected Navajo delete(Message msg) {
    System.err.println("Delete called in BasePanel. Not implemented.");
    return null;
  }

  public void setStateRecursive(int state) {
    panelState = state;
    for (int i = 0; i < getComponentCount(); i++) {
      Component current = getComponent(i);
      if (BasePanel.class.isInstance(current)) {
         ( (BasePanel) current).setStateRecursive(state);
      }
    }
  }

  public void setStateDeleted() {
    switch (panelState) {
      case LOADED:
        System.err.println("Deleting loaded panel");
        setPanelState(DELETED);
        break;
      case UPDATED:
        System.err.println("Deleting updated panel");
        setPanelState(DELETED);
        break;
      case INSERTED:
        System.err.println("Deleting inserted panel. No action.");
        setPanelState(INITIALIZED);
        break;
      case INITIALIZED:
      case NO_STATE:
        System.err.println("Ignoring delete for current panel.");
        break;
    }
  }

  public void setStateInserted() {

    switch (panelState) {
      case INSERTED:
        System.err.println("Setting inserted panel to inserted. Ignoring.");
        break;
      case INITIALIZED:
        setPanelState(INSERTED);
        break;
      default:
        System.err.println("Attempting to set to inserted from illegal state. Ignoring. State: " + getPanelState());
//        panelState = UPDATED;
    }
  }

  public void setStateInitialized() {
    setPanelState(INITIALIZED);
  }

  public void setStateUpdated() {
    switch (panelState) {
      case LOADED:
        setPanelState(UPDATED);
        break;
      case INSERTED:
        System.err.println("Setting inserted panel to updated. Ignoring.");
        break;
      case UPDATED:
        break;
      default:
        System.err.println("Attempting to set to updated from illegal state. Ignoring");
//        panelState = UPDATED;
    }
  }

  public void commit() {
    hasExceptions = false;
    switch (panelState) {
      case INITIALIZED:
        System.err.println("Store: Discarding initialized panel");
        break;
      case INSERTED:
        insert();
        break;
      case LOADED:
        System.err.println("Ignoring unchanged panel");
        break;
      case NO_STATE:
        System.err.println("Warning: Updating panel without state");
        break;
      case UPDATED:
        store();
        break;
      case DELETED:
        delete();
        break;
    }
    ArrayList<Component> al = getAllPanels();
    if (al.size() > 0) {
//      System.err.println("End of commit panel. Now committing subpanels.");
      for (int i = 0; i < al.size(); i++) {
        BasePanel aw = (BasePanel) al.get(i);
        aw.commit();
      }
//      System.err.println("Committed subpanels.");
    }
    if (!hasExceptions()) {
      setPanelState(LOADED);
    }
    else {
      System.err.println("This panel had errors: " + this);
    }
  }

  public void setHostingWindow(StandardWindow w) {
    myWindow = w;
  }

  public void setHostingPanel(BasePanel p) {
    hostingPanel = p;
  }

  protected Component findParentComponent(Class<?> type) {
    Component c = getParent();
    while (c != null) {
      if (type.isInstance(c)) {
        return c;
      }
      c = c.getParent();
    }
    return null;
  }

  public ArrayList<Component> getAllPanels() {
    ArrayList<Component> al = new ArrayList<Component>();
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
        BasePanel current = (BasePanel) c;
        al.add(current);
      }
    }
    return al;
  }

  @Override
public void setEnabled(boolean b) {
    super.setEnabled(b);
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      c.setEnabled(b);
    }
  }


  public void setFocus() {
    focusState = FOCUS_REQUEST;
    if (hostingPanel != null) {
      hostingPanel.focusUpdate();
    }
  }



  // Required for V1 !!
  public Navajo createMemberMessage(String id) {
    Navajo n = null;
    try {
      n = NavajoClientFactory.getClient().doSimpleSend("InitUpdateMember");
      n.getMessage("QueryUpdateMember").getProperty("MemberIdentifier").setValue(id);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return n;
  }

  public void setWaiting(boolean b) {
//    if (getRootPane()!=null) {
//      getRootPane().setCursor(b?new Cursor(Cursor.WAIT_CURSOR):Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
//    }
  }







  public boolean hasExceptions() {
    if (hasExceptions) {
      return true;
    }
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
        BasePanel handler = (BasePanel) c;
        //System.err.println("Checking: " + c.getClass() + ", errors: " + handler.hasConditionErrors());
        if (handler.hasExceptions()) {
          //System.err.println("HasConditionErrors");
          return true;
        }
      }
    }
    return false;
  }

 

  public String getPanelTitle() {
    return panelTitle;
  }

  public void setPanelTitle(String title) {
    panelTitle = title;
  }

  public void setPanelEnabled(boolean state) {
    Component[] components = getComponents();
    for (int i = 0; i < components.length; i++) {
      Component c = components[i];
      c.setEnabled(state);
      if (BasePanel.class.isInstance(c)) {
        BasePanel cc = (BasePanel) c;
        cc.setPanelEnabled(state);
      }
    }
    setEnabled(state);
  }


  public BasePanel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private final void jbInit() throws Exception {
    this.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
	public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
    });
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      @Override
	public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }
    });
  }

  void this_focusGained(FocusEvent e) {
    setFocus();
    if (hostingPanel != null) {
      this.hostingPanel.focusUpdate();
      System.err.println("BasePanel Focus gained");
    }
  }

  void this_mouseClicked(MouseEvent e) {
    setFocus();
  }

  public StandardWindow getHostingWindow() {
    return this.myWindow;
  }


}
