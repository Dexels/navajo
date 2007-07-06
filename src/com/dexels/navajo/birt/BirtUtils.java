package com.dexels.navajo.birt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.birt.report.model.api.util.DocumentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.NavajoLaszloConverter;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;

public class BirtUtils {
	
	// for XML tag id's. Start at 10
	private int idCounter = 10;
	
	/**
	 * 1. Creates a datasource xml file, based on the Navajo object(s).
	 * 2. Creates a an empty rptdesign file on location 'File'
	 * 
	 * Returns the location of the datasource xml file.
	 * @param n
	 * @param destination
	 * @return
	 * @throws IOException 
	 * @throws NavajoException 
	 */
	public void createEmptyReport(Navajo n, File reportFolder, String reportName, String serviceName ) throws IOException, NavajoException {
		File source = createDataSource(n,n.getHeader().getRPCName());
		createReportFile(source, n, reportName, reportFolder,serviceName);
	}
	
	public void createEmptyReport(File tmlFile, File reportFolder, String reportName, String serviceName ) throws IOException, NavajoException {
		FileInputStream fis = new FileInputStream(tmlFile);
		Navajo n = NavajoFactory.getInstance().createNavajo(fis);
		fis.close();
		if(serviceName==null) {
			serviceName = n.getHeader().getRPCName();
		}
		File source = createDataSource(n,serviceName);
		createReportFile(source, n, reportName, reportFolder,serviceName);
	}
		
	public File createFixedReportDataSource(InputStream rptdesign, File datasource) throws NavajoException, IOException {
		Document d = XMLDocumentUtils.createDocument(rptdesign, false);
		Element e = (Element)XMLutils.findNodeWithAttributeValue(d, "property", "name", "FILELIST");
		e.setTextContent(datasource.toURI().toURL().toString());
//		e = (Element)XMLutils.findNodeWithAttributeValue(d, "property", "name", "XML_FILE");
//		e.setTextContent(datasource.getAbsolutePath());

		File f = File.createTempFile("fixedReport", ".rptdesign");
		FileWriter fw = new FileWriter(f);
		XMLDocumentUtils.write(d, fw, false);
		fw.flush();
		fw.close();
		return f;
		
	}
	
	/**
	 * TODO Implement params
	 * @param n
	 * @param reportName
	 * @param params
	 * @throws IOException 
	 * @throws NavajoException 
	 */
//	public void invokeReport(String serviceName, Navajo n, String reportName, Map params, File reportPath) throws NavajoException, IOException {
//		FileInputStream fis = new FileInputStream(new File(reportPath,reportName));
//		File datasource = createDataSource(n,serviceName);
//		File fixedreport = createFixedReportDataSource(fis, datasource);
//		
//	}
	
	/**
	 * TODO: Support for multi-service reports
	 * TODO: More defensive against resource leaks
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public File createDataSource(Navajo n, String serviceName) throws IOException {
		File sourceFile = File.createTempFile("ali", "baba");
		Document t = NavajoLaszloConverter.createLaszloFromNavajo(n,serviceName);
		FileWriter fw = new FileWriter(sourceFile);
		System.err.println("Data source created: "+sourceFile.getAbsolutePath());
		XMLDocumentUtils.write(t, fw, false);
		fw.flush();
		fw.close();
		return sourceFile;
	}

	private void createReportFile(File datasource, Navajo n, String reportName, File reportFolder,String serviceName) throws IOException, NavajoException {
		URL u = BirtUtils.class.getResource("blank.rptdesign");
		InputStream is = u.openStream();
		Document d = null;
		try {
			d = XMLDocumentUtils.createDocument(is, false);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		is.close();

		Element dsrc = (Element)XMLutils.findNode(d, "oda-data-source");
		dsrc.setAttribute("name", serviceName);
		Element datasourcePath = (Element)XMLutils.findNodeWithAttributeValue(d, "property","name","FILELIST");
		datasourcePath.setTextContent(datasource.getAbsolutePath());
		Element datasourceName = (Element)XMLutils.findNodeWithAttributeValue(d, "text-property","name","displayName");
		datasourceName.setTextContent(serviceName);

		parseArrayMessages(d,n.getAllMessages(),datasource.getAbsolutePath(),serviceName);
		
		File reportFile = new File(reportFolder,reportName);
		FileWriter fw = new FileWriter(reportFile);
		XMLDocumentUtils.write(d, fw, false);
		fw.flush();
		fw.close();

	}
	/**
	 * TODO: Support nested arrays
	 * @param d
	 * @param messageList
	 * @param absolutePath
	 * @param datasourceName
	 */
	
