package com.dexels.navajo.tipi.swingclient.components;

import java.util.Map;
import java.util.Map.Entry;

import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.swingclient.components.sort.CustomTableHeaderRenderer;

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

public final class MessageTableColumnModel extends DefaultTableColumnModel {

	private static final long serialVersionUID = 4339496482186009316L;
	// private Message myMessage;
	private int maxadvance;

	public MessageTableColumnModel() {
		CustomTableHeaderRenderer myRenderer = new CustomTableHeaderRenderer();
		maxadvance = myRenderer.getFontMetrics(myRenderer.getFont()).charWidth(
				'M');
	}

	public final void removeColumns() {
		// super.remo
		for (int i = getColumnCount() - 1; i >= 0; i--) {
			// logger.info("Removing column: "+i);
			removeColumn(getColumn(i));
		}
	}

	public final void setMessage(Message m, MessageTableModel mtm) {
		// removeColumns();
		if (m == null) {
			createFromModel(mtm);
			return;
		}
		if (m.getArraySize() == 0) {
			createFromModel(mtm);
			return;
		}

		Message def = m.getDefinitionMessage();
		if (def != null) {
			for (int i = 0; i < mtm.getColumnCount(); i++) {
				createColumn(i, mtm, def);
			}
		} else {
			Message first = m.getMessage(0);
			for (int i = 0; i < mtm.getColumnCount(); i++) {
				createColumn(i, mtm, first);
			}
		}
	}

	private final void createFromModel(MessageTableModel mtm) {
		for (int i = 0; i < mtm.getColumnCount(); i++) {
			createColumn(i, mtm, null);
		}
	}

	@Override
	public TableColumn getColumn(int columnIndex) {
		if (getColumnCount() == 0) {
			return null;
		}
		if (columnIndex < super.getColumnCount()) {
			return super.getColumn(columnIndex);
		} else {
			return null;
		}
	}

	public void loadSizes(Map<Integer, Integer> m) {
		for (Entry<Integer, Integer> e : m.entrySet()) {
			Integer size = e.getValue();
			Integer index = e.getKey();
			if (size != null) {
				TableColumn tc = getColumn(index.intValue());
				if (tc != null) {
					tc.setPreferredWidth(size.intValue());
					tc.setWidth(size.intValue());
				}
			}

		}
	}

	public final void createColumn(int index, MessageTableModel mtm, Message m) {
		String cId = mtm.getColumnId(index);
		int width = 0;
		TableColumn tc;
		if (m == null) {
			tc = new TableColumn(index);
		} else {
			Property p = m.getProperty(cId);
			if (p == null) {
				tc = new TableColumn(index);
			} else {
				width = p.getLength() * maxadvance;
				// logger.info("Creating column [" + index + "]: " +
				// width);
				tc = new TableColumn(index, width);
				tc.setHeaderValue(p.getDescription());
				tc.setPreferredWidth(width);
				tc.setMinWidth(10);
				tc.setWidth(width);
			}
		}
		addColumn(tc);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// logger.info("Column selection changed!");
		super.valueChanged(e);
	}

}
