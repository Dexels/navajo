package com.dexels.navajo.tipi.swingclient;
//import com.dexels.sportlink.client.swing.dialogs.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public interface UserInterface {


 
  
  //Date:
  public JDialog getTopDialog();
  public JFrame getMainFrame();
  public void addDialog(JDialog d);
  public Locale getLocale();
  
  // Merge utils use them, for opening mail client
  public boolean showQuestionDialog(String s);
  public Object pasteFromClipBoard(PasteCompatible pc);
  public void copyToClipBoard(CopyCompatible cc);
  public void clearClipboard();
  public void closeWindow(BaseWindow d);

//public void updateAllTopLevels();
//public JDesktopPane getDesktopPane();
//public JRootPane getRootPane();
//public void setStatusText(String s);
//public void exit();
//public void addFrame(BaseWindow d);
//public void addErrorDialog(Message msg);
//public void addErrorDialog(Message msg, String stack);
//public void addErrorDialog(Exception e);
//public void addErrorDialog(String errorMessage);
//public RootPaneContainer getRootPaneContainer();
//public boolean areYouSure();
//public boolean isAuthenticated(String method);
//public void showCenteredWindow(BaseWindow bw);
//public void showCenteredDialog(JDialog bw);
//public void showDialogAt(JDialog dlg, int x, int y);
//public void showInfoDialog(String s);
//public ResourceBundle getResource(String bundleName);
//public void setLocale(Locale l);
//public void rebuildWindow();
//public void addStickyFrame(StandardWindow sw, String direction);
//public void addStickyness(StandardWindow sw, String dir);
//public void removeStickyness(StandardWindow sw, String dir);
//public Object getPreference(int type, String id);
//public Property getCachedSelectionProperty(String name);
//public String[] getServerList();
//public void rebuildMenu(Map<String,Object> localWindowState);
//public void updateMenu(String id, Map<String,Object> localWindowState);
}
