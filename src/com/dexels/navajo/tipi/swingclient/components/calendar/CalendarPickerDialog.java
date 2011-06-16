package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import com.dexels.navajo.tipi.swingclient.components.BasePanel;
import com.dexels.navajo.tipi.swingclient.components.DialogConstants;
import com.dexels.navajo.tipi.swingclient.components.StandardDialog;

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
 * @deprecated
 */

public class CalendarPickerDialog extends StandardDialog implements
		CalendarManager {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2258070699725278356L;
	BasePanel mainPanel = new BasePanel();
	BasePanel mainControlPanel = new BasePanel();
	BasePanel controlMonthPanel = new BasePanel();
	BasePanel controlYearPanel = new BasePanel();
	CalendarTable calendar = new CalendarTable();
	CalendarConstants constants = new CalendarConstants();
	JButton nextMonthButton = new JButton();
	JButton previousMonthButton = new JButton();
	JLabel monthLabel = new JLabel();
	JButton nextYearButton = new JButton();
	JButton previousYearButton = new JButton();
	JLabel yearLabel = new JLabel();
	Date selected = null;

	public void load(Date d) {
	}

	public CalendarPickerDialog(JFrame j) {
		super(j);
		init();
	}

	public CalendarPickerDialog(JDialog j) {
		super(j);
		init();
	}

	public CalendarPickerDialog() {
		init();
	}

	private void init() {
		addMainPanel(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		mainControlPanel.setLayout(new BorderLayout());
		mainPanel.add(mainControlPanel, BorderLayout.NORTH);
		mainControlPanel.add(controlYearPanel, BorderLayout.NORTH);
		mainControlPanel.add(controlMonthPanel, BorderLayout.SOUTH);
		mainPanel.add(calendar, BorderLayout.CENTER);
		calendar.setCalendarConstants(constants);
		calendar.getConstants().setColumnWidth(22);
		calendar.getConstants().setRowHeight(22);
		calendar.getConstants();
		CalendarConstants
				.setColorScheme(CalendarConstants.COLORSCHEME_SPORTLINK);
		calendar.rebuildUI();
		setTitle("Selecteer datum");
		setMode(DialogConstants.MODE_CLOSE);
		iconButtonPanel.setButtonAlignment(FlowLayout.RIGHT);
		// setMode(DialogConstants.MODE_OK_CANCEL);
		calendar.setBorder(BorderFactory.createEtchedBorder());

		nextMonthButton.setIcon(new ImageIcon(CalendarPickerDialog.class
				.getResource("next-small.gif")));
		previousMonthButton.setIcon(new ImageIcon(CalendarPickerDialog.class
				.getResource("previous-small.gif")));
		nextMonthButton.setMargin(new Insets(0, 0, 0, 0));
		previousMonthButton.setMargin(new Insets(0, 0, 0, 0));
		controlMonthPanel.setLayout(new GridBagLayout());
		controlMonthPanel.add(previousMonthButton, new GridBagConstraints(0, 0,
				1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		controlMonthPanel.add(monthLabel, new GridBagConstraints(1, 0, 1, 1,
				1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		controlMonthPanel.add(nextMonthButton, new GridBagConstraints(2, 0, 1,
				1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		nextMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performAction(1);
			}
		});
		previousMonthButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performAction(-1);
			}
		});
		monthLabel.setText(calendar.getMonthString());

		nextYearButton.setIcon(new ImageIcon(CalendarPickerDialog.class
				.getResource("next-small.gif")));
		previousYearButton.setIcon(new ImageIcon(CalendarPickerDialog.class
				.getResource("previous-small.gif")));
		nextYearButton.setMargin(new Insets(0, 0, 0, 0));
		previousYearButton.setMargin(new Insets(0, 0, 0, 0));
		controlYearPanel.setLayout(new GridBagLayout());
		controlYearPanel.add(previousYearButton, new GridBagConstraints(0, 0,
				1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0));
		controlYearPanel.add(yearLabel, new GridBagConstraints(1, 0, 1, 1, 1.0,
				0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		controlYearPanel.add(nextYearButton, new GridBagConstraints(2, 0, 1, 1,
				0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE,
				new Insets(2, 2, 2, 2), 0, 0));
		nextYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performAction(12);
			}
		});
		previousYearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performAction(-12);
			}
		});
		yearLabel.setText("" + calendar.getYear());

		calendar.setCalendarManager(this);
		mainPanel.setPreferredSize(new Dimension(250, 250));
		// setUndecorated(true);
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				((JComponent) getContentPane()).revalidate();
			}
		});
	}

	/** @todo Add day selection. Not a clue how to set a day. Ask Arnoud. */

	public void setDate(Date d) {
		if (d == null) {
			return;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		calendar.setDate(d);
		calendar.setYear(c.get(Calendar.YEAR));
		calendar.setMonth(c.get(Calendar.MONTH));
		performAction(0);

	}

	public void performAction(int add) {
		if (add == 12) {
			calendar.setYear(calendar.getYear() + 1);
			calendar.repaint();
		} else if (add == 1) {
			if (calendar.getMonth() == 11) {
				calendar.setYear(calendar.getYear() + 1);
			}
			calendar.nextMonth();
		} else if (add == -1) {
			if (calendar.getMonth() == 0) {
				calendar.setYear(calendar.getYear() - 1);
			}
			calendar.previousMonth();
		} else if (add == -12) {
			calendar.setYear(calendar.getYear() - 1);
			calendar.repaint();
		}
		monthLabel.setText(calendar.getMonthString());
		yearLabel.setText(calendar.getYear() + "");
	}

	/**
	 * fireCalendarEvent
	 * 
	 * @param e
	 *            CalendarEvent
	 * @todo Implement this
	 *       com.dexels.navajo.swingclient.components.calendar.CalendarManager
	 *       method
	 */
	public void fireCalendarEvent(CalendarEvent e) {
		ArrayList<Day> days = calendar.getSelectedDays();
		if (days.size() > 0) {
			Day d = days.get(0);
			selected = d.getDate();
			if (MouseEvent.class.isInstance(e.getEvent())) {
				MouseEvent me = (MouseEvent) e.getEvent();
				if (me.getClickCount() == 2) {
					super.commit();
					setCommitted(true);
					this.closeWindow();
				}
			}
		}
	}

	/**
	 * setSource
	 * 
	 * @param t
	 *            CalendarTable
	 * @todo Implement this
	 *       com.dexels.navajo.swingclient.components.calendar.CalendarManager
	 *       method
	 */
	public void setSource(CalendarTable t) {
	}

	public Date getSelectedDate() {
		return selected;
	}

}
