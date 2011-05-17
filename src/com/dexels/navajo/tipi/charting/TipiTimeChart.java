package com.dexels.navajo.tipi.charting;


import java.awt.Color;
import java.awt.Paint;
import java.io.InputStream;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.time.Week;
import org.jfree.data.xml.DatasetReader;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.Selection;
import com.dexels.navajo.document.types.ClockTime;
import com.dexels.navajo.document.types.StopwatchTime;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;

public class TipiTimeChart extends TipiChart {

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
	            System.err.println("Adding series: " + valuePropName);
	            
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
	      e.printStackTrace();
	      return null;
	    }
	  }

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
	}
	
	
	
}
