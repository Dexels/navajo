package com.dexels.navajo.birt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

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
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.parser.DefaultExpressionEvaluator;

public class BirtUtils {

	// for XML tag id's. Start at 10
	private int idCounter = 10;

	

	public Binary createEmptyReport(Navajo n, Binary template) throws IOException,
			NavajoException {
		InputStream templateStream = null;
		if(template==null) {
			templateStream = getClass().getResourceAsStream("blank.rptdesign");
		} else {
			templateStream = template.getDataAsStream();
		}
		File reportFile = File.createTempFile("temp", ".rptdesign");
		createEmptyReport(n,reportFile,templateStream);
		Binary result = new Binary(reportFile,false);
		reportFile.delete();
		return result;
	}
	
	/**
	 * 1. Creates a datasource xml file, based on the Navajo object(s). 2.
	 * Creates a an empty rptdesign file on location 'File'
	 * 
	 * Returns the location of the datasource xml file.
	 * 
	 * @param n
	 * @param destination
	 * @return
	 * @throws IOException
	 * @throws NavajoException
	 */
	public void createEmptyReport(Navajo n, File reportFile, InputStream reportTemplateStream) throws IOException,
			NavajoException {
		File source = createDataSource(n);
		System.err.println("Datasource created.");
		System.err.println("Creating report: "+reportFile.getAbsolutePath());
		createReportFile(source, n, reportFile,"navajo",reportTemplateStream);
	}

	public void setupMasterPage(Document d, int left, int top, int right, int bottom, boolean landscape) {
		Element simpleMaster = (Element) XMLutils.findNode(d, "simple-master-page");
		addProperty(d, simpleMaster, "property", "type", "a4");
		addProperty(d, simpleMaster, "property", "orientation", landscape ? "landscape" : "portrait");
		addProperty(d, simpleMaster, "property", "topMargin", top + "mm");
		addProperty(d, simpleMaster, "property", "leftMargin", left + "mm");
		addProperty(d, simpleMaster, "property", "bottomMargin", bottom + "mm");
		addProperty(d, simpleMaster, "property", "rightMargin", right + "mm");

	}

	public void createEmptyReport(File tmlFile, File reportFolder, String reportName, File reportTemplateFile)
			throws IOException, NavajoException {
		FileInputStream fis = new FileInputStream(tmlFile);
		Navajo n = NavajoFactory.getInstance().createNavajo(fis);
		fis.close();

		File source = createDataSource(n);
		File reportFile = new File(reportFolder, reportName);
		FileInputStream templatInput = new FileInputStream(reportTemplateFile);
		createReportFile(source, n, reportFile, "navajo", templatInput);
	}

	public File createFixedReportDefinition(InputStream rptdesign, File datasource, File tempReportDir) throws NavajoException, IOException {
		Document d = XMLDocumentUtils.createDocument(rptdesign, false);
		Element ods = (Element) XMLutils.findNode(d, "oda-data-source");
		Element cr = (Element) XMLutils.findNodeWithAttributeValue(d, "property", "name", "createdBy");
		cr.setTextContent("Dexels");
		Element e = (Element) XMLutils.findNodeWithAttributeValue(d, "property", "name", "FILELIST");
		if (e == null) {
			addProperty(d, ods, "property", "FILELIST", datasource.getAbsolutePath());
		} else {
			e.setTextContent(datasource.getAbsolutePath());
		}
		e = (Element) XMLutils.findNodeWithAttributeValue(d, "property", "name", "XML_FILE");
		if (e != null) {
			e.setTextContent(datasource.getAbsolutePath());
		}

		// File f = File.createTempFile("fixedReport", ".rptdesign");
		File f = createTempReport(tempReportDir);
		FileWriter fw = new FileWriter(f);
		XMLDocumentUtils.write(d, fw, false);
		fw.flush();
		fw.close();
		return f;

	}

	public File createTempReport(File parentDir) {
		String random = "report" + ((int) (Math.random() * 10000)) + ".rptdesign";
		File ff = new File(parentDir, random);
		if (ff.exists()) {
			return createTempReport(parentDir);
		} else {
			return ff;
		}
	}

	/**
	 * TODO Implement params
	 * 
	 * @param n
	 * @param reportName
	 * @param params
	 * @throws IOException
	 * @throws NavajoException
	 */
	// public void invokeReport(String serviceName, Navajo n, String reportName,
	// Map params, File reportPath) throws NavajoException, IOException {
	// FileInputStream fis = new FileInputStream(new
	// File(reportPath,reportName));
	// File datasource = createDataSource(n,serviceName);
	// File fixedreport = createFixedReportDataSource(fis, datasource);
	//		
	// }
	/**
	 * TODO: Support for multi-service reports TODO: More defensive against
	 * resource leaks
	 * 
	 * @param n
	 * @return
	 * @throws IOException
	 */
	public File createDataSource(Navajo n) throws IOException {

		File sourceFile = File.createTempFile("dat", ".txt", new File(System.getProperty("java.io.tmpdir")));
		File origFile = new File(sourceFile.getAbsolutePath() + ".xml");
		FileWriter origW = new FileWriter(origFile);
		try {
			n.write(origW);
		} catch (NavajoException e) {
			e.printStackTrace();
		} finally {
			if (origW != null) {
				origW.flush();
				origW.close();
			}
		}
		Document t = NavajoLaszloConverter.createLaszloFromNavajo(n, false);
		FileWriter fw = new FileWriter(sourceFile);
		System.err.println("Data source created: " + sourceFile.getAbsolutePath());
		XMLDocumentUtils.write(t, fw, false);
		fw.flush();
		fw.close();
		return sourceFile;
	}

	private void createReportFile(File datasource, Navajo n, File reportFile, String dataSourceName, InputStream reportTemplateStream)
			throws IOException, NavajoException {
		// URL u =
		// BirtUtils.class.getClassLoader().getResource("blank.rptdesign");
		Document d = null;
		try {
			d = XMLDocumentUtils.createDocument(reportTemplateStream, false);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		reportTemplateStream.close();

		Element dsrc = (Element) XMLutils.findNode(d, "oda-data-source");
		dsrc.setAttribute("name", dataSourceName);
		Element datasourcePath = (Element) XMLutils.findNodeWithAttributeValue(d, "property", "name", "FILELIST");
		if (datasourcePath == null) {
			addProperty(d, dsrc, "property", "FILELIST", datasource.getAbsolutePath());
		} else {
			datasourcePath.setTextContent(datasource.getAbsolutePath());
		}
		Element datasourceName = (Element) XMLutils.findNodeWithAttributeValue(d, "text-property", "name", "displayName");
		if (datasourceName == null) {
			addProperty(d, dsrc, "text-property", "name", dataSourceName);
		} else {
			datasourceName.setTextContent(dataSourceName);
		}

		parseArrayMessages(d, n.getAllMessages(), datasource.getAbsolutePath(), dataSourceName);

		// File reportFile = new File(reportFolder,reportName);
		File parentFile = reportFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		FileWriter fw = new FileWriter(reportFile);
		XMLDocumentUtils.write(d, fw, false);
		fw.flush();
		fw.close();

	}

	/**
	 * TODO: Support nested arrays
	 * 
	 * @param d
	 * @param messageList
	 * @param absolutePath
	 * @param datasourceName
	 */

	private void parseArrayMessages(Document d, ArrayList messageList, String absolutePath, String datasourceName) {
		for (int i = 0; i < messageList.size(); i++) {
			Message current = (Message) messageList.get(i);
			String fullPath = current.getFullMessageName();
			if (fullPath.startsWith("/")) {
				fullPath = fullPath.substring(1);
			}
			if (current.getType().equals(Message.MSG_TYPE_ARRAY)) {
				createDataSet(d, fullPath, datasourceName, current, absolutePath);
			} else {
				ArrayList aa = current.getAllMessages();
				ArrayList pp = current.getAllProperties();
				if (pp.size() > 0) {
					createDataSet(d, fullPath, datasourceName, current, absolutePath);
				}
				parseArrayMessages(d, aa, absolutePath, datasourceName);
			}
		}
	}

	private Element createDataSet(Document d, String dataSetName, String datasource, Message sourceObject, String xmlFilePath) {
		Element parent = (Element) XMLutils.findNode(d, "data-sets");
		if (parent == null) {
			Node report = XMLutils.findNode(d, "report");
			parent = (Element) report.appendChild(d.createElement("data-sets"));
		}
		Element source = d.createElement("oda-data-set");
		parent.appendChild(source);
		source.setAttribute("extensionID", "org.eclipse.birt.report.data.oda.xml.dataSet");
		source.setAttribute("name", dataSetName);
		source.setAttribute("id", "" + idCounter++);
		addProperty(d, source, "property", "dataSource", datasource);
		addProperty(d, source, "property", "MAX_ROW", "-1");
		addProperty(d, source, "property", "XML_FILE", xmlFilePath);
		parent.appendChild(source);
		createResultSet(d, source, sourceObject, datasource);
		return source;

	}

	private void createResultSet(Document d, Element odaDataSetTag, Message sourceObject, String service) {
		Message first = null;
		if (sourceObject == null) {
			return;
		}
		if (sourceObject.getType().equals(Message.MSG_TYPE_ARRAY)) {
			first = sourceObject.getMessage(0);
			if (first == null) {
				return;
			}
		} else {
			first = sourceObject;
		}
		ArrayList props = first.getAllProperties();
		Element listprop = addProperty(d, odaDataSetTag, "list-property", "resultSet", null);
		for (int i = 0; i < props.size(); i++) {
			Property current = (Property) props.get(i);
			Element s = d.createElement("structure");
			listprop.appendChild(s);
			addProperty(d, s, "property", "position", "" + (i + 1));
			addProperty(d, s, "property", "name", current.getName());
			addProperty(d, s, "property", "nativeName", current.getName());
			// TODO Check which types work well.
			// addProperty(d, s, "property", "dataType", current.getType());

			addProperty(d, s, "property", "dataType", getPropertyType(current, false));
			// if(current.getDescription()!=null) {
			// s = d.createElement("structure");
			// listprop.appendChild(s);
			// addProperty(d, s, "property", "position", ""+(i+1));
			// addProperty(d, s, "property", "name",
			// current.getName()+"_Description");
			// addProperty(d, s, "property", "nativeName",
			// current.getName()+"_Description");
			// // TODO Check which types work well.
			// addProperty(d, s, "property", "dataType", "String");
			//				
			// }

			if (Property.BINARY_PROPERTY.equals(current.getType())) {
				System.err.println("Binary found.");
				Element n = (Element) XMLutils.findNode(odaDataSetTag, "computedColumns");
				if (n == null) {
					n = addProperty(d, odaDataSetTag, "list-property", "computedColumns", null);
				}
				Element struc = addProperty(d, n, "structure", null, null);
				// org.apache.commons.codec.binary.Base64.decodeBase64("aap".getBytes());

				addProperty(d, struc, "property", "name", current.getName() + "_Data");
				addProperty(d, struc, "expression", "expression", "importPackage(Packages.org.dexels.utils); Base64.decode(dataSetRow[\""
						+ current.getName() + "\"])");
				addProperty(d, struc, "property", "dataType", "any");
			}
		}
		addProperty(d, odaDataSetTag, "property", "queryText", createArrayMessageQueryText(props, service, sourceObject));
	}

	private String createArrayMessageQueryText(ArrayList props, String serviceName, Message arrayMessage) {
		boolean isArray = arrayMessage.getType().equals(Message.MSG_TYPE_ARRAY);
		String fixed = serviceName.replaceAll("/", "_");
		StringBuffer sb = new StringBuffer();

		sb.append("table0#-TNAME-#table0#");
		sb.append(":");
		if (isArray) {
			sb.append("#[/" + fixed + "/tml/" + getLaszloMessagePath(arrayMessage) + "/row]#");
		} else {
			sb.append("#[/" + fixed + "/tml/" + getLaszloMessagePath(arrayMessage) + "]#");
		}
		sb.append(":");
		sb.append("#");
		// for each prop:
		for (int i = 0; i < props.size(); i++) {
			Property current = (Property) props.get(i);
			if (isArray) {
				sb.append("{" + current.getName() + ";" + getPropertyType(current, true) + ";/@" + current.getName() + "}");
			} else {
				sb.append("{" + current.getName() + ";" + getPropertyType(current, true) + ";/p_" + current.getName() + "/@value}");
			}
			if (i < props.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String getPropertyType(Property p, boolean queryString) {
		if (p.getType().equals(Property.LONG_PROPERTY)) {
			return queryString ? "BigDecimal" : "decimal";
		}
		if (p.getType().equals(Property.INTEGER_PROPERTY)) {
			return queryString ? "Int" : "integer";
		}
		if (p.getType().equals(Property.FLOAT_PROPERTY)) {
			return queryString ? "Double" : "float";
		}

		if (p.getType().equals(Property.CLOCKTIME_PROPERTY)) {
			return queryString ? "Timestamp" : "date-time";
		}
		if (p.getType().equals(Property.DATE_PROPERTY)) {
			return queryString ? "String" : "String";
			// return queryString?"Date":"date-time";
		}
		if (p.getType().equals(Property.MONEY_PROPERTY)) {
			return queryString ? "Double" : "float";
		}
		if (p.getType().equals(Property.PERCENTAGE_PROPERTY)) {
			return queryString ? "Double" : "float";
		}
		if (p.getType().equals(Property.PASSWORD_PROPERTY)) {
			return queryString ? "String" : "String";
		}

		return queryString ? "String" : "String";

	}

	private String getLaszloMessagePath(Message m) {
		String parentPath = "";
		if (m.getParentMessage() != null) {
			parentPath = getLaszloMessagePath(m.getParentMessage()) + "/";
			if (parentPath.startsWith("/")) {
				parentPath = parentPath.substring(1);
			}
		}
		if (m.getType().equals(Message.MSG_TYPE_ARRAY)) {
			return parentPath + "a_" + m.getName();
		} else {
			if (m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
				return getLaszloMessagePath(m.getParentMessage()) + "@" + m.getIndex();
			} else {
				if (m.getParentMessage() == null) {
					return "";
				} else {
					return parentPath + "m_" + m.getName();

				}
			}
		}
	}

	private Element addProperty(Document d, Element parent, String tag, String name, String content) {
		Element n = d.createElement(tag);
		if (content != null) {
			n.setTextContent(content);
		}
		if (name != null) {
			n.setAttribute("name", name);
		}
		Element e = (Element) XMLutils.findNodeWithAttributeValue(parent, tag, name, content);
		if (e != null) {
			parent.removeChild(e);
		}
		parent.appendChild(n);

		return n;
	}

	// public void buildReportFolder(File aux) {
	// File tmlFolder = new File(aux,"tml");
	// File reportFolder = new File(aux,"report");
	// buildReport(tmlFolder,reportFolder,"");
	//		
	// }

	// private void buildReport(File currentTml, File currentReport,String path,
	// File reportTemplateFile) {
	// File[] list = currentTml.listFiles();
	// for (int i = 0; i < list.length; i++) {
	// System.err.println("Current file: "+list[i].getAbsolutePath());
	// if(list[i].isDirectory()) {
	// File newReportFolder = new File(currentReport,list[i].getName());
	// if(!newReportFolder.exists()) {
	// newReportFolder.mkdirs();
	// }
	// buildReport(list[i],
	// newReportFolder,path+list[i].getName(),reportTemplateFile);
	// } else {
	// String name = list[i].getName();
	// if(name.endsWith(".tml")) {
	// try {
	// createEmptyReport(list[i],currentReport,
	// name+".rptdesign",null,reportTemplateFile);
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (NavajoException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// }
	// }
	// }

	public void createTableReport(InputStream reportTemplateStream, File reportFile, Navajo n, int left, int top, int right, int bottom,
			boolean landscape) throws IOException, NavajoException {
		// public void setupMasterPage(Document d, int left, int top, int right,
		// int bottom, boolean landscape) {
		System.err.println("Margin: " + left + " top: " + top + " right: " + right + " bottom: " + bottom);
		String serviceName = n.getHeader().getHeaderAttribute("sourceScript");
		ArrayList pNames = new ArrayList();
		ArrayList pSizes = new ArrayList();
		ArrayList pTitles = new ArrayList();
		Property propNames = n.getProperty("__ReportDefinition/PropertyNames");
		Property propSizes = n.getProperty("__ReportDefinition/PropertySizes");
		Property propTitles = n.getProperty("__ReportDefinition/PropertyTitles");
		Property messagePathProp = n.getProperty("__ReportDefinition/MessagePath");
		// Message m = n.getMessage("__ReportDefinition");
		// m.write(System.err);
		String messagePath = null;
		if (messagePathProp != null) {
			messagePath = messagePathProp.getValue();
		}

		String propertyNames = propNames.getValue();
		String propertySizes = propSizes.getValue();
		String propertyTitles = null;
		if (propTitles != null) {
			propertyTitles = propTitles.getValue();
		}
		StringTokenizer st = new StringTokenizer(propertyNames, ",");
		StringTokenizer st2 = new StringTokenizer(propertySizes, ",");
		while (st.hasMoreElements()) {
			pNames.add(st.nextToken());
		}
		while (st2.hasMoreElements()) {
			pSizes.add(Integer.parseInt(st2.nextToken()));
		}
		if (propertyTitles != null) {
			StringTokenizer st3 = new StringTokenizer(propertyTitles, ",");
			while (st3.hasMoreElements()) {
				pTitles.add(st3.nextToken());
			}

		}
		System.err.println("SERVICE NAME: " + serviceName);
		createTableReport(n, reportTemplateStream, reportFile, serviceName, messagePath, pNames.toArray(), pSizes.toArray(), pTitles
				.toArray(), left, top, right, bottom, landscape);
	}

	private void createTableReport(Navajo n, InputStream reportTemplateStream, File reportFile, String serviceName, String messagePath,
			Object[] propertyNames, Object[] propertyWidths, Object[] propertyTitles, int left, int top, int right, int bottom,
			boolean landscape) throws IOException, NavajoException {
		createEmptyReport(n, reportFile, reportTemplateStream);
		FileInputStream fis = new FileInputStream(reportFile);

		if (messagePath == null) {
			messagePath = locateArrayMessage(n);
		}

		Document d = null;
		try {
			d = XMLDocumentUtils.createDocument(fis, false);
		} catch (NavajoException e1) {
			e1.printStackTrace();
		}
		fis.close();

		setupMasterPage(d, left, top, right, bottom, landscape);
		Element report = (Element) XMLutils.findNode(d, "report");
		Element body = d.createElement("body");
		Element table = d.createElement("table");
		report.appendChild(body);
		body.appendChild(table);
		addProperty(d, table, "property", "style", "MessageTable");

		table.setAttribute("id", "" + idCounter++);
		addProperty(d, table, "property", "width", "100%");
		addProperty(d, table, "property", "dataSet", messagePath);

		Element boundProps = d.createElement("list-property");
		boundProps.setAttribute("name", "boundDataColumns");
		table.appendChild(boundProps);
		for (int i = 0; i < propertyNames.length; i++) {
			Element struc = d.createElement("structure");
			boundProps.appendChild(struc);
			addProperty(d, struc, "property", "name", "" + propertyNames[i]);
			addProperty(d, struc, "expression", "expression", "dataSetRow[\"" + propertyNames[i] + "\"]");
			addProperty(d, struc, "property", "dataType", "string");
		}
		for (int i = 0; i < propertyWidths.length; i++) {
			Element column = d.createElement("column");
			column.setAttribute("id", "" + idCounter++);
			table.appendChild(column);
			addProperty(d, column, "property", "width", "" + propertyWidths[i] + "px");
		}
		createTablePart(propertyNames, propertyTitles, d, table, true);
		createTablePart(propertyNames, propertyTitles, d, table, false);

		FileWriter fw = new FileWriter(reportFile);

		XMLDocumentUtils.write(d, fw, false);
		fw.flush();
		fw.close();

	}

	private String locateArrayMessage(Navajo n) throws NavajoException {
		ArrayList<Message> mm = n.getAllMessages();
		for (Iterator iter = mm.iterator(); iter.hasNext();) {
			Message m = (Message) iter.next();
			if (Message.MSG_TYPE_ARRAY.equals(m.getType())) {
				return m.getFullMessageName();
			}
		}
		// two loops, for pre order:
		for (Iterator iter = mm.iterator(); iter.hasNext();) {
			Message m = (Message) iter.next();
			String res = locateArrayMessage(m);
			if (res != null) {
				return res;
			}
		}

		return null;
	}

	private String locateArrayMessage(Message in) {
		ArrayList<Message> mm = in.getAllMessages();
		for (Iterator iter = mm.iterator(); iter.hasNext();) {
			
			Message m = (Message) iter.next();
			if (Message.MSG_TYPE_ARRAY.equals(m.getType())) {
				return m.getFullMessageName();
			}

		}
		// two loops, for breath first order:
		for (Iterator iter = mm.iterator(); iter.hasNext();) {
			Message m = (Message) iter.next();
			String res = locateArrayMessage(m);
			if (res != null) {
				return res;
			}
		}
		return null;

	}

	private void createTablePart(Object[] propertyNames, Object[] propertyTitles, Document d, Element table, boolean isHeader) {
		String tagname = isHeader ? "header" : "detail";
		Element header = d.createElement(tagname);
		table.appendChild(header);
		Element row = d.createElement("row");
		row.setAttribute("id", "" + idCounter++);
		header.appendChild(row);

		for (int i = 0; i < propertyNames.length; i++) {
			Element cell = d.createElement("cell");
			cell.setAttribute("id", "" + idCounter++);
			row.appendChild(cell);
			Element label = d.createElement(isHeader ? "label" : "data");
			if (isHeader) {
				addProperty(d, label, "property", "style", "TableHeader");
			} else {
				addProperty(d, label, "property", "style", "TableBody");
			}

			label.setAttribute("id", "" + idCounter++);
			cell.appendChild(label);
			String title = null;
			if (i < propertyTitles.length) {
				title = (String) propertyTitles[i];
			} else {
				title = (String) propertyNames[i];
			}
			addProperty(d, label, isHeader ? "text-property" : "property", isHeader ? "text" : "resultSetColumn", isHeader ? title
					: (String) propertyNames[i]);
			// add width
		}
	}

}
