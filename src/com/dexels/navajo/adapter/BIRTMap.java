package com.dexels.navajo.adapter;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.*;
import java.util.*;
import java.util.logging.Level;
import java.io.*;
import java.nio.charset.Charset;
 
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
	
//	public String birtDatasourceUrl;
	
	public final Map birtDataSources = new HashMap();
	private static IReportEngine myEngine = null;
 
  public Binary getReport(){
	  
	  try{
		  return  executeReport(reportName, parameters, outputFormat);
	  }catch(Throwable e){
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

  private File getFixedReport(String reportName, Map dataUrl) throws XMLParseException, IOException {
	  File reportFile = new File(reportDir+reportName+ ".rptdesign");
	  Reader fw = (Reader) new InputStreamReader(new FileInputStream(reportFile),"UTF-8");
	  XMLElement xe = new CaseSensitiveXMLElement();
	  xe.parseFromReader(fw);
	  fw.close();
	  XMLElement dataSources =  xe.getElementByTagName("data-sources");
	  if (dataSources==null) {
		System.err.println("No datasources found"); 
		return null;
	  }
	  
	  for (Iterator iter = dataSources.getChildren().iterator(); iter.hasNext();) {
		XMLElement dataSource = (XMLElement) iter.next();
		if(dataSource.getName().equals("oda-data-source")) {
			String dataSourceName = dataSource.getStringAttribute("name");
			if(dataSourceName!=null) {
				String urlValue = (String)dataUrl.get(dataSourceName);
				if(urlValue!=null) {
					 Vector children = dataSource.getChildren();
					  for (int i = 0; i < children.size(); i++) {
						XMLElement current = (XMLElement)children.get(i);
						String name = current.getStringAttribute("name");
						if ("odaURL".equals(name)) {
							current.setContent(urlValue);
						}
					}
				}
			}
			 

		}
	}
//	  XMLElement dataSource = dataSources.getElementByTagName("oda-data-source");
//	  if (dataSource==null) {
//			System.err.println("No datasource found"); 
//			return null;
//	  }
//	  Vector children = dataSource.getChildren();
//	  for (int i = 0; i < children.size(); i++) {
//		XMLElement current = (XMLElement)children.get(i);
//		String name = current.getStringAttribute("name");
//		if ("odaURL".equals(name)) {
//			current.setContent(dataUrl);
//		}
//	}
	  File temp = File.createTempFile("tempReport", ".rptdesign");
	  Writer ffw = new OutputStreamWriter(new FileOutputStream(temp),"UTF-8");
	  ffw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	  ffw.write("<!-- Written by NavajoBIRTAdapter  -->\n");

	  xe.write(ffw);
	  ffw.flush();
	  fw.close();
	  System.err.println("wrote temp file: "+temp.getAbsolutePath());
	  return temp;
  }
  
  private Binary executeReport(String reportName, HashMap reportParams, String outputFormat) throws EngineException, FileNotFoundException {
	  //ByteArrayOutputStream bout = new ByteArrayOutputStream();
	  
//	  IReportEngine engine = null;
	  Binary result = new Binary();
		  //Engine Configuration - set and get temp dir, BIRT home, Servlet context
		  if (myEngine==null) {
			  EngineConfig config = new EngineConfig();
//			  config.getLogger();
			  config.setEngineHome(engineDir);
			  config.setLogConfig(null, Level.SEVERE);
			  try {
				Platform.startup(config);
			} catch (BirtException e) {
				e.printStackTrace();
			}
			  IReportEngineFactory factory = (IReportEngineFactory) Platform
				.createFactoryObject( IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY );
			  if (factory==null) {
				System.err.println("WTF?! No factory.");
			} else {
				System.err.println("Factory created.");

			}
			  myEngine = factory.createReportEngine( config );
			  			
		  }

		  //Open a report design - use design to modify design, retrieve embedded images etc.
		  File f = null;

		if (directFile != null) {
			  f = new File(directFile);
		} else {
			try {
				f = getFixedReport(reportName,birtDataSources);
			} catch (XMLParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * From here, rewrite to new-style:
		 */
		IReportRunnable design = null;
		FileInputStream fis = null;
		if (f==null) {
			design = myEngine.openReportDesign(reportDir + reportName + ".rptdesign");
		} else {
			System.err.println("Opening fixed file: "+f);
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
		  System.err.println("Running for outputFormat: "+outputFormat);
		  OutputStream out = result.getOutputStream();
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
			  options.setOutputStream(out);
			  options.setOutputFormat("html");
			  task.setRenderOption(options);
			  System.err.println("Running task");
				task.run();
				  System.err.println("Finished task");
		  }
		  else {
			  RenderOptionBase options = new RenderOptionBase();
			  options.setOutputFormat("pdf");
			  options.setOutputStream(out);
//			  File ff;
//			try {
//				ff = File.createTempFile("report", "aap.pdf");
//				options.setOutputFileName(ff.getAbsolutePath());
//				 System.err.println("Saving to file: "+ff.getAbsolutePath());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			  task.setRenderOption(options);
			  System.err.println("Render options set.");
			  System.err.println("Running task");
			  task.run();
			  System.err.println("Finished task");
		  }

	
		  if (out!=null) {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		  if (directFile==null && f!=null) {
			f.delete();
			
		}
	return result;
	
  }
  
  public void finalize() {
	  if(myEngine!=null) {
		  myEngine.shutdown();
		  myEngine = null;
	  }
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
	  System.err.println("User dir: "+System.getProperty("user.dir"));
	  BIRTMap bm = new BIRTMap();
	  bm.engineDir = "c:/projecten/NavajoBirt/birt-engine";
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
//    birtDatasourceUrl = inMessage.getMessage("__globals__").getProperty("BIRTKernelDatasourceUrl").getValue();
//    birtDatasourceUrl = inMessage.getMessage("__globals__").getProperty("BIRTClubDatasourceUrl").getValue();

    birtDataSources.put("kernel", inMessage.getMessage("__globals__").getProperty("BIRTKernelDatasourceUrl").getValue());
    birtDataSources.put("club", inMessage.getMessage("__globals__").getProperty("BIRTClubDatasourceUrl").getValue());
    
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

public String getBirtDatasourceUrl(String name) {
	return (String)birtDataSources.get(name);
}
  
}
