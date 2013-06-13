package com.dexels.navajo.birt.adapter;

//import org.eclipse.birt.core.exception.BirtException;
//import org.eclipse.birt.core.framework.Platform;
//import org.eclipse.birt.report.engine.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dexels.navajo.birt.BirtUtils;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.server.DispatcherFactory;
import com.dexels.navajo.script.api.UserException;

public class BIRTXmlMap implements Mappable {
	private static final String DEFAULT_OUTPUT_FORMAT = "pdf";
	private Map<String,Object> parameters = new HashMap<String,Object>();
	private String reportDir = "reports/";
	private String viewerReportDir = "reports/";
	private String viewerUrl = "http://distel:8080/birt/run";
	
	private final static Logger logger = LoggerFactory
			.getLogger(BIRTXmlMap.class);
	// private String engineDir = "birt-engine/";

//	private String directFile = null; // "c:/projects/sportlink-serv/navajo-tester/auxilary/reports/tempReport51567.rptdesign";
	// private static final String CHART_HOME =
	// "/home/orion/projects/NavajoBIRT/birt-runtime-2_1_2/ChartEngine";

	public Binary report;
	public Binary tableReport;
	public String reportName;
	public String outputFormat = "html";
	public Object parameterValue;
	public String parameterName;

	private Navajo inNavajo;

	// private IReportEngine myEngine = null;

	public Binary getReport() throws NavajoException {

		try {
			return executeReport(reportName, inNavajo);
		}
		catch (IOException e) {
			throw NavajoFactory.getInstance().createNavajoException(e);
		}
	}

	public Binary getTableReport() throws NavajoException {

		try {
			return executeReport(inNavajo);
		}
		catch (IOException e) {
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
			parameters = new HashMap<String,Object>();
		}
		parameters.put(parameterName, o);
	}
	private Binary executeReport( Navajo input) throws NavajoException, IOException {
		int top = 15;
		int right = 15;
		int bottom = 15;
		int left = 15;
		boolean landscape = true;

		BirtUtils b = new BirtUtils();
		
		File rep = File.createTempFile("generic", ".rptdesign",new File(getViewerReportDir()));
		Property marginProperty = inNavajo.getProperty("/__ReportDefinition/Margin");
		String margin = null;

		if(marginProperty!=null) {
			margin = marginProperty.getValue();
		}
		
		if(margin!=null) {
			logger.debug("Margin: "+margin);
			StringTokenizer st = new StringTokenizer(margin,",");
			top = Integer.parseInt(st.nextToken());
			right = Integer.parseInt(st.nextToken());
			bottom = Integer.parseInt(st.nextToken());
			left = Integer.parseInt(st.nextToken());
		}
		
		Property landscapeProperty  = inNavajo.getProperty("/__ReportDefinition/Orientation");
		if(landscapeProperty!=null) {
			String l = landscapeProperty.getValue();
			if("landscape".equals(l)) {
				landscape = true;
			}
			if("portrait".equals(l)) {
				landscape = false;
			}
		}
		File templateDir = new File(getReportDir()+"template/");
		if(!templateDir.exists()) {
			templateDir.mkdirs();
		}
		File reportTemplateFile = new File(templateDir,"template.rptdesign");
		InputStream reportTemplateStream = new FileInputStream(reportTemplateFile);
		b.createTableReport(reportTemplateStream,rep,input,left,top,right,bottom, landscape);
		return processReport(rep,input);
	}
	
	/*
	 * For 'defined' reports, so the master page is left alone
	 */
	@SuppressWarnings("resource")
	private Binary executeReport(String reportName,Navajo input) throws NavajoException,
			IOException {
		Binary result = new Binary();
		Binary reportDef = null;

		Property outputFormatProperty = inNavajo.getProperty("/__ReportDefinition/OutputFormat");
		if(outputFormatProperty!=null) {
			outputFormat = outputFormatProperty.getValue();
		}
		if(outputFormat==null || "".equals(outputFormat)) {
			outputFormat = DEFAULT_OUTPUT_FORMAT;
		}
		
		Property reportDefinitionProperty = inNavajo.getProperty("/__ReportDefinition/Report");
		if(reportDefinitionProperty!=null) {
			reportDef = (Binary) reportDefinitionProperty.getTypedValue();
		}
		
		BirtUtils b = new BirtUtils();
		File lzfile = b.createDataSource(input);
		InputStream reportIs = null;
		if(reportDef==null) {
			File reportFile = new File(reportDir + reportName + ".rptdesign");
			logger.debug("No definition defined. Using reportname: "+reportFile.getAbsolutePath());
			if (!reportFile.exists()) {
				throw NavajoFactory.getInstance().createNavajoException("Report: " + reportFile + " not found.");
			}
			reportIs = new FileInputStream(reportFile);
		} else {
			logger.debug("Using supplied definition. Size: "+reportDef.getLength());
			reportIs = reportDef.getDataAsStream();		
		}
		
		File fixedFile = b.createFixedReportDefinition(reportIs, lzfile, new File(getViewerReportDir()));
		reportIs.close();
		
		
		result = processReport(fixedFile,inNavajo);
		return result;
	}

