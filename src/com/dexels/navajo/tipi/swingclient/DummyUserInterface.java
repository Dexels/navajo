package com.dexels.navajo.tipi.swingclient;

import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.swingclient.components.*;

//import com.dexels.sportlink.client.swing.*;

public class DummyUserInterface
    implements UserInterface {

  public DummyUserInterface() {
  }


  public void addDialog(JDialog d) {
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


//  public Preferences getPreferences() {
//    /**@todo: Implement this com.dexels.sportlink.client.swing.UserInterface method*/
//    throw new java.lang.UnsupportedOperationException("Method getPreferences() not yet implemented.");
//  }
  public boolean showQuestionDialog(String s) {
    return true;
  }


public JFrame getMainFrame() {
	return null;
}

public JDialog getTopDialog() {
	return null;
}


public void closeWindow(BaseWindow d) {
	
}

}