	private void parseArrayMessages(Document d, ArrayList messageList, String absolutePath, String datasourceName) {
		for (int i = 0; i < messageList.size(); i++) {
			Message current = (Message)messageList.get(i);
			String fullPath = current.getFullMessageName();
			if(fullPath.startsWith("/")) {
				fullPath = fullPath.substring(1);
			}
			if(current.getType().equals(Message.MSG_TYPE_ARRAY)) {
				createDataSet(d,fullPath,datasourceName,current,absolutePath);
			} else {
				ArrayList aa = current.getAllMessages();
				ArrayList pp = current.getAllProperties();
				if(pp.size()>0) {
					createDataSet(d,fullPath,datasourceName,current,absolutePath);
				}
				parseArrayMessages(d, aa, absolutePath, datasourceName);
			}
		}
	}

	private Element createDataSet(Document d, String dataSetName, String datasource, Message sourceObject, String xmlFilePath) {
			Element parent = (Element)XMLutils.findNode(d, "data-sets");
			Element source = d.createElement("oda-data-set");
			parent.appendChild(source);
			source.setAttribute("extensionID", "org.eclipse.birt.report.data.oda.xml.dataSet");
			source.setAttribute("name",dataSetName);
			source.setAttribute("id",""+idCounter++);
			addProperty(d, source, "property", "dataSource", datasource);
			addProperty(d, source, "property", "MAX_ROW", "-1");
			addProperty(d, source, "property", "XML_FILE", xmlFilePath);
			parent.appendChild(source);
			createResultSet(d,source,sourceObject,datasource);
			return source;			
		
	}

	private void createResultSet(Document d, Element odaDataSetTag, Message sourceObject,String service) {
		Message first = null;
		if(sourceObject==null) {
			return;
		}
		if(sourceObject.getType().equals(Message.MSG_TYPE_ARRAY)) {
			first = sourceObject.getMessage(0);
			if(first==null) {
				return;
			}
		} else {
			first = sourceObject;
		}
		ArrayList props = first.getAllProperties();
		Element listprop = addProperty(d, odaDataSetTag, "list-property", "resultSet", null);
		for (int i = 0; i < props.size(); i++) {
			Property current = (Property)props.get(i);
			Element s = d.createElement("structure");
			listprop.appendChild(s);
			addProperty(d, s, "property", "position", ""+(i+1));
			addProperty(d, s, "property", "name", current.getName());
			addProperty(d, s, "property", "nativeName", current.getName());
			// TODO Check which types work well.
//			addProperty(d, s, "property", "dataType", current.getType());
			
			addProperty(d, s, "property", "dataType", getPropertyType(current, false));
			if(Property.BINARY_PROPERTY.equals(current.getType())) {
				Element n = (Element)XMLutils.findNode(odaDataSetTag, "computedColumns");
				if(n==null) {
					n = addProperty(d, odaDataSetTag, "list-property", "name", "computedColumns");
				}
				Element struc = addProperty(d, n, "structure", null, null);
				addProperty(d, struc, "property", "name", current.getName()+"_Data");
				addProperty(d, struc, "expression", "expression", "importPackage(Packages.org.dexels.utils); Base64.decode(dataSetRow[\""+current.getName()+"\"])");
				addProperty(d, struc, "property", "dataType", "any");
			}
		}
		addProperty(d, odaDataSetTag, "property", "queryText", createArrayMessageQueryText(props,service,sourceObject));
	}

