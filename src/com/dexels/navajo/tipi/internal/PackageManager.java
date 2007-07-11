package com.dexels.navajo.tipi.internal;

import java.util.*;

import com.dexels.navajo.tipi.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class PackageManager {
	// JLabel jLabel1 = new JLabel();
	// JComboBox mainImplBox = new JComboBox();
	// JScrollPane optionalScroll = new JScrollPane();
	// TitledBorder titledBorder1;
	// JList jarList = new JList();
	// JScrollPane jScrollPane1 = new JScrollPane();
	// GridBagLayout gridBagLayout1 = new GridBagLayout();
	// JTextField pathField = new JTextField();
	// JLabel jLabel3 = new JLabel();
	// JButton deployButton = new JButton();
	private final Map requiredMap = new HashMap();
	private final Map mainImplMap = new HashMap();
	private final Map optionalImplMap = new HashMap();
	private TipiComponent myComponent = null;
	// TitledBorder titledBorder2;
	// JList optionalList = new JList();
	// JLabel statusLabel = new JLabel();
	private TipiContext myContext = null;
	private List packageSet = new ArrayList();

	public PackageManager() {
		// try {
		// jbInit();
		// }
		// catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// optionalList.setModel(new DefaultListModel());
		// jarList.setModel(new DefaultListModel());
		// pathField.setText(System.getProperty("user.dir"));
	}

	public void setContext(TipiContext tc) {
		myContext = tc;
	}

	// public void setComponent(TipiComponent t) {
	// myComponent = t;
	// }
	// public void loadPackageDefinition(XMLElement x) {
	// Vector v = x.getChildren();
	// for (int i = 0; i < v.size(); i++) {
	// XMLElement child = (XMLElement) v.get(i);
	// boolean isRequired = child.getBooleanAttribute("required", "true",
	// "false", false);
	// String type = child.getStringAttribute("type");
	// boolean isMainImpl = "mainimpl".equals(type);
	// if (isRequired) {
	// addRequiredImpl(child);
	// }
	// else {
	// if (isMainImpl) {
	// addMainImpl(child);
	// }
	// else {
	// addOptionalImpl(child);
	// }
	// }
	// }
	// doUpdate();
	// }

	// public void doUpdate() {
	// Set jarSet = new HashSet();
	// Set depSet = new HashSet();
	// Set idSet = new HashSet();
	// packageSet.clear();
	// Iterator it = requiredMap.keySet().iterator();
	// while (it.hasNext()) {
	// String key = (String) it.next();
	// TipiPackage current = (TipiPackage) requiredMap.get(key);
	// jarSet.addAll(current.getJars());
	// depSet.addAll(current.getDeps());
	// idSet.add(current.getId());
	// packageSet.add(current);
	// }
	// TipiPackage main = (TipiPackage) mainImplBox.getSelectedItem();
	// jarSet.addAll(main.getJars());
	// depSet.addAll(main.getDeps());
	// idSet.add(main.getId());
	// packageSet.add(main);
	// Object[] o = optionalList.getSelectedValues();
	// for (int i = 0; i < o.length; i++) {
	// TipiPackage tp = (TipiPackage) o[i];
	// jarSet.addAll(tp.getJars());
	// depSet.addAll(tp.getDeps());
	// packageSet.add(tp);
	// }
	// ( (DefaultListModel) jarList.getModel()).removeAllElements();
	// Iterator iter = jarSet.iterator();
	// while (iter.hasNext()) {
	// String tp = (String) iter.next();
	// ( (DefaultListModel) jarList.getModel()).addElement(tp);
	// }
	// setDependenciesOk(idSet.containsAll(depSet));
	// }
	//
	// private final void addRequiredImpl(XMLElement xe) {
	// String id = xe.getStringAttribute("id", "");
	// if ("".equals(id)) {
	// return;
	// }
	// TipiPackage tp = new TipiPackage(xe);
	// requiredMap.put(id, tp);
	// }
	//
	// private final void addMainImpl(XMLElement xe) {
	// TipiPackage tp = new TipiPackage(xe);
	// String id = xe.getStringAttribute("id", "");
	// if ("".equals(id)) {
	// return;
	// }
	// mainImplMap.put(id, tp);
	// mainImplBox.addItem(tp);
	// }
	//
	// private final void addOptionalImpl(XMLElement xe) {
	// String id = xe.getStringAttribute("id", "");
	// if ("".equals(id)) {
	// return;
	// }
	// TipiPackage tp = new TipiPackage(xe);
	// optionalImplMap.put(id, tp);
	// ( (DefaultListModel) optionalList.getModel()).addElement(tp);
	// }
	//
	// private final void deploy() {
	// File f = new File(pathField.getText());
	// String sourcePath = "c:/projecten/NavajoStudioDemo/lib";
	// File source = new File(sourcePath);
	// ArrayList jars = new ArrayList();
	// for (int i = 0; i < jarList.getModel().getSize(); i++) {
	// String current = (String) jarList.getModel().getElementAt(i);
	// jars.add(current);
	// }
	// List classDefList = new ArrayList();
	// Iterator aap = packageSet.iterator();
	// System.err.println("Looping packageset:");
	// while (aap.hasNext()) {
	// TipiPackage tp = (TipiPackage) aap.next();
	// System.err.println("Adding: " + tp.getClassDef());
	// classDefList.add(tp.getClassDef());
	// }
	// try {
	// myContext.writeComponentMap(source, jars, f, classDefList);
	// }
	// catch (IOException ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// private final void setDependenciesOk(boolean b) {
	// statusLabel.setText(b ? "Ok" : "Dependency failed!");
	// statusLabel.setForeground(b ? Color.black : Color.red);
	// deployButton.setEnabled(b);
	// }
	//
	// void jbInit() throws Exception {
	// titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.
	// white, new Color(148, 145, 140)), "Optional packages");
	// titledBorder2 = new
	// TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148,
	// 145, 140)), "Required libraries:");
	// jLabel1.setText("Select implementation:");
	// this.setLayout(gridBagLayout1);
	// optionalScroll.setBorder(titledBorder1);
	// mainImplBox.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(ActionEvent e) {
	// mainImplBox_actionPerformed(e);
	// }
	// });
	// jLabel3.setText("Deploy application:");
	// deployButton.setText("Deploy");
	// deployButton.addActionListener(new java.awt.event.ActionListener() {
	// public void actionPerformed(ActionEvent e) {
	// deployButton_actionPerformed(e);
	// }
	// });
	// pathField.setText("");
	// jScrollPane1.setBorder(titledBorder2);
	// jarList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	// optionalList.addListSelectionListener(new
	// javax.swing.event.ListSelectionListener() {
	// public void valueChanged(ListSelectionEvent e) {
	// optionalList_valueChanged(e);
	// }
	// });
	// statusLabel.setText("Ok");
	// this.add(mainImplBox, new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
	// , GridBagConstraints.CENTER,
	// GridBagConstraints.HORIZONTAL,
	// new Insets(3, 3, 3, 3), 0, 0));
	// this.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
	// , GridBagConstraints.WEST,
	// GridBagConstraints.NONE,
	// new Insets(3, 3, 3, 3), 0, 0));
	// this.add(optionalScroll, new GridBagConstraints(0, 1, 3, 1, 1.0, 1.0
	// , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3,
	// 3), 0, 0));
	// optionalScroll.getViewport().add(optionalList, null);
	// this.add(jScrollPane1, new GridBagConstraints(0, 3, 3, 1, 1.0, 1.0
	// , GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(3, 3, 3,
	// 3), 0, 0));
	// this.add(pathField, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0
	// , GridBagConstraints.CENTER,
	// GridBagConstraints.HORIZONTAL,
	// new Insets(3, 3, 3, 3), 0, 0));
	// this.add(jLabel3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
	// , GridBagConstraints.CENTER,
	// GridBagConstraints.NONE,
	// new Insets(3, 3, 3, 3), 0, 0));
	// this.add(deployButton, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
	// , GridBagConstraints.CENTER,
	// GridBagConstraints.NONE,
	// new Insets(3, 3, 3, 3), 0, 0));
	// this.add(statusLabel, new GridBagConstraints(0, 5, 3, 1, 1.0, 0.0
	// , GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(0,
	// 0, 0, 0), 0, 0));
	// jScrollPane1.getViewport().add(jarList, null);
	// }
	//
	// void mainImplBox_actionPerformed(ActionEvent e) {
	// doUpdate();
	// }
	//
	// void deployButton_actionPerformed(ActionEvent e) {
	// deploy();
	// try {
	// myComponent.performTipiEvent("onFinished", null, false);
	// }
	// catch (TipiException ex) {
	// ex.printStackTrace();
	// }
	// }
	//
	// void optionalList_valueChanged(ListSelectionEvent e) {
	// doUpdate();
	// }
}
