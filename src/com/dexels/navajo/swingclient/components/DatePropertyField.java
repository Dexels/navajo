package com.dexels.navajo.swingclient.components;

import com.dexels.navajo.document.*;
import java.awt.event.*;
//import com.dexels.sportlink.client.swing.*;
import java.util.*;
//import com.dexels.sportlink.client.swing.dialogs.ErrorDialog;
import java.text.ParseException;
import java.text.*;
import com.dexels.navajo.swingclient.*;

//import com.dexels.sportlink.client.swing.components.*;
/**
 * <p>Title: SportLink Client:</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Dexels.com</p>
 * @author unascribed
 * @version 1.0
 */

public class DatePropertyField extends PropertyField  implements PropertyControlled {

  private String textDate = "";
  private String toolTipText = "";
  private Property initProperty;
  ResourceBundle res;
  private static SimpleDateFormat navajoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//  private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy",Locale.getDefault());
  private static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMM-yyyy",SwingClient.getUserInterface().getLocale());
  private static SimpleDateFormat inputFormat1 = new SimpleDateFormat("dd-MM-yy");
  private static SimpleDateFormat inputFormat2 = new SimpleDateFormat("dd/MM/yy");
  private static SimpleDateFormat inputFormat3 = new SimpleDateFormat("ddMMyy");

  public DatePropertyField() {
    try {
      res = SwingClient.getUserInterface().getResource("com.dexels.sportlink.client.swing.properties");
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public Property getProperty() {
    return initProperty;
  }

  public void setProperty(Property p){

    if (p==null) {
      return;
    }

    if (!p.getType().equals(Property.DATE_PROPERTY)) {
      System.err.println("Warning: Setting date field to non date property of type: "+p.getType());
    }

    initProperty = p;

    try{
      if (Date.class.isInstance(p.getValue())) {

        setDate((Date)p.getValue());
      }
      else {

          setDate((String)p.getValue());
       }
      toolTipText = res.getString(p.getName());
      setToolTipText(toolTipText);
    }catch(Exception e){
      toolTipText = p.getName();
      setToolTipText(toolTipText);
    }
    setChanged(false);
    setEditable(p.isEditable());
  }


  public Date getDate() throws ParseException {
    String text = getText();
    if (text==null || text.equals("")) {
      return null;
    }
    // Try different parsers:
    try {
      return inputFormat1.parse(getText());
    } catch (ParseException pe1) {
      try {
        return inputFormat2.parse(getText());
      } catch (ParseException pe2) {
        try {
          return inputFormat3.parse(getText());
        } catch (ParseException pe3) {
          return displayDateFormat.parse(getText());
          // If this one fails, data entry person should get an other job (person is too creative!)
        }
      }
    }
  }

  public void setDate(Date d) {
    if (initProperty==null) {
      return;
    }

    if(d != null){
        initProperty.setValue(d);
        setText(displayDateFormat.format(d));
    } else {
      initProperty.setValue("");
      setText("");
    }
    setChanged(false);
  }

  public void setDate(String s) {
    if (s==null) {
      setText("");
      return;
    }

    try {
      Date d = navajoDateFormat.parse(s);
      if (d!=null) {
        setDate(d);
      }
      return;
    }
    catch (ParseException ex) {
      Date d = null;
      setDate(d);
    }

  }

  public void setToNow() {
    setDate(new Date());
  }

  public static String formatDate(String s) throws ParseException {
    //Date d = displayDateFormat.parse(s);
    Date d = new SimpleDateFormat().parse(s);
    return displayDateFormat.format(d);
  }

  private void jbInit() throws Exception {
    this.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        this_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        this_focusLost(e);
      }
    });
  }

  public void this_focusGained(FocusEvent e) {
    super.this_focusGained(e);
  }

  public void update() {
    boolean b = hasChanged();
    try{
       setDate(getDate());
    }catch(ParseException ex){
      setText("-");
      SwingClient.getUserInterface().setStatusText("Invalid date format");
      ErrorHandler eh = new ErrorHandler(ex);
    }
    setChanged(b);
  }

  public void this_focusLost(FocusEvent e) {
    super.this_focusLost(e);
    update();
   }
}