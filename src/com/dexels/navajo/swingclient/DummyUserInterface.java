package com.dexels.navajo.swingclient;

import javax.swing.JDesktopPane;
import javax.swing.JRootPane;
import com.dexels.navajo.swingclient.components.*;
import javax.swing.JDialog;
import com.dexels.navajo.document.Message;
import javax.swing.JFrame;
import java.util.*;

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

  public void closeWindow(BaseWindow d) {
  }

  public JFrame getMainFrame() {
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

  public ResourceBundle getResource(String bundleName) {
    return null;
  }

//  public Preferences getPreferences() {
//    /**@todo: Implement this com.dexels.sportlink.client.swing.UserInterface method*/
//    throw new java.lang.UnsupportedOperationException("Method getPreferences() not yet implemented.");
//  }
  public boolean showQuestionDialog(String s) {
    return true;
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
}