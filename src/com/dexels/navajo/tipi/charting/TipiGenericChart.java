package com.dexels.navajo.tipi.charting;


import java.awt.Color;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;

public class TipiGenericChart extends TipiSwingDataComponentImpl {

	protected ChartPanel myChart;
	
	private String series = "Sex";
	private String category = "SeasonId";
	private String value = "Total";
	private String messagePath = "HistoricOverview";
	private String type = "bar";
	private String title = "Title";
	
	public Object createContainer() {		 
		myChart = new ChartPanel(null);
		myChart.setMaximumDrawHeight(10000);
		myChart.setMaximumDrawWidth(10000);
		return myChart;
	}
	
	@Override
	public void loadData(Navajo n, String method) throws TipiException, TipiBreakException {				
		super.loadData(n, method);
		JFreeChart chart = createChart(n);		
		chart.getPlot().setBackgroundPaint(Color.black);		
		myChart.setChart(chart);	
	}	
	
	public JFreeChart createChart(Navajo n) {
	    try {

	      Message stats = n.getMessage(messagePath);
	      
	      DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
	     
	      for ( int i = 0; i < stats.getArraySize(); i++ ) {
	    	  Message row = stats.getMessage(i);
	    	  Object seriesProp = row.getProperty(series).getTypedValue();
	    	  Object catProp = row.getProperty(category).getTypedValue();
	    	  Number valueProp = (Number) row.getProperty(value).getTypedValue();
	    	  dataSet.addValue(valueProp, (Comparable) seriesProp, (Comparable) catProp);
	      }
	         
	      String xAxisTitle = stats.getMessage(0).getProperty(category).getDescription();
	      String yAxisTitle = stats.getMessage(0).getProperty(value).getDescription();
	      
	      JFreeChart f = ChartFactory.createBarChart(title, xAxisTitle, yAxisTitle, dataSet, PlotOrientation.VERTICAL, true, true, false);

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
		
		if("title".equals(name)){
			this.title = object.toString();
		}
	}
	
	
	
}
