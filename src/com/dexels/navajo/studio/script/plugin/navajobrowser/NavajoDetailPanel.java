package com.dexels.navajo.studio.script.plugin.navajobrowser;

import java.util.*;

import java.awt.*;
import javax.swing.*;

import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.*;
import org.eclipse.ui.internal.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.studio.script.plugin.*;
import com.dexels.navajo.studio.script.plugin.editors.*;
import com.dexels.navajo.swingclient.components.*;
import com.dexels.navajo.swingclient.components.GenericPropertyComponent;

import java.awt.event.*;
import com.dexels.navajo.client.*;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class NavajoDetailPanel
    extends JPanel {

//  private NavajoDetail myComponent = null;
//  private TipiContext myContext = null;

  private String selectedConnection = null;
  private String selectedService = null;
  private Navajo selectedNavajo = null;

  private final JPanel myPanel = new JPanel(new GridBagLayout());
  private final JScrollPane jsp = new JScrollPane(myPanel);
  private final JPanel myBar = new JPanel();
  private final List panelList = new ArrayList();
  private IFile myCurrentFile;

//  private final JPanel statusPanel = new JPanel();
//  private final JLabel statusLabel = new JLabel();
//  private final JButton insertListenerButton = new JButton("Insert Listener");
//  private final JButton loadNavajoButton = new JButton("Load with this Navajo");

//  private final NavajoBrowser myComponent;
  
  public NavajoDetailPanel() {
//      myComponent = nb;
      setLayout(new BorderLayout());
    FlowLayout barLayout = new TipiSwingWrapLayout(FlowLayout.LEFT);
    barLayout.setAlignment(FlowLayout.LEFT);
    myBar.setLayout(barLayout);
    add(jsp, BorderLayout.CENTER);
    add(myBar, BorderLayout.SOUTH);
//     statusPanel.add(insertListenerButton);
//    statusPanel.add(loadNavajoButton);
//    statusPanel.add(statusLabel);
//    add(statusPanel, BorderLayout.NORTH);
//    this.myComponent = myComponent;
//    myContext = tc;
//    ((StudioTipiContext)myContext).addNavajoTemplateListener(this);
  }

  public void navajoLoaded(String service, Navajo n) {
  }

  public void navajoSelected(String service, final Navajo n, IFile myFile) {
      myCurrentFile = myFile;
//    System.err.println("Thread for navajo selected: "+Thread.currentThread().toString());
     final String serviceName;
    if (service.indexOf(":")!=-1) {
      StringTokenizer st = new StringTokenizer(service,":");
      selectedConnection = st.nextToken();
      serviceName = st.nextToken();
    } else {
      serviceName = service;
    }

    if (n == null) {
        return;
    }
//    myComponent.runSyncInEventThread(new Runnable() {
//      public void run() {
        selectedService = serviceName;
        selectedNavajo = n;
        loadPanel(n, serviceName);
//        validateTipiStatus();
//      }
//    });
  }

//  public void navajoRemoved(String service) {
//    if (service.equals(selectedService)) {
//      clearPanel();
//      selectedService = null;
//      selectedNavajo = null;
//    }
//  }

  public void clearPanel() {
    myPanel.removeAll();
    myPanel.invalidate();
    Container c = myPanel.getParent();
    c.remove(myPanel);
    c.add(myPanel);
    myBar.removeAll();
    panelList.clear();
  }

  public void loadPanel(Navajo n, String service) {
    if (n == null) {
      clearPanel();
      return;
    }
    myBar.removeAll();
    myPanel.removeAll();
    myPanel.invalidate();
    Container c = myPanel.getParent();
    c.remove(myPanel);
    c.add(myPanel);
    myPanel.setBorder(BorderFactory.createTitledBorder(service));
    try {
      ArrayList myList = n.getAllMessages();
      addMessage(myList, myPanel, 0);

    }
    catch (NavajoException ex) {
      ex.printStackTrace();
    }
    ArrayList al = n.getAllMethods();
    for (int i = 0; i < al.size(); i++) {
      final Method m = (Method) al.get(i);
      JButton jb = new JButton(m.getName());
      jb.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
              Workbench.getInstance().getDisplay().syncExec(new Runnable() {

                public void run() {
                    if (myEditor!=null) {
                        myEditor.doSave(null);
                    }

                    IProject ipp = myCurrentFile.getProject();
                    IFile scriptFile = NavajoScriptPluginPlugin.getDefault().getScriptFile(ipp, m.getName());
                    try {
                        NavajoScriptPluginPlugin.getDefault().runNavajo(scriptFile,myCurrentFile);
                    } catch (CoreException e1) {
                        e1.printStackTrace();
                    }  
                    
                }});
         }
      });
      myBar.add(jb);
      myBar.doLayout();
    }
    repaint();
  }

  public void addMessage(ArrayList myList, JPanel currentPanel, int depth) {
    for (int i = 0; i < myList.size(); i++) {
      Message m = (Message) myList.get(i);
      if (Message.MSG_TYPE_ARRAY.equals(m.getType()) && m.getArraySize() > 0) {
         Message first = m.getMessage(0);
        if (first.getAllMessages().size() > 0) {
          createMultiTable(m, currentPanel, i, depth);
        }
        else {
          createTable(m, currentPanel, i);
        }
      }
      else {
        // create a form
        createForm(m, currentPanel, i, depth);
      }

    }
  }

  private void createMultiTable(Message current, JPanel currentPanel, int index,
                                int depth) {
    if (depth % 2 == 0) {
      JTabbedPane jt = new JTabbedPane();
      currentPanel.add(jt,
                       new GridBagConstraints(0, index, 1, 1, 1, 1,
                                              GridBagConstraints.WEST,
                                              GridBagConstraints.BOTH,
                                              new Insets(2, 2, 2, 2), 0, 0));
      for (int i = 0; i < current.getArraySize(); i++) {
        Message m = current.getMessage(i);
        JPanel jp = new JPanel();
        jp.setLayout(new GridBagLayout());
        jt.add(jp, m.getName() + "(" + i + ")");
        addMessage(m.getAllMessages(), jp, depth + 1);
      }
    }
    else {
      JPanel jt = new JPanel();
      currentPanel.add(jt,
                       new GridBagConstraints(0, index, 1, 1, 1, 1,
                                              GridBagConstraints.WEST,
                                              GridBagConstraints.BOTH,
                                              new Insets(2, 2, 2, 2), 0, 0));

      jt.setLayout(new GridBagLayout());
      for (int i = 0; i < current.getArraySize(); i++) {
        Message m = current.getMessage(i);
        JPanel jp = new JPanel();
        jt.add(jp, new GridBagConstraints(0, i, 1, 1, 1, 1,
                                          GridBagConstraints.WEST,
                                          GridBagConstraints.HORIZONTAL,
                                          new Insets(2, 2, 2, 2), 0, 0)
               );
        jp.setLayout(new GridBagLayout());
        addMessage(m.getAllMessages(), jp, depth + 1);
      }
    }
  }

  private void createTable(Message current, JPanel currentPanel, int index) {
    MessageTablePanel mtp = new MessageTablePanel();
    mtp.setFiltersVisible(true);
    mtp.setPreferredSize(new Dimension(100,200));
    mtp.setRefreshAfterEdit(true);
    Message first = current.getMessage(0);
    ArrayList propertyList = first.getAllProperties();
    for (int i = 0; i < propertyList.size(); i++) {
      Property p = (Property) propertyList.get(i);
      mtp.addColumn(p.getName(),
                    p.getDescription() == null ? p.getName() : p.getDescription(),
                    p.isDirIn());
    }
    mtp.setMessage(current);
    mtp.setBorder(BorderFactory.createTitledBorder(current.getName()));
    currentPanel.add(mtp,
                     new GridBagConstraints(0, index, 1, 1, 1, 1,
                                            GridBagConstraints.WEST,
                                            GridBagConstraints.BOTH,
                                            new Insets(2, 2, 2, 2), 0, 0));
  }

  private void createForm(Message m, JPanel currentPanel, int index, int depth) {
    ArrayList subList = m.getAllMessages();
    JPanel jp = new JPanel();
    jp.setLayout(new GridBagLayout());
    String name;
    if (m.isArrayMessage()) {
      name = m.getName()+"("+index+")";
    } else {
      name = m.getName();
    }
    jp.setBorder(BorderFactory.createTitledBorder(name));
    addMessage(subList, jp, depth + 1);
    currentPanel.add(jp,
                     new GridBagConstraints(0, index, 1, 1, 1, 0,
                                            GridBagConstraints.WEST,
                                            GridBagConstraints.HORIZONTAL,
                                            new Insets(2, 2, 2, 2), 0, 0));
    ArrayList propertyList = m.getAllProperties();
    addProperties(jp, propertyList);
    currentPanel.doLayout();
    jp.repaint();
  }

  public void addProperties(JPanel jp, ArrayList propertyList) {
    int startCount = jp.getComponentCount();
    String path = null;
    for (int i = 0; i < propertyList.size(); i++) {
      Property p = (Property) propertyList.get(i);
      GenericPropertyComponent bp = new GenericPropertyComponent();
      bp.setProperty(p);
      bp.setLabelIndent(150);
       try {
        path = p.getFullPropertyName();
      }
      catch (NavajoException ex) {
        ex.printStackTrace();
      }
        jp.add(bp,
             new GridBagConstraints(1, i, 1, 1, 1, 0, GridBagConstraints.WEST,
                                    GridBagConstraints.HORIZONTAL,
                                    new Insets(2, 2, 2, 2), 0, 0));

      bp.repaint();
    }

  }

  private IEditorPart myEditor = null;

  /**
 * @param example
 */
public void setEditor(IEditorPart ie) {
    myEditor = ie;
}

}
