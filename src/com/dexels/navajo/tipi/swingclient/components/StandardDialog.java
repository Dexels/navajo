package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
//import com.dexels.sportlink.client.swing.components.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import com.dexels.navajo.client.ResponseListener;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.swingclient.*;
//import com.dexels.sportlink.client.swing.dialogs.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class StandardDialog extends BaseDialog implements DialogConstants, ResponseListener {

  private boolean doWindowClose = true;

  ResourceBundle res;
  private boolean isCommitted = false;
  BorderLayout borderLayout1 = new BorderLayout();
  public IconButtonPanel iconButtonPanel = new IconButtonPanel();
  JToolBar dialogToolbar = new JToolBar();

  public StandardDialog() {
    init();
  }

  public StandardDialog(JFrame f) {
    super(f);
    init();
  }

  public StandardDialog(JDialog f) {
	    super(f);
	    init();
	  }

private void init() {
	dialogToolbar.setFloatable(false);
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.dialogs.StandardDialog");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
}


  public void commit(){
    if(BasePanel.class.isInstance(mainPanel)){
      BasePanel myBase = (BasePanel) mainPanel;
      // Reset condition errors.

      myBase.checkUpdate();
      myBase.commit();
      if (myBase.hasConditionErrors() || myBase.hasExceptions()) {
        setDoWindowClose(false);
        setCommitted(false);
      }
    }else{
      System.err.println("----> StandarDialogs mainPanel is not of class BasePanel, but: " + mainPanel.getClass());
    }
  }

  public void discard(){
  }

  public void addMainPanel(BasePanel p) {
    this.getContentPane().add(p,BorderLayout.CENTER);
    mainPanel = p;
  }

  public boolean doWindowClose() {
    return doWindowClose;
  }

  public void setDoWindowClose(boolean state){
    doWindowClose = state;
  }

  public void setMode(int mode) {
    iconButtonPanel.setAllVisible(false);
    switch (mode) {
      case MODE_OK_CANCEL:
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        break;
      case MODE_CONFIRM:
      case MODE_CLOSE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,false);
        break;
      case MODE_OK_CANCEL_APPLY:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        break;
      case MODE_OK_CANCEL_APPLY_INSERT:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
      case MODE_OK_CANCEL_INSERT_DELETE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.DELETE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
      case MODE_OK_CANCEL_APPLY_INSERT_DELETE:
        iconButtonPanel.setButtonVisible(iconButtonPanel.SAVE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.DELETE_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.OK_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.CANCEL_BUTTON,true);
        iconButtonPanel.setButtonVisible(iconButtonPanel.INSERT_BUTTON,true);
        break;
    }
//
  }


  public boolean isCommitted() {
    return isCommitted;
  }

  public void setCommitted(boolean state){
    isCommitted = state;
  }

  private final void jbInit() throws Exception {

    InputMap im = getLayeredPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = getLayeredPane().getActionMap();
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK), "Save");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_MASK), "Insert");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_MASK), "SaveExit");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK), "Exit");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Exit");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK), "Fetch");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Delete");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "Clear");
    am.put("Save", new KeyEventHandler(this, "Save"));
    am.put("Insert", new KeyEventHandler(this, "Insert"));
    am.put("SaveExit", new KeyEventHandler(this, "SaveExit"));
    am.put("Clear", new KeyEventHandler(this, "Clear"));
    am.put("Exit", new KeyEventHandler(this, "Exit"));
    am.put("Delete", new KeyEventHandler(this, "Delete"));

    dialogToolbar.setFloatable(false);
    this.getContentPane().setLayout(borderLayout1);
    setModal(true);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        this_windowClosing(e);
      }
    });
    iconButtonPanel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        iconButtonPanel_actionPerformed(e);
      }
    });
    dialogToolbar.add(iconButtonPanel, null);
    this.getContentPane().add(dialogToolbar,  BorderLayout.SOUTH);
  }

  void this_windowClosing(WindowEvent e) {
    if (!isCommitted()) {
      discard();
    }
//    setVisible(false);
    closeWindow();
  }

  public void delete() {
  }

  public void newItem() {
  }

  public void insert() {
  }

//  public void closeWindow(){
//    super.closeWindow();
//  }

  void iconButtonPanel_actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals(IconButtonPanel.OK_BUTTON)) {
//      System.err.println("OK");
      if (!isCommitted()) {
        isCommitted = true;
        commit();
      }
      if(doWindowClose){
        closeWindow();
      }
      setDoWindowClose(true);
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.CANCEL_BUTTON)) {
      discard();
      //if(doWindowClose){
        closeWindow();
      //}
        
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.DELETE_BUTTON)) {
      delete();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.NEW_BUTTON)) {
      newItem();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.INSERT_BUTTON)) {
      insert();
      return;
    }
    if (e.getActionCommand().equals(IconButtonPanel.SAVE_BUTTON)) {
//      System.err.println("SAVE");
      isCommitted = false;
      commit();
      return;
    }

  }

  public void setVisible(boolean parm1) {
    super.setVisible( parm1);
    iconButtonPanel.requestFocus();
  }

  public void showDialog() {
    setCommitted(false);
    super.showDialog();
  }
  
  public void handleException(Exception e) {
		throw new UnsupportedOperationException("MenuAction does not implement handleException()");
	}

	public void receive(Navajo n, String method, String id) {
		throw new UnsupportedOperationException("MenuAction does not implement receive()");
	}

	public void setWaiting(boolean b) {
		throw new UnsupportedOperationException("MenuAction does not implement setWaiting()");
	}

	public void swingSafeReceive(final Navajo n, final String method, final String id) {
//		 TODO Auto-generated method stub
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
}
