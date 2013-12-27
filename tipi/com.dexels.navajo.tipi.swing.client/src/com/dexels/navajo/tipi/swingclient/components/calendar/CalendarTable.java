package com.dexels.navajo.tipi.swingclient.components.calendar;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.tipi.swingclient.components.BasePanel;

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

public class CalendarTable extends JTable {

	private static final long serialVersionUID = 9165405397452206651L;
	private CalendarTableCellEditor myEditor = new CalendarTableCellEditor();
	private CalendarTableCellRenderer myRenderer = new CalendarTableCellRenderer();
	private CalendarTableModel myDefaultModel = new CalendarTableModel();
	private MultiCalendarModel myMultiModel = new MultiCalendarModel();
	private CalendarManager myManager;
	public final int SELECTION_CONTINOUS = 1;
	public final int SELECTION_NORMAL = 0;
	public boolean selectAllImageVisible = true;
	private CalendarConstants myConstants = new CalendarConstants();
	int modelGetCount = 0;
	private int selectionMode = SELECTION_NORMAL;
	private Message myData;
	private Date myDate;

	public CalendarTable() {
		init();
	}

	public CalendarTable(CalendarConstants cc) {
		myConstants = cc;
		myDefaultModel.setCalendarConstants(myConstants);
		myMultiModel.setCalendarConstants(myConstants);
		init();
	}

	public void setMessage(Message msg) {
		myData = msg;
		myDefaultModel.setMessage(myData);
	}

