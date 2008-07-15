package com.dexels.navajo.tipi.swingclient;
//import com.dexels.sportlink.client.swing.dialogs.*;
import javax.swing.*;

import com.dexels.navajo.document.*;
//import com.dexels.sportlink.client.swing.member.*;
import java.awt.*;
import javax.swing.plaf.metal.*;

import com.dexels.navajo.tipi.swingclient.components.*;

import java.util.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public interface UserInterface {

  public JDesktopPane getDesktopPane();
  public JRootPane getRootPane();
//  public BaseGlassPane getBaseGlassPane(JRootPane jp);
  public void setStatusText(String s);
  public void exit();
  public void addFrame(BaseWindow d);
  public void addDialog(JDialog d);
  public void addErrorDialog(Message msg);
  public void addErrorDialog(Message msg, String stack);
  public void addErrorDialog(Exception e);
  public void addErrorDialog(String errorMessage);
  public void closeWindow(BaseWindow d);
  public RootPaneContainer getRootPaneContainer();
  public JDialog getTopDialog();

  public JFrame getMainFrame();

  //  public void showErrorPopup(String title, String message);
  public void updateAllTopLevels();
//  public Preferences getPreferences();
  public boolean showQuestionDialog(String s);
  public boolean areYouSure();
//  public void setWaiting(boolean b);
  public boolean isAuthenticated(String method);
  public void showCenteredWindow(BaseWindow bw);
  public void showCenteredDialog(JDialog bw);
  public void showDialogAt(JDialog dlg, int x, int y);

  public void showInfoDialog(String s);
  public ResourceBundle getResource(String bundleName);
  public void setLocale(Locale l);
  public Locale getLocale();
  public void rebuildWindow();
  public void rebuildMenu(Map localWindowState);
  public void updateMenu(String id, Map localWindowState);
  public void addStickyFrame(StandardWindow sw, String direction);
  public void addStickyness(StandardWindow sw, String dir);
  public void removeStickyness(StandardWindow sw, String dir);
  public Object pasteFromClipBoard(PasteCompatible pc);
  public void copyToClipBoard(CopyCompatible cc);
  public void clearClipboard();
  public Object getPreference(int type, String id);
  public Property getCachedSelectionProperty(String name);
  public String[] getServerList();
}
