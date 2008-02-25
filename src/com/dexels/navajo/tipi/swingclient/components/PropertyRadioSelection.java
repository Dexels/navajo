package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.JPanel;
import com.dexels.navajo.document.Property;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.util.*;
import com.dexels.navajo.document.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

/**
 * <p>Title: Seperate project for Navajo Swing client</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Dexels</p>
 * @author not attributable
 * @version 1.0
 */

public final class PropertyRadioSelection extends JPanel
    implements PropertyControlled, Ghostable, ActionListener {
  private Property myProperty = null;
  private final ButtonGroup myGroup = new ButtonGroup();

  private final Map selectionMap = new HashMap();
  private final ArrayList buttonList = new ArrayList();
  private final ArrayList myActionListeners = new ArrayList();

  private static final int VERTICAL = 1;
  private static final int HORIZONTAL = 2;

  private int direction = VERTICAL;

  public PropertyRadioSelection() {
    setVertical();

  }

  public final void setVertical() {
//    setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
      direction = VERTICAL;
    setLayout(new GridBagLayout());
//    doLayout();
  }
  public final void setHorizontal() {
      direction = HORIZONTAL;
      setLayout(new GridBagLayout());
//    setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
    doLayout();
  }

  public void gainFocus(){
    // gar nichts
  }

  public final Property getProperty() {
    return myProperty;
  }

  public final boolean isGhosted() {
    return false;
  }

  public final void setGhosted(boolean b) {
  }

  public final void setProperty(Property p) {
    removeAll();
    selectionMap.clear();
    buttonList.clear();
    if (p==null) {
      //System.err.println("Null prop");
      return;
    }
    myProperty = p;
    if (!Property.SELECTION_PROPERTY.equals(p.getType())) {
      throw new RuntimeException("No selection property.");
    }
    if (!Property.CARDINALITY_SINGLE.equals(p.getCardinality())) {
      throw new RuntimeException("No single cardinality.");
    }
    try {
      ArrayList al = p.getAllSelections();
      for (int i = 0; i < al.size(); i++) {
        Selection s = (Selection) al.get(i);
        if (direction==HORIZONTAL) {
            add(createButton(s),new GridBagConstraints(i,0,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
        } else {
            add(createButton(s),new GridBagConstraints(0,i,1,1,1,0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0));
        }
      }
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }

  }

  private final JComponent createButton(Selection s) {
    JRadioButton jr = new JRadioButton();
    selectionMap.put(jr,s);
    buttonList.add(jr.getModel());
    jr.setText(s.getName()!=null?s.getName():s.getValue());
    myGroup.add(jr);
    jr.addActionListener(this);
    if (s.isSelected()) {
      jr.setSelected(true);
    }
//    jr.addItemListener(new ItemListener() {
//      public void itemStateChanged(ItemEvent ce) {
//        if (ce.getStateChange()==ItemEvent.SELECTED) {
//          System.err.println("ce: "+ce.getSource());
//          updateProperty((JRadioButton)ce.getSource());
//        }w
//      }
//    });
    return jr;
  }

  public final void update() {
  }

  public int getSelectedIndex() {
    ButtonModel b = myGroup.getSelection();
    if (b==null) {
      return -1;
    }
    Integer i = new Integer(buttonList.indexOf(b));
    if (i==null) {
      System.err.println("Hmm weird. Unknown radiobutton, or something.");
//      System.err.println("MAP: "+indexMap.toString()+" aap: "+b);
      return -1;
    }
    return i.intValue();
  }

  public final void actionPerformed(ActionEvent e) {
	  System.err.println("Radio action performed");
    updateProperty((JRadioButton)e.getSource());
  }
  protected void printComponent(Graphics g) {
    Color cc = g.getColor();
    g.setColor(Color.white);
    g.fillRect(0,0,getWidth(),getHeight());
    g.setColor(cc);
    Color c = getBackground();
    setBackground(Color.white);
    super.printComponent(g);
    setBackground(c);
  }

  private final void updateProperty(JRadioButton source) {
    if (myProperty==null) {
      return;
    }
    if (source==null) {
        System.err.println("Tsja, updateprop with null source");
    }
    try {
      Selection s = (Selection) selectionMap.get(source);
      if (s != null) {
        myProperty.setSelected(s);
      }
      else {
        myProperty.clearSelections();
      }
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
//    for (int i = 0; i < myActionListeners.size(); i++) {
//      ActionListener current = (ActionListener)myActionListeners.get(i);
//      current.actionPerformed(new ActionEvent(source,-1,""));
//    }

  }

  public final void addActionListener(ActionListener al) {
    myActionListeners.add(al);
  }

  public final void removeActionListener(ActionListener al) {
    myActionListeners.remove(al);
  }

}
