package com.dexels.navajo.tipi.swingclient.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

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
 *          se JList.setFixedCellHeight() and setFixedCellWidth() in combination
 *          with setPreferredSize() and setMaximumSize() to set size of
 *          container.
 */

public final class MultipleSelectionPropertyList extends JPanel implements
		PropertyControlled {

	private static final long serialVersionUID = 2207572420028970359L;
	private Property myProperty;
	private final DefaultListModel myModel = new DefaultListModel();
	private final JList myList = new JList(myModel);

	private JScrollPane jScrollPane1; // = new JScrollPane();
	private boolean orderBySelected = false;
	// private PropertyChangeListener myPropertyChangeListener = null;

	private boolean isReloading = false;

	public MultipleSelectionPropertyList() {
		try {
			jbInit();
			myList.setCellRenderer(new PropertyCellRenderer());
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	// @Override
	// public Dimension getPreferredSize() {
	// return jScrollPane1.getPreferredSize();
	// // return new Dimension(80,100);
	// }

	public void setSelectedColor(Color c) {
		try {
			((PropertyCellRenderer) myList.getCellRenderer())
					.setSelectedColor(c);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public final void setVerticalScrolls(boolean b) {
		jScrollPane1
				.setVerticalScrollBarPolicy(b ? ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED
						: ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
	}

	public final void setHorizontalScrolls(boolean b) {
		jScrollPane1
				.setHorizontalScrollBarPolicy(b ? ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
						: ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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

		jScrollPane1 = new JScrollPane(myList);
		jScrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// jScrollPane1.getViewport().setView(myList);
		this.add(jScrollPane1, BorderLayout.CENTER);

	}

	public final void setVisibleRowCount(int i) {
		myList.setVisibleRowCount(i);
	}

	public final void setProperty(Property p) {
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
			if (selections.size() <= 0) {
				System.err
						.println("Watch it! No selection property, or selection property without selections!");
			} else {
				for (int i = 0; i < selections.size(); i++) {
					Selection current = selections.get(i);
					// System.err.println("Adding: "+current);
					if (orderBySelected && current.isSelected()) {
						// System.err.println("Selected: " + current.getName());
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
				}
			}

			updateUI();
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
		isReloading = false;

	}

	public final void update() {
		// dummy
	}

	final void this_valueChanged(ListSelectionEvent e) {

		if (isReloading) {
			return;
		}
		ArrayList<String> selected = new ArrayList<String>();
		for (int i = 0; i < myModel.size(); i++) {
			if (myList.isSelectedIndex(i)) {
				Selection current = (Selection) myModel.get(i);
				selected.add(current.getValue());
			}
		}
		try {
			myProperty.setSelected(selected);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
	}

}