	private String createArrayMessageQueryText(ArrayList props,String serviceName,Message arrayMessage) {
		boolean isArray = arrayMessage.getType().equals(Message.MSG_TYPE_ARRAY);
		String fixed = serviceName.replaceAll("/", "_");
		StringBuffer sb = new StringBuffer();
		
		sb.append("table0#-TNAME-#table0#");
		sb.append(":");
		if (isArray) {
			sb.append("#[/"+fixed+"/tml/"+getLaszloMessagePath(arrayMessage)+"/row]#");
		} else {
			sb.append("#[/"+fixed+"/tml/"+getLaszloMessagePath(arrayMessage)+"]#");
		}
		sb.append(":");
		sb.append("#");
		// for each prop:
		for (int i = 0; i < props.size(); i++) {
			Property current = (Property)props.get(i);
			if(isArray) {
				sb.append("{"+current.getName()+";"+getPropertyType(current, true)+";/@"+current.getName()+"}");
			} else {
				sb.append("{"+current.getName()+";"+getPropertyType(current, true)+";/p_"+current.getName()+"/@value}");
			}
			if(i<props.size()-1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String getPropertyType(Property p, boolean queryString) {
		if(p.getType().equals(Property.LONG_PROPERTY)) {
			return queryString?"BigDecimal":"decimal";
		}
		if(p.getType().equals(Property.INTEGER_PROPERTY)) {
			return queryString?"Int":"integer";
		}
		if(p.getType().equals(Property.FLOAT_PROPERTY)) {
			return queryString?"Double":"float";
		}

		if(p.getType().equals(Property.CLOCKTIME_PROPERTY)) {
			return queryString?"Timestamp":"date-time";
		}		
		if(p.getType().equals(Property.DATE_PROPERTY)) {
			return queryString?"Date":"date-time";
		}
		if(p.getType().equals(Property.MONEY_PROPERTY)) {
			return queryString?"Double":"float";
		}
		if(p.getType().equals(Property.PERCENTAGE_PROPERTY)) {
			return queryString?"Double":"float";
		}		
		if(p.getType().equals(Property.PASSWORD_PROPERTY)) {
			return queryString?"String":"String";
		}		
		
		return queryString?"String":"String";

	}
	
	private String getLaszloMessagePath(Message m) {
		String parentPath = "";
		if(m.getParentMessage()!=null) {
			parentPath = getLaszloMessagePath(m.getParentMessage())+"/";
			if(parentPath.startsWith("/")) {
				parentPath = parentPath.substring(1);
			}
		}
		if(m.getType().equals(Message.MSG_TYPE_ARRAY)) {
			return parentPath+"a_"+m.getName();
		} else {
			if(m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
				return getLaszloMessagePath( m.getParentMessage())+"@"+m.getIndex() ;
			} else {
				if(m.getParentMessage()==null) {
					return "";
				} else {
					return parentPath+"m_"+m.getName();
					
				}
			}
		}
	}
	
	private Element addProperty(Document d, Element parent, String tag, String name, String content) {
		Element n = d.createElement(tag);
		if(content!=null) {
			n.setTextContent(content);
		}
		n.setAttribute("name",name);
		parent.appendChild(n);
		
		return n;
	}

	public void buildReportFolder(File aux) {
		File tmlFolder = new File(aux,"tml");
		File reportFolder = new File(aux,"report");
		buildReport(tmlFolder,reportFolder,"");
		
	}

	private void buildReport(File currentTml, File currentReport,String path) {
		File[] list = currentTml.listFiles();
		for (int i = 0; i < list.length; i++) {
			System.err.println("Current file: "+list[i].getAbsolutePath());
			if(list[i].isDirectory()) {
				File newReportFolder = new File(currentReport,list[i].getName());
				if(!newReportFolder.exists()) {
					newReportFolder.mkdirs();
				}
				buildReport(list[i], newReportFolder,path+list[i].getName());
			} else {
				String name = list[i].getName();
				if(name.endsWith(".tml")) {
					try {
						createEmptyReport(list[i],currentReport, name+".rptdesign",null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NavajoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}
	}
}
