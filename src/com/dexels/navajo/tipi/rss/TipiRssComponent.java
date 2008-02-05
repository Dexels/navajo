package com.dexels.navajo.tipi.rss;

import java.io.*;
import java.net.*;
import java.util.*;

import sun.awt.geom.AreaOp.*;


//import com.dexels.navajo.document.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.connectors.*;
import com.dexels.navajo.tipi.internal.*;
import com.sun.cnpi.rss.elements.*;
import com.sun.cnpi.rss.parser.*;

public class TipiRssComponent extends TipiBaseConnector implements TipiConnector {
	//http://search-result.com/directhit/xml/NL_algemeen.xml
	@Override
	public Object createContainer() {
		return null;
	}

	@Override
	protected void setComponentValue(String name, Object object) {
		super.setComponentValue(name, object);
	
		
	}

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);
	}

	public void doTransaction(Navajo n, String service, String destination) throws TipiBreakException, TipiException {
		try {
			Rss rss = createRssFeed(destination);
			Navajo nn = getRssNavajo(rss.getChannel());
			myContext.addNavajo(service, n);
			myContext.loadNavajo(nn, service);
		} catch (Exception e) {
			throw new TipiException(e);
		}
	}
	public Rss createRssFeed(String feed) throws MalformedURLException, RssParserException, IOException {
			RssParser parser = RssParserFactory.createDefault();
			Rss rss = parser.parse(new URL(feed));
			return rss;
	}

	public String getConnectorId() {
		return "rss";
	}

	private Navajo getRssNavajo(Channel c) throws NavajoException {
		Navajo n = NavajoFactory.getInstance().createNavajo();

		Message channelMessage = NavajoFactory.getInstance().createMessage(n,"Channel");
		n.addMessage(channelMessage);
		addProperty(channelMessage,"Title",c.getTitle(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"Link",c.getLink(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"Description",c.getDescription(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"Language",c.getLanguage(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"Copyright",c.getCopyright(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"PubDate",c.getPubDate(),Property.STRING_PROPERTY);
		addProperty(channelMessage,"Ttl",c.getTtl(),Property.STRING_PROPERTY);
		addImage(channelMessage, c.getImage());
		
		Message m = NavajoFactory.getInstance().createMessage(n, "Rss", Message.MSG_TYPE_ARRAY);
		n.addMessage(m);
		Collection s = c.getItems();
		for (Iterator<Item> iterator = s.iterator(); iterator.hasNext();) {
			Item i = iterator.next();
			addItem(m,i);
		}		
		return n;
	}
	private void addImage(Message channelMessage, Image image) throws NavajoException {
		if(image==null) {
			return;
		}
		Navajo n = channelMessage.getRootDoc();
		Message imageMessage = NavajoFactory.getInstance().createMessage(n,"Image");
		channelMessage.addMessage(imageMessage);
		addProperty(imageMessage,"Title",image.getTitle(),Property.STRING_PROPERTY);
		addProperty(imageMessage,"Link",image.getLink(),Property.STRING_PROPERTY);
		addProperty(imageMessage,"Description",image.getDescription(),Property.STRING_PROPERTY);
		addProperty(imageMessage,"Width",image.getWidth(),Property.INTEGER_PROPERTY);
		addProperty(imageMessage,"Height",image.getWidth(),Property.INTEGER_PROPERTY);
		
		addProperty(imageMessage,"Url",image.getUrl(),Property.STRING_PROPERTY);
		addProperty(imageMessage,"ImageData",image.getUrl(),Property.STRING_PROPERTY);
		URL u;
		try {
			u = new URL(image.getUrl().getText());
			InputStream is = u.openStream();
			imageMessage.getProperty("ImageData").setAnyValue(new Binary(is));
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void addItem(Message m, Item i) throws NavajoException {
		Navajo n = m.getRootDoc();

		
		Message itemMessage = NavajoFactory.getInstance().createMessage(n,"Rss",Message.MSG_TYPE_ARRAY_ELEMENT);
		m.addMessage(itemMessage);
		addProperty(itemMessage,"Title",i.getTitle(),Property.STRING_PROPERTY);
		addProperty(itemMessage,"Link",i.getLink(),Property.STRING_PROPERTY);
		addProperty(itemMessage,"Description",i.getDescription(),Property.MEMO_PROPERTY);
		addProperty(itemMessage,"Author",i.getAuthor(),Property.STRING_PROPERTY);
		addProperty(itemMessage,"PubDate",i.getPubDate(),Property.STRING_PROPERTY);
		addProperty(itemMessage,"Source",i.getSource(),Property.STRING_PROPERTY);
		
	}
	private void addProperty(Message m, String name, BasicElement e, String type, String attribute) throws NavajoException {
		if(e==null) {
			return;
		}
		Navajo n = m.getRootDoc();
		String text = null;
		if(attribute==null) { 
			text = e.getText();
		} else {
			text = e.getAttribute(attribute);
		}
		
		Property p = NavajoFactory.getInstance().createProperty(n,name,type,text,0,null,Property.DIR_IN);
		m.addProperty(p);
	}
	private void addProperty(Message m, String name, BasicElement e, String type) throws NavajoException {
		addProperty(m, name, e, type,null);
	}

	public static void main(String[] args) throws MalformedURLException, RssParserException, IOException, NavajoException {
		TipiRssComponent trc = new TipiRssComponent();
		Rss r = trc.createRssFeed("http://search-result.com/directhit/xml/NL_algemeen.xml");
//		http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml
		Channel ccc = r.getChannel();
		
		Navajo n = trc.getRssNavajo(ccc);
		Collection<Item> s = ccc.getItems();
		for (Iterator<Item> iterator = s.iterator(); iterator.hasNext();) {
			Item i = iterator.next();
			Title t = i.getTitle();
			System.err.println("TT: "+t.getText());
		}
		System.err.println("s: "+s);
	}

	public void doTransaction(Navajo n, String service) throws TipiBreakException, TipiException {
		
	}
	
	
}
