package com.dexels.navajo.swingclient.components;

import java.awt.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.swingclient.*;
import java.util.*;
import com.dexels.navajo.client.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.dialogs.*;
//import com.dexels.navajo.nanoclient.ResponseListener;
import java.awt.event.*;
import javax.swing.border.LineBorder;
//import com.dexels.navajo.document.nanoimpl.*;

//import com.dexels.sportlink.client.swing.components.*;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class BasePanel extends JPanel implements ChangeMonitoring, ResponseListener, ConditionErrorHandler {
  private Message loadMessage = null;
  private Message initMessage = null;
  private Message rootMessage = null;
  protected BasePanel hostingPanel = null;
  private String panelTitle = "no name";

  private int panelState = NO_STATE;

  public static final int NO_STATE = -1;
  public static final int INITIALIZED = 1;
  public static final int LOADED = 2;
  public static final int UPDATED = 3;
  public static final int INSERTED = 4;
  public static final int DELETED = 5;
  private boolean hasConditionErrors = false;
  public static final int FOCUS_REQUEST = 0;
  public static final int FOCUS_GAINED = 1;
  public static final int FOCUS_LOST = 2;
  private int focusState = FOCUS_LOST;

  protected void init(Message msg) {
    initMessage = msg;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if(BasePanel.class.isInstance(c)) {
//        System.err.println("Initializing BasePanel");
        ((BasePanel)c).init(msg);
      }
    }
  }

  protected void load(Message msg) {
    loadMessage = msg;
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if(BasePanel.class.isInstance(c)) {
//        System.err.println("Loading BasePanel");
        ((BasePanel)c).load(msg);
      }
    }
  }

  public void focusUpdate(){
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if(BasePanel.class.isInstance(c)) {
        ((BasePanel)c).focusChange();
      }
    }
    focusChange();
  }

  private void focusChange(){
    switch (focusState){
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

  private void setFocusState(int state){
    focusState = state;
  }

  public void addGlassPane(int type) {
//    setPanelGhosted(true);
    if (BaseWindow.class.isInstance(getRootPane().getParent())) {
      BaseWindow myWindow = (BaseWindow) getRootPane().getParent();
      BaseGlassPane bgp = myWindow.getBaseGlassPane();
      bgp.addBusyPanel(this,type);
    }

    if (BaseDialog.class.isInstance(getRootPane().getParent())) {
      BaseDialog myDialog = (BaseDialog) getRootPane().getParent();
      BaseGlassPane bgp = myDialog.getBaseGlassPane();
      bgp.addBusyPanel(this,type);
    }
  }
//
  public void addBusyPanel() {
    addGlassPane(BaseGlassPane.MODE_HOURGLASS);
  }
  public void removeBusyPanel() {
    removeGlassPanel();
  }
  public void removeGlassPanel() {
    setPanelGhosted(false);
    if (BaseWindow.class.isInstance(getRootPane().getParent())) {
      BaseWindow myWindow = (BaseWindow) getRootPane().getParent();
      BaseGlassPane bgp = myWindow.getBaseGlassPane();
      bgp.removeBusyPanel(this);
      if (bgp.getBusyPanelCount()==0) {
        JPanel jp = new JPanel();
        jp.setOpaque(false);
        getRootPane().setGlassPane(jp);
      }

    }
    if (BaseDialog.class.isInstance(getRootPane().getParent())) {
      BaseDialog myDialog = (BaseDialog) getRootPane().getParent();
      BaseGlassPane bgp = myDialog.getBaseGlassPane();
      bgp.removeBusyPanel(this);
      if (bgp.getBusyPanelCount()==0) {
        JPanel jp = new JPanel();
        jp.setOpaque(false);
        getRootPane().setGlassPane(jp);
      }
    }
  }

  public void load() {
    if (loadMessage!=null) {
      load(loadMessage);
    } else {
      System.err.println("Warning: Attempting to load without loadMessage");
    }
  }

  public void store() {
    if (loadMessage!=null) {
      store(loadMessage);
    } else {
      System.err.println("Error: Attempting to store without loadMessage");
    }
  }

  public void init() {
    if (initMessage!=null) {
      init(initMessage);
    } else {
      System.err.println("Warning: Attempting to init without initMessage");
    }
  }

  public void insert() {
    if (initMessage!=null) {
      insert(initMessage);
    } else {
      System.err.println("Error: Attempting to insert without initMessage");
    }
  }
  public void delete() {
    if (loadMessage!=null) {
      delete(loadMessage);
    } else {
      System.err.println("Error: Attempting to delete without loadMessage");
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
    initializePanel(init,load);
  }

  public int getPanelState() {
    return panelState;
  }

  private void setPanelState(int newState) {
    panelState = newState;
    revalidate();
//    setStateRecursive(newState);
  }

  public void setRootMessage(Message msg) {
    if (rootMessage != null) {
      System.err.println("WARNING: Resetting root message");
    }
    rootMessage = msg;
  }

  public Message getRootMessage() {
    return rootMessage;
  }


  public void setLoadMessage(Message msg) {
    switch (getPanelState()) {
      case NO_STATE:
        System.err.println("WARNING: Loading panel without initializing first");
        break;
      case INSERTED:
        System.err.println("WARNING: Loading newly inserted panel");
        break;
      case LOADED:
        System.err.println("Reloading panel");
        break;
      case UPDATED:
        System.err.println("Reverting updated panel");
        break;
    }
    setPanelState(LOADED);
    loadMessage = msg;
//    load(loadMessage);
  }

  public void setInitMessage(Message msg) {
    switch (getPanelState()) {
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
    }
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

  protected void insert(Message msg) {
    System.err.println("Insert called in BasePanel. Not implemented. "+getClass().getName());
  }

  protected void store(Message msg) {
    System.err.println("Store called in BasePanel. Not implemented.");
    }

  protected void delete(Message msg) {
    System.err.println("Delete called in BasePanel. Not implemented.");
  }

  public void setStateRecursive(int state) {
    panelState = state;
    for (int i = 0; i < getComponentCount(); i++) {
      Component current = getComponent(i);
      if (BasePanel.class.isInstance(current)) {
        ((BasePanel)current).setStateRecursive(state);
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
        System.err.println("Attempting to set to inserted from illegal state. Ignoring. State: "+getPanelState());
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
    System.err.println("State: "+panelState);
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
    setPanelState(LOADED);
    ArrayList al = getAllPanels();
    if (al.size()>0) {
      System.err.println("End of commit panel. Now committing subpanels.");
      for (int i = 0; i < al.size(); i++) {
        BasePanel aw = (BasePanel)al.get(i);
        aw.commit();
      }
      System.err.println("Committed subpanels.");
    }
  }

  public void setHostingPanel(BasePanel p) {
    hostingPanel = p;
  }
  protected Component findParentComponent(Class type) {
    Component c = getParent();
    while (c!=null) {
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
        BasePanel current = (BasePanel)c;
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

  protected Navajo createMemberMessage(String memberCode) {
    Navajo n = NavajoFactory.getInstance().createNavajo();
    Message m = NavajoFactory.getInstance().createMessage(n,"QueryUpdateMember");
    try {
      Property p = NavajoFactory.getInstance().createProperty(n, "MemberIdentifier", Property.STRING_PROPERTY, memberCode, 20, "nope", Property.DIR_INOUT);
      n.addMessage(m);
      m.addProperty(p);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return n;
  }

//  protected Navajo loadByMemberId(String memberNumber, String service) {
//    return AdvancedNavajoClient.doSimpleSend(createMemberMessage(memberNumber),service);
//    super.getRootPane().setCu
//   }


   public boolean hasFocus(){
     for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ChangeMonitoring.class.isInstance(c)) {
        ChangeMonitoring current = (ChangeMonitoring)c;
        if (current.hasFocus()) {
//          System.err.println("Component hasfocus: "+current.getClass());
          return true;
        }
      }
    }
    if(focusState == FOCUS_GAINED){
      return true;
    }
    return false;
   }

   public void setFocus(){
     focusState = FOCUS_REQUEST;
     if(hostingPanel != null){
      hostingPanel.focusUpdate();
    }
   }

   public boolean hasChanged() {
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if (ChangeMonitoring.class.isInstance(c)) {
        ChangeMonitoring current = (ChangeMonitoring)c;
        if (current.hasChanged()) {
          System.err.println("Component changed: "+current.getClass());
          return true;
        }
      }
    }
    return false;
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
    hasConditionErrors = false;
    System.err.println("Checking conditionerrors");
    if(msg == null){
      return;
    }
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if(ConditionErrorHandler.class.isInstance(c)){
        ConditionErrorHandler handler = (ConditionErrorHandler)c;
        handler.checkValidation(msg);
        if(handler.hasConditionErrors()){
          hasConditionErrors = true;
        }
      }
      if (Validatable.class.isInstance(c)) {
        Validatable current = (Validatable)c;
        current.checkValidation(msg);
        if (current.getValidationState() == current.INVALID) {
          //System.err.println("Invalid state found: "+current.getClass());
          hasConditionErrors = true;
        }
      }
    }
  }

  public boolean hasConditionErrors(){
    for (int i = 0; i < getComponentCount(); i++) {
      Component c = getComponent(i);
      if(ConditionErrorHandler.class.isInstance(c)){
        ConditionErrorHandler handler = (ConditionErrorHandler)c;
        if(handler.hasConditionErrors()){
          System.err.println("HasConditionErrors");
          return true;
        }
      }
      if(Validatable.class.isInstance(c)){
        Validatable current = (Validatable)c;
        if(current.getValidationState() == current.INVALID){
          return true;
        }
      }
    }
    System.err.println("Has NO ConditionErrors");
    return false;
  }

  public void checkUpdate() {
    if (hasChanged()) {
      System.err.println("Switching to updated!");
      setStateUpdated();
    }
  }

  public String getPanelTitle(){
    return panelTitle;
  }

  public void setPanelTitle(String title){
    panelTitle = title;
  }

  public void setPanelEnabled(boolean state){
    Component[] components = getComponents();
    for(int i=0;i<components.length;i++){
      Component c = components[i];
      c.setEnabled(state);
      if (BasePanel.class.isInstance(c)) {
        BasePanel cc  = (BasePanel)c;
        cc.setPanelEnabled(state);
      }

    }
    setEnabled(state);
  }

  public void setPanelGhosted(boolean state){
    Component[] components = getComponents();
    for(int i=0;i<components.length;i++){
      if (Ghostable.class.isInstance(components[i])) {
        ((Ghostable)components[i]).setGhosted(state);
      }
      if (BasePanel.class.isInstance(components[i])) {
        ((BasePanel)components[i]).setPanelGhosted(state);
      }

    }
//    setEnabled(state);
  }


  public void receive(Navajo n, String id) {
    throw new java.lang.UnsupportedOperationException("Method receive() not yet implemented.");
  }

  public BasePanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
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
    if(hostingPanel != null){
      this.hostingPanel.focusUpdate();
      System.err.println("BasePanel Focus gained");
    }
  }

  void this_mouseClicked(MouseEvent e) {
    setFocus();
  }

}