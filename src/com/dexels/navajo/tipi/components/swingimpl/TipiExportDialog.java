package com.dexels.navajo.tipi.components.swingimpl;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiExportDialog
    extends TipiDialog {
//  private JDialog d = null;
  TipiSwingExportSortingPanel sp;
  TipiSwingExportSeparatorPanel sep;
  private String msgPath;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton proceedButton = new JButton();
  JButton cancelButton = new JButton();
  JPanel container;
  JButton backButton = new JButton();
  private int current = 0;
  private Message data;

  private JToolBar myBar = new JToolBar();

  public TipiExportDialog() {
  }

  public Object createContainer() {
    Object c = super.createContainer();
    setContainer(c);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return c;
  }

  private final void jbInit() throws Exception {
    backButton.setEnabled(false);
    container = new JPanel();

    getSwingContainer().setLayout(gridBagLayout1);
    proceedButton.setText("Verder");
    proceedButton.addActionListener(new TipiExportDialog_proceedButton_actionAdapter(this));
    cancelButton.setText("Annuleren");
    cancelButton.addActionListener(new TipiExportDialog_cancelButton_actionAdapter(this));
    backButton.setText("Terug");
    backButton.addActionListener(new TipiExportDialog_backButton_actionAdapter(this));
    getSwingContainer().add(container, new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    container.setLayout(new CardLayout());
    sp = new TipiSwingExportSortingPanel();
    sep = new TipiSwingExportSeparatorPanel();
    container.add(sp, "Sort");
    container.add(sep, "Separator");
    myBar.setFloatable(false);
    getSwingContainer().add(myBar, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0
        , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    myBar.add(proceedButton);
    myBar.add(cancelButton);
    myBar.add(backButton);
    CardLayout c = (CardLayout) container.getLayout();
    c.first(container);
  }

  public void setContainerLayout(Object layout) {
  }

  public void setComponentValue(String name, Object value) {
    super.setComponentValue(name, value);
    if ("messagepath".equals(name)) {
      msgPath = (String) value;
      data = getNavajo().getMessage(msgPath);
      sp.setMessage(data);
    }
  }

  public Object getComponentValue(String name) {
    if ("messagepath".equals(name)) {
      return msgPath;
    }
    return super.getComponentValue(name);
  }

  void proceedButton_actionPerformed(ActionEvent e) {
    if (current == 1) {
      Vector props = sp.getExportedPropertyNames();
      String[] filter = null;
      String separator = sep.getSelectedSeparator();
      exportData(props, filter, separator, sep.isHeaderSelected());
//      d.setVisible(false);
      myContext.disposeTipiComponent(this);
      return;
    }
    backButton.setEnabled(true);
    CardLayout c = (CardLayout) container.getLayout();
    c.next(container);
    current++;
    if (current == 1) {
      proceedButton.setText("Voltooien");
    }
    else {
      proceedButton.setText("Verder >>");
    }
  }

  private final void exportTitles(Message current, Vector properties, String separator, Writer output) throws IOException {
    StringBuffer currentLine = new StringBuffer();
    for (int j = 0; j < properties.size(); j++) {
      Property current_prop = current.getProperty( (String) properties.get(j));
      String propName = current_prop.getDescription();
      output.write(propName);
      if (j < properties.size() - 1) {
        output.write(separator);
      }
    }
    output.write("\n");
  }

  private final void exportData(Vector properties, String[] filter, String separator, boolean addTitles) {
    boolean filtering = false;
    HashMap descIdMap = sp.getDescriptionIdMap();
    HashMap descPropMap = sp.getDescriptionPropertyMap();
    if (data != null) {
      JFileChooser fd = new JFileChooser("Opslaan");
      fd.showSaveDialog( (Container) (this.getTipiParent().getContainer()));
      File out = fd.getSelectedFile();
      if (out == null) {
        return;
      }
      FileWriter fw = null;
      ArrayList subMsgs = data.getAllMessages();
      try {
        fw = new FileWriter(out);
        if (subMsgs.size() > 0 && addTitles) {
          Message first = (Message) subMsgs.get(0);
          exportTitles(first, properties, separator, fw);
        }
      }
      catch (IOException ex1) {
        ex1.printStackTrace();
      }
      for (int i = 0; i < subMsgs.size(); i++) {
        Message current = (Message) subMsgs.get(i);
        //ArrayList props = current.getAllProperties();
        boolean new_line = true;
        boolean line_complies_to_filter = false;
        String line = "";
        for (int j = 0; j < properties.size(); j++) {
          Property current_prop = current.getProperty( (String) properties.get(j));
          String propValue;
          if (current_prop.getType() == Property.DATE_PROPERTY) {
            Date d = (Date) current_prop.getTypedValue();
            java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd-MM-yyyy");
            propValue = df.format(d);
          }
          else {
            propValue = current_prop.getValue();
          }
          if (properties.contains(current_prop.getName())) {
            line_complies_to_filter = true;
            if (new_line) {
              line = line + "\"" + propValue + "\"";
              new_line = false;
            }
            else {
              line = line + separator + "\"" + propValue + "\"";
            }
          }
        }
        // Write the constructed line
        try {
          if (line_complies_to_filter) {
            fw.write(line + System.getProperty("line.separator"));
          }
        }
        catch (IOException ex) {
          ex.printStackTrace();
        }
      }
      try {
        fw.flush();
        fw.close();
      }
      catch (IOException ex2) {
        ex2.printStackTrace();
      }
    }
  }

  void cancelButton_actionPerformed(ActionEvent e) {
    myContext.disposeTipiComponent(this);
  }

  void backButton_actionPerformed(ActionEvent e) {
    CardLayout c = (CardLayout) container.getLayout();
    c.previous(container);
    proceedButton.setEnabled(true);
    current--;
    if (current == 0) {
      backButton.setEnabled(false);
    }
    if (current == 1) {
      proceedButton.setText("Voltooien");
    }
    else {
      proceedButton.setText("Verder");
    }
  }
}

class TipiExportDialog_proceedButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;
  TipiExportDialog_proceedButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.proceedButton_actionPerformed(e);
  }
}

class TipiExportDialog_cancelButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;
  TipiExportDialog_cancelButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cancelButton_actionPerformed(e);
  }
}

class TipiExportDialog_backButton_actionAdapter
    implements java.awt.event.ActionListener {
  TipiExportDialog adaptee;
  TipiExportDialog_backButton_actionAdapter(TipiExportDialog adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.backButton_actionPerformed(e);
  }
}
