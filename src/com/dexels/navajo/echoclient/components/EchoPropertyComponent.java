package com.dexels.navajo.echoclient.components;

import nextapp.echo.Panel;
import com.dexels.navajo.document.*;
import nextapp.echo.*;
import echopoint.*;
import nextapp.echo.event.*;
import nextapp.echo.table.TableCellRenderer;
import echopoint.Label;
import echopoint.TextField;
import echopoint.PasswordField;

public class EchoPropertyComponent extends Panel implements TableCellRenderer {
  private Property myProperty = null;
  private boolean showLabel = true;

  public void setProperty(Property p) throws NavajoException {
    myProperty = p;
    if (p==null) {
      return;
    }
    removeAll();
    if (showLabel) {

      Label l = new Label();
      if (p.getDescription()!=null) {
        l.setText(p.getDescription());
      } else {
        l.setText(p.getName());
      }
      add(l);
    }

    String type = p.getType();
    if (type.equals(Property.SELECTION_PROPERTY)) {
      if (p.getCardinality().equals("1")) {
        final ListBox lb = new ListBox(p.getAllSelections().toArray());
        lb.setVisibleRowCount(1);
        add(lb);
        lb.setEnabled(p.isDirIn());
        Selection s = p.getSelected();
        lb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent ce) {
            System.err.println("AAP: ");
           try {
             Selection s = (Selection)lb.getSelectedValue();
             int index = lb.getSelectedIndex();
             System.err.println("Selected index: "+index+" value: "+s.getValue()+" name: "+s.getName());
              myProperty.setSelected(s.getName());
            }
            catch (NavajoException ex) {
              ex.printStackTrace();
            }
          }
        });
      } else {
        ListBox lb = new ListBox(p.getAllSelections().toArray());
        lb.setVisibleRowCount(8);
        add(lb);
        lb.setEnabled(p.isDirIn());
        lb.setSelectionMode(ListSelectionModel.MULTIPLE_SELECTION);
        for (int i = 0; i < p.getAllSelections().size(); i++) {
          Selection current = (Selection)p.getAllSelections().get(i);
          lb.setSelectedIndex(i,current.isSelected());
        }
        lb.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent ce) {
            System.err.println("Noot: ");
          }
        });
      }
    }
    if (type.equals(Property.INTEGER_PROPERTY) || type.equals(Property.STRING_PROPERTY) || type.equals(Property.FLOAT_PROPERTY) || type.equals(Property.DATE_PROPERTY)) {
        final TextField tf = new TextField(p.getValue());
        add(tf);
        boolean isEdit = p.isDirIn();
        tf.setEditable(isEdit);
        tf.setEnabled(isEdit);
        tf.getDocument().addDocumentListener(new DocumentListener() {
           public void documentUpdate(DocumentEvent e) {
//             TextField tf = (TextField)e.getSource();
              System.err.println("DOCUMENTUPDATE OF TEXTFIELD");
              String text = tf.getText();
              System.err.println("Found text: "+text);
              System.err.println("Old value: "+myProperty.getValue());
              myProperty.setValue(text);

           }
        });
        tf.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TextField tf = (TextField)e.getSource();
            System.err.println("ACTIONPERFORMED OF TEXTFIELD");
            String text = tf.getText();
            System.err.println("Found text: "+text);
            System.err.println("Old value: "+myProperty.getValue());
            myProperty.setValue(text);
          }
        });;
    }
    if (type.equals(Property.BOOLEAN_PROPERTY)) {
      CheckBox cb = new CheckBox();
      cb.setSelected(p.getValue().equals("true"));
      add(cb);
      cb.setEnabled(p.isDirIn());
    }
    if (type.equals(Property.PASSWORD_PROPERTY)) {
        final PasswordField tf = new PasswordField();
        tf.setText(p.getValue());
        add(tf);
        boolean isEdit = p.isDirIn();
        tf.setEditable(isEdit);
        tf.setEnabled(isEdit);
        tf.getDocument().addDocumentListener(new DocumentListener() {
           public void documentUpdate(DocumentEvent e) {
//             TextField tf = (TextField)e.getSource();
              System.err.println("DOCUMENTUPDATE OF TEXTFIELD");
              String text = tf.getText();
              System.err.println("Found text: "+text);
              System.err.println("Old value: "+myProperty.getValue());
              myProperty.setValue(text);

           }
        });
        tf.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            TextField tf = (TextField)e.getSource();
            System.err.println("ACTIONPERFORMED OF TEXTFIELD");
            String text = tf.getText();
            System.err.println("Found text: "+text);
            System.err.println("Old value: "+myProperty.getValue());
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


}
