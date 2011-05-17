package com.dexels.navajo.tipi.charting;


import java.awt.Color;
import java.util.HashSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.TableOrder;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiGenericChart extends TipiSwingDataComponentImpl {

	protected ChartPanel myChart;
	
	private String series = "Sex";
	private String category = "SeasonId";
	private String value = "Total";
	private String messagePath = "HistoricOverview";
	private String type = "";
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
	}	
	
	public JFreeChart createChart(Navajo n) {
	    try {

	      Message stats = n.getMessage(messagePath);
	      
	      DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
	      DefaultPieDataset pieDataSet = new DefaultPieDataset();

	      HashSet seriesSet = new HashSet();
	      
	      for ( int i = 0; i < stats.getArraySize(); i++ ) {
	    	  Message row = stats.getMessage(i);
	    	  Object seriesProp = row.getProperty(series).getTypedValue();
	    	  seriesSet.add(seriesProp);
	    	  Object catProp = row.getProperty(category).getTypedValue();
	    	  Number valueProp = (Number) row.getProperty(value).getTypedValue();
	    	  dataSet.addValue(valueProp, (Comparable) seriesProp, (Comparable) catProp);
	    	  pieDataSet.setValue((Comparable) catProp, valueProp);
	      }
	         
	      boolean hasMultipleSeries = seriesSet.size() > 1;
	      
	      String xAxisTitle = stats.getMessage(0).getProperty(category).getDescription();
	      String yAxisTitle = stats.getMessage(0).getProperty(value).getDescription();
	      
	      JFreeChart f = null;
	      if ( type.equals("bar")) {
	       f = ChartFactory.createBarChart(title, xAxisTitle, yAxisTitle, dataSet, PlotOrientation.VERTICAL, true, true, false);
	      } else if ( type.equals("pie") ) {
	    	  if ( !hasMultipleSeries ) {
	    		  f = ChartFactory.createPieChart(title, pieDataSet, true, true, false);
	    	  } else {
	    		  f = ChartFactory.createMultiplePieChart(title, dataSet, TableOrder.BY_COLUMN, true, true, false);
	    	  }
	      } else {
	    	  f = ChartFactory.createLineChart(title, xAxisTitle, yAxisTitle, dataSet, PlotOrientation.VERTICAL, true, true, false);
	      }
	     
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
		
		if("type".equals(name)){
			this.type = object.toString();
		}
		
		if("series".equals(name)){
			this.series = object.toString();
		}
		
		if("category".equals(name)){
			this.category = object.toString();
		}
		
		if("value".equals(name)){
			this.value = object.toString();
		}
		
		if("messagePath".equals(name)){
			this.messagePath = object.toString();
		}
	}
	
	
	@Override
	protected void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
		render();
	}

	public void render() {
		JFreeChart chart = createChart(getNavajo());		
		chart.getPlot().setBackgroundPaint(Color.black);		
		myChart.setChart(chart);	
	}
	
	
	
}