	public void init() {
		setShowHorizontalLines(false);
		setShowVerticalLines(false);
		setIntercellSpacing(new Dimension(0, 0));
		setDefaultEditor(Object.class, myEditor);
		// Order is important!! Only setTable after adding the default model
		myMultiModel.addCalendar(myDefaultModel);
		myMultiModel.setTable(this);
		setModel(myMultiModel);
		setDefaultRenderer(Object.class, myRenderer);
		setCellSelectionEnabled(true);
		this.setTableHeader(null);
		this.setRowHeight(myConstants.getRowHeight());
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.setBackground(CalendarConstants
				.getColor(CalendarConstants.BACKGROUND_COLOR));

		this.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				// logger.info("You pressed: " +
				// e.getKeyText(e.getKeyCode()));
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// logger.info("You released: " +
				// e.getKeyText(e.getKeyCode()));
				registerKeyEvent(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// logger.info("You typed: " +
				// e.getKeyText(e.getKeyCode()));
				// Gives weird unrecognized keycodes.
			}

		});

		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cellClicked(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				return;
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				return;
			}
		});

	}

	public void setCalendarConstants(CalendarConstants cc) {
		myConstants = cc;
		myMultiModel.setCalendarConstants(myConstants);
	}

	public void addCalendar(CalendarTableModel model) {
		myMultiModel.addCalendar(model);
	}

	public CalendarConstants getConstants() {
		return myConstants;
	}

	private final void registerKeyEvent(KeyEvent e) {
		myDate = null;
		if (myManager == null) {
			myManager = new DefaultCalendarManager();
			myManager.fireCalendarEvent(new CalendarEvent(this, e));
		} else {
			myManager.fireCalendarEvent(new CalendarEvent(this, e));
		}
	}

	public void setSelectAllImageVisible(boolean value) {
		selectAllImageVisible = value;
	}

	public ArrayList<Day> getSelectedDays() {
		ArrayList<Day> selected = new ArrayList<Day>();
		for (int i = 1; i < this.getRowCount(); i++) {
			for (int j = 1; j < this.getColumnCount(); j++) {
				if (isCellSelected(i, j)) {
					Object o = getModel().getValueAt(i, j);
					if (Day.class.isInstance(o)) {
						Day d = (Day) o;
						selected.add(d);
					}
				}
			}
		}
		return selected;
	}

	// This is not 'clicking'.. it's just passing on any event
	public void cellClicked(MouseEvent e) {
		myDate = null;
		if (myManager == null) {
			myManager = new DefaultCalendarManager();
			myManager.fireCalendarEvent(new CalendarEvent(this, e));
		} else {
			myManager.fireCalendarEvent(new CalendarEvent(this, e));
		}
	}

	@Override
	public boolean isCellSelected(int row, int col) {
		Object o = myDefaultModel.getValueAt(row, col);
		if (Day.class.isInstance(o)) {
			Day d = (Day) o;
			if (d != null) {
				Date cd = d.getDate();
				if (cd != null && myDate != null) {
					if ((cd.getTime() == myDate.getTime())) {
						return true;
					}
				}
			}
		}

		if (getSelectedColumn() == 0) {
			setColumnSelectionInterval(1, 7);
		}
		if (getSelectedRow() == 0) {
			setRowSelectionInterval(0, getRowCount() - 1);
		}

		if (selectionMode == SELECTION_CONTINOUS) {
			int minRow = getSelectionModel().getMinSelectionIndex();
			int maxRow = getSelectionModel().getMaxSelectionIndex();

			if (row > minRow && row < maxRow) {
				return true; // all cells between start and end but not start
								// and end
			}

			if ((row == minRow || row == maxRow) && minRow != maxRow) {
				int anchorCol = getColumnModel().getSelectionModel()
						.getAnchorSelectionIndex();
				int leadCol = getColumnModel().getSelectionModel()
						.getLeadSelectionIndex();
				int anchorRow = getSelectionModel().getAnchorSelectionIndex();
				int leadRow = getSelectionModel().getLeadSelectionIndex();

				// boolean toRight = leadCol > anchorCol;
				boolean toBottom = leadRow > anchorRow;

				if (toBottom) {
					if (row == maxRow) {
						return col <= leadCol;
					} else { // minRow
						return col >= anchorCol;
					}
				} else if (row == maxRow) { // to top
					return col <= anchorCol;
				} else { // minRow
					return col >= leadCol;
				}
			}
			return super.isCellSelected(row, col);
		} else {
			return super.isCellSelected(row, col);
		}
	}

	public int getYear() {
		return myDefaultModel.getYear();
	}

	public void setYear(int year) {
		myDefaultModel.setYear(year);
		if (myManager != null) {
			myManager.fireCalendarEvent(new CalendarEvent(this, null));
		}
	}

	public void setDate(Date d) {
		myDate = d;
		if (myManager != null) {
			myManager.fireCalendarEvent(new CalendarEvent(this, null));
		}

	}

	public void setMonth(int month) {
		myDefaultModel.setMonth(month);
		if (myManager != null) {
			myManager.fireCalendarEvent(new CalendarEvent(this, null));
		}
	}

	public String getMonthString() {
		return myDefaultModel.getMonthString();
	}

	public int getMonth() {
		return myDefaultModel.getMonth();
	}

	public Day getDay(String dayOfYear) {
		return myDefaultModel.getDay(dayOfYear);
	}

	@Override
	public void setSelectionMode(int mode) {
		selectionMode = mode;
	}

	public Message getMessage() {
		return myData;
	}

	public void setCalendarManager(CalendarManager m) {
		myManager = m;
		myManager.setSource(this);
		// myDefaultModel.setManager(myManager);
	}

	public CalendarManager getManager() {
		return myManager;
	}

	public void rebuildUI() {
		setRowHeight(myConstants.getRowHeight());
	}

	public void nextMonth() {
		if (getMonth() == 11) {
			setMonth(0);
		} else {
			setMonth(getMonth() + 1);
		}
		repaint();
	}

	public void previousMonth() {
		if (getMonth() == 0) {
			setMonth(11);
		} else {
			setMonth(getMonth() - 1);
		}
		repaint();
	}

	public void load(Message m) {
		// logger.info("Loading Table!!");
		if (BasePanel.class.isInstance(myManager)) {
			((BasePanel) myManager).setLoadMessage(m);
			((BasePanel) myManager).load();
		}
	}

	public void init(Message m) {
		if (BasePanel.class.isInstance(myManager)) {
			((BasePanel) myManager).setInitMessage(m);
			((BasePanel) myManager).init();
		}

	}

}
