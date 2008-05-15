package com.dexels.navajo.tipi.swingclient.components;

import javax.swing.event.*;
import com.dexels.navajo.document.*;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;

import javax.swing.*;

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
 * 
 * 
 * se JList.setFixedCellHeight() and setFixedCellWidth() in combination with
 * setPreferredSize() and setMaximumSize() to set size of container.
 */

public final class MultipleSelectionPropertyList extends JPanel implements PropertyControlled {
	/** @todo Add action / changelisteners to this class */

	private Property myProperty;
	private final DefaultListModel myModel = new DefaultListModel();
	private final JList myList = new JList(myModel);

	// private final BorderLayout borderLayout1 = new BorderLayout();
	private final JScrollPane jScrollPane1 = new JScrollPane();
	private boolean isGhosted = false;
	private int myVisibleRowCount = 8;
//	private ArrayList listeners = new ArrayList();
	private boolean orderBySelected = false;
	private PropertyChangeListener myPropertyChangeListener = null;

	private boolean isReloading = false;
	
	public MultipleSelectionPropertyList() {
		try {
			jbInit();
			myList.setCellRenderer(new PropertyCellRenderer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setBackground(Color.red);
	}

	public void setOrderBySelected(boolean b) {
		orderBySelected = b;
	}

	public void addListSelectionListener(ListSelectionListener l) {
		myList.addListSelectionListener(l);
	}

	public void removeListSelectionListener(ListSelectionListener l) {
		myList.removeListSelectionListener(l);
	}

	public Dimension getPreferredSize() {
		return jScrollPane1.getPreferredSize();
		// return new Dimension(80,100);
	}

	public final void setGhosted(boolean b) {
		isGhosted = b;
	}

	public void gainFocus() {
		// gar nichts
	}

	public void setSelectedColor(Color c) {
		try {
			((PropertyCellRenderer) myList.getCellRenderer()).setSelectedColor(c);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public final void setVerticalScrolls(boolean b) {
		jScrollPane1.setVerticalScrollBarPolicy(b ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	}

	public final void setHorizontalScrolls(boolean b) {
		jScrollPane1.setHorizontalScrollBarPolicy(b ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	}

	public final boolean isGhosted() {
		return isGhosted;
	}

	public final Property getProperty() {
		return myProperty;
	}

	private final void jbInit() throws Exception {
		this.setLayout(new BorderLayout());
		myList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				this_valueChanged(e);
			}
		});
		
//		myList.addPropertyChangeListener(new PropertyChangeListener(){
//
//			public void propertyChange(PropertyChangeEvent evt) {
//				System.err.println("PROP: "+evt.getPropertyName()+" size: "+getSize()+" pref: "+getPreferredSize());
//				System.err.println("SCROLLSIZE: "+jScrollPane1.getSize()+" PREFSCROLL: "+jScrollPane1.getPreferredSize());
//			}});

	
		jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		myList.setFixedCellHeight(16);
		// jScrollPane1.setPreferredSize(myList.getPreferredSize());
//		jScrollPane1.add(myList);
		jScrollPane1.getViewport().setView(myList);
		// jScrollPane1.add(myList);
		this.add(jScrollPane1, BorderLayout.CENTER);

		// setBackground(Color.CYAN);
		// System.err.println("*************************************");
		// System.err.println("scroll: "+jScrollPane1.getPreferredSize());
		// System.err.println("myList: "+myList.getPreferredSize());
		// System.err.println("this: "+this.getPreferredSize());
		// System.err.println("*************************************");
	}

	public final void setVisibleRowCount(int i) {
		// System.err.println("SETTING ROWCOUNT>> "+i);
		// Thread.dumpStack();
		myList.setVisibleRowCount(i);
//		myVisibleRowCount = i;
	}

	public final void setProperty(Property p) {
		// myList.setModel(myModel);
		// System.err.println("Setting property in MulitpleDinges: " +
		// p.getName());
//		if(myProperty!=p) {
//			if(myProperty!=null) {
//				myProperty.removePropertyChangeListener(myPropertyChangeListener );
//			}
//			myPropertyChangeListener = new PropertyChangeListener(){
//
//				public void propertyChange(PropertyChangeEvent ppp) {
//					System.err.println("Bomchicka: "+ppp.getOldValue()+" new: "+ppp.getNewValue());
//				}};
//			
//			p.addPropertyChangeListener(myPropertyChangeListener);
//		}
		isReloading = true;
		myProperty = p;
		try {
			myModel.clear();
			if (p.getCardinality().equals("1")) {
				myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			} else {
				myList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			}
			List<Selection> selections = myProperty.getAllSelections();
//			System.err.println("# of selections: "+selections.size());
			if (selections.size() <= 0) {
				System.err.println("Watch it! No selection property, or selection property without selections!");
			} else {
				for (int i = 0; i < selections.size(); i++) {
					Selection current = selections.get(i);
					// System.err.println("Adding: "+current);
					if (orderBySelected && current.isSelected()) {
//						System.err.println("Selected: " + current.getName());
						myModel.add(0, current);
					} else {
						myModel.addElement(current);
					}
					if (current.isSelected()) {
						if (orderBySelected) {
							myList.addSelectionInterval(0, 0);
						} else {
							myList.addSelectionInterval(i, i);
						}
					}
					// SelectionCheckBox cb = new SelectionCheckBox();
				}
			}
			
			 updateUI();
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
		isReloading = false;

//		setVisibleRowCount(myVisibleRowCount);
	}

	public final void update() {
		// dummy
	}

	final void this_valueChanged(ListSelectionEvent e) {
//		int[] sel = myList.getSelectedIndices();
//
//		try {
//			List<Selection> mySel = myProperty.getAllSelections();
//			for (Selection selection : mySel) {
//				
//			}
//		} catch (NavajoException e2) {
//			e2.printStackTrace();
//		}
//		
		if(isReloading) {
			return;
		}
		for (int i = 0; i < myModel.size(); i++) {
//			Thread.dumpStack();
//			System.err.println("Model size: "+myModel.size());
			Selection current = (Selection) myModel.get(i);
			try {
				myProperty.setSelected(current, myList.isSelectedIndex(i));
			} catch (NavajoException e1) {
				e1.printStackTrace();
			}
		}
	}

}
