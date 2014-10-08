package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.javaswingcomponents.clock.analog.JSCAnalogClock;
import com.javaswingcomponents.clock.analog.JSCAnalogClock.AnalogClockPosition;
import com.javaswingcomponents.clock.analog.plaf.AnalogClockTextPosition;
import com.javaswingcomponents.clock.analog.plaf.AnalogClockUI;
import com.javaswingcomponents.clock.analog.plaf.basic.BasicDatePainter;
import com.javaswingcomponents.clock.analog.plaf.basic.BasicDatePainter.DisplayFormat;
import com.javaswingcomponents.clock.analog.plaf.basic.BasicHandPainter;
import com.javaswingcomponents.clock.analog.plaf.basic.BasicTextPainter;
import com.javaswingcomponents.clock.analog.plaf.darksteel.DarkSteelAnalogClockUI;
import com.javaswingcomponents.clock.analog.plaf.darksteel.DarkSteelSmallAnalogClockUI;
import com.javaswingcomponents.clock.analog.plaf.steel.SteelAnalogClockUI;
import com.javaswingcomponents.clock.analog.plaf.steel.SteelSmallAnalogClockUI;
import com.javaswingcomponents.clock.common.model.StaticClockModel;
import com.javaswingcomponents.framework.painters.text.TextPosition;

public class TipiClock extends TipiSwingDataComponentImpl {
	private static final long serialVersionUID = 7720368571935784065L;
	private final static Logger logger = LoggerFactory.getLogger(TipiClock.class);
	private JSCAnalogClock myClock = new JSCAnalogClock();
	private boolean digitalMode = false;
	private Locale locale = Locale.getDefault();
	private TimeZone timeZone = TimeZone.getDefault();
	private AnalogClockPosition clockPosition = AnalogClockPosition.CENTER;
	private BasicDatePainter datePainter = null;
	private BasicTextPainter textPainter = null;
	private BasicHandPainter handPainter = null;
	
	public TipiClock() {
		this.myClock.setLocale(locale);
		this.myClock.setTimeZone(timeZone);
		this.myClock.setAnalogClockPosition(clockPosition);
		this.myClock.setAccurateSeconds(true);
		this.myClock.setAntiAlias(true);
		this.myClock.setAutoscrolls(true);
		datePainter = new BasicDatePainter(this.myClock);
		textPainter = new BasicTextPainter(this.myClock);
		handPainter = new BasicHandPainter(this.myClock);
	}

	@Override
	public Object createContainer() {
		myClock = new JSCAnalogClock();
		TipiHelper th = new TipiSwingHelper();
		th.initHelper(this);
		addHelper(th);
		return myClock;
	}

	@Override
	public Object getContainer() {
		return myClock;
	}

