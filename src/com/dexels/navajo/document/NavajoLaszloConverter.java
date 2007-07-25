package com.dexels.navajo.document;

import java.io.BufferedInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.*;
import java.util.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;

public class NavajoLaszloConverter {
	public static Navajo createNavajoFromLaszlo(BufferedInputStream is) {
		Navajo n = null;
		try {
			Document doc = XMLDocumentUtils.createDocument(is, false);

			// System.err.println("Received: " +
			// XMLDocumentUtils.toString(doc));

			Node root = doc.getFirstChild();
			n = NavajoFactory.getInstance().createNavajo();
			if (root != null) {

				Node tml = root.getFirstChild();

				String rpc_name = ((Element) tml).getAttribute("rpc_name");
				rpc_name = rpc_name.replaceAll("_", "/");
				String rpc_usr = ((Element) tml).getAttribute("rpc_usr");
				String rpc_pwd = ((Element) tml).getAttribute("rpc_pwd");

				Header h = NavajoFactory.getInstance().createHeader(n, rpc_name, rpc_usr, rpc_pwd, -1);
				n.addHeader(h);
				NodeList children = tml.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node noot = children.item(i);
					createMessageFromLaszlo(noot, n, null);
				}
			}
			// System.err.println("Created navajo: ");
			// n.write(System.err);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	public static Document createLaszloFromNavajo(Navajo in, String serviceName) {
		return createLaszloFromNavajo(in, serviceName, true);
	}

	public static Document createLaszloFromNavajo(Navajo in, String serviceName, boolean includeSelections) {
		Document doc = XMLDocumentUtils.createDocument();
		try {
			String nodeName = serviceName.replaceAll("/", "_");
			Element root = doc.createElement(nodeName);
			doc.appendChild(root);
			Element tml = doc.createElement("tml");
			tml.setAttribute("rpc_usr", in.getHeader().getRPCUser());
			tml.setAttribute("rpc_pwd", in.getHeader().getRPCPassword());
			tml.setAttribute("rpc_name", serviceName);
			root.appendChild(tml);
			ArrayList l = in.getAllMessages();
			for (int i = 0; i < l.size(); i++) {
				appendMessage((Message) l.get(i), tml, doc, includeSelections);
			}
			// System.err.println("Created doc: " +
			// XMLDocumentUtils.toString(doc));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	private static void appendMessage(Message m, Element e, Document d, boolean includeSelections) {
		try {
			if (m.getType().equals(Message.MSG_TYPE_ARRAY_ELEMENT)) {
				System.err.println("CREATING ARRAY ELEMENT");
				Element row = d.createElement("row");
				ArrayList allProp = m.getAllProperties();
				for (int j = 0; j < allProp.size(); j++) {
					Property cp = (Property) allProp.get(j);
					if (cp.getType().equals(Property.SELECTION_PROPERTY)) {

						if (includeSelections) {
							Element prop = d.createElement(cp.getName());
							ArrayList sel = cp.getAllSelections();
							for (int k = 0; k < sel.size(); k++) {
								Selection s = (Selection) sel.get(k);
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
							System.err.println("null date");
							row.setAttribute(cp.getName(), "#");
						}
					} else {
						if (!cp.getType().equals(Property.SELECTION_PROPERTY)) {
							String result = cp.getValue().replaceAll("\n", "<br/>");
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
				ArrayList allMes = m.getAllMessages();
				for (int k = 0; k < allMes.size(); k++) {
					Message cm = (Message) allMes.get(k);
					appendMessage(cm, mes, d, includeSelections);
				}
				appendProperties(m, mes, d);
				e.appendChild(mes);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
					Node option = (Element) options.item(i);
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}

	private static void appendProperties(Message m, Element e, Document d) {
		try {
			ArrayList allProp = m.getAllProperties();
			for (int i = 0; i < allProp.size(); i++) {
				Property current = (Property) allProp.get(i);

				Element prop = d.createElement("p_" + current.getName());
				prop.setAttribute("value", current.getValue());
				prop.setAttribute("description", current.getDescription());
				prop.setAttribute("direction", current.getDirection());
				prop.setAttribute("type", current.getType());
				prop.setAttribute("length", "" + current.getLength());

				if (current.getType().equals(Property.SELECTION_PROPERTY)) {
					ArrayList sel = current.getAllSelections();
					for (int j = 0; j < sel.size(); j++) {
						Selection s = (Selection) sel.get(j);
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
			ex.printStackTrace();
		}
	}

	public static void dumpNavajoLaszloStyle(Navajo n, String filename, String serviceName) throws IOException {
		Document d = createLaszloFromNavajo(n, serviceName);
		FileWriter fw = new FileWriter(filename);
		XMLDocumentUtils.write(d, fw, false);
		fw.close();
	}

}
