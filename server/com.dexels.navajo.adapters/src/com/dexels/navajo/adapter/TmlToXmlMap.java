package com.dexels.navajo.adapter;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TmlToXmlMap implements Mappable {
	public XMLMap content = new XMLMap();
	Access access = null;
	public String rootPath = null;
	Navajo document = null;
	public String attributePath = "";
	public String attributeName = "";
	public String attributeValue = "";
	public boolean buildContent, dumpObject;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TmlToXmlMap.class);
	
	private boolean hasBeenBuild = false;
	
	ArrayList<String[]> attributes = new ArrayList<String[]>();
	private String targetNamespace;

	public String getTargetNamespace() {
		return targetNamespace;
	}

	public void setTargetNamespace(String targetNamespace) {
		this.targetNamespace = targetNamespace;
	}

	public void setAttributePath(String path) {
		this.attributePath = path;
	}

	public void setAttributeName(String name) {
		this.attributeName = name;
	}

	public void setAttributeValue(String value) {
		this.attributeValue = value;
		if (!"".equals(attributeName) && !"".equals(attributePath) && !"".equals(value)) {
			addAttribute(attributePath, attributeName, attributeValue);
		}
	}

	public void addAttribute(String messagePath, String name, String value) {
		String[] attr = new String[] { messagePath, name, value };
		attributes.add(attr);
		
	}

	// This function is for testing only
	public void setDocument(Navajo n) {
		this.document = n;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public void kill() {
	}

	public void load(Access access) throws MappableException, UserException {
		this.access = access;
	}

	public void store() throws MappableException, UserException {
	}

	public void setBuildContent(boolean b) throws UserException {
		try {
			if (b) {
				if (document == null) {
					document = access.getOutputDoc();
				}
				content.setCompact(true);
				Message root = document.getMessage(rootPath);
				
				if(root == null){
					logger.error("ERROR! Could not find message: " + rootPath);
					throw new UserException(1200, "Root message not found for TML2XML map");
				}
				content.setStart(root.getName());
				// Add the target namespace 
				if(targetNamespace!=null) {
					content.setAttributeName("xmlns");
					content.setAttributeText(targetNamespace);
				}
				ArrayList<Property> allProperties = root.getAllProperties();
				for (int i = 0; i < allProperties.size(); i++) {
					appendProperty(allProperties.get(i), content);
				}
				
				// Watch it, we assume the root has no properies
				ArrayList<Message> allMessages = root.getAllMessages();
				for (int i = 0; i < allMessages.size(); i++) {
					appendMessages(allMessages.get(i), content);
				}

				appendAttributes();
				hasBeenBuild = true;
			}
		} catch (Exception e) {
			throw new UserException(392, e.getMessage(),e);
		}
	}

	public void setDumpObject(boolean b) {
		try {
			if (b) {
				getContent().write(System.err);
			}
		} catch (Exception e) {
			logger.error("Error: ", e);
		}
	}

	private final void appendAttributes() throws UserException {
		for (int i = 0; i < attributes.size(); i++) {
			String[] attr = attributes.get(i);
			if ( !attr[0].equals(content.getName())) {
				content.setChildName(attr[0]);
			}
			// TagMap child = content.getChild();
			// if(child != null){
			content.setAttributeName(attr[1]);
			content.setAttributeText(attr[2]);
			// }
		}
	}

	private final void appendMessages(Message m, TagMap parent) throws UserException {
		if (!m.getMode().equals(Message.MSG_MODE_IGNORE)) {
			// Check for array messages. Only append array children
			TagMap child;
			if (!m.getType().equals(Message.MSG_TYPE_ARRAY)) {
				child = new TagMap();
				child.setCompact(true);
				child.setName(m.getName());
				parent.setChild(child);
			} else {
				child = parent;
			}

			ArrayList<Property> allProperties = m.getAllProperties();
			for (int i = 0; i < allProperties.size(); i++) {
				appendProperty(allProperties.get(i), child);
			}

			ArrayList<Message> allMessages = m.getAllMessages();
			for (int i = 0; i < allMessages.size(); i++) {
				appendMessages(allMessages.get(i), child);
			}
		}
	}

	private final void appendProperty(Property p, TagMap parent) throws UserException {
		TagMap property = new TagMap();
		property.setCompact(true);
		property.setName(p.getName());
		property.setText(p.getValue());
		parent.setChild(property);
	}

	public Binary getContent() throws UserException {
		if ( !hasBeenBuild ) {
			setBuildContent(true);
		}
		return content.getContent();
	}

	
	/**
	 * @param b  
	 */
	public void setProcessSoapData(boolean b) throws UserException {
		// TODO I think this overwrites multipart messages (?). Find an example and verify
		Message settings = this.document.getMessage("_SoapSettings");
		Message headers = this.document.getMessage("_SoapHeaders");
		settings.setMode(Message.MSG_MODE_IGNORE);
		headers.setMode(Message.MSG_MODE_IGNORE);
		setTargetNamespace(settings.getProperty("TargetNamespace").getValue());
		logger.debug("Using namespace: "+settings.getProperty("TargetNamespace").getValue());
		Property parts = settings.getProperty("Parts");
		String partList = parts.getValue();
		String[] partArray = partList.split(",");
		for (String pt : partArray) {
			logger.debug("Part: "+pt);
			Message rootParent = this.document.getMessage(pt);
			// should have only one child, if I'm not mistaken.
			for (Message child : rootParent.getAllMessages()) {
				setRootPath(child.getFullMessageName());
				setBuildContent(true);
			}
		}
	}
	public static void main(String[] args) throws UserException {
			TmlToXmlMap t2x = new TmlToXmlMap();

			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message apenootWrapepr = NavajoFactory.getInstance().createMessage(n, "Apenoot", Message.MSG_TYPE_SIMPLE);
			
			Message cms = NavajoFactory.getInstance().createMessage(n, "nam:cmsRequest", Message.MSG_TYPE_SIMPLE);
			Message sporttaal = NavajoFactory.getInstance().createMessage(n, "nam:Sporttaal", Message.MSG_TYPE_SIMPLE);
			apenootWrapepr.addMessage(cms);
			
			// Array test
			Message input = NavajoFactory.getInstance().createMessage(n, "nam:Input", Message.MSG_TYPE_ARRAY);
			Message one = NavajoFactory.getInstance().createMessage(n, "nam:Input", Message.MSG_TYPE_ARRAY_ELEMENT);
			Message two = NavajoFactory.getInstance().createMessage(n, "nam:Input", Message.MSG_TYPE_ARRAY_ELEMENT);
			input.addElement(one);
			input.addElement(two);
			sporttaal.addMessage(input);

			// Ignore mode ignore messages
			Message bogus = NavajoFactory.getInstance().createMessage(n, "nam:TotallyBogus", Message.MSG_TYPE_SIMPLE);
			bogus.setMode(Message.MSG_MODE_IGNORE);
			sporttaal.addMessage(bogus);

			Property sender = NavajoFactory.getInstance().createProperty(n, "nam:Sender", "string", "Arnoud", 10, "", Property.DIR_OUT);
			n.addMessage(apenootWrapepr);
			cms.addMessage(sporttaal);
			sporttaal.addProperty(sender);

			t2x.setRootPath("Apenoot/nam:cmsRequest");
			t2x.setDocument(n);
			t2x.addAttribute("nam:Sporttaal", "nsp:schemaLocation", "http://nationalesportpas.nl/namespace NSP_v1.1.1.1.xsd");
			t2x.addAttribute("nam:Sporttaal", "xmlns", "http://nationalesportpas.nl/namespace");
			t2x.addAttribute("nam:Sporttaal", "xmlns:nsp", "http://www.w3.org/2001/XMLSchema-instance");

			t2x.setBuildContent(true);
			t2x.setDumpObject(true);

	}
}