	@Override
	public void setComponentValue(final String name, final Object object) {
		if (name.equals("name")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myClock.setName((String) object);
				}
			});
			return;
		}
		if (name.equals("opaque")) {
			runSyncInEventThread(new Runnable() {
				@Override
				public void run() {
					myClock.setOpaque(Boolean.parseBoolean((String) object));
				}
			});
			return;
		}
		super.setComponentValue(name, object);
	}

	@Override
	public Object getComponentValue(String name) {
		if (name.equals("name")) {
			return myClock.getName();
		}
		if (name.equals("height")) {
			return new Integer(myClock.getHeight());
		}
		return super.getComponentValue(name);
	}

	public JSCAnalogClock getMyClock() {
		return myClock;
	}

	public void setMyClock(JSCAnalogClock myClock) {
		this.myClock = myClock;
	}
	
	public boolean isDigitalMode() {
		return digitalMode;
	}

	public void setDigitalMode(boolean digitalMode) {
		this.digitalMode = digitalMode;
	}

	public void startClock() {
		this.myClock.start();
	}
	
	public void stopClock() {
		this.myClock.stop();
	}
	
	public void setStaticTime(Date date) {
		//this code creates the appropriate date object for new years.
		Calendar calendar = Calendar.getInstance(timeZone, locale);
		calendar.setTime(date);
		StaticClockModel staticClockModel = new StaticClockModel(calendar.getTime());
		//we set the new model on the clock.
		getMyClock().setClockModel(staticClockModel);		
	}

	public void setText(String text) {
		setText(text, 8, null, null);
	}
	public void setText(String text, float fontSize) {
		setText(text, fontSize, null, null);
	}
	public void setText(String text, float fontSize, String textPosition, String textColor) {
		Color color = Color.WHITE;
		TextPosition position = TextPosition.CENTER;
		AnalogClockTextPosition boxPosition = AnalogClockTextPosition.SOUTH;
		
		//to ensure that the appropriate painter is set
		//as not all look and feels are intended to support text
		//I will be overriding the clock's textPainter with an
		//implementation of the BasicTextPainter from the
		//BasicAnalogClockUI. It is perfectly safe to mix
		//and match painters from different look and feels
		myClock.setTextPainter(textPainter);
		
		if (textPosition != null) {
			if (textPosition.equalsIgnoreCase("north")) {
//				position = TextPosition.NORTH;
				boxPosition = AnalogClockTextPosition.NORTH;
			} else if (textPosition.equalsIgnoreCase("center")) {
				position = TextPosition.CENTER;
				boxPosition = AnalogClockTextPosition.CENTER;
			} else if (textPosition.equalsIgnoreCase("south")) {
//				position = TextPosition.SOUTH;
				boxPosition = AnalogClockTextPosition.SOUTH;
			}
		}
		textPainter.setPositionInBox(position);
		textPainter.setTextPosition(boxPosition);
		
		if (textColor != null) {
			color = Color.getColor(textColor);
		}
		textPainter.setTextColor(color);
		textPainter.setFontSize((fontSize / 100));
		myClock.setText(text);
		myClock.setDrawText(true);
	}
	
	public void setHandPainter() {
		handPainter.setHourHandInnerColor(Color.BLACK);
		handPainter.setHourHandOutterColor(Color.LIGHT_GRAY);
		handPainter.setMinuteHandInnerColor(Color.BLACK);
		handPainter.setMinuteHandOutterColor(Color.LIGHT_GRAY);
		handPainter.setSecondHandColor(Color.WHITE);
		myClock.setHandPainter(handPainter);
	}
	
	public void showAmPmIndicator(boolean showIndicator) {
		if (showIndicator) {
			datePainter.setDisplayFormat(DisplayFormat.AM_PM);
		}
		myClock.setDatePainter(datePainter);
	}
	
	public void showDay(boolean showDay) {
		if (showDay) {
			datePainter.setDisplayFormat(DisplayFormat.DAY_OF_MONTH);
		}
		myClock.setDatePainter(datePainter);
	}
	
	public void showDate(String datePosition) {
		showDate(datePosition, null);
	}
	public void showDate(String datePosition, String fillColor) {
		AnalogClockTextPosition position = AnalogClockTextPosition.EAST;
		Color color = Color.LIGHT_GRAY;
		
		//to ensure that the appropriate painter is set
		//as not all look and feels are intended to support date
		//I will be overriding the clock's datePainter with an
		//implementation of the BasicDatePainter from the
		//BasicAnalogClockUI. It is perfectly safe to mix
		//and match painters from different look and feels
		myClock.setDatePainter(datePainter);
		//this will ensure the the clock will draw the date
		myClock.setDrawDate(true);
		//this will draw the date on the right of the clock
		if (datePosition != null) {
			if (datePosition.equalsIgnoreCase("east")) {
				position = AnalogClockTextPosition.EAST;
			} else if (datePosition.equalsIgnoreCase("west")) {
				position = AnalogClockTextPosition.WEST;
			} else if (datePosition.equalsIgnoreCase("center")) {
				position = AnalogClockTextPosition.CENTER;
			} else if (datePosition.equalsIgnoreCase("south")) {
				position = AnalogClockTextPosition.SOUTH;
			} else if (datePosition.equalsIgnoreCase("northeast")) {
				position = AnalogClockTextPosition.NORTH_EAST;
			} else if (datePosition.equalsIgnoreCase("northwest")) {
				position = AnalogClockTextPosition.NORTH_WEST;
			} else if (datePosition.equalsIgnoreCase("southeast")) {
				position = AnalogClockTextPosition.SOUTH_EAST;
			} else if (datePosition.equalsIgnoreCase("southwest")) {
				position = AnalogClockTextPosition.SOUTH_WEST;
			}
		}
		datePainter.setTextPosition(position);
		if (fillColor != null) {
			color = Color.getColor(fillColor);
		}
		datePainter.setFillColor(color);
	}
	
	public void setLAF(String laf) {
		AnalogClockUI newUI = DarkSteelAnalogClockUI.createUI(myClock);
		if (laf != null) {
			if (laf.equalsIgnoreCase("steel")) {
				newUI = (AnalogClockUI) SteelAnalogClockUI.createUI(myClock);
			} else if (laf.equalsIgnoreCase("steelsmall")) {
					newUI = (AnalogClockUI) SteelSmallAnalogClockUI.createUI(myClock);
			} else if (laf.equalsIgnoreCase("darksteel")) {
				newUI = (AnalogClockUI) DarkSteelAnalogClockUI.createUI(myClock);
			} else if (laf.equalsIgnoreCase("darksteelsmall")) {
				newUI = (AnalogClockUI) DarkSteelSmallAnalogClockUI.createUI(myClock);
			}
		}
		myClock.setUI(newUI);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TipiClock clock = new TipiClock();
				//clock.setStaticTime(new Date());
				//clock.setLAF("darksteel");
				clock.setLAF("steel");
				clock.showDay(true);
				clock.showAmPmIndicator(false);
				clock.setText("Waterpolo met gevoel");
				clock.setHandPainter();
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container panel = frame.getContentPane();
				panel.setLayout(new BorderLayout());
				panel.add(clock.getMyClock(), BorderLayout.CENTER);
				frame.pack();
				frame.setSize(500, 300);
				frame.setVisible(true);
				
				System.out.println("Current time: " + clock.getMyClock().getValue());
//				clock.stopClock();
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				clock.startClock();
//				System.out.println("Current time: " + clock.getMyClock().getValue());
			}
		});
	}
}
