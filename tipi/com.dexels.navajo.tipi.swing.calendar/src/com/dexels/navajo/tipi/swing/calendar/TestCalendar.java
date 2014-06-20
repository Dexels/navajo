package com.dexels.navajo.tipi.swing.calendar;

import java.awt.EventQueue;

import javax.swing.JFrame;
import com.miginfocom.beans.DateHeaderBean;
import com.miginfocom.calendar.header.CellDecorationRow;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.dates.DateFormatList;
import com.miginfocom.util.gfx.geometry.numbers.AtFixed;
import com.miginfocom.util.gfx.geometry.AbsRect;
import com.miginfocom.util.gfx.geometry.numbers.AtStart;
import com.miginfocom.util.gfx.geometry.numbers.AtEnd;
import java.awt.Paint;
import java.awt.Color;
import com.miginfocom.util.repetition.DefaultRepetition;
import java.awt.Font;
import javax.swing.plaf.FontUIResource;
import com.miginfocom.util.gfx.geometry.numbers.AtFraction;
import com.miginfocom.beans.DateAreaBean;
import java.awt.BorderLayout;
import com.miginfocom.beans.WestCategoryHeaderBean;
import com.miginfocom.beans.DemoDataBean;
import com.miginfocom.beans.PrintSpecificationBean;

public class TestCalendar {

	private JFrame frame;
	/**
	 * @wbp.nonvisual location=503,243
	 */
	private final DemoDataBean demoDataBean = new DemoDataBean();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestCalendar window = new TestCalendar();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestCalendar() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DateAreaBean dateAreaBean = new DateAreaBean();
		frame.getContentPane().add(dateAreaBean, BorderLayout.CENTER);
	}

}
