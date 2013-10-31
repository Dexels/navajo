package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Dexels.com
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 * @deprecated
 */

public class CalendarTableCellRenderer implements TableCellRenderer {
	JLabel week = new JLabel();
	DayRendererInterface dd = new DefaultDayRenderer();
	MultipleDayRenderer md = new MultipleDayRenderer();

	public CalendarTableCellRenderer() {
		week.setHorizontalAlignment(SwingConstants.CENTER);
		week.setHorizontalTextPosition(SwingConstants.CENTER);
		week.setFont(new java.awt.Font("Dialog", 1, 11));
		week.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {

		// Determine if day is anchor of selection
		CalendarTable ct = (CalendarTable) table;

		boolean isAnchor = false;
		if (table.getSelectedColumn() == column
				&& table.getSelectedRow() == row) {
			isAnchor = true;
		}

		if (row == 0 && column == 0) {
			if (ct.selectAllImageVisible) {
				week.setIcon(new ImageIcon(CalendarTableCellRenderer.class
						.getResource("select-all.gif")));
			}
			week.setText("");
			week.setForeground(CalendarConstants
					.getColor(CalendarConstants.BGFONT_COLOR));
			week.setBackground(CalendarConstants
					.getColor(CalendarConstants.BACKGROUND_COLOR));
			return week;
		} else {
			week.setIcon(null);
		}

		/*
		 * Single Calendar Rendering Mode
		 */

		if (Day.class.isInstance(value)) {
			Day d = (Day) value;

			if (column > 0) {
				dd.setBackground(isSelected ? Color.blue : Color.white);
				dd.setDay(d);

				dd.setSelected(isSelected);
				dd.setAnchor(isAnchor);
				return dd.getComponent();
			} else {
				week.setText("" + d.getWeekOfYear());
				week.setForeground(CalendarConstants
						.getColor(CalendarConstants.BGFONT_COLOR));
				week.setBackground(CalendarConstants
						.getColor(CalendarConstants.BACKGROUND_COLOR));
				return week;
			}
		}

		/*
		 * Multiple Calendar Rendering Mode
		 */

		else if (MultipleDayContainer.class.isInstance(value)) {
			// Implement this!!!
			// logger.info("Rendering mulitple day container");
			MultipleDayContainer d = (MultipleDayContainer) value;
			if (column > 0) {
				md.setDays(d.getDays());
				md.setBackground(isSelected ? Color.blue : Color.white);
				md.setSelected(isSelected);
				md.setAnchor(isAnchor);
				return md;
			} else {
				week.setText("" + d.getDays()[0].getWeekOfYear());
				week.setForeground(CalendarConstants
						.getColor(CalendarConstants.BGFONT_COLOR));
				week.setBackground(CalendarConstants
						.getColor(CalendarConstants.BACKGROUND_COLOR));
				return week;
			}
		} else {
			week.setText((String) value);
			week.setForeground(CalendarConstants
					.getColor(CalendarConstants.BGFONT_COLOR));
			week.setBackground(CalendarConstants
					.getColor(CalendarConstants.BACKGROUND_COLOR));
			return week;
		}

	}
}
