package com.dexels.navajo.tipi.swingclient;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JRootPane;
import javax.swing.RootPaneContainer;

import com.dexels.navajo.tipi.swingclient.components.*;

import com.dexels.navajo.document.Message;
import javax.swing.JFrame;
import java.util.*;
import com.dexels.navajo.document.*;

//import com.dexels.sportlink.client.swing.*;

public class DummyUserInterface
    implements UserInterface {

  public DummyUserInterface() {
  }

  public JDesktopPane getDesktopPane() {
    return null;
  }
  public void rebuildWindow() {
  }
  public JRootPane getRootPane() {
    return null;
  }

  public void addStickyFrame(StandardWindow sw, String direction) {

  }

  public Object getPreference(int type, String id){
    return null;
  }

  public void addStickyness(StandardWindow sw, String dir){}
  public void removeStickyness(StandardWindow sw, String dir){}

  public void setStatusText(String s) {
  }

  public void exit() {
  }

  public void addFrame(BaseWindow d) {
  }

  public void addDialog(JDialog d) {
  }

  public void addErrorDialog(Message msg) {
  }

  public void addErrorDialog(Message msg, String stack) {
  }

  public void addErrorDialog(Exception e) {
  }

  public void addErrorDialog(String errorMessage) {
  }

  public void closeWindow(BaseWindow d) {
  }

  public RootPaneContainer getRootPaneContainer() {
    return null;
  }

  public void showErrorPopup(String title, String message) {
  }

  public void updateAllTopLevels() {
  }

  public boolean isAuthenticated(String method) {
    return true;
  }

  public void setLocale(Locale l) {
  }

  public Locale getLocale() {
    return Locale.getDefault();
  }

  public Object pasteFromClipBoard(PasteCompatible pc){
    return null;
  }

  public void copyToClipBoard(CopyCompatible cc){
  }
  public void clearClipboard(){}

  public ResourceBundle getResource(String bundleName) {
    return null;
  }

   public Property getCachedSelectionProperty(String name){
     return null;
   }

//  public Preferences getPreferences() {
//    /**@todo: Implement this com.dexels.sportlink.client.swing.UserInterface method*/
//    throw new java.lang.UnsupportedOperationException("Method getPreferences() not yet implemented.");
//  }
  public boolean showQuestionDialog(String s) {
    return true;
  }
  public void showInfoDialog(String s) {
  }

  public boolean areYouSure() {
    return true;
  }

//  public BaseGlassPane getBaseGlassPane(JRootPane jp) {
//    return null;
//  }

//  public void setWaiting(boolean b) {
//  }
  public void showCenteredWindow(BaseWindow bw) {
  }
  public void showCenteredDialog(JDialog bw) {
  }

  public void rebuildMenu(Map<String,Object> localWindowState) {

  }

  public void updateMenu(String id, Map<String,Object> localWindowState) {

  }

public String[] getServerList() {
	return null;
}

public void showDialogAt(JDialog dlg, int x, int y) {
	
}

public JFrame getMainFrame() {
	return null;
}

public JDialog getTopDialog() {
	return null;
}

}
