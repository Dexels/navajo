/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//A simple clock application using javax.swing.Timer class
public class TipiStopWatch extends TipiSwingDataComponentImpl {
	private static final long serialVersionUID = 1720958060528831564L;
	private Timer myTimer1;
	public static final int ONE_SEC = 1000;   //time step in milliseconds
	public static final int TENTH_SEC = 100;
	private Locale locale = Locale.getDefault();
	private TimeZone timeZone = TimeZone.getDefault();
	private DecimalFormat formatNoDecimals = new DecimalFormat("#0");


	private Font myClockFont = new Font("Serif", Font.PLAIN, 50);
	private Color fontColor = Color.BLACK;

	private JButton startBtn;
	private JButton stopBtn;
	private JButton resetBtn;
	private JButton getClockTimeBtn;
	private JLabel timeLbl;
	private JPanel topPanel, bottomPanel;

	private int initialClockTick = 0;  	//number of clock ticks; tick can be 1.0 s or 0.1 s
	private int clockTick = 0;  	//number of clock ticks; tick can be 1.0 s or 0.1 s
	private double clockTime;  	//time in seconds
	private double maxClockTime;  	//time in seconds
	private String clockTimeString;
	private boolean countBackwards = false; // will count downwards and stop at 0
	private boolean useDecimals = true; // you can use this to count in complete seconds or with decimals


	public TipiStopWatch()
	{
		clockTime = ((double)clockTick)/10.0;
		myTimer1 = new Timer(TENTH_SEC, new ActionListener() {
		public void actionPerformed(ActionEvent evt) {
			System.out.println(clockTime);
			if (countBackwards) {
				if (clockTick <= 0) {
					stop();
				} else {
					clockTick--;
				}
			} else {
				if (clockTick >= maxClockTime) {
					stop();
				} else {
					clockTick++;
				}
			}
			if (useDecimals) {
				clockTime = ((double)clockTick) / 10.0;
			} else {
				if ( ( Math.floor(clockTime) % 1 ) == 0.0 ) {
					clockTime = Math.floor(clockTick / 10);
				} else {
					clockTime = ((double)clockTick) / 10.0;
				}
			}
			clockTimeString = formatClockTimeString(clockTime);
			updateTimeLabel(clockTimeString);
		    }
		});
	}

	public int getInitialClockTick() {
		return initialClockTick;
	}
	public void setInitialClockTick(int initialClockTick) {
		this.initialClockTick = initialClockTick;
		this.setClockTick(initialClockTick);
	}

	public int getClockTick() {
		return clockTick;
	}
	public void setClockTick(int clockTick) {
		this.clockTick = clockTick;
	}

	public double getMaxClockTime() {
		return maxClockTime;
	}
	public void setMaxClockTime(double maxClockTime) {
		this.maxClockTime = maxClockTime;
	}

	public boolean isCountBackwards() {
		return countBackwards;
	}
	public void setCountBackwards(boolean countBackwards) {
		this.countBackwards = countBackwards;
	}
	
	public Color getFontColor() {
		return fontColor;
	}
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public boolean isUseDecimals() {
		return useDecimals;
	}
	public void setUseDecimals(boolean useDecimals) {
		this.useDecimals = useDecimals;
	}

	private JLabel createTimeLabel() {
		timeLbl = new JLabel();
		timeLbl.setFont(myClockFont);
		timeLbl.setForeground(fontColor);
		timeLbl.setHorizontalTextPosition(SwingConstants.CENTER);
		timeLbl.setVerticalTextPosition(SwingConstants.CENTER);
		timeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		timeLbl.setText(clockTimeString);
		return timeLbl;
	}
	
	private JLabel updateTimeLabel(String text) {
		timeLbl.setText(text);
		return timeLbl;
	}
	
	private JButton createStartButton() {
		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				start();
			}
		});
		return startBtn;
	}

	private JButton createStopButton() {
		stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				stop();
			}
		});
		return stopBtn;
	}

	private JButton createGetClockTimeButton() {
		getClockTimeBtn = new JButton("Tijd?");
		getClockTimeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				getClockTime("Tijd?");
			}
		});
		return getClockTimeBtn;
	}
	
	public void start() {
		myTimer1.start();
		// print the exact moment that stuff started
		getClockTime("Start");
	}
	public void stop() {
		myTimer1.stop();
		// print the exact moment that stuff stopped
		getClockTime("Stop");
	}
	public void reset() {
		clockTick = this.initialClockTick;
		clockTime = clockTick;
		clockTimeString = formatClockTimeString(clockTime);
		timeLbl.setText(clockTimeString);
		// print the exact moment that stuff reset
		getClockTime("Reset");
	}
	public Date getClockTime(String text) {
		Calendar calendar = Calendar.getInstance(timeZone, locale);
		System.out.println(text + " -> " + calendar.getTime());
		return calendar.getTime();
	}

	private JButton createResetButton() {
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				reset();
			}
		});
		return resetBtn;
	}

	public Component createStopWatchPanel() {
		topPanel = new JPanel();
		topPanel.setBackground(Color.orange);
		topPanel.setSize(300,200);
		topPanel.setBackground(Color.orange);
		topPanel.setLayout(new BorderLayout());
		topPanel.add(createTimeLabel(), BorderLayout.CENTER);

		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.yellow);
		bottomPanel.add(createStartButton());
		bottomPanel.add(createStopButton());
		bottomPanel.add(createResetButton());
		bottomPanel.add(createGetClockTimeButton());

		topPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		return topPanel;
	}
	
	private String formatClockTimeString(Double input) {
		String output = "";
		if (useDecimals) {
			output = input.toString();
		} else {
			if ( ( Math.floor(input) % 1 ) == 0.0 ) {
				input = Math.floor(clockTick / 10);
				output = formatNoDecimals.format(input);
			} else {
				input = ((double)clockTick) / 10.0;
				output = input.toString();
			}
		}
		return output;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				TipiStopWatch stopWatch = new TipiStopWatch();
				stopWatch.setCountBackwards(false);
				stopWatch.setInitialClockTick(0);
				stopWatch.setMaxClockTime(50);
				stopWatch.setFontColor(Color.GRAY);
				stopWatch.setUseDecimals(true);
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container panel = frame.getContentPane();
				panel.setLayout(new BorderLayout());
				//stopWatch.launchStopWatch();
				panel.add(stopWatch.createStopWatchPanel(), BorderLayout.CENTER);
				frame.pack();
				frame.setSize(500, 300);
				frame.setVisible(true);
			}
		});
	}
}
