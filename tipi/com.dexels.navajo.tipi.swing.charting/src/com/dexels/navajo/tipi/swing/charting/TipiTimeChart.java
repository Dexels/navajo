/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.swing.charting;


import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;

public class TipiTimeChart extends TipiChart {

	private static final long serialVersionUID = -8579552742186344850L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTimeChart.class);
	
	private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");	

	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {				
		super.loadData(n, method);
		JFreeChart chart = createChart(n, xAxis, message, title, label_x, label_y);		
		chart.getPlot().setBackgroundPaint(Color.black);		
		myChart.setChart(chart);	
	}	
	
	public JFreeChart createChart(Navajo n, String nameProp, String messageName, String title, String xaxis, String yaxis) {
	    try {

	      Message stats = n.getMessage(messageName);
	      TimeSeriesCollection dataset = new TimeSeriesCollection();

	      if (series != null) {
	    	  StringTokenizer tok = new StringTokenizer(series, ",");
	        
	          while(tok.hasMoreTokens()){	        	  
	            String valuePropName = tok.nextToken().trim();	            
	            logger.info("Adding series: " + valuePropName);
	            
	            TimeSeries ts = new TimeSeries(valuePropName);
	            for(int i=0;i<stats.getArraySize();i++) {
	            	Message row = stats.getMessage(i);
	            	String timeString = row.getProperty(xAxis).getValue();
	            	Date dateValue = sdf.parse(timeString);
	            	RegularTimePeriod p = RegularTimePeriod.createInstance(Millisecond.class, dateValue, TimeZone.getDefault());
	            	ts.add(p, Double.parseDouble(row.getProperty(valuePropName).getValue()));
	            }

	            dataset.addSeries(ts);
	          }
	        }
	    	     
	      JFreeChart f = ChartFactory.createTimeSeriesChart(title, xaxis, yaxis, dataset, true, true, false);

	      return f;
	    }
	    catch (Exception e) {
	      logger.error("Error: ",e);
	      return null;
	    }
	  }

	
	
	
}
