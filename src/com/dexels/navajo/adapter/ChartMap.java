package com.dexels.navajo.adapter;

import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.core.framework.PlatformConfig;

import com.dexels.navajo.document.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.server.*;

public class ChartMap implements Mappable{
	private ChartEngine engine = new ChartEngine();
	private Message data = null;
	public String title;
	public String seriesLabel;
	public int type;
	public String outputFile;
	
	private String engineDir;
	private PlatformConfig platformConfig;
	
	
	public ChartMap(){
		
	}

	public void kill() {
	
	}
	
	public void setTitle(String s){
		this.title = s; 
	}

	public void setSeriesLabel(String s){
		this.seriesLabel = s; 
	}
	
	public void setOutputFile(String s){
		this.outputFile = s; 
	}
	
	public void setType(int i){
		this.type = i; 
	}
	
//	public String getTitle(){
//		 return title; 
//	}
//
//	public String getSeriesLabel(){
//		return seriesLabel; 
//	}
//	
//	public String getOutputFile(){
//		return outputFile; 
//	}
	
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

		
	    // paths
	    engineDir = inMessage.getMessage("__globals__").getProperty("BIRTEngineDir").getValue();
	    System.err.println("BIRTEngineDir: " + engineDir);

	    platformConfig = new PlatformConfig();
//	    platformConfig.setBIRTHome(engineDir);
	
	    try{

			// we expect the Result message to be incoming..
			inMessage.write(System.err);
			data = inMessage.getMessage("Result");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void getChart(){
		engine.setTitle(title);
		engine.setXAxisProperty("Week", "week");
		engine.setYAxisProperty("Value", "value");
		engine.addData(data, type, seriesLabel);
		engine.createChart();
		engine.saveChart(outputFile);
	}
	
	public void store() throws MappableException, UserException {
		System.err.println("Title : " + title);
		System.err.println("Type  : " + type);
		System.err.println("Series: " + seriesLabel);
		System.err.println("File  : " + outputFile);
		getChart();		
	}
	
}