package com.dexels.navajo.document;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.types.ClockTime;

public class NavajoLaszloConverter {
	
	private final static Logger logger = LoggerFactory
			.getLogger(NavajoLaszloConverter.class);
	
	public static Navajo createNavajoFromLaszlo(BufferedInputStream is) {
		Navajo n = null;
		try {
			Document doc = XMLDocumentUtils.createDocument(is, false);

			 Node root = doc.getFirstChild();
			 NodeList nl = root.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node nn = nl.item(i);
				if(nn==null) {
					logger.error("WTF?!");
				}
				if(nn instanceof Text) {
//					Text t = (Text)nn;
				}
				
				if(nn instanceof Element) {
					Element rootElement = (Element)nn;
					if("tml".equals(rootElement.getNodeName())) {
						return convertNodeToNavajo(rootElement);
//					} else {
//						return convertChildrenToNavajo(rootElement);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return n;
	}


	private static Navajo convertNodeToNavajo(Element tml) {
		Navajo n;
		n = NavajoFactory.getInstance().createNavajo();
			String rpc_name = tml.getAttribute("rpc_name");
			rpc_name = rpc_name.replaceAll("_", "/");
			String rpc_usr = tml.getAttribute("rpc_usr");
			String rpc_pwd = tml.getAttribute("rpc_pwd");
 
			Header h = NavajoFactory.getInstance().createHeader(n, rpc_name, rpc_usr, rpc_pwd, -1);
			n.addHeader(h);
			NodeList children = tml.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				Node noot = children.item(i);
				createMessageFromLaszlo(noot, n, null);
			}
		return n;
	}

	public static Document createLaszloFromNavajo(Navajo in) {
		return createLaszloFromNavajo(in, "navajoDataSource");
	}

	public static void writeBirtXml(Navajo n, Writer w) {
		Document d = createLaszloFromNavajo(n);
		XMLDocumentUtils.write(d, w, false);
	}
	/**
	 * Laszlo == BIRT style, by the way
	 * @param m
	 * @param w
	 */
	public static void writeBirtXml(Message m, Writer w) {
		Document d = createLaszloFromNavajo(m, false);
		XMLDocumentUtils.write(d, w, false);

	}

	
	public static Document createLaszloFromNavajo(Navajo in, String serviceName) {
		return createLaszloFromNavajo(in, false, serviceName);
	}
	public static Document createLaszloFromNavajo(Navajo in, boolean includeSelections) {
		return createLaszloFromNavajo(in, includeSelections, "navajoDataSource");
	}
	public static Document createLaszloFromNavajo(Navajo in, boolean includeSelections,  String serviceName) {
		Document doc = XMLDocumentUtils.createDocument();
		try {
			in.write(System.err);
			String nodeName = serviceName.replaceAll("/", "_");
			Element root = doc.createElement(nodeName);
			doc.appendChild(root);
			Element tml = doc.createElement("tml");
			if ( in.getHeader() != null ) {
				tml.setAttribute("rpc_usr", in.getHeader().getRPCUser());
				tml.setAttribute("rpc_pwd", in.getHeader().getRPCPassword());
				tml.setAttribute("rpc_name", serviceName);
			}
			root.appendChild(tml);
			ArrayList<Message> l = in.getAllMessages();
			for (int i = 0; i < l.size(); i++) {
				appendMessage(l.get(i), tml, doc, includeSelections);
			}
			// System.err.println("Created doc: " +
			// XMLDocumentUtils.toString(doc));
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return doc;
	}

	public static Document createLaszloFromNavajo(Message in, boolean includeSelections) {
		Document doc = XMLDocumentUtils.createDocument();
		Element root = doc.createElement("navajoDataSource");
		doc.appendChild(root);
		Element tml = doc.createElement("tml");
		root.appendChild(tml);

		try {
			ArrayList<Message> l = in.getAllMessages();
			for (int i = 0; i < l.size(); i++) {
				appendMessage(l.get(i), tml, doc, includeSelections);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
		return doc;
	}

	
	private static void appendMessage(Message m, Element e, Document d, boolean includeSelections) {
		try {
			if (m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
				Element row = d.createElement("row");
				ArrayList<Property> allProp = m.getAllProperties();
				for (int j = 0; j < allProp.size(); j++) {
					Property cp = allProp.get(j);
					if (cp.getType().equals(Property.SELECTION_PROPERTY)) {

						if (includeSelections) {
							Element prop = d.createElement(cp.getName());
							ArrayList<Selection> sel = cp.getAllSelections();
							for (int k = 0; k < sel.size(); k++) {
								Selection s = sel.get(k);
								Element option = d.createElement("option");
								option.setAttribute("value", s.getValue());
								option.setAttribute("name", s.getName());
								option.setAttribute("selected", "" + s.isSelected());
								prop.appendChild(option);
							}
							Selection s = cp.getSelected();
							if (s != null) {
								prop.setAttribute("value", s.getName());
							}
							row.appendChild(prop);
						} else {
							Selection s = cp.getSelected();
							if(s!=null && s.getName()!=null) {
							row.setAttribute(cp.getName(), s.getName());
							} else {
								row.setAttribute(cp.getName(), "-");
								
							}
						}
						
					}

					// DateFormat Hack:
					if (cp.getType().equals(Property.DATE_PROPERTY)) {
						Date dd = (Date) cp.getTypedValue();
						if(dd!=null) {
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
							row.setAttribute(cp.getName(), df.format(dd));
						} else {
							row.setAttribute(cp.getName(), "#");
						}
					} else {
						if (!cp.getType().equals(Property.SELECTION_PROPERTY)) {
							String result = cp.getValue();
							if(result != null){
							  result = result.replaceAll("\n", "<br/>");
							}
							row.setAttribute(cp.getName(), result);
						}
					}

					if (includeSelections) {
						row.setAttribute("type_" + cp.getName(), cp.getType());
					} else {
						// if selection properties are not supported, treat
						// selections as strings
						if (cp.getType().equals(Property.SELECTION_PROPERTY)) {
							row.setAttribute("type_" + cp.getName(), Property.STRING_PROPERTY);
						} else {
							row.setAttribute("type_" + cp.getName(), cp.getType());
						}
					}
				}
				e.appendChild(row);
			}
			if (m.getType().equals(Message.MSG_TYPE_ARRAY)) {
				Element array = d.createElement("a_" + m.getName());
				e.appendChild(array);
				for (int i = 0; i < m.getArraySize(); i++) {
					Message row = m.getMessage(i);
					appendMessage(row, array, d, includeSelections);
				}
			}
			if (m.getType().equals(Message.MSG_TYPE_SIMPLE) || m.getType().equals(Message.MSG_TYPE)) {
				Element mes = d.createElement("m_" + m.getName());
				ArrayList<Message> allMes = m.getAllMessages();
				for (int k = 0; k < allMes.size(); k++) {
					Message cm = allMes.get(k);
					appendMessage(cm, mes, d, includeSelections);
				}
				appendProperties(m, mes, d);
				e.appendChild(mes);
			}
		} catch (Exception ex) {
			logger.error("Error: ", ex);
		}
	}

	private static void createMessageFromLaszlo(Node node, Navajo n, Message msg) {
		try {
			String name = node.getNodeName().substring(2);
			String type = Message.MSG_TYPE_SIMPLE;
			if (node.getNodeName().startsWith("a_")) {
				type = Message.MSG_TYPE_ARRAY;
			}
			Message m = NavajoFactory.getInstance().createMessage(n, name, type);

			NodeList nl = node.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				Node cn = nl.item(i);
				if (cn.getNodeName().startsWith("p_")) {
					createPropertyFromLaszlo(cn, n, m);
				} else if (cn.getNodeName().startsWith("m_") || cn.getNodeName().startsWith("a_")) {
					createMessageFromLaszlo(cn, n, m);
				} else if (cn.getNodeName().equals("row")) {
					createMessageFromRow(cn, n, m);
				}
			}

			if (msg == null) {
				n.addMessage(m);
			} else {
				msg.addMessage(m);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private static void createSelectionFromLaszlo(Element cn, Navajo n, Property p) {
		try {
			String name = cn.getAttribute("name");
			String value = cn.getAttribute("value");
			String selected = cn.getAttribute("selected");
			Selection s = NavajoFactory.getInstance().createSelection(n, name, value, "true".equals(selected));
			p.addSelection(s);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private static void createPropertyFromLaszlo(Node cn, Navajo n, Message m) {
		try {
			Element elm = (Element) cn;
			String name = elm.getNodeName().substring(2);
			String length = elm.getAttribute("length");
			String type = elm.getAttribute("type");
			String direction = elm.getAttribute("direction");
			String value = elm.getAttribute("value");
			String description = elm.getAttribute("description");

			int l = 0;
			try {
				l = Integer.parseInt(length);
			} catch (Exception e) {
			}
			if (type.equals(Property.SELECTION_PROPERTY)) {
				Property p = NavajoFactory.getInstance().createProperty(n, name, "1", description, direction);
				NodeList options = elm.getChildNodes();
				for (int i = 0; i < options.getLength(); i++) {
					Node option = options.item(i);
					if (option.getNodeName().startsWith("option")) {
						createSelectionFromLaszlo((Element) option, n, p);
					}
				}
				m.addProperty(p);
			} else {
				Property p = NavajoFactory.getInstance().createProperty(n, name, type, value, l, description, direction);
				m.addProperty(p);
			}

		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private static void createMessageFromRow(Node cn, Navajo n, Message m) {
		try {
			Message row = NavajoFactory.getInstance().createMessage(n, m.getName(), Message.MSG_TYPE_ARRAY_ELEMENT);
			Element elm = (Element) cn;
			NamedNodeMap properties = elm.getAttributes();
			for (int i = 0; i < properties.getLength(); i++) {
				Node prop = properties.item(i);
				String name = prop.getNodeName();
				String value = prop.getNodeValue();
				if (!name.startsWith("type_")) {
					String type = elm.getAttribute("type_" + name);
					Property p = NavajoFactory.getInstance().createProperty(n, name, type, value, 0, "", "in");
					row.addProperty(p);
				}
			}

			NodeList selProps = elm.getChildNodes();
			for (int j = 0; j < selProps.getLength(); j++) {
				Element prop = (Element) selProps.item(j);
				String name = prop.getNodeName();
				Property p = NavajoFactory.getInstance().createProperty(n, name, "1", "", "in");

				NodeList options = prop.getChildNodes();
				for (int k = 0; k < options.getLength(); k++) {
					Element op = (Element) options.item(k);
					String op_name = op.getAttribute("name");
					String op_value = op.getAttribute("value");
					String op_selected = op.getAttribute("selected");
					Selection s = NavajoFactory.getInstance().createSelection(n, op_name, op_value, "true".equals(op_selected));
					p.addSelection(s);
				}
				row.addProperty(p);
			}
			m.addMessage(row);
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private static void appendProperties(Message m, Element e, Document d) {
		try {
			ArrayList<Property> allProp = m.getAllProperties();
			for (int i = 0; i < allProp.size(); i++) {
				Property current = allProp.get(i);

				Element prop = d.createElement("p_" + current.getName());
				if(current.getType().equals(Property.DATE_PROPERTY)){
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					if(current.getTypedValue() != null){
						prop.setAttribute("value", df.format((Date)current.getTypedValue()));
					}else{
						prop.setAttribute("value", current.getValue());
					}
				}else if(current.getType().equals(Property.CLOCKTIME_PROPERTY)){
					ClockTime ct = (ClockTime)current.getTypedValue();
				  if(ct != null){
				  	Date dv = ct.dateValue();
				  	DateFormat dfct = new SimpleDateFormat("HH:mm");
				  	prop.setAttribute("value", dfct.format(dv));
				  }else{
						prop.setAttribute("value", current.getValue());
					}
				  
				}else{
					prop.setAttribute("value", current.getValue());
				}
				
				prop.setAttribute("description", current.getDescription());
				prop.setAttribute("direction", current.getDirection());
				prop.setAttribute("type", current.getType());
				prop.setAttribute("subtype", current.getSubType());
				prop.setAttribute("length", "" + current.getLength());
	
				if (current.getType().equals(Property.SELECTION_PROPERTY)) {
					ArrayList<Selection> sel = current.getAllSelections();
					for (int j = 0; j < sel.size(); j++) {
						Selection s = sel.get(j);
						Element option = d.createElement("option");
						option.setAttribute("value", s.getValue());
						option.setAttribute("name", s.getName());
						option.setAttribute("selected", "" + s.isSelected());
						prop.appendChild(option);
					}

				}
				e.appendChild(prop);
			}
		} catch (Exception ex) {
			logger.error("Error: ", ex);
		}
	}

	public static void dumpNavajoLaszloStyle(Navajo n, String filename, String serviceName) throws IOException {
		Document d = createLaszloFromNavajo(n, serviceName);
		FileWriter fw = new FileWriter(filename);
		XMLDocumentUtils.write(d, fw, false);
		fw.close();
	}

}
