package com.dexels.navajo.tipi.components.echoimpl;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.components.echoimpl.impl.*;
import com.dexels.navajo.tipi.components.echoimpl.layout.*;
import echopoint.Label;
import echopoint.Panel;
import echopoint.PasswordField;
import nextapp.echo.*;
import nextapp.echo.event.*;
import nextapp.echo.table.*;

public class EchoPropertyComponent
    extends Panel
    implements TableCellRenderer {
  private Property myProperty = null;
  private boolean showLabel = true;
  Label l = null;
  int label_indent = 100;

  private final ArrayList myPropertyEventListeners = new ArrayList();

  public final void addPropertyEventListener(PropertyEventListener pel) {
    myPropertyEventListeners.add(pel);
  }

  public final void removePropertyEventListner(PropertyEventListener pel) {
    myPropertyEventListeners.remove(pel);
  }

  public final void firePropertyEvents(Property p, String eventType) {
    for (int i = 0; i < myPropertyEventListeners.size(); i++) {
      PropertyEventListener current = (PropertyEventListener) myPropertyEventListeners.get(i);
      current.propertyEventFired(p, eventType);
    }
  }

  public final void fireTipiEvent(String type) {
    firePropertyEvents(myProperty, type);
  }

  public void setProperty(Property p) throws NavajoException {
    setLayoutManager(new EchoGridBagLayout(2, 1));
    myProperty = p;
    if (p == null) {
      return;
    }
    removeAll();
    if (showLabel) {
      l = new Label();
      if (p.getDescription() != null) {
        l.setText(p.getDescription());
      }
      else {
        l.setText(p.getName());
      }
      EchoGridBagConstraints cons = new EchoGridBagConstraints(0, 0, 1, 1, 0.0, 0.0, 17, 0, new Insets(2, 2, 2, 2), label_indent, 0);
      add(l, cons);
    }

    String type = p.getType();
    if (type.equals(Property.SELECTION_PROPERTY)) {
      if (p.getCardinality().equals("1")) {
        final ListBox lb = new ListBox(p.getAllSelections().toArray());
        lb.setVisibleRowCount(1);
        EchoGridBagConstraints cons = new EchoGridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 17, 2, new Insets(2, 2, 2, 2), 0, 0);
        add(lb, cons);
        lb.setEnabled(p.isDirIn());
        Selection s = p.getSelected();
        lb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent ce) {
            System.err.println("AAP: ");
            try {
              Selection s = (Selection) lb.getSelectedValue();
              int index = lb.getSelectedIndex();
              System.err.println("Selected index: " + index + " value: " + s.getValue() + " name: " + s.getName());
              myProperty.setSelected(s.getName());
              fireTipiEvent("onValueChanged");
            }
            catch (NavajoException ex) {
              ex.printStackTrace();
            }
          }
        });
      }
      else {
        ListBox lb = new ListBox(p.getAllSelections().toArray());
        lb.setVisibleRowCount(8);
        EchoGridBagConstraints cons = new EchoGridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 17, 2, new Insets(2, 2, 2, 2), 0, 0);
        add(lb, cons);
        lb.setEnabled(p.isDirIn());
        lb.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
        for (int i = 0; i < p.getAllSelections().size(); i++) {
          Selection current = (Selection) p.getAllSelections().get(i);
          lb.setSelectedIndex(i, current.isSelected());
        }
        lb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent ce) {
            System.err.println("Noot: ");
            fireTipiEvent("onValueChanged");
          }
        });
      }
    }
    if (type.equals(Property.INTEGER_PROPERTY) || type.equals(Property.STRING_PROPERTY) || type.equals(Property.FLOAT_PROPERTY) || type.equals(Property.DATE_PROPERTY)) {
      final TipiEchoTextField tf = new TipiEchoTextField(p.getValue());
      tf.setColumnUnits(TipiEchoTextField.PERCENT_UNITS);
      tf.setColumns(100);
      EchoGridBagConstraints cons = new EchoGridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 17, 2, new Insets(2, 2, 2, 2), 0, 0);
      add(tf, cons);
      boolean isEdit = p.isDirIn();
      tf.setEditable(isEdit);
      tf.setEnabled(isEdit);
      tf.getDocument().addDocumentListener(new DocumentListener() {
        public void documentUpdate(DocumentEvent e) {
//             TipiEchoTextField tf = (TipiEchoTextField)e.getSource();
          System.err.println("DOCUMENTUPDATE OF TipiEchoTextField");
          String text = tf.getText();
          System.err.println("Found text: " + text);
          System.err.println("Old value: " + myProperty.getValue());
          myProperty.setValue(text);

        }
      });
      tf.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
          System.err.println("ACTIONPERFORMED OF TipiEchoTextField");
          String text = tf.getText();
          System.err.println("Found text: " + text);
          System.err.println("Old value: " + myProperty.getValue());
          myProperty.setValue(text);
        }
      });
      ;
    }
    if (type.equals(Property.BOOLEAN_PROPERTY)) {
      CheckBox cb = new CheckBox();
      cb.setSelected(p.getValue().equals("true"));
      EchoGridBagConstraints cons = new EchoGridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 17, 2, new Insets(2, 2, 2, 2), 0, 0);
      add(cb, cons);
      cb.setEnabled(p.isDirIn());
    }
    if (type.equals(Property.PASSWORD_PROPERTY)) {
      final PasswordField tf = new PasswordField();
      tf.setColumnUnits(TipiEchoTextField.PERCENT_UNITS);
      tf.setColumns(100);
      tf.setText(p.getValue());
      EchoGridBagConstraints cons = new EchoGridBagConstraints(1, 0, 1, 1, 0.0, 0.0, 17, 2, new Insets(2, 2, 2, 2), 0, 0);
      add(tf, cons);
      boolean isEdit = p.isDirIn();
      tf.setEditable(isEdit);
      tf.setEnabled(isEdit);
      tf.getDocument().addDocumentListener(new DocumentListener() {
        public void documentUpdate(DocumentEvent e) {
//             TipiEchoTextField tf = (TipiEchoTextField)e.getSource();
          System.err.println("DOCUMENTUPDATE OF TipiEchoTextField");
          String text = tf.getText();
          System.err.println("Found text: " + text);
          System.err.println("Old value: " + myProperty.getValue());
          myProperty.setValue(text);
        }
      });
      tf.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          TipiEchoTextField tf = (TipiEchoTextField) e.getSource();
          System.err.println("ACTIONPERFORMED OF TipiEchoTextField");
          String text = tf.getText();
          System.err.println("Found text: " + text);
          System.err.println("Old value: " + myProperty.getValue());
          myProperty.setValue(text);
        }
      });
    }
  }

  public Component getTableCellRendererComponent(Table table, Object value, int column, int row) {
    try {
      showLabel = false;
      setProperty( (Property) value);
    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    return this;
  }

  public void setLabelIndent(int indent) {
    label_indent = indent;
    try {
      setProperty(getProperty());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Property getProperty() {
    return myProperty;
  }

}
