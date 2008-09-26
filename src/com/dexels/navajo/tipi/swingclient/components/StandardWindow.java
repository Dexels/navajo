package com.dexels.navajo.tipi.swingclient.components;

/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.*;

//import com.dexels.sportlink.client.swing.dialogs.*;

public class StandardWindow
    extends BaseWindow
    implements DialogConstants {
  protected JToolBar dialogToolbar = new JToolBar();
  protected JButton closeButton = new JButton();
  protected JButton insertButton = new JButton();
  protected JButton saveButton = new JButton();
  protected JButton clearButton = new JButton();
  private BasePanel myPanel = null;
  private String windowId = "";
  private Navajo myData = null;
  private Rectangle preferredBounds;
  private String stickDir = BorderLayout.WEST;
  private boolean isSticky = false;
  private boolean isCollapsed = false;
  private String myGroup = null;
  private ArrayList<KeyListener> keyListeners = new ArrayList<KeyListener>();

//  private final static Object showWindowSemaphore = new Object();

  private Map<String,Object> localWindowProperties = null;

  public StandardWindow() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
public void addKeyListener(KeyListener k) {
    keyListeners.add(k);
  }

  @Override
public void removeKeyListener(KeyListener k) {
    keyListeners.remove(k);
  }

  public void performKeyEvent(KeyEvent e) {
    for (int i = 0; i < keyListeners.size(); i++) {
      KeyListener kl = keyListeners.get(i);
      if (e.getID() == KeyEvent.KEY_PRESSED) {
        kl.keyPressed(e);
      }
      if (e.getID() == KeyEvent.KEY_TYPED) {
        kl.keyTyped(e);
      }
      if (e.getID() == KeyEvent.KEY_RELEASED) {
        kl.keyReleased(e);
      }
    }
  }

  public void setDesktopGroup(String name) {
    myGroup = name;
  }

  public String getGroup() {
    return myGroup;
  }

  public void setCollapsed(boolean value) {
    if (isSticky) {
      isCollapsed = value;
    }
    else {
      isCollapsed = false;
      System.err.println("Cant set collapsed for a non-sticky window");
    }
  }

  public boolean isCollapsed() {
    return isCollapsed;
  }

  @Override
public void setSelected(boolean value) throws PropertyVetoException {
    super.setSelected(value);
    repaint();
  }


  public void setStickyDirection(String dir) {
    stickDir = dir;
    updateLayout();
  }

  public void setSticky(boolean b) {
    isSticky = b;
    updateLayout();
  }

  private final void updateLayout() {
    if (getParent() != null) {
      Container parent = getParent();
      parent.remove(this);
      parent.add(this, getStickyDirection());
    }
  }


  public String getStickyDirection() {
    return stickDir;
  }

  public boolean isSticky() {
    return isSticky;
  }

  public void setData(Navajo n) {
    myData = n;
  }

  public Navajo getData() {
    return myData;
  }

  public String getPreferredStickyLocation() {
    return BorderLayout.WEST;
  }

  public void setWindowId(String wId) {
    windowId = wId;
  }

  public String getWindowId() {
    return windowId;
  }

  protected void setButtonBarVisible(boolean b) {
    dialogToolbar.setVisible(b);
  }

  public void setPreferredBounds(Rectangle r) {
    preferredBounds = r;
  }

  public void addButtonToBar(JButton but) {
    dialogToolbar.add(but);
  }

  public void setToolbarLocation(int location) {
    dialogToolbar.setOrientation(location);
  }

  public void addMainPanel(BasePanel p) {
    if (myPanel != null) {
      mainPanel.remove(myPanel);
    }
    myPanel = p;
    mainPanel.add(myPanel, BorderLayout.CENTER);
  }

  public void reloadWindow() {
    System.err.println("WARNING: You called reloadWindow in StandardWindow, this should be overridden");
  }

  private final void jbInit() throws Exception {
    InputMap im = getLayeredPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
    ActionMap am = getLayeredPane().getActionMap();

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK), "Save");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_MASK), "Insert");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK), "SaveExit");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK), "Exit");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK), "Fetch");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "Clear");

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UpPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DownPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RightPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "EnterPressed");

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), "UpReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), "DownReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "LeftReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "RightReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), "EnterReleased");

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "WPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "SPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "APressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "DPressed");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, false), "QPressed");

    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "WReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, true), "SReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "AReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "DReleased");
    im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0, true), "QReleased");


    am.put("Save", new KeyEventHandler(this, "Save"));
    am.put("Insert", new KeyEventHandler(this, "Insert"));
    am.put("SaveExit", new KeyEventHandler(this, "SaveExit"));
    am.put("Clear", new KeyEventHandler(this, "Clear"));
    am.put("Exit", new KeyEventHandler(this, "Exit"));
    am.put("Fetch", new KeyEventHandler(this, "Fetch"));

    am.put("UpPressed", new KeyEventHandler(this, "UpPressed"));
    am.put("DownPressed", new KeyEventHandler(this, "DownPressed"));
    am.put("LeftPressed", new KeyEventHandler(this, "LeftPressed"));
    am.put("RightPressed", new KeyEventHandler(this, "RightPressed"));
    am.put("EnterPressed", new KeyEventHandler(this, "EnterPressed"));

    am.put("UpReleased", new KeyEventHandler(this, "UpReleased"));
    am.put("DownReleased", new KeyEventHandler(this, "DownReleased"));
    am.put("LeftReleased", new KeyEventHandler(this, "LeftReleased"));
    am.put("RightReleased", new KeyEventHandler(this, "RightReleased"));
    am.put("EnterReleased", new KeyEventHandler(this, "EnterReleased"));

    am.put("WPressed", new KeyEventHandler(this, "WPressed"));
    am.put("SPressed", new KeyEventHandler(this, "SPressed"));
    am.put("APressed", new KeyEventHandler(this, "APressed"));
    am.put("DPressed", new KeyEventHandler(this, "DPressed"));
    am.put("QPressed", new KeyEventHandler(this, "QPressed"));

    am.put("WReleased", new KeyEventHandler(this, "WReleased"));
    am.put("SReleased", new KeyEventHandler(this, "SReleased"));
    am.put("AReleased", new KeyEventHandler(this, "AReleased"));
    am.put("DReleased", new KeyEventHandler(this, "DReleased"));
    am.put("QReleased", new KeyEventHandler(this, "QReleased"));



    closeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        closeButton_actionPerformed(e);
      }
    });
    insertButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        insertButton_actionPerformed(e);
      }
    });
    saveButton.setEnabled(false);
    saveButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        saveButton_actionPerformed(e);
      }
    });
    clearButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clearButton_actionPerformed(e);
      }
    });
    this.getContentPane().add(dialogToolbar, BorderLayout.SOUTH);
    dialogToolbar.add(Box.createGlue());
    dialogToolbar.add(insertButton, null);
    dialogToolbar.add(saveButton, null);
    dialogToolbar.add(clearButton, null);
    dialogToolbar.add(closeButton, null);

  }

  void clearButton_actionPerformed(ActionEvent e) {
    clear();
  }

  void saveButton_actionPerformed(ActionEvent e) {
    save();
  }

  public void update(Graphics g) {
    paint(g);
  }

  void closeButton_actionPerformed(ActionEvent e) {
    try {
      closeWindow(); // hier stond: super.closeWindow() daardoor was ie niet te overiden
    }
    catch (PropertyVetoException ex) {
    }
  }

  void insertButton_actionPerformed(ActionEvent e) {
    insert();
  }

  public void setMaximum(boolean value) throws PropertyVetoException {
    if (isSticky()) {
      return;
    }
    super.setMaximum(value);
    if (!value && preferredBounds != null) {
      // Restoring window
      setBounds(preferredBounds);
    }
  }

  public void clear() {
    insertButton.setEnabled(true);
    saveButton.setEnabled(false);
  }

  public void save() {
    //please override..
  }

  public void insert() {

  }

  public void setLoaded() {
    insertButton.setEnabled(false);
    saveButton.setEnabled(true);

  }

  public void setInsert() {
    insertButton.setEnabled(true);
    saveButton.setEnabled(false);
  }

  public JButton getInsertButton() {
    return insertButton;
  }

  public JButton getSaveButton() {
    return saveButton;
  }

  public Map<String,Object> getLocalWindowState() {
    return localWindowProperties;
  }

  public void putLocalWindowProperty(String name, Object value) {
    if (localWindowProperties == null) {
      localWindowProperties = new HashMap<String,Object>();
    }
    localWindowProperties.put(name, value);
  }

  public void removeLocalWindowProperty(String name) {
    if (localWindowProperties == null) {
      return;
    }
    localWindowProperties.remove(name);
  }

//  public void updateMenu() {
//    SwingClient.getUserInterface().updateMenu(getWindowId(), getLocalWindowState());
//  }
//
//  public void rebuildMenu() {
//    SwingClient.getUserInterface().rebuildMenu(getLocalWindowState());
//  }

}
