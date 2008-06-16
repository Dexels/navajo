package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import javax.swing.*;

import com.dexels.navajo.document.*;

import java.util.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.base.*;

import java.awt.event.*;
import java.beans.*;
import java.io.*;

/**
 * <p>
 * Title: Seperate project for Navajo Swing client
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public final class MultipleSelectionPropertyPickList extends JPanel implements PropertyControlled {
	JScrollPane jScrollPane1 = new JScrollPane();
	JScrollPane jScrollPane2 = new JScrollPane();
	JButton selectButton = new JButton();
	JButton deselectButton = new JButton();
	DefaultListModel selectedModel = new DefaultListModel();
	DefaultListModel deselectedModel = new DefaultListModel();
	JList notSelectedList = new JList(deselectedModel);
	JList selectedList = new JList(selectedModel);
	GridBagLayout gridBagLayout1 = new GridBagLayout();
	private Property myProperty;
	private PropertyChangeListener myPropertyListener = null;

	private int visibleRows = 6;

	public MultipleSelectionPropertyPickList() {
		try {
			jbInit();
			selectedList.setCellRenderer(new PropertyCellRenderer());
			notSelectedList.setCellRenderer(new PropertyCellRenderer());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void gainFocus() {
		// gar nichts
	}

	private final void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);
		// setBackground(Color.red);
		selectButton.setText("");
		selectButton.addActionListener(new MultipleSelectionPropertyPickList_selectButton_actionAdapter(this));
		selectButton.setInputVerifier(null);
		selectButton.setIcon(new ImageIcon(MultipleSelectionPropertyPickList.class.getResource("arrow_right.gif")));
		selectButton.setMargin(new Insets(2, 2, 2, 2));
		deselectButton.setIcon(new ImageIcon(MultipleSelectionPropertyPickList.class.getResource("arrow_left.gif")));
		deselectButton.setMargin(new Insets(2, 2, 2, 2));
		deselectButton.addActionListener(new MultipleSelectionPropertyPickList_deselectButton_actionAdapter(this));
		this.add(jScrollPane1, new GridBagConstraints(0, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
				2, 2, 2, 2), -1000, 0));
		jScrollPane1.getViewport().add(notSelectedList, null);
		this.add(jScrollPane2, new GridBagConstraints(2, 0, 1, 2, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
				2, 2, 2, 2), -1000, 0));
		jScrollPane2.getViewport().add(selectedList, null);
		this.add(selectButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
				2, 2, 2, 2), 0, 0));
		this.add(deselectButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
	}

	/**
	 * getProperty
	 * 
	 * @return Property
	 * @todo Implement this
	 *       com.dexels.navajo.swingclient.components.PropertyControlled method
	 */
	public final Property getProperty() {
		return myProperty;
	}

	/**
	 * setProperty
	 * 
	 * @param p
	 *            Property
	 * @todo Implement this
	 *       com.dexels.navajo.swingclient.components.PropertyControlled method
	 */
	public final void setProperty(final Property p) {
		if(myPropertyListener!=null && myProperty!=null) {
			myProperty.removePropertyChangeListener(myPropertyListener);
			myPropertyListener = null;
		}
		myPropertyListener = new PropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent evt) {
				System.err.println("CHANGE DETECTED: "+evt.getPropertyName()+" old: "+evt.getOldValue()+" new: "+evt.getNewValue());
				Thread.dumpStack();
				System.err.println("OLDVALCLASS: "+evt.getOldValue().getClass());
				System.err.println("NEWVALCLASS: "+evt.getNewValue().getClass());
				BasePropertyImpl pp = (BasePropertyImpl) p;
				try {
					StringWriter stringWriter = new StringWriter();
					pp.write(stringWriter);
					System.err.println("s: "+stringWriter.toString());
				} catch (NavajoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}};
			p.addPropertyChangeListener(myPropertyListener);
			
			try {
			selectedModel.clear();
			deselectedModel.clear();
			myProperty = p;
			selectedList.setVisibleRowCount(visibleRows);
			notSelectedList.setVisibleRowCount(visibleRows);
			buildListModel(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildListModel(Property p) {
		selectedModel.clear();
		deselectedModel.clear();
		try {
			if (p.getType().equals(Property.SELECTION_PROPERTY)) {
				ArrayList all;
				all = p.getAllSelections();

				for (int i = 0; i < all.size(); i++) {
					Selection s = (Selection) all.get(i);
					if (s.isSelected()) {
						selectedModel.addElement(s);
					} else {
						deselectedModel.addElement(s);
					}
				}
			} else {
				System.err.println("WARNING: Setting a non-selection type property in MultipleSelectionPropertyPickList");
			}
		} catch (NavajoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public final void setGhosted(boolean b) {
	}

	public final boolean isGhosted() {
		return false;
	}

	public final void update() {
	}

	final void selectButton_actionPerformed(ActionEvent e) {
		Object[] selectedInList = notSelectedList.getSelectedValues();
		ArrayList<String> ll = new ArrayList<String>();
		for (int i = 0; i < selectedInList.length; i++) {
			Selection s = (Selection) selectedInList[i];
			ll.add(s.getValue());
		}
		
		try {
			ArrayList<Selection> alreadySelected = myProperty.getAllSelectedSelections();
			for (Selection selection : alreadySelected) {
				ll.add(selection.getValue());
			}
			myProperty.setSelected(ll);
			} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		buildListModel(myProperty);

	}

	private ArrayList<String> invertSelection(Property p, ArrayList<String> ll) throws NavajoException {
		ArrayList<Selection> all = p.getAllSelectedSelections();
		ArrayList<String> invList = new ArrayList<String>();
		for (int i = 0; i < all.size(); i++) {
			Selection s = all.get(i);
			String value = s.getValue();
			if(!ll.contains(value)) {
				invList.add(value);
			}
		}

		return invList;
	}

	final void deselectButton_actionPerformed(ActionEvent e) {
		Object[] selectedInList = selectedList.getSelectedValues();
		ArrayList<String> ll = new ArrayList<String>();
		for (int i = 0; i < selectedInList.length; i++) {
			Selection s = (Selection) selectedInList[i];
			ll.add(s.getValue());

		}

		try {
			ArrayList<String> invLL = invertSelection(myProperty, ll);
			myProperty.setSelected(invLL);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		buildListModel(myProperty);

	}

	public static void main(String[] args) {
		try {
			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message m = NavajoFactory.getInstance().createMessage(n, "test");
			n.addMessage(m);
			Property p = NavajoFactory.getInstance().createProperty(n, "Test", "+", "Test", "in");
			m.addProperty(p);

			Selection s1 = NavajoFactory.getInstance().createSelection(n, "n_Aap", "v_Aap", true);
			Selection s2 = NavajoFactory.getInstance().createSelection(n, "n_Noot", "v_Noot", false);
			Selection s3 = NavajoFactory.getInstance().createSelection(n, "n_Mies", "v_Mies", false);
			Selection s4 = NavajoFactory.getInstance().createSelection(n, "n_Geit", "v_Geit", true);
			Selection s5 = NavajoFactory.getInstance().createSelection(n, "n_Boot", "v_Boot", false);
			Selection s6 = NavajoFactory.getInstance().createSelection(n, "n_Raket", "v_Raket", false);
			p.addSelection(s1);
			p.addSelection(s2);
			p.addSelection(s3);
			p.addSelection(s4);
			p.addSelection(s5);
			p.addSelection(s6);

			JFrame frame = new JFrame("Test");
			frame.setSize(800, 600);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.getContentPane().setLayout(new BorderLayout());
			MultipleSelectionPropertyPickList msppl = new MultipleSelectionPropertyPickList();
			frame.getContentPane().add(msppl, BorderLayout.CENTER);
			msppl.setProperty(p);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setVisibleRowCount(int count) {
		// TODO Auto-generated method stub
		visibleRows = count;
		if (selectedList != null) {
			selectedList.setVisibleRowCount(count);
		}
		if (notSelectedList != null) {
			notSelectedList.setVisibleRowCount(count);
		}
		revalidate();

	}

}

final class MultipleSelectionPropertyPickList_selectButton_actionAdapter implements java.awt.event.ActionListener {
	MultipleSelectionPropertyPickList adaptee;

	MultipleSelectionPropertyPickList_selectButton_actionAdapter(MultipleSelectionPropertyPickList adaptee) {
		this.adaptee = adaptee;
	}

	public final void actionPerformed(ActionEvent e) {
		adaptee.selectButton_actionPerformed(e);
	}
}

final class MultipleSelectionPropertyPickList_deselectButton_actionAdapter implements java.awt.event.ActionListener {
	MultipleSelectionPropertyPickList adaptee;

	MultipleSelectionPropertyPickList_deselectButton_actionAdapter(MultipleSelectionPropertyPickList adaptee) {
		this.adaptee = adaptee;
	}

	public final void actionPerformed(ActionEvent e) {
		adaptee.deselectButton_actionPerformed(e);
	}
}
