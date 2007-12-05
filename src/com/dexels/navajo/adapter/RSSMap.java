package com.dexels.navajo.adapter;

import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.adapter.rss.RSSItem;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.UserException;
import com.sun.cnpi.rss.parser.*;
import com.sun.cnpi.rss.elements.*;

import java.net.*;
import java.util.*;

public class RSSMap implements Mappable {

	/**
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * <p>Copyright: Copyright (c) 2005</p>
	 * <p>Company: </p>
	 * @author not attributable
	 * @version 1.0
	 */

	private Rss rss;

	public String rssFeed = "http://www.uefa.com/rssfeed/index.xml";
	public RSSItem[] items = null;
	public Binary content;
	public String title;
	public String link;
	public String description;
	public String language;
	public String copyright;
	public String managingEditor;
	public String webMaster;
	public String pubDate;
	public String lastBuildDate;
	public String category;
	public String generator;
	public String docs;
	public String cloud;
	public String ttl;
	public Binary image;
	public String rating;
	public String textInput;
	public String skipHours;
	public String skipDays;

	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {

	}

	public void store() throws MappableException, UserException {
	}

	public Binary getContent() {
	
		StringBuffer sb = new StringBuffer();
		sb.append("<rss version=\"2.0\">\n");
		sb.append("  <channel>\n");
		sb.append("    <title>" + getTitle() + "</title>\n");
		sb.append("    <link>" + getLink() + "</link>\n");
		sb.append("    <copyright>" + getCopyright() + "</copyright>\n");
		sb.append("    <language>" + getLanguage() + "</language>\n");
		sb.append("    <category>" + getCategory() + "</category>\n");
		sb.append("    <webmaster>" + getWebMaster() + "</webmaster>\n");
		sb.append("    <pubDate>" + getPubDate() + "</pubDate>\n");
		
		Image img = rss.getChannel().getImage();
		if ( img != null ) {
			sb.append("    <image>\n");
			sb.append("      <title>" + img.getTitle() + "</title>\n");
			sb.append("      <url>" + img.getUrl() + "</url>\n");
			sb.append("      <link>" + img.getLink() + "</link>\n");
			if ( img.getWidth() != null)  sb.append("      <width>" + img.getWidth() + "</width>\n");
			if ( img.getHeight() != null) sb.append("      <height>" + img.getHeight() + "</height>\n");
			sb.append("      <description>" + img.getDescription() + "</description>\n");
			sb.append("    </image>\n");
		}
			
		getItems();
		for ( int i = 0; i < items.length; i++ ) {
			sb.append("    <item>\n");
			sb.append("      <title>" + items[i].getTitle() + "</title>\n");
			sb.append("      <link>" + items[i].getLink() + "</link>\n");
			sb.append("      <description>" + items[i].getDescription() + "</description>\n");
			sb.append("      <enclosure>" + items[i].getEnclosure() + "</enclosure>\n");
			sb.append("      <category>" + items[i].getCategory() + "</category>\n");
			sb.append("      <pubDate>" + items[i].getPubDate() + "</pubDate>\n");
			sb.append("    </item>\n");
		}
		sb.append("  </channel>\n");
		sb.append("</rss>\n");
		
		return new Binary(sb.toString().getBytes());
	}
	
	public void setItems(RSSItem [] rssitems) {
		items = new RSSItem[rssitems.length];
		for ( int i = 0; i < items.length; i++ ) {
			items[i] = rssitems[i];
		}
	}
	
	public RSSItem[] getItems() {
		
		Collection rssitems = rss.getChannel().getItems();
		Iterator it = rssitems.iterator();
		items = new RSSItem[rssitems.size()];
		int index = 0;

		while (it.hasNext()) {
			Item i = (Item) it.next();
			items[index] = new RSSItem();

			if (i.getTitle() != null) {
				items[index].setTitle(i.getTitle().getText());
			}
			if (i.getDescription() != null) {
				items[index].setDescription(i.getDescription().getText());
			}
			if (i.getPubDate() != null) {
				items[index].setPubDate(i.getPubDate().getText());
			}
			if (i.getLink() != null) {
				items[index].setLink(i.getLink().getText());
			}
			if (i.getAuthor() != null) {
				items[index].setAuthor(i.getAuthor().getText());
			}
			if (i.getComments() != null) {
				items[index].setComments(i.getComments().getText());
			}
			if (i.getEnclosure() != null) {
				items[index].setEnclosure(i.getEnclosure().getAttribute("url"));
			}
			if (i.getGuid() != null) {
				items[index].setGuid(i.getGuid().getText());
			}
			if (i.getSource() != null) {
				items[index].setSource(i.getSource().getText());
			}
			if (i.getCategories() != null) {
				Collection c = i.getCategories();
				String cc = "";
				if (c != null) {
					Iterator cit = c.iterator();
					while (cit.hasNext()) {
						cc = cc + ((Category) cit.next()).getText();
						if (cit.hasNext()) {
							cc = cc + ", ";
						}
					}
					items[index].setCategory(cc);
				}
			}
			index++;
		}
		return items;
	}

