package com.dexels.navajo.tipi.components;

import java.awt.Container;
import com.dexels.navajo.tipi.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import com.dexels.navajo.tipi.impl.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.dexels.navajo.tipi.tipixml.XMLElement;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import java.util.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.tree.TreePath;
import tipi.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiInstantiationPanel extends DefaultTipi{
  private JPanel myContainer = new JPanel();
  final JRadioButton fromScratch;
  final JComboBox newClassList;
  final JTextField newId;
  final JTextField newLocation;
  final JButton newLocationTreeButton;
  final JRadioButton fromDefinition;
  final JComboBox defList;
  final JTextField defId;
  final JTextField defLocation;
  final JButton defLocationTreeButton;
  JTable attributeTable;


  public TipiInstantiationPanel() {
    myContainer.setLayout(new GridBagLayout());

    JPanel instancePanel = new JPanel(new GridBagLayout());
    instancePanel.setBorder(new TitledBorder("Instance"));

    fromScratch = new JRadioButton("New");
    JLabel newClassLabel = new JLabel("Class");
    JLabel newIdLabel = new JLabel("Id");
    JLabel newLocationLabel = new JLabel("Location");
    newClassList = new JComboBox(getComponentClasses());
    newId = new JTextField();
    newLocation = new JTextField();
    newLocationTreeButton = new JButton("tree");
    newLocationTreeButton.setIcon(new ImageIcon(MainApplication.class.getResource("tree_button.gif")));

    instancePanel.add(fromScratch, new GridBagConstraints(0,0,3,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newClassLabel, new GridBagConstraints(0,1,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newClassList, new GridBagConstraints(1,1,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newIdLabel, new GridBagConstraints(0,2,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newId, new GridBagConstraints(1,2,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newLocationLabel, new GridBagConstraints(0,3,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newLocation, new GridBagConstraints(1,3,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(newLocationTreeButton, new GridBagConstraints(2,3,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,2,2,2),0,0));

    fromDefinition = new JRadioButton("Predifined");
    defList = new JComboBox(getComponentDefinitions());
    defId = new JTextField();
    defLocation = new JTextField();
    JLabel defDefLabel = new JLabel("Definition");
    JLabel defLocationLabel = new JLabel("Location");
    JLabel defIdLabel = new JLabel("Id");
    defLocationTreeButton = new JButton("tree");
    defLocationTreeButton.setIcon(new ImageIcon(MainApplication.class.getResource("tree_button.gif")));

    instancePanel.add(fromDefinition, new GridBagConstraints(0,4,3,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defDefLabel, new GridBagConstraints(0,5,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defList, new GridBagConstraints(1,5,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defIdLabel, new GridBagConstraints(0,6,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defId, new GridBagConstraints(1,6,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defLocationLabel, new GridBagConstraints(0,7,1,1,0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defLocation, new GridBagConstraints(1,7,1,1,1.0,0,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(2,2,2,2),0,0));
    instancePanel.add(defLocationTreeButton, new GridBagConstraints(2,7,1,1,0.0,0,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2,2,2,2),0,0));

    JPanel attributePanel = new JPanel(new BorderLayout());
    attributePanel.setBorder(new TitledBorder("Attributes"));

    myContainer.add(instancePanel, new GridBagConstraints(0,0,1,1,1.0,0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2,2,2,2),0,0));
    myContainer.add(attributePanel, new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2,2,2,2),0,0));
    attributeTable = new JTable();
    attributeTable.setModel(new TipiAttributeTableModel());
    attributeTable.setDefaultRenderer(Object.class, new TipiAttributeTableRenderer());
    attributeTable.setDefaultEditor(Object.class, new TipiAttributeTableEditor());
    attributeTable.setCellSelectionEnabled(true);
    JScrollPane scroll = new JScrollPane();
    attributePanel.add(scroll, BorderLayout.CENTER);
    scroll.getViewport().add(attributeTable);

    // Initial state and action Listeners

    fromScratch.setSelected(true);
    fromDefinition.setSelected(!fromScratch.isSelected());
    defList.setEnabled(!fromScratch.isSelected());
    defId.setEnabled(!fromScratch.isSelected());
    defLocation.setEnabled(!fromScratch.isSelected());

    fromScratch.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        fromDefinition.setSelected(!fromScratch.isSelected());
        defList.setEnabled(!fromScratch.isSelected());
        defId.setEnabled(!fromScratch.isSelected());
        defLocation.setEnabled(!fromScratch.isSelected());
        defLocationTreeButton.setEnabled(!fromScratch.isSelected());
        newClassList.setEnabled(fromScratch.isSelected());
        newId.setEnabled(fromScratch.isSelected());
        newLocation.setEnabled(fromScratch.isSelected());
        newLocationTreeButton.setEnabled(fromScratch.isSelected());
      }
    });

    fromDefinition.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent e){
        fromScratch.setSelected(!fromDefinition.isSelected());
        newClassList.setEnabled(!fromDefinition.isSelected());
        newId.setEnabled(!fromDefinition.isSelected());
        newLocation.setEnabled(!fromDefinition.isSelected());
        newLocationTreeButton.setEnabled(!fromScratch.isSelected());
        defList.setEnabled(fromDefinition.isSelected());
        defId.setEnabled(fromDefinition.isSelected());
        defLocation.setEnabled(fromDefinition.isSelected());
        defLocationTreeButton.setEnabled(fromDefinition.isSelected());
      }
    });

    defLocationTreeButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        defLocation.setText(getPath());
      }
    });

    newLocationTreeButton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        newLocation.setText(getPath());
      }
    });

    newClassList.addItemListener(new ItemListener(){
      public void itemStateChanged(ItemEvent e){
        updateAttributes();
      }
    });
  }

  private String getPath(){
    TipiComponentInstanceTreeDialog tid = new TipiComponentInstanceTreeDialog();
    tid.setLocationRelativeTo(this.getContainer());
    tid.setSize(new Dimension(300, 400));
    tid.setModal(true);
    tid.show();
    TreePath treePath = tid.getPath();

    //Temporarily implementation ALSO implemeneted in TipiAttributeTableEditor
    String sp = treePath.toString();
    sp = sp.substring(6, sp.length()-1);
    System.err.println("sp_cut: " + sp);

    StringTokenizer tok = new StringTokenizer(sp, ",");
    String path = "tipi:/";
    while(tok.hasMoreTokens()){
      path = path +"/" + tok.nextToken().trim();
    }
    return path;
  }

  public Container createContainer() {
    return myContainer;
  }

  private Vector getComponentClasses(){
    Vector v = new Vector();
    Map m = TipiContext.getInstance().getTipiClassDefMap();
    Iterator it = m.keySet().iterator();
    while(it.hasNext()){
      v.addElement(it.next());
    }
    return v;
  }

  private void updateAttributes(){
    Map m = getAvailableAttributes();
    ((TipiAttributeTableModel)attributeTable.getModel()).setAttributes(m, (XMLElement)TipiContext.getInstance().getTipiClassDefMap().get(newClassList.getSelectedItem()));
     attributeTable.revalidate();
  }

  private Map getAvailableAttributes(){
    Map m = new HashMap();
    if(fromScratch.isSelected()){
      Object clazz = newClassList.getSelectedItem();
      XMLElement elm = (XMLElement)TipiContext.getInstance().getTipiClassDefMap().get(clazz);
      if(elm != null){
        Vector children = elm.getChildren();
        for(int i=0;i<children.size();i++){
          XMLElement child = (XMLElement)children.get(i);
          if(child.getName().equals("values")){
            Vector values = child.getChildren();
            for(int j=0;j<values.size();j++){
              XMLElement value = (XMLElement)values.get(j);
              TipiValue tv = new TipiValue();
              tv.load(value);
              //System.err.println("Adding TipiValue: " + tv.getName());
              m.put(tv.getName(), tv);
            }
          }
        }
      }
    }else{
      System.err.println("Not yet supported!");
    }
    return m;
  }

  protected void performComponentMethod(String name, XMLElement invocation, TipiComponentMethod compMeth) {
   if (name.equals("instantiate")) {
     instantiate();
   }
   //    super.performComponentMethod( name,  invocation,  compMeth);
 }

  private void instantiate(){
    String defname;
    String id;
    String location;
    Object clazz;
    try {
      if(fromScratch.isSelected()){
        id = newId.getText();
        clazz = newClassList.getSelectedItem();
        location = newLocation.getText();
        String componentPath = location + "/"+id;
        TipiPathParser tp = new TipiPathParser( (TipiComponent)this, TipiContext.getInstance(), componentPath);
        TipiComponent comp = (TipiComponent) tp.getTipi();
        XMLElement xe = new CaseSensitiveXMLElement();
        xe.setName("component-instance");
        xe.setAttribute("name", id);
        xe.setAttribute("class", clazz.toString());

        Map tipiValueMap = ((TipiAttributeTableModel)attributeTable.getModel()).getValues();
        Iterator it = tipiValueMap.keySet().iterator();
        while(it.hasNext()){
          String attr = (String)it.next();
          TipiValue current = (TipiValue)tipiValueMap.get(attr);
          String value = current.getValue();
          if(value != null && !value.equals("")){
            xe.setAttribute(attr, value);
            System.err.println("Attribute: " + attr + " set to: " + value);
          }
        }


        TipiComponent inst = TipiContext.getInstance().instantiateComponent(xe);
        inst.setId(id);
        System.err.println("Looking for destination: [" + location + "]");
        TipiPathParser dpp = new TipiPathParser( (TipiComponent)this, TipiContext.getInstance(), location);
        TipiComponent dest = (TipiComponent) dpp.getTipi();
        if (dest != null) {
          dest.addComponent(inst, TipiContext.getInstance(), null);
        }
        else {
          System.err.println("Could not find destination tipi");
      }

      }else{
        id = defId.getText();
        clazz = TipiContext.getInstance().getTipiDefinitionMap().get(defList.getSelectedItem());
        TipiComponent inst = TipiContext.getInstance().instantiateComponent((XMLElement)clazz);
        location = defLocation.getText();
        TipiPathParser dpp = new TipiPathParser( (TipiComponent)this, TipiContext.getInstance(), location);
        TipiComponent dest = (TipiComponent) dpp.getTipi();
        if (dest != null) {
          dest.addComponent(inst, TipiContext.getInstance(), null);
        }
        else {
          System.err.println("Could not find destination tipi");
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  private Vector getComponentDefinitions(){
    Vector v = new Vector();
    Map m = TipiContext.getInstance().getTipiDefinitionMap();
    Iterator it = m.keySet().iterator();
    while(it.hasNext()){
      v.addElement(it.next());
    }
    return v;
  }

}