package com.dexels.navajo.adapter;

import java.util.ArrayList;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class TmlToXmlMap implements Mappable {
	XMLMap content = new XMLMap();
	Access access = null;
	String rootPath = null;
	Navajo document = null;
	ArrayList<String[]> attributes = new ArrayList<String[]>();

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

	@Override
	public void kill() {
		// TODO Auto-generated method stub

	}

	@Override
	public void load(Access access) throws MappableException, UserException {
		this.access = access;
		buildContent();
	}

	@Override
	public void store() throws MappableException, UserException {
		// TODO Auto-generated method stub

	}

	public void buildContent() throws UserException {
		try {
			if (document == null) {
				document = access.getOutputDoc();
			}
			Message root = document.getMessage(rootPath);
			content.setStart(root.getName());

			// Watch it, we assume the root has no properies
			ArrayList<Message> allMessages = root.getAllMessages();
			for (int i = 0; i < allMessages.size(); i++) {
				appendMessages(allMessages.get(i), content);
			}

			appendAttributes();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException(392, e.getMessage());
		}
	}

	private final void appendAttributes() throws UserException {
		for (int i = 0; i < attributes.size(); i++) {
			String[] attr = attributes.get(i);
			content.setChildName(attr[0]);
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
		property.setName(p.getName());
		property.setText(p.getValue());
		parent.setChild(property);
	}

	public XMLMap getContent() {
		return content;
	}

	public static void main(String[] args) {
		try {
			TmlToXmlMap t2x = new TmlToXmlMap();

			Navajo n = NavajoFactory.getInstance().createNavajo();
			Message cms = NavajoFactory.getInstance().createMessage(n, "nam:cmsRequest", Message.MSG_TYPE_SIMPLE);
			Message sporttaal = NavajoFactory.getInstance().createMessage(n, "nam:Sporttaal", Message.MSG_TYPE_SIMPLE);
			
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
			n.addMessage(cms);
			cms.addMessage(sporttaal);
			sporttaal.addProperty(sender);

			t2x.setRootPath("nam:cmsRequest");
			t2x.setDocument(n);
			t2x.addAttribute("nam:Sporttaal", "nsp:schemaLocation", "http://nationalesportpas.nl/namespace NSP_v1.1.1.1.xsd");
			t2x.addAttribute("nam:Sporttaal", "xmlns", "http://nationalesportpas.nl/namespace");
			t2x.addAttribute("nam:Sporttaal", "xmlns:nsp", "http://www.w3.org/2001/XMLSchema-instance");

			
			t2x.buildContent();
			XMLMap c = t2x.getContent();
			c.getContent().write(System.err);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
