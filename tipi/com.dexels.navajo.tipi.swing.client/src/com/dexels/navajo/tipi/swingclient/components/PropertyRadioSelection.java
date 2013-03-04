package com.dexels.navajo.tipi.swingclient.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;

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

public final class PropertyRadioSelection extends JPanel implements
		PropertyControlled, ActionListener {

	private static final long serialVersionUID = 2786990335267017128L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(PropertyRadioSelection.class);
	private Property myProperty = null;
	private final ButtonGroup myGroup = new ButtonGroup();
	private JRadioButton lastButtonOfGroup = null;

	private final Map<JRadioButton, Selection> selectionMap = new HashMap<JRadioButton, Selection>();
	private final List<ButtonModel> buttonList = new ArrayList<ButtonModel>();
	private final ArrayList<ActionListener> myActionListeners = new ArrayList<ActionListener>();
	private final FocusAdapter myFocusListener;

	// private static final int VERTICAL = 1;
	// private static final int HORIZONTAL = 2;

	// private int direction = VERTICAL;
	private boolean columnMode;
	private int checkboxGroupColumnCount;

	public PropertyRadioSelection()
	{
		this(null);
	}
	public PropertyRadioSelection(FocusAdapter focusAdapter) {
		
		setVertical();
		myFocusListener = focusAdapter;
	}

	public final void setVertical() {
		// setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		// direction = VERTICAL;
		setLayout(new GridBagLayout());
		// doLayout();
	}

	public final void setHorizontal() {
		// direction = HORIZONTAL;
		setLayout(new GridBagLayout());
		// setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		doLayout();
	}

	public final Property getProperty() {
		return myProperty;
	}

	public final void setProperty(Property p) {
		selectionMap.clear();
		buttonList.clear();
		if (p == null) {
			// logger.info("Null prop");
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
			removeAll();
			int col = 0;
			int row = 0;

			List<Selection> al = p.getAllSelections();
			for (int i = 0; i < al.size(); i++) {
				Selection s = al.get(i);
				if (columnMode) {
					int req = (int) Math.ceil(al.size()
							/ (float) checkboxGroupColumnCount) - 1; // offset
																		// with
																		// 1
																		// because
																		// gridbag
																		// starts
																		// at 0
					if (row + 1 > req) {
						add(createButton(s), new GridBagConstraints(col, row,
								1, 1, 1.0, 1.0, GridBagConstraints.NORTHWEST,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
					} else {
						add(createButton(s), new GridBagConstraints(col, row,
								1, 1, 1.0, 0.0, GridBagConstraints.NORTHWEST,
								GridBagConstraints.HORIZONTAL, new Insets(0, 0,
										0, 0), 0, 0));
					}
					row++;
					if (row > req) {
						row = 0;
						col++;
					}
				} else {
					add(createButton(s), new GridBagConstraints(0, i, 1, 1, 1,
							1.0, GridBagConstraints.NORTHWEST,
							GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0,
							0));
				}

			}
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}

	}

	private final JComponent createButton(Selection s) {
		JRadioButton jr = new JRadioButton();
		selectionMap.put(jr, s);
		buttonList.add(jr.getModel());
		jr.setText(s.getName() != null ? s.getName() : s.getValue());
		myGroup.add(jr);
		jr.addActionListener(this);
		jr.addFocusListener(myFocusListener);
		if (s.isSelected()) {
			jr.setSelected(true);
		}
		lastButtonOfGroup = jr;
		// jr.addItemListener(new ItemListener() {
		// public void itemStateChanged(ItemEvent ce) {
		// if (ce.getStateChange()==ItemEvent.SELECTED) {
		// logger.info("ce: "+ce.getSource());
		// updateProperty((JRadioButton)ce.getSource());
		// }w
		// }
		// });
		return jr;
	}

	public final void update() {
	}

	public int getSelectedIndex() {
		ButtonModel b = myGroup.getSelection();
		if (b == null) {
			return -1;
		}
		Integer i = new Integer(buttonList.indexOf(b));

		return i.intValue();
	}

	public final void actionPerformed(ActionEvent e) {
		logger.info("Radio action performed");
		updateProperty((JRadioButton) e.getSource());
		lastButtonOfGroup.transferFocus();
	}

	private final void updateProperty(JRadioButton source) {
		if (myProperty == null) {
			return;
		}
		if (source == null) {
			logger.info("Tsja, updateprop with null source");
		}
		try {
			Selection s = selectionMap.get(source);
			if (s != null) {
				myProperty.setSelected(s);
			} else {
				myProperty.clearSelections();
			}
		} catch (NavajoException ex) {
			ex.printStackTrace();
		}
		// for (int i = 0; i < myActionListeners.size(); i++) {
		// ActionListener current = (ActionListener)myActionListeners.get(i);
		// current.actionPerformed(new ActionEvent(source,-1,""));
		// }

	}

	public final void addActionListener(ActionListener al) {
		myActionListeners.add(al);
	}

	public final void removeActionListener(ActionListener al) {
		myActionListeners.remove(al);
	}

	public void setColumnMode(boolean b) {
		columnMode = b;
	}

	public void setColumns(int checkboxGroupColumnCount) {
		this.checkboxGroupColumnCount = checkboxGroupColumnCount;
	}

}
