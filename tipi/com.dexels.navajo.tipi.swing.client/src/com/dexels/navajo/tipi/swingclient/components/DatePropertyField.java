package com.dexels.navajo.tipi.swingclient.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JDialog;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.swingclient.SwingClient;
import com.dexels.navajo.tipi.swingclient.components.calendar.CalendarPickerDialog;



@SuppressWarnings("deprecation")
public final class DatePropertyField extends PropertyField implements
		PropertyControlled {

	private static final long serialVersionUID = -6817551990943620076L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(DatePropertyField.class);
	
	private static SimpleDateFormat displayDateFormat = new SimpleDateFormat(
			"dd-MMM-yyyy", Locale.getDefault());
	private static SimpleDateFormat inputFormat1 = new SimpleDateFormat(
			"dd-MM-yy");
	private static SimpleDateFormat inputFormat2 = new SimpleDateFormat(
			"dd/MM/yy");
	private static SimpleDateFormat inputFormat3 = new SimpleDateFormat(
			"ddMMyy");
	private static SimpleDateFormat inputFormat4 = new SimpleDateFormat("ddMM");
	private static SimpleDateFormat inputFormat5 = new SimpleDateFormat("dd");
	private boolean showCalendarPickerButton = true;
	private boolean readOnly = false;
	private MessageTable myTable = null;
	private int row, column;

	public DatePropertyField() {
		this(true);
		
	}
	public DatePropertyField(Boolean addFocusLostListener) {
		super(addFocusLostListener);
		setColumns(10);

		try {

			this.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					checkMousePos(e);
				}
			});
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					checkMouseClick(e);
				}
			});

		} catch (Exception e) {
			logger.error("Error: ",e);
		}
	}

	@Override
	public final void setProperty(Property p) {
		if (p == null) {
			return;
		}
		if (!p.getType().equals(Property.DATE_PROPERTY)) {
			System.err
					.println("Warning: Setting date field to non date property of type: "
							+ p.getType());
		}
		initProperty = p;

		setEditable(p.isDirIn());

		try {
			setDate((Date) p.getTypedValue());
		} catch (Exception e) {
			logger.error("Error: ",e);
		}
		super.setProperty(p);
	}

	public final void setReadOnly(boolean b) {
		readOnly = b;
	}

	public final Date getDate() throws ParseException {
		String text = getText();
		if (text == null || text.equals("")) {
			return null;
		}

		// see if there is only one separator provided; if so, add the current
		// year to string
		if (text.indexOf('-') >= 0
				&& text.indexOf('-') == text.lastIndexOf('-')) {
			text = text + "-" + Calendar.getInstance().get(Calendar.YEAR);
		}
		if (text.indexOf('/') >= 0
				&& text.indexOf('/') == text.lastIndexOf('/')) {
			text = text + "/" + Calendar.getInstance().get(Calendar.YEAR);

			// Try different parsers:
		}
		try {
			return inputFormat1.parse(text);
		} catch (ParseException pe1) {
			try {
				return inputFormat2.parse(text);
			} catch (ParseException pe2) {
				try {
					return inputFormat3.parse(text);
				} catch (ParseException pe6) {
					try {
						return displayDateFormat.parse(text);
					} catch (ParseException pe4) {
						try {
							// There is a bug in SimpleDateFormat causing the
							// date to reset to 01-01-1970
							// So we make sure the right month and year are set
							// again.
							Date wrong1 = inputFormat4.parse(text);
							Calendar c = Calendar.getInstance();
							c.setTime(wrong1);
							c.set(Calendar.YEAR,
									Calendar.getInstance().get(Calendar.YEAR));
							return c.getTime();
						} catch (ParseException pe5) {
							try {
								// There is a bug in SimpleDateFormat causing
								// the date to reset to 01-01-1970
								// So we make sure the right month and year are
								// set again.
								Date wrong2 = inputFormat5.parse(text);
								Calendar x = Calendar.getInstance();
								x.setTime(wrong2);
								x.set(Calendar.MONTH, Calendar.getInstance()
										.get(Calendar.MONTH));
								x.set(Calendar.YEAR, Calendar.getInstance()
										.get(Calendar.YEAR));
								return x.getTime();
							} catch (ParseException pe3) {
								return displayDateFormat.parse(text);
								// If this one fails, data entry person should
								// get an other job (person is too creative!);
							}
						}
					}
				}
			}
		}
	}

	public final void setDate(Date d) {
		if (initProperty == null) {
			return;
		}
		if (d != null) {
			setText(displayDateFormat.format(d));
		} else {
			setText("");
		}
	}

	public final void setShowCalendarPickerButton(boolean b) {
		showCalendarPickerButton = b;
		if (isShowing()) {
			repaint();
		}
	}

	public final void setTable(MessageTable t) {
		myTable = t;
	}

	/**
	 * Stores the row/column, if this is a table editor
	 * 
	 * @param row
	 * @param column
	 */
	public final void setSelectedCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	@Override
	public final void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (showCalendarPickerButton) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.orange);
			Composite old = g2.getComposite();
			AlphaComposite ac = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.6f);
			g2.setComposite(ac);

			if (getHeight() > 15) {
				g2.fillRect(getWidth() - (getHeight() / 2), 0,
						(getHeight() / 2), (getHeight() / 2));
				g2.setColor(Color.black);
				g2.drawRect(getWidth() - (getHeight() / 2), 0,
						(getHeight() / 2), (getHeight() / 2));
				g2.drawLine(getWidth() - (getHeight() / 2) + 3, 4,
						getWidth() - 4, 4);
				g2.drawLine(getWidth() - 4, 4, getWidth() - 4,
						(getHeight() / 2) - 3);
				g2.drawLine(getWidth() - 4, 4, getWidth() - (getHeight() / 2)
						+ 3, (getHeight() / 2) - 3);
				g2.setComposite(old);
			} else {
				g2.fillRect(getWidth() - getHeight(), 0, getHeight(),
						getHeight());
				g2.setColor(Color.black);
				g2.drawRect(getWidth() - getHeight(), 0, getHeight(),
						getHeight());
				g2.drawLine(getWidth() - getHeight() + 3, 3, getWidth() - 4, 3);
				g2.drawLine(getWidth() - 4, 3, getWidth() - 4, getHeight() - 4);
				g2.drawLine(getWidth() - 4, 3, getWidth() - getHeight() + 3,
						getHeight() - 4);
				g2.setComposite(old);

			}
		}
	}

	private final void checkMousePos(MouseEvent e) {
		if (showCalendarPickerButton) {
			if (getHeight() > 15) {
				if (e.getX() > getWidth() - (getHeight() / 2)
						&& e.getX() < getWidth() && e.getY() > 0
						&& e.getY() < (getHeight() / 2)) {
					setToolTipText("Kalender");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			} else {
				if (e.getX() > getWidth() - getHeight()
						&& e.getX() < getWidth() && e.getY() > 0
						&& e.getY() < getHeight()) {
					setToolTipText("Kalender");
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					setCursor(Cursor.getDefaultCursor());
				}
			}
		}
	}

	private final void checkMouseClick(MouseEvent e) {
		if (showCalendarPickerButton && isEditable()) {
			if (getHeight() > 15) {
				if (e.getX() > getWidth() - (getHeight() / 2)
						&& e.getX() < getWidth() && e.getY() > 0
						&& e.getY() < (getHeight() / 2)) {
					JDialog jj = SwingClient.getUserInterface().getTopDialog();
					JFrame ff = SwingClient.getUserInterface().getMainFrame();

					CalendarPickerDialog cpd;
					if (jj != null) {
						cpd = new CalendarPickerDialog(jj);
					} else {
						cpd = new CalendarPickerDialog(ff);
					}
					cpd.getMainPanel()
							.setPreferredSize(new Dimension(255, 198));
					logger.info("Entering: checkMouseClick: "
							+ isEditable() + " -- " + showCalendarPickerButton
							+ " >>> " + getHeight());
					logger.info("ThreaD: "
							+ Thread.currentThread().getName());
					Property pp = getProperty();
					if (pp != null
							&& pp.getType().equals(Property.DATE_PROPERTY)) {
						cpd.setDate((Date) pp.getTypedValue());
					}
					cpd.pack();
					SwingClient.getUserInterface().addDialog(cpd);
					if (cpd.isCommitted()) {
						Date d = cpd.getSelectedDate();
						if (d != null) {
							// setDate(d);
							getProperty().setAnyValue(d);
							// resetProperty();
							// setChanged(true);
							if (myTable != null) {
								myTable.fireDataChanged();
								myTable.requestFocus();
								myTable.setRowSelectionInterval(row, row);
								myTable.editCellAt(row, column);
							}
						}
					}
				}
			} else {
				if (e.getX() > getWidth() - getHeight()
						&& e.getX() < getWidth() && e.getY() > 0
						&& e.getY() < getHeight()) {
					// CalendarPickerDialog cpd = new CalendarPickerDialog();
					JDialog jj = SwingClient.getUserInterface().getTopDialog();
					JFrame ff = SwingClient.getUserInterface().getMainFrame();

					CalendarPickerDialog cpd;
					if (jj != null) {
						cpd = new CalendarPickerDialog(jj);
					} else {
						cpd = new CalendarPickerDialog(ff);
					}
					cpd.setSize(new Dimension(250, 250));
					SwingClient.getUserInterface().addDialog(cpd);
					if (cpd.isCommitted()) {
						Date d = cpd.getSelectedDate();
						if (d != null) {
							// setDate(d);
							// resetProperty();
							// setChanged(true);
							getProperty().setAnyValue(d);

							if (myTable != null) {
								myTable.fireDataChanged();
								myTable.requestFocus();
								myTable.setRowSelectionInterval(row, row);
								myTable.editCellAt(row, column);
							}
						}
					}
				}
			}
		}
	}

	public final void setToNow() {
		setDate(new Date());
	}

	public static final String formatDate(String s) throws ParseException {
		Date d = new SimpleDateFormat().parse(s);
		return displayDateFormat.format(d);
	}

	@Override
	public final String toString() {
		return this.getText();
	}

	public final void dateUpdate() {
		if (readOnly) {
			return;
		}
		try {
			if (initProperty != null) {
				if (((initProperty.getValue() == null || initProperty
						.getValue().equals("")) && getDate() != null)
						|| (!(initProperty.getValue() == null || initProperty
								.getValue().equals("")) && getDate() == null)
						|| ((initProperty.getTypedValue() != null
								&& getDate() != null && !initProperty
								.getTypedValue().equals(getDate())))) {
					initProperty.setValue(getDate());
					if (getDate() != null) {
						setText(displayDateFormat.format(getDate()));
					}
				}
			}
		} catch (ParseException ex) {
			setText("-");
			// SwingClient.getUserInterface().setStatusText("Invalid date format");
			// ErrorHandler eh = new ErrorHandler(ex);
		}
	}

	@Override
	public final void update() {
		// logger.info("YOU FILTHY BASTARD! YOU SHOULD NOT CALL THIS FUNCTION! LUCKILY, WE CAUGHT YOUR ERROR! "
		// + getClass());
		dateUpdate();
	}

	@Override
	public final void focusLost(FocusEvent e) {
		// No call to super. Good.
		dateUpdate();
	}

	public static void main(String[] args) {
		try {
			String text = null;
			DatePropertyField df = new DatePropertyField();

			text = "7-5";
			df.setText(text);
			logger.info("INPUT1: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "07-5";
			df.setText(text);
			logger.info("INPUT2: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "7-05";
			df.setText(text);
			logger.info("INPUT3: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "07-05";
			df.setText(text);
			logger.info("INPUT4: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "06";
			df.setText(text);
			logger.info("INPUT5: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "1-12";
			df.setText(text);
			logger.info("INPUT6: " + text + " gives:\t "
					+ df.getDate().toString());

			text = "01-01-12005";
			df.setText(text);
			logger.info("INPUT6: " + text + " gives:\t "
					+ df.getDate().toString());

		} catch (Exception e) {
			logger.error("Error: ",e);
		}

	}

	public void propertyChange(PropertyChangeEvent e) {
		setDate((Date) e.getNewValue());
	}

}
