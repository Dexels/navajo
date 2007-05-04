package com.dexels.navajo.tipi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dexels.navajo.adapter.descriptionprovider.TestBabel;
import com.dexels.navajo.client.ClientException;
import com.dexels.navajo.client.NavajoClientFactory;
import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.jaxpimpl.xml.XMLDocumentUtils;
import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
//import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
//import com.dexels.navajo.tipi.tipixml.XMLElement;
//import com.dexels.navajo.tipi.tipixml.XMLParseException;
import com.dexels.navajo.tipi.internal.RemoteDescriptionProvider;

public class TipiTranslator {
	
	String url = "penelope1.dexels.com/sportlink/knvb/servlet/Postman";
	String user = "ROOT";
	String pass = "R20T";
	private RemoteDescriptionProvider myDescriptionProvider;

	public static void main(String[] args) throws Exception {
		
		  TipiTranslator tt = new TipiTranslator();
		  tt.init();
		  tt.initRemoteDescriptionProvider("%", "nl");
		  tt.loop("nl","fr","%","%");
//		  String aa = TestBabel.getTranslation("aap", "nl_en");
//		System.err.println(">"+aa);
		 
	}

	public void loop(String lang, String tolang, String sublocale, String context) throws MalformedURLException, ProtocolException, IOException, NavajoException, ClientException {
		myDescriptionProvider.getDescriptionNames();
		int i = 0;
		for (Iterator iter = myDescriptionProvider.getDescriptionNames().iterator(); iter.hasNext();) {
			String element = (String) iter.next();
			String desc = myDescriptionProvider.getDescription(element);
//			if(i>=643) {
			String aa =translate(element,lang,tolang,sublocale,context);
			System.err.println("#"+i+++" Element: "+element+" : "+desc+" transalted: "+aa);
//			insertDescription(element, aa, tolang, sublocale, context);
//			} else {
//				i++;
//			}
		}
	}
	public String translate(String name, String lang, String tolang, String sublocale, String context)  {
		String description = getDescription(name);
//		System.err.println("name: "+name+" d: "+description);
		String trans;
		try {
			trans = TestBabel.getTranslation(name, description, lang+"_"+tolang,context);
			return trans;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "<untranslatable:"+name+">";
	}

	private String getDescription(String name) {
		return myDescriptionProvider.getDescription(name);
	}

	public void insertDescription(String name, String description, String locale, String sublocale, String context) throws NavajoException, ClientException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		   Message m = NavajoFactory.getInstance().createMessage(n, "Description");
		   n.addMessage(m);
		   Property w = NavajoFactory.getInstance().createProperty(n, "Context", Property.STRING_PROPERTY,context, 99,"", Property.DIR_IN);
		   m.addProperty(w);
		   Property l = NavajoFactory.getInstance().createProperty(n, "Locale", Property.STRING_PROPERTY,locale, 99,"", Property.DIR_IN);
		   m.addProperty(l);
		   Property nn = NavajoFactory.getInstance().createProperty(n, "Name", Property.STRING_PROPERTY,name, 99,"", Property.DIR_IN);
		   m.addProperty(nn);
		   Property des = NavajoFactory.getInstance().createProperty(n, "Description", Property.STRING_PROPERTY,description, 300,"", Property.DIR_IN);
		   m.addProperty(des);		   
		   Property sub = NavajoFactory.getInstance().createProperty(n, "SubLocale", Property.STRING_PROPERTY,description, 300,"", Property.DIR_IN);
		   m.addProperty(sub);	
		   Property obj = NavajoFactory.getInstance().createProperty(n, "ObjectId", Property.STRING_PROPERTY,"", 300,"", Property.DIR_IN);
		   m.addProperty(obj);
		   Property objt = NavajoFactory.getInstance().createProperty(n, "ObjectType", Property.STRING_PROPERTY,"", 300,"", Property.DIR_IN);
		   m.addProperty(objt);
	//	   n.write(System.err);
		   
		 
		   
		   Navajo res = NavajoClientFactory.getClient().doSimpleSend(n, "navajo/description/ProcessInsertDescription");
	//	   res.write(System.err);
	}

	private void init() {
		NavajoClientFactory.getClient().setServerUrl(url);
		   NavajoClientFactory.getClient().setUsername(user);
		   NavajoClientFactory.getClient().setPassword(pass);
	}
	

	public void initRemoteDescriptionProvider(String context, String locale) throws NavajoException, ClientException {
		Navajo n = NavajoFactory.getInstance().createNavajo();
		   Message m = NavajoFactory.getInstance().createMessage(n, "Description");
		   n.addMessage(m);
		   Property w = NavajoFactory.getInstance().createProperty(n, "Context", Property.STRING_PROPERTY,context, 99,"", Property.DIR_IN);
		   m.addProperty(w);
		   Property l = NavajoFactory.getInstance().createProperty(n, "Locale", Property.STRING_PROPERTY,locale, 99,"", Property.DIR_IN);
		   m.addProperty(l);
//		   n.write(System.err);
		   Navajo res = NavajoClientFactory.getClient().doSimpleSend(n, "navajo/description/ProcessGetContextResources");
//		   res.write(System.err);
		   Message descr = res.getMessage("Descriptions");
		   myDescriptionProvider = new RemoteDescriptionProvider(null);
		   ((RemoteDescriptionProvider)myDescriptionProvider).setMessage(descr);		
	}

}
