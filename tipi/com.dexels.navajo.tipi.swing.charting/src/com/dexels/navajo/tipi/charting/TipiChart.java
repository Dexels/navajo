package com.dexels.navajo.tipi.charting;

import java.awt.GridBagConstraints;

import org.jfree.chart.ChartPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.components.core.TipiDataComponentImpl;

public abstract class TipiChart extends TipiDataComponentImpl {

	private static final long serialVersionUID = -957662862872135436L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiChart.class);
	protected ChartPanel myChart;
	protected String xAxis;
	protected String series;
	protected String chartType;
	protected String message;
	protected String label_x;
	protected String label_y;
	protected String title;
	
	public Object createContainer() {		 
		myChart = new ChartPanel(null);
		myChart.setMaximumDrawHeight(10000);
		myChart.setMaximumDrawWidth(10000);
		return myChart;
	}

	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
		
		if("x-axis".equals(name)){
			this.xAxis = object.toString();
		}
		
		if("series".equals(name)){
			this.series = object.toString();
		}
		if("chartType".equals(name)){
			this.chartType = object.toString();
		}
		if("dataMessage".equals(name)){
			this.message = object.toString();
		}
		if("label-x".equals(name)){
			this.label_x = object.toString();
		}
		if("label-y".equals(name)){
			this.label_y = object.toString();
		}
		if("title".equals(name)){
			this.title = object.toString();
		}
	}

	public static void main(String[] artgs){
		logger.info("CENTER: " + GridBagConstraints.CENTER);
		logger.info("NONE: " + GridBagConstraints.NONE);
	}
	
}
