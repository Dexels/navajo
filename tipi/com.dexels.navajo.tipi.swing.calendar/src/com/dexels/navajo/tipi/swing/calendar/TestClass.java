package com.dexels.navajo.tipi.swing.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.miginfocom.beans.DateAreaBean;
import com.miginfocom.calendar.activity.Activity;
import com.miginfocom.calendar.activity.ActivityDepository;
import com.miginfocom.calendar.activity.DefaultActivity;
import com.miginfocom.calendar.activity.recurrence.ByXXXRuleData;
import com.miginfocom.calendar.activity.recurrence.RecurrenceRule;
import com.miginfocom.calendar.datearea.DefaultDateArea;
import com.miginfocom.util.ActivityHelper;
import com.miginfocom.util.dates.DateRange;
import com.miginfocom.util.dates.DateRangeI;
import com.miginfocom.util.dates.ImmutableDateRange;
import com.miginfocom.util.dates.TimeSpanListEvent;
import com.miginfocom.util.gfx.geometry.numbers.AtFraction;

public class TestClass extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -286048306821410881L;

	public TestClass() {
       setTitle("Simple example");
       setSize(1024, 768);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       DateAreaImpl c = new DateAreaImpl();
       add(c);
       
    }
    

    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	TestClass ex = new TestClass();
                ex.setVisible(true);
            }
        });
    }
    
    
}
