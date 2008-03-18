package com.dexels.navajo.tipi.swingclient.components;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import com.dexels.navajo.client.*;
import com.dexels.navajo.document.*;

import java.awt.Point;
import javax.swing.event.*;
import java.beans.*;

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
    implements ChangeMonitoring, ConditionErrorHandler, ResponseListener {

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
  private boolean hasConditionErrors = false;
  private boolean hasExceptions = false;
  public static final int FOCUS_REQUEST = 0;
  public static final int FOCUS_GAINED = 1;
  public static final int FOCUS_LOST = 2;
  private int focusState = FOCUS_LOST;

  private static int busyPanelCount = 0;
  private static Object busyPanelSemaphore = new Object();
 // private Paint myPaint = SystemColor.getColor("control");

  protected void init(Message msg) {
    initMessage = msg;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
//        System.err.println("Initializing BasePanel");
         ( (BasePanel) c).init(msg);
      }
    }
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
//    Rectangle bounds = getBounds();
//    Graphics2D g2 = (Graphics2D) g;
//    Paint old = g2.getPaint();
//    GradientPaint gp = new GradientPaint(bounds.x, 0, Color.lightGray, bounds.width, 0, Color.white);
//    g2.setPaint(gp);
//    g2.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
//    g2.setPaint(old);
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
        //System.err.println("lost -> lost");
        break;
      case FOCUS_GAINED:
        setFocusState(FOCUS_LOST);
        setBorder(null);
        //System.err.println("gained -> lost");
        break;
    }
  }

  private final void setFocusState(int state) {
    focusState = state;
  }

  public void addGlassPane(int type) {
//    setPanelGhosted(true);
    if (getRootPane() != null) {
      if (BaseWindow.class.isInstance(getRootPane().getParent())) {
        BaseWindow myWindow = (BaseWindow) getRootPane().getParent();

        BaseGlassPane bgp = myWindow.getBaseGlassPane();
        if (bgp.getBusyPanelCount() == 0) {
          bgp.addBusyPanel(this, type);
        }
      }

      if (BaseDialog.class.isInstance(getRootPane().getParent())) {
        BaseDialog myDialog = (BaseDialog) getRootPane().getParent();
        BaseGlassPane bgp = myDialog.getBaseGlassPane();
        if (bgp.getBusyPanelCount() == 0) {
          bgp.addBusyPanel(this, type);
        }
      }
    }
  }


  public final void removeBusyPanel() {
    synchronized (busyPanelSemaphore) {
      removeGlassPanel();
      busyPanelCount--;
    }
  }

  public final void removeGlassPanel() {
    setPanelGhosted(false);
    if (getRootPane() != null) {
      if (BaseWindow.class.isInstance(getRootPane().getParent())) {

        final BaseWindow myWindow = (BaseWindow) getRootPane().getParent();
        BaseGlassPane bgp = myWindow.getBaseGlassPane();
        bgp.removeBusyPanel(this);
        if (bgp.getBusyPanelCount() == 0) {
          getRootPane().remove(getRootPane().getGlassPane());
          getRootPane().setGlassPane(myWindow.getOldGlassPane());
          myWindow.invalidate();
        }
      }
      if (BaseDialog.class.isInstance(getRootPane().getParent())) {
        BaseDialog myDialog = (BaseDialog) getRootPane().getParent();
        BaseGlassPane bgp = myDialog.getBaseGlassPane();
        bgp.removeBusyPanel(this);
        if (bgp.getBusyPanelCount() == 0) {
          JPanel jp = new JPanel();
          jp.setOpaque(false);
          getRootPane().setGlassPane(jp);
        }
      }
    }
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
        Message condition = n.getMessage("ConditionErrors");
        Message exception = n.getMessage("error");
        if (condition != null) {
          checkValidation(condition);
        }
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
		  else {
			  //System.err.println("Warning: Attempting to init without initMessage");
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
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  } catch (InvocationTargetException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	  }
  }

  public void insert() {
    if (initMessage != null) {
      Navajo n = insert(initMessage);
      Message condition = n.getMessage("ConditionErrors");
      Message exception = n.getMessage("error");
      if (condition != null) {
        checkValidation(condition);
      }
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
      Message condition = n.getMessage("ConditionErrors");
      Message exception = n.getMessage("error");
      if (condition != null) {
        checkValidation(condition);
      }
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
//    System.err.println("State: " + panelState);
    checkUpdate();
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
    ArrayList al = getAllPanels();
    if (al.size() > 0) {
//      System.err.println("End of commit panel. Now committing subpanels.");
      for (int i = 0; i < al.size(); i++) {
        BasePanel aw = (BasePanel) al.get(i);
        aw.commit();
      }
//      System.err.println("Committed subpanels.");
    }
    if (!hasConditionErrors() && !hasExceptions()) {
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

  protected Component findParentComponent(Class type) {
    Component c = getParent();
    while (c != null) {
      if (type.isInstance(c)) {
        return c;
      }
      c = c.getParent();
    }
    return null;
  }

  public ArrayList getAllPanels() {
    ArrayList al = new ArrayList();
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (BasePanel.class.isInstance(c)) {
        BasePanel current = (BasePanel) c;
        al.add(current);
      }
    }
    return al;
  }

  public void setEnabled(boolean b) {
    super.setEnabled(b);
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      c.setEnabled(b);
    }
  }

  public boolean hasFocus() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ChangeMonitoring.class.isInstance(c)) {
        ChangeMonitoring current = (ChangeMonitoring) c;
        if (current.hasFocus()) {
//          System.err.println("Component hasfocus: "+current.getClass());
          return true;
        }
      }
    }
    if (focusState == FOCUS_GAINED) {
      return true;
    }
    return false;
  }

  public void setFocus() {
    focusState = FOCUS_REQUEST;
    if (hostingPanel != null) {
      hostingPanel.focusUpdate();
    }
  }

  public void resetChanged() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ChangeMonitoring.class.isInstance(c)) {
        ChangeMonitoring current = (ChangeMonitoring) c;
        current.resetChanged();
      }
    }
  }

  public boolean hasChanged() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ChangeMonitoring.class.isInstance(c)) {
        ChangeMonitoring current = (ChangeMonitoring) c;
        if (MessageTable.class.isInstance(c)) {

          try {
             ( (MessageTable) c).saveColumnsNavajo();
          }
          catch (Exception e) {
            System.err.println("WARNING: Could not save columns...");
          }
        }
        if (current.hasChanged()) {
          return true;
        }
      }
    }
    return false;
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

  /*public boolean isValid() {
    int invalidCount = 0;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (Validatable.class.isInstance(c)) {
        Validatable current = (Validatable)c;
        current.checkValidation();
        if (current.getValidationState() == current.INVALID) {
          System.err.println("Invalid state found: "+current.getClass());
          invalidCount++;
        }
      }
    }
    if(invalidCount > 0){
      return false;
    }else{
      return true;
    }
     }*/

  public void checkValidation(Message msg) {
    String pmn = "";
    int state = getPanelState();
    if (hasConditionErrors) {
      System.err.println("Resetting hasConditionErrors: " + this.getPanelTitle());
    }
    hasConditionErrors = false;
    if (msg == null) {
      return;
    }
    System.err.println("MESSAGENAME: " + msg.getName());
    if (msg.getName().equals("ConditionErrors")) {
      hasConditionErrors = true;

    }

//    System.err.println("Checking conditionerrors");
    if (msg == null) {
      return;
    }
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ConditionErrorHandler.class.isInstance(c)) {
        ConditionErrorHandler handler = (ConditionErrorHandler) c;
        handler.checkValidation(msg);
        if (handler.hasConditionErrors()) {
          System.err.println("Detected conditionerror!");
          hasConditionErrors = true;
        }
      }
      if (Validatable.class.isInstance(c)) {
        System.err.println("Validating: " + c.getClass());
        Validatable current = (Validatable) c;
        current.checkValidation(msg);
        if (current.getValidationState() == current.INVALID) {
          //System.err.println("--> This component was INVALID: " + c.getClass());
          System.err.println("Detected conditionerror!");
          hasConditionErrors = true;
        }
      }
    }
  }

  public void clearConditionErrors() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ConditionErrorHandler.class.isInstance(c)) {
        ConditionErrorHandler handler = (ConditionErrorHandler) c;
        handler.clearConditionErrors();
      }
      if (Validatable.class.isInstance(c)) {
        Validatable current = (Validatable) c;
        current.setValidationState(Validatable.VALID);
      }
    }
    hasConditionErrors = false;
  }

  public boolean hasConditionErrors() {
    if(!hasConditionErrors){
      for (int i = 0; i < getComponentCount(); i++) {
        Component c = getComponent(i);
        if (ConditionErrorHandler.class.isInstance(c)) {
          ConditionErrorHandler handler = (ConditionErrorHandler) c;
          //System.err.println("Checking: " + c.getClass() + ", errors: " + handler.hasConditionErrors());
          if (handler.hasConditionErrors()) {
            System.err.println("----> BasePanel " + getPanelTitle() + " found ConditionErrors in a SubPanel");
            return true;
          }
        }
        if (Validatable.class.isInstance(c)) {
          Validatable current = (Validatable) c;
          if (current.getValidationState() == current.INVALID) {
            System.err.println("---> Found an invalid component " + current + " in " + getPanelTitle());
            return true;
          }
        }
      }
    }
    return hasConditionErrors;
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

  public void checkUpdate() {
    if (hasChanged()) {
      System.err.println("Switching to updated!");
      setStateUpdated();
    }
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

  public void setPanelGhosted(boolean state) {
    Component[] components = getComponents();
    for (int i = 0; i < components.length; i++) {
      if (Ghostable.class.isInstance(components[i])) {
         ( (Ghostable) components[i]).setGhosted(state);
      }
      if (BasePanel.class.isInstance(components[i])) {
         ( (BasePanel) components[i]).setPanelGhosted(state);
      }
    }
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
      public void mouseClicked(MouseEvent e) {
        this_mouseClicked(e);
      }
    });
    this.addFocusListener(new java.awt.event.FocusAdapter() {
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
//    System.err.println("Klik in BasePanel");
//    final BaseWindow myWindow = (BaseWindow) getRootPane().getParent();
//    if (myWindow != null) {
//      try {
//        myWindow.setSelected(true);
//      }
//      catch (PropertyVetoException ex) {
//      }
//    }
    setFocus();
  }

  public void handleException(Exception e) {
    System.err.println("--> An exception is passed to BasePanel it was: " + e.toString());
    removeBusyPanel();
  }
  public StandardWindow getHostingWindow() {
    return this.myWindow;
  }

public void receive(Navajo n, String method, String id) {
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException("Basepanel does not implement receive()");
}

public void swingSafeReceive(final Navajo n, final String method, final String id) {
	// TODO Auto-generated method stub
	if ( SwingUtilities.isEventDispatchThread() ) {
		receive(n, method, id);
	} else {
		try {
			SwingUtilities.invokeAndWait(new Runnable(){

				public void run() {
					receive(n, method, id);
				}
			}
			);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

//  /**
//   * Adds the specified component to this container at the specified index.
//   *
//   * @param comp the component to be added
//   * @param constraints an object expressing layout constraints for this component
//   * @param index the position in the container's list at which to insert the component, where <code>-1</code> means append to the end
//   * @todo Implement this java.awt.Container method
//   */
//  protected void addImpl(Component comp, Object constraints, int index) {
//    super.addImpl(comp, constraints, index);
//
//    comp.addFocusListener(new FocusAdapter() {
//      public void focusGained(FocusEvent fe) {
//        final BaseWindow myWindow = (BaseWindow) getRootPane().getParent();
//        if (myWindow != null) {
//          try {
//            myWindow.setSelected(true);
//          }
//          catch (PropertyVetoException ex) {
//          }
//        }
//      }
//    });
//
//  }

}
