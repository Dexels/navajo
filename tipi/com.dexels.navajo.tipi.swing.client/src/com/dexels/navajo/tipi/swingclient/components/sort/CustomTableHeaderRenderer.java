package com.dexels.navajo.tipi.swingclient.components.sort;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.swingclient.components.MessageTable;

/**
 * <p>
 * Title: ShellApplet
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels
 * </p>
 * 
 * @author Frank Lyaruu
 * @version 1.0
 */

public class CustomTableHeaderRenderer extends JLabel implements
		TableCellRenderer {

	
	private final static Logger logger = LoggerFactory
			.getLogger(CustomTableHeaderRenderer.class);
	
	private static final long serialVersionUID = -3227757814599248801L;

	private static final int DEFAULT_INSET = 3;

	public static final int ASCENDING = 1;

	public static final int DESCENDING = 2;

	public static final int NONE = 0;

	// private int sortingState = NONE;

	private ImageIcon up = new ImageIcon(
			TableSorter.class.getResource("up.png"));

	private ImageIcon down = new ImageIcon(
			TableSorter.class.getResource("down.png"));

	private ImageIcon none = new ImageIcon(
			TableSorter.class.getResource("none.png"));

	private final Map<Integer, Integer> sortingMap = new HashMap<Integer, Integer>();

	public CustomTableHeaderRenderer() {
		super();
		try {
			jbInit();
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		setHorizontalTextPosition(SwingConstants.LEADING);
		setIconTextGap(10);
		setHorizontalAlignment(SwingConstants.LEADING);

		// setBorderPainted(false);
		// setSortingState(NONE);

	}

	public void setSortingState(int column, int s) {
		sortingMap.clear();
		sortingMap.put(new Integer(column), new Integer(s));
		// sortingState = s;
		updateSorting(column);

	}

	private final void updateSorting(int column) {
		int sortingState = NONE;
		Integer sortInt = sortingMap.get(new Integer(column));
		if (sortInt != null) {
			sortingState = sortInt.intValue();
		}
		switch (sortingState) {
		case ASCENDING:
			setIcon(down);
			break;
		case DESCENDING:
			setIcon(up);
			break;
		case NONE:
			setIcon(none);
			break;
		}
		repaint();
	}

	public int getSortingState(int column) {
		int sortingState = NONE;
		Integer sortInt = sortingMap.get(new Integer(column));
		if (sortInt != null) {
			sortingState = sortInt.intValue();
		}
		return sortingState;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new GridBagLayout());

		updateSorting(column);
		setText("" + table.getModel().getColumnName(column));
		myPanel.add(this, new GridBagConstraints(0, 0, 1, 1, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						DEFAULT_INSET, DEFAULT_INSET, DEFAULT_INSET,
						DEFAULT_INSET), 0, 0));
		revalidate();
		((MessageTable) table)
				.setHeaderHeight(myPanel.getPreferredSize().height);
		return myPanel;
	}

	private final void jbInit() throws Exception {
		this.setHorizontalAlignment(SwingConstants.LEADING);
		this.setHorizontalTextPosition(SwingConstants.LEADING);
		// this.setMargin(new Insets(0, 3, 0, 0));
	}

}