	public void setRssFeed(String feed) {
		rssFeed = feed;
		try {
			
			RssParser parser = RssParserFactory.createDefault();
			rss = parser.parse(new URL(rssFeed));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRssFeed() {
		return rssFeed;
	}

	public static void main(String[] args) {
		
		try{
			RSSMap m = new RSSMap();
			m.setRssFeed(m.rssFeed);
			m.load(null, null, null, null);
			
			System.err.println(new String( m.getContent().getData() ));
			//m.getItems();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getCategory() {
		category = "";
		Collection c = rss.getChannel().getCategories();
		if (c != null) {
			Iterator it = c.iterator();
			while (it.hasNext()) {
				category = category + ((Category) it.next()).getText();
				if (it.hasNext()) {
					category = category + ", ";
				}
			}
		}
		return category;
	}

	// not fully fledged.. a cloud is more complex
	public String getCloud() {
		if (rss.getChannel().getCloud() != null) {
			return rss.getChannel().getCloud().getText();
		}
		else {
			return null;
		}
	}

	public String getCopyright() {
		if (rss.getChannel().getCopyright() != null) {
			return rss.getChannel().getCopyright().getText();
		}
		else {
			return "";
		}

	}

	public String getDescription() {
		if (rss.getChannel().getDescription() != null) {
			return rss.getChannel().getDescription().getText();
		}
		else {
			return null;
		}
	}

	public String getDocs() {
		if (rss.getChannel().getDocs() != null) {
			return rss.getChannel().getDocs().getText();
		}
		else {
			return null;
		}
	}

	public String getGenerator() {
		if (rss.getChannel().getGenerator() != null) {
			return rss.getChannel().getGenerator().getText();
		}
		else {
			return null;
		}
	}

	// complex, should we perhaps return a binary property here?
	public Binary getImage() {
		if (rss.getChannel().getImage() != null) {
			try {
				return new Binary( new URL(rss.getChannel().getImage().getUrl().getText()).openStream() );
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public String getLanguage() {
		if (rss.getChannel().getLanguage() != null) {
			return rss.getChannel().getLanguage().getText();
		}
		else {
			return null;
		}
	}

	public String getLastBuildDate() {
		if (rss.getChannel().getLastBuildDate() != null) {
			return rss.getChannel().getLastBuildDate().getText();
		}
		else {
			return null;
		}
	}

	public String getLink() {
		if (rss.getChannel().getLink() != null) {
			return rss.getChannel().getLink().getText();
		}
		else {
			return null;
		}
	}

	public String getManagingEditor() {
		if (rss.getChannel().getManagingEditor() != null) {
			return rss.getChannel().getManagingEditor().getText();
		}
		else {
			return null;
		}
	}

	public String getPubDate() {
		if (rss.getChannel().getPubDate() != null) {
			return rss.getChannel().getPubDate().getText();
		}
		else {
			return null;
		}
	}

	public String getRating() {
		if (rss.getChannel().getRating() != null) {
			return rss.getChannel().getRating().getText();
		}
		else {
			return null;
		}
	}

	public String getSkipDays() {
		if (rss.getChannel().getSkipDays() != null) {
			return rss.getChannel().getSkipDays().getText();
		}
		else {
			return null;
		}
	}

	public String getTextInput() {
		if (rss.getChannel().getTextInput() != null) {
			return rss.getChannel().getTextInput().getText();
		}
		else {
			return null;
		}
	}

	public String getSkipHours() {
		if (rss.getChannel().getSkipHours() != null) {
			return rss.getChannel().getSkipHours().getText();
		}
		else {
			return null;
		}
	}

	public String getTitle() {
		if (rss.getChannel().getTitle() != null) {
			return rss.getChannel().getTitle().getText();
		}
		else {
			return null;
		}
	}

	public String getTtl() {
		if (rss.getChannel().getTtl() != null) {
			return rss.getChannel().getTtl().getText();
		}
		else {
			return null;
		}
	}

	public String getWebMaster() {
		if (rss.getChannel().getWebMaster() != null) {
			return rss.getChannel().getWebMaster().getText();
		}
		else {
			return null;
		}
	}

}
