package com.dexels.navajo.adapter;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.*;

import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;
import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.mapping.*;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.nanoimpl.XMLParseException;
import com.dexels.navajo.document.types.*;
import java.util.*;
import java.util.logging.Level;
import java.io.*;
import java.nio.charset.Charset;

public class BIRTXmlMap implements Mappable {
	private HashMap parameters = new HashMap();
	private String reportDir = "reports/";
	private String engineDir = "birt-engine/";
	private String directFile = null; // "c:/projects/sportlink-serv/navajo-tester/auxilary/reports/tempReport51567.rptdesign";
	// private static final String CHART_HOME =
	// "/home/orion/projects/NavajoBIRT/birt-runtime-2_1_2/ChartEngine";

	public Binary report;
	public String reportName;
	public String outputFormat = "html";
	public Object parameterValue;
	public String parameterName;

	public String birtDatasourceUrl;
	private Navajo inNavajo;
	private IReportEngine myEngine = null;

	public Binary getReport() throws NavajoException {

		try {
			return executeReport(reportName, parameters, outputFormat, inNavajo);
		} catch (EngineException e) {
			e.printStackTrace();
			throw NavajoFactory.getInstance().createNavajoException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
	}

	public void setReportName(String s) {
		this.reportName = s;
	}

	public void setOutputFormat(String s) {
		this.outputFormat = s;
	}

	public void setParameterName(String s) {
		this.parameterName = s;
	}

	public void setParameterValue(Object o) {
		if (parameters == null) {
			parameters = new HashMap();
		}
		parameters.put(parameterName, o);
	}

	private Binary executeReport(String reportName, HashMap reportParams, String outputFormat, Navajo input) throws EngineException,
			NavajoException, IOException {
		Binary result = new Binary();
		
		HTMLRenderContext renderContext = new HTMLRenderContext();
		String imagePath = "C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\NavajoStandardEdition\\images\\";
		String base = "http://distel:8080/NavajoStandardEdition";
		String imageUrl = base+"/images";


		renderContext.setImageDirectory(imagePath);


		renderContext.setBaseImageURL(imageUrl);




		renderContext.setBaseURL(base);



		renderContext.setSupportedImageFormats( "PNG;GIF;JPG;BMP" );


//	    HashMap< String, HTMLRenderContext > contextMap 
//        = new HashMap< String, HTMLRenderContext >();
//
//	    contextMap.put( 
//         EngineConstants.
//             APPCONTEXT_HTML_RENDER_CONTEXT, 
//             renderContext );
//		
		
		
		// Engine Configuration - set and get temp dir, BIRT home, Servlet
		// context
		// System.setProperty("BIRT_HOME", CHART_HOME);
		// System.err.println("BIRT_HOME set to: "+CHART_HOME);
		if (reportName == null) {
			System.err.println("No reportName supplied");
			System.err.println("Source script: " + inNavajo.getHeader().getHeaderAttribute("sourceScript"));
			reportName = "person/ProcessSearchPersons";
			inNavajo.write(System.err);
			reportName = inNavajo.getHeader().getHeaderAttribute("sourceScript");
		}
		System.err.println("Starting engine...");
		if (myEngine == null) {
			EngineConfig config = new EngineConfig();
			config.setEngineHome(engineDir);
			System.err.println("Engine Dir: " + engineDir);
			config.setLogConfig("c:/birtlog", Level.ALL);
			try {
				Platform.startup(config);
			} catch (BirtException e) {
				e.printStackTrace();
			}
			System.err.println("Platform started...");
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			if (factory == null) {
				System.err.println("WTF?! No factory.");
			} else {
				System.err.println("Factory created.");

			}
			myEngine = factory.createReportEngine(config);
			System.err.println("Engine created.");
		}

		BirtUtils b = new BirtUtils();
		File lzfile = b.createDataSource(input, input.getHeader().getHeaderAttribute("sourceScript"));
		// File lzfile =
		// b.createDataSource(input,input.getHeader().getRPCName());
		// Open a report design - use design to modify design, retrieve embedded
		// images etc.

		File reportFile = new File(reportDir + reportName + ".rptdesign");
		if (!reportFile.exists()) {
			throw NavajoFactory.getInstance().createNavajoException("Report: " + reportFile + " not found.");
		}
		FileInputStream reportIs = new FileInputStream(reportFile);
		File fixedFile = b.createFixedReportDataSource(reportIs, lzfile);
		reportIs.close();

		System.err.println("Created fixed reportfile: " + fixedFile.getAbsolutePath());
		IReportRunnable design = null;
		FileInputStream fis = null;
		fis = new FileInputStream(fixedFile);
		// design = myEngine.openReportDesign(fis);
		// TODO close with finally...
		design = myEngine.openReportDesign(fis);
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Create task to run the report - use the task to execute and run the
		// report,
		IRunAndRenderTask task = myEngine.createRunAndRenderTask(design);
		task.setParameterValues(reportParams);
		System.err.println("Running for outputFormat: " + outputFormat);
		OutputStream out = result.getOutputStream();
		// Set render context to handle url and image locataions
		if (outputFormat.equals("html")) {
			HTMLRenderContext renderContextHTML = new HTMLRenderContext();
			renderContextHTML.setImageDirectory("image");
			HashMap contextMap = new HashMap();
			contextMap.put(EngineConstants.APPCONTEXT_HTML_RENDER_CONTEXT, renderContextHTML);
			task.setAppContext(contextMap);

			// Set rendering options - such as file or stream output,
			// output format, whether it is embeddable, etc
			HTMLRenderOption options = new HTMLRenderOption();
			options.setOutputStream(out);
			options.setOutputFormat(outputFormat);

			task.setRenderOption(options);
			System.err.println("Running task");
			task.run();
			System.err.println("Finished task");
		} else {
			RenderOptionBase options = new RenderOptionBase();
			options.setOutputFormat("pdf");
			options.setOutputStream(out);

			task.setRenderOption(options);
			System.err.println("Render options set.");
			System.err.println("Running task");
			task.run();
			System.err.println("Finished task");
		}
		if (out != null) {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public void finalize() {
		if (myEngine != null) {
			myEngine.shutdown();
			myEngine.destroy();
			myEngine = null;
		}
	}

	/**
	 * A Mappable class is executed by the Navajo Mapping Environment.
	 * 
	 * @param parms
	 *            Parameters
	 * @param inMessage
	 *            Navajo
	 * @param access
	 *            Access
	 * @param config
	 *            NavajoConfig
	 * @throws MappableException
	 * @throws UserException
	 * @todo Implement this com.dexels.navajo.mapping.Mappable method
	 */
	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		// paths
		inNavajo = inMessage;
		// reportDir =
		// inMessage.getMessage("__globals__").getProperty("BIRTReportDir").getValue();
		// engineDir =
		// inMessage.getMessage("__globals__").getProperty("BIRTEngineDir").getValue();
		reportDir = "C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\NavajoStandardEdition\\reports\\";
		engineDir = "C:\\Program Files\\Apache Software Foundation\\Tomcat 5.5\\webapps\\ReportEngine";
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

	public String getBirtDatasourceUrl() {
		return birtDatasourceUrl;
	}

	public static void main(String args[]) {

	}

}
