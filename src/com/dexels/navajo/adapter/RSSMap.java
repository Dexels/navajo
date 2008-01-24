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
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2005
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
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

	  public final static String XML_ESCAPE_DELIMITERS = "&'<>\"";

	
	private Channel channel = null;

	public void kill() {
	}

	public void load(Parameters parms, Navajo inMessage, Access access, NavajoConfig config) throws MappableException, UserException {
		channel = new Channel();
		rss = new Rss("version 2.0");
		rss.setChannel(channel);
	}

	public void store() throws MappableException, UserException {
	}

	public Binary getContent() {

		StringBuffer sb = new StringBuffer();
		sb.append("<rss version=\"2.0\">\n");
		sb.append("  <channel>\n");
		if (getTitle() != null)
			sb.append("    <title>" + XMLEscape(getTitle()) + "</title>\n");
		if (getLink() != null)
			sb.append("    <link>" +getLink() + "</link>\n");
		if (getCopyright() != null)
			sb.append("    <copyright>" + XMLEscape(getCopyright()) + "</copyright>\n");
		if (getLanguage() != null)
			sb.append("    <language>" + XMLEscape(getLanguage()) + "</language>\n");
		if (getCategory() != null)
			sb.append("    <category>" + XMLEscape(getCategory()) + "</category>\n");
		if (getWebMaster() != null)
			sb.append("    <webmaster>" + XMLEscape(getWebMaster()) + "</webmaster>\n");
		if (getPubDate() != null)
			sb.append("    <pubDate>" + XMLEscape(getPubDate()) + "</pubDate>\n");

		Image img = rss.getChannel().getImage();
		if (img != null) {
			sb.append("    <image>\n");
			if (img.getTitle() != null)
				sb.append("      <title>" + XMLEscape(img.getTitle().toString()) + "</title>\n");
			if (img.getUrl() != null)
				sb.append("      <url>" + img.getUrl().toString() + "</url>\n");
			if (img.getLink() != null)
				sb.append("      <link>" + img.getLink().toString() + "</link>\n");
			if (img.getWidth() != null)
				sb.append("      <width>" + img.getWidth() + "</width>\n");
			if (img.getHeight() != null)
				sb.append("      <height>" + img.getHeight() + "</height>\n");
			if (img.getDescription() != null)
				sb.append("      <description>" + XMLEscape(img.getDescription().toString()) + "</description>\n");
			sb.append("    </image>\n");
		}

		getItems();
		if (items != null) {
			for (int i = 0; i < items.length; i++) {
				sb.append("    <item>\n");
				if (items[i].getTitle() != null)
					sb.append("      <title>" + XMLEscape(items[i].getTitle()) + "</title>\n");
				if (items[i].getLink() != null)
					sb.append("      <link>" + XMLEscape(items[i].getLink()) + "</link>\n");
				if (items[i].getCategory() != null)
					sb.append("      <category>" + XMLEscape(items[i].getCategory()) + "</category>\n");
				if (items[i].getGuid() != null)
					sb.append("      <guid>" + XMLEscape(items[i].getGuid()) + "</guid>\n");
				if (items[i].getAuthor() != null)
					sb.append("      <author>" + XMLEscape(items[i].getAuthor()) + "</author>\n");
				if (items[i].getDescription() != null)
					sb.append("      <description>" + XMLEscape(items[i].getDescription()) + "</description>\n");
				if (items[i].getEnclosure() != null)
					sb.append("      <enclosure>" + XMLEscape(items[i].getEnclosure()) + "</enclosure>\n");
				if (items[i].getCategory() != null)
					sb.append("      <category>" + XMLEscape(items[i].getCategory()) + "</category>\n");
				if (items[i].getPubDate() != null)
					sb.append("      <pubDate>" + XMLEscape(items[i].getPubDate()) + "</pubDate>\n");
				sb.append("    </item>\n");
			}
		}

		sb.append("  </channel>\n");
		sb.append("</rss>\n");

		return new Binary(sb.toString().getBytes());
	}

	public void setItems(RSSItem[] rssitems) {
		HashSet<Item> s = new HashSet<Item>();
		for (int i = 0; i < rssitems.length; i++) {
			System.err.println("Processing item..." + i);
			Item item = new Item();
			item.setAuthor(rssitems[i].getItemAuthor());
			item.setComments(rssitems[i].getItemComments());
			item.setDescription(rssitems[i].getItemDescription());
			item.setEnclosure(rssitems[i].getItemEnclosure());
			item.setGuid(rssitems[i].getItemGuid());
			item.setLink(rssitems[i].getItemLink());
			item.setPubDate(rssitems[i].getItemPubDate());
			item.setSource(rssitems[i].getItemSource());
			item.setTitle(rssitems[i].getItemTitle());
			s.add(item);
		}
		rss.getChannel().setItems(s);
	}

	public RSSItem[] getItems() {

		if (rss.getChannel().getItems() == null) {
			return null;
		}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getRssFeed() {
		return rssFeed;
	}

	public static void main(String[] args) {

		try {
			RSSMap m = new RSSMap();
			// m.setRssFeed(m.rssFeed);
			m.load(null, null, null, null);
			m.setTitle("Dexels News");
			m.setCopyright("CNN");
			m.setLanguage("en");
			m.setLink("http://www.aap.com/apenoot?hoera&olifant=compleet");
			m.setPubDate("21-dec-2017");
			RSSItem[] is = new RSSItem[1];
			is[0] = new RSSItem();
			is[0].setTitle("Dexels has acquired Microsoft & Sun");
			is[0].setAuthor("Ling jang Hu");
			m.setItems(is);

			System.err.println(new String(m.getContent().getData()));

			// m.getItems();
		} catch (Exception e) {
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
		} else {
			return null;
		}
	}

	public String getCopyright() {
		if (rss.getChannel().getCopyright() != null) {
			return rss.getChannel().getCopyright().getText();
		} else {
			return "";
		}

	}

	public String getDescription() {
		if (rss.getChannel().getDescription() != null) {
			return rss.getChannel().getDescription().getText();
		} else {
			return null;
		}
	}

	public String getDocs() {
		if (rss.getChannel().getDocs() != null) {
			return rss.getChannel().getDocs().getText();
		} else {
			return null;
		}
	}

	public String getGenerator() {
		if (rss.getChannel().getGenerator() != null) {
			return rss.getChannel().getGenerator().getText();
		} else {
			return null;
		}
	}

	// complex, should we perhaps return a binary property here?
	public Binary getImage() {
		if (rss.getChannel().getImage() != null) {
			try {
				return new Binary(new URL(rss.getChannel().getImage().getUrl().getText()).openStream());
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
		} else {
			return null;
		}
	}

	public String getLastBuildDate() {
		if (rss.getChannel().getLastBuildDate() != null) {
			return rss.getChannel().getLastBuildDate().getText();
		} else {
			return null;
		}
	}

	public String getLink() {
		if (rss.getChannel().getLink() != null) {
			return rss.getChannel().getLink().getText();
		} else {
			return null;
		}
	}

	public String getManagingEditor() {
		if (rss.getChannel().getManagingEditor() != null) {
			return rss.getChannel().getManagingEditor().getText();
		} else {
			return null;
		}
	}

	public String getPubDate() {
		if (rss.getChannel().getPubDate() != null) {
			return rss.getChannel().getPubDate().getText();
		} else {
			return null;
		}
	}

	public String getRating() {
		if (rss.getChannel().getRating() != null) {
			return rss.getChannel().getRating().getText();
		} else {
			return null;
		}
	}

	public String getSkipDays() {
		if (rss.getChannel().getSkipDays() != null) {
			return rss.getChannel().getSkipDays().getText();
		} else {
			return null;
		}
	}

	public String getTextInput() {
		if (rss.getChannel().getTextInput() != null) {
			return rss.getChannel().getTextInput().getText();
		} else {
			return null;
		}
	}

	public String getSkipHours() {
		if (rss.getChannel().getSkipHours() != null) {
			return rss.getChannel().getSkipHours().getText();
		} else {
			return null;
		}
	}

	public String getTitle() {
		if (rss.getChannel().getTitle() != null) {
			return rss.getChannel().getTitle().getText();
		} else {
			return null;
		}
	}

	public String getTtl() {
		if (rss.getChannel().getTtl() != null) {
			return rss.getChannel().getTtl().getText();
		} else {
			return null;
		}
	}

	public String getWebMaster() {
		if (rss.getChannel().getWebMaster() != null) {
			return rss.getChannel().getWebMaster().getText();
		} else {
			return null;
		}
	}

	public void setTitle(String title) {
		this.title = title;
		Title tt = new Title();
		tt.setText(title);
		rss.getChannel().setTitle(tt);
	}

	public void setLink(String link) {
		this.link = link;
		Link l = new Link();
		l.setText(link);
		rss.getChannel().setLink(l);
	}

	public void setDescription(String description) {
		this.description = description;
		Description d = new Description();
		d.setText(description);
		rss.getChannel().setDescription(d);
	}

	public void setLanguage(String language) {
		this.language = language;
		Language l = new Language();
		l.setText(language);
		rss.getChannel().setLanguage(l);
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
		Copyright c = new Copyright();
		c.setText(copyright);
		rss.getChannel().setCopyright(c);
	}

	public void setManagingEditor(String managingEditor) {
		this.managingEditor = managingEditor;
		ManagingEditor m = new ManagingEditor();
		m.setText(managingEditor);
		rss.getChannel().setManagingEditor(m);
	}

	public void setWebMaster(String webMaster) {
		this.webMaster = webMaster;
		WebMaster w = new WebMaster();
		w.setText(webMaster);
		rss.getChannel().setWebMaster(w);
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
		PubDate p = new PubDate();
		p.setText(pubDate);
		rss.getChannel().setPubDate(p);
	}

	public void setLastBuildDate(String lastBuildDate) {
		this.lastBuildDate = lastBuildDate;
		LastBuildDate l = new LastBuildDate();
		l.setText(lastBuildDate);
		rss.getChannel().setLastBuildDate(l);
	}

	public void setCategory(String category) {
		this.category = category;
		Category c = new Category();
		c.setText(category);
	}

	public void setGenerator(String generator) {
		this.generator = generator;
		Generator g = new Generator();
		g.setText(generator);
		rss.getChannel().setGenerator(g);
	}

	public void setDocs(String docs) {
		this.docs = docs;
	}

	/**
	 * Replace all occurrences of the characters &, ', ", < and > by the escaped
	 * characters &amp;, &quot;, &apos;, &lt; and &gt;
	 */
	private static final String XMLEscape(String s) {

		boolean contains = false;
		for (int i = 0; i < XML_ESCAPE_DELIMITERS.length(); i++) {
			if (s.indexOf(XML_ESCAPE_DELIMITERS.charAt(i)) != -1) {
				contains = true;
			}
		}

		if (!contains) {
			return s;
		}

		if ((s == null) || (s.length() == 0)) {
			return s;
		}

		StringTokenizer tokenizer = new StringTokenizer(s, XML_ESCAPE_DELIMITERS, true);
		StringBuffer result = new StringBuffer();

		while (tokenizer.hasMoreElements()) {
			String substring = tokenizer.nextToken();

			if (substring.length() == 1) {
				switch (substring.charAt(0)) {

				case '&':
					result.append("&amp;");
					break;

				// case '\'' :
				// result.append("&apos;");
				// break;

				case ';':
					result.append("\\;");
					break;

				case '<':
					result.append("&lt;");
					break;

				case '>':
					result.append("&gt;");
					break;

				case '\"':
					result.append("&quot;");
					break;

				// case '\n' :
				// result.append("\\n");
				// break;

				default:
					result.append(substring);
				}
			} else {
				result.append(substring);
			}
		}

		return result.toString();
	}

}
