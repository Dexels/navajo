package com.dexels.navajo.adapter;

import org.eclipse.birt.report.engine.api.*;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.types.*;
import java.util.*;
import java.io.*;
 
public class BIRTMap implements Mappable {
	private HashMap parameters;
	private  String reportDir = "reports/";
	private String engineDir = "birt-engine/";

	public Binary report;
	public String reportName;
	public String outputFormat;
	public Object parameterValue;
	public String parameterName;

 
  public Binary getReport(){
	  
	  try{
		  return  executeReport(reportName, parameters, outputFormat);
	  }catch(Exception e){
		  e.printStackTrace();
		  return null;
	  }
	  
  }
  
  public void setReportName(String s){
    this.reportName = s;
  }

  public void setOutputFormat(String s){
    this.outputFormat = s;
  }

  public void setParameterName(String s){
    this.parameterName = s;
  }

  public void setParameterValue(Object o){
    if(parameters == null){
      parameters = new HashMap();
    }
    parameters.put(parameterName, o);
  }

  private Binary executeReport(String reportName, HashMap reportParams, String outputFormat) throws EngineException {
	  //ByteArrayOutputStream bout = new ByteArrayOutputStream();
	  
	  ReportEngine engine = null;
	  
	  try {
		  Binary result = new Binary();
		  //Engine Configuration - set and get temp dir, BIRT home, Servlet context
		  
		  EngineConfig config = new EngineConfig();
		  config.setEngineHome(engineDir);
		  engine = new ReportEngine(config);
		  
		  //Open a report design - use design to modify design, retrieve embedded images etc.
		  IReportRunnable design = engine.openReportDesign(reportDir + reportName + ".rptdesign");
		  
		  //Create task to run the report - use the task to execute and run the report,
		  IRunAndRenderTask task = engine.createRunAndRenderTask(design);
		  task.setParameterValues(reportParams);
		  
		  //Set render context to handle url and image locataions
		  if (outputFormat == "html") {
			  HTMLRenderContext renderContextHTML = new HTMLRenderContext();
			  renderContextHTML.setImageDirectory("image");
			  HashMap contextMap = new HashMap();
			  contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContextHTML);
			  task.setAppContext(contextMap);
			  
			  //Set rendering options - such as file or stream output,
			  //output format, whether it is embeddable, etc
			  HTMLRenderOption options = new HTMLRenderOption();
			  options.setOutputStream(result.getOutputStream());
			  options.setOutputFormat("html");
			  task.setRenderOption(options);
		  }
		  else {
			  RenderOptionBase options = new RenderOptionBase();
			  options.setOutputFormat("pdf");
			  options.setOutputStream(result.getOutputStream());
			  task.setRenderOption(options);
		  }
		  
		  //run the report and destroy the engine
		  task.run();
		  // engine.destroy();
		  return result;
	  } finally {
		  if ( engine != null ) {
			  engine.destroy();
		  }
	  }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
	  System.err.println("User dir: "+System.getProperty("user.dir"));
	  BIRTMap bm = new BIRTMap();

    bm.setReportName("ProcessQueryInvoice");
    bm.setOutputFormat("pdf");
    bm.setParameterName("ClubIdentifier");
    bm.setParameterValue("BBKY84H");
    bm.setParameterName("InvoiceId");
    bm.setParameterValue("1758");

    try {
      Binary b = bm.getReport();
      System.err.println(b.getBase64());
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * A Mappable class is executed by the Navajo Mapping Environment.
   *
   * @param parms Parameters
   * @param inMessage Navajo
   * @param access Access
   * @param config NavajoConfig
   * @throws MappableException
   * @throws UserException
   * @todo Implement this com.dexels.navajo.mapping.Mappable method
   */
  public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
    // paths
    reportDir = inMessage.getMessage("__globals__").getProperty("BIRTReportDir").getValue();
    engineDir = inMessage.getMessage("__globals__").getProperty("BIRTEngineDir").getValue();

    System.err.println("BIRTReportDir: " + reportDir);
    System.err.println("BIRTEngineDir: " + engineDir);

  }

  /**
   * store
   *
   * @throws MappableException
   * @throws UserException
   * @todo Implement this com.dexels.navajo.mapping.Mappable method
   */
  public void store() throws MappableException, UserException {
  }

  /**
   * kill
   *
   * @todo Implement this com.dexels.navajo.mapping.Mappable method
   */
  public void kill() {
  }
  
}