	private Binary processReport(File fixedFile, Navajo n) throws MalformedURLException, IOException {
		// todo: 
		// check reportName

		Property outputFormatProperty = n.getProperty("/__ReportDefinition/OutputFormat");
		if(outputFormatProperty!=null) {
			outputFormat = outputFormatProperty.getValue();
		}
		if(outputFormat==null || "".equals(outputFormat)) {
			outputFormat = DEFAULT_OUTPUT_FORMAT;
		}
	
		Binary result;
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(getViewerUrl());
		urlBuffer.append("/run?__report=" + fixedFile.getName());

		for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
			String element = iter.next();
			String value = (String) parameters.get(element);
			urlBuffer.append("&" + element + "=" + value);
		}
		urlBuffer.append("&__format=" + outputFormat);

		logger.debug("Result = " + urlBuffer.toString());

		URL u = new URL(urlBuffer.toString());
		InputStream is = u.openStream();
		result = new Binary(is);
		return result;
	}

	// public void finalize() {
	// if (myEngine != null) {
	// myEngine.shutdown();
	// myEngine.destroy();
	// myEngine = null;
	// }
	// }

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
	public void load(Access access) throws MappableException, UserException {
		// paths
		inNavajo = access.getInDoc();

		InputStream in = null;
		try {
			in = DispatcherFactory.getInstance().getNavajoConfig().getConfig("birt.xml");
		} catch (IOException e) {
			throw new MappableException("No birt.xml configuration file found!");
		}
		if (in == null) {
			throw new MappableException("No birt.xml configuration file found!");
		}

		try {
			Document d = XMLDocumentUtils.createDocument(in, false);
			
			Element a =(Element) XMLutils.findNode(d, "viewerReportDir");
			if(a!=null) {
				viewerReportDir = a.getAttribute("value");
				File f = new File(viewerReportDir);
				if(!f.exists()) {
					throw new MappableException("viewerReportDir:"+viewerReportDir+" not found!");
				}
			} else {
				throw new MappableException("No tag: viewerReportDir found in birt.xml");
				
			}
			Element b = (Element) XMLutils.findNode(d, "viewerUrl");
			if(b!=null) {
				viewerUrl = b.getAttribute("value");
				// check url?
			} else {
				throw new MappableException("No tag: viewerUrl found in birt.xml");
				
			}
			Element c = (Element) XMLutils.findNode(d, "reportDir");
			if(c!=null) {
				reportDir = c.getAttribute("value");
				File f = new File(reportDir);
				if(!f.exists()) {
					throw new MappableException("reportDir:"+reportDir+" not found!");
				}
			} else {
				throw new MappableException("No tag: reportDir found in birt.xml");
			}
			logger.debug("Birt configured succesfully!");

			
		} catch (NavajoException e1) {
			throw new MappableException("Error reading birt.xml configuration file!");
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.error("Error: ", e);
			}
		}

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

	public static void main(String args[]) {

	}

	public String getViewerUrl() {
		return viewerUrl;
	}

	public void setViewerUrl(String birtUrl) {
		this.viewerUrl = birtUrl;
	}

	public String getReportDir() {
		return reportDir;
	}

	public void setReportDir(String birtReportDir) {
		this.reportDir = birtReportDir;
	}

	public String getViewerReportDir() {
		return viewerReportDir;
	}

	public void setViewerReportDir(String viewerReportDir) {
		this.viewerReportDir = viewerReportDir;
	}

}
