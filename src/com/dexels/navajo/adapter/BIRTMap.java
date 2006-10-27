package com.dexels.navajo.adapter;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.server.*;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.*;
import java.util.*;
import java.util.logging.Level;
import java.io.*;
 
public class BIRTMap implements Mappable {
	private HashMap parameters;
	private  String reportDir = "reports/";
	private String engineDir = "birt-engine/";
	private String directFile = null; //"c:/projects/sportlink-serv/navajo-tester/auxilary/reports/tempReport51567.rptdesign";

	public Binary report;
	public String reportName;
	public String outputFormat;
	public Object parameterValue;
	public String parameterName;

	private static IReportEngine myEngine = null;
 
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

  private File getFixedReport(String reportName, String dataUrl, String username, String password) throws XMLParseException, IOException {
	  File reportFile = new File(reportDir+reportName+ ".rptdesign");
	  System.err.println("ReportFile: "+reportFile.getAbsolutePath()+" dataURL: "+dataUrl);
	  FileReader fw = new FileReader(reportFile);
	  XMLElement xe = new CaseSensitiveXMLElement();
	  xe.parseFromReader(fw);
	  System.err.println("Toplevel tagname: "+xe.getName());
	  fw.close();
	  XMLElement dataSources =  xe.getElementByTagName("data-sources");
	  if (dataSources==null) {
		System.err.println("No datasources found"); 
		return null;
	  }
	  XMLElement dataSource = dataSources.getElementByTagName("oda-data-source");
	  System.err.println(dataSources);
	  if (dataSource==null) {
			System.err.println("No datasource found"); 
			return null;
	  }
	  Vector children = dataSource.getChildren();
	  for (int i = 0; i < children.size(); i++) {
		XMLElement current = (XMLElement)children.get(i);
		String name = current.getStringAttribute("name");
		if ("odaURL".equals(name)) {
			current.setContent(dataUrl);
		}
		if ("odaUser".equals(name)) {
			current.setContent(username);
		}
		if ("odaPassword".equals(name)) {
			current.setContent(password);
		}
	}
	  File temp = File.createTempFile("tempReport", ".rptdesign");
	  FileWriter ffw = new FileWriter(temp);
	  ffw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	  ffw.write("<!-- Written by NavajoBIRTAdapter  -->\n");

	  xe.write(ffw);
	  ffw.flush();
	  fw.close();
	  return temp;
  }
  
  private Binary executeReport(String reportName, HashMap reportParams, String outputFormat) throws EngineException, FileNotFoundException {
	  //ByteArrayOutputStream bout = new ByteArrayOutputStream();
	  
//	  IReportEngine engine = null;
	  Binary result = new Binary();
		  //Engine Configuration - set and get temp dir, BIRT home, Servlet context
		  if (myEngine==null) {
			  EngineConfig config = new EngineConfig();
			  System.err.println("Using engine dir: "+engineDir);
			  config.setEngineHome(engineDir);
			  System.err.println(">>> "+System.getProperty("java.io.tmpdir"));
//			  File f = File.createTempFile(System.getProperty("java.io.tmpdir"), ".txt").getParentFile();
			  config.setLogConfig(System.getProperty("java.io.tmpdir"), Level.SEVERE);
			  System.err.println("System home: " +config.getBIRTHome());  
			  System.err.println(config.getLogDirectory());
			  System.err.println(config.getMaxRowsPerQuery());
			  String[] aa = config.getOSGiArguments();
//			  for (int i = 0; i < aa.length; i++) {
//				System.err.println(">"+aa[i]);
//			}
//			  String[] aa = config.getOSGiArguments();
//			  for (int i = 0; i < aa.length; i++) {
//				System.err.println(">"+aa[i]);
//			}
			  try {
				Platform.startup(config);
			} catch (BirtException e) {
				e.printStackTrace();
			}
			  IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			  if (factory==null) {
				System.err.println("SHIT!");
			}
			  System.err.println("After create factory...:: "+(factory!=null));
			  System.err.println("Confit: "+factory);
			  myEngine = factory.createReportEngine( config );
			  			
		}

		  //Open a report design - use design to modify design, retrieve embedded images etc.
		  System.err.println("About to open report design: " + reportDir + reportName + ".rptdesign");
		  File f = null;

		if (directFile != null) {
			  f = new File(directFile);
		} else {
			try {
				f = getFixedReport(reportName, "database", "username",
						"password");
			} catch (XMLParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IReportRunnable design = null;
		FileInputStream fis = null;
		if (f==null) {
			System.err.println("Opening (unaltered) design: "+reportDir + reportName + ".rptdesign");
			design = myEngine.openReportDesign(reportDir + reportName + ".rptdesign");
		} else {
			System.err.println("PATH: "+f.getAbsolutePath());
			fis = new FileInputStream(f);

// TODO close with finally...	
			design = myEngine.openReportDesign(fis);
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		  //Create task to run the report - use the task to execute and run the report,
		  IRunAndRenderTask task = myEngine.createRunAndRenderTask(design);
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
		  
		  // TODO don't forget to delete the altered temp file!
		  
		  return result;
	
  }
  
  public void finalize() {
	  if(myEngine!=null) {
		  myEngine.shutdown();
		  
	  }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
	  System.err.println("User dir: "+System.getProperty("user.dir"));
	  BIRTMap bm = new BIRTMap();

	  bm.reportDir = System.getProperty("user.dir")+"/reports/";
//	  bm.setReportName("ProcessQueryInvoice");
	  bm.setReportName("DataTest");
    bm.setOutputFormat("pdf");
    bm.setParameterName("ClubIdentifier");
    bm.setParameterValue("BBFW63X");
    bm.setParameterName("InvoiceId");
    bm.setParameterValue("1758");

    try {
      Binary b = bm.getReport();
      System.err.println(b.getBase64());
      FileOutputStream fos = new FileOutputStream("c:/noot.pdf");
      b.write(fos);
      fos.flush();
      fos.close();
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
