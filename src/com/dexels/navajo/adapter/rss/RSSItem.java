package com.dexels.navajo.adapter.rss;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;
import com.sun.cnpi.rss.elements.Author;
import com.sun.cnpi.rss.elements.Category;
import com.sun.cnpi.rss.elements.Comments;
import com.sun.cnpi.rss.elements.Description;
import com.sun.cnpi.rss.elements.Enclosure;
import com.sun.cnpi.rss.elements.Guid;
import com.sun.cnpi.rss.elements.Link;
import com.sun.cnpi.rss.elements.PubDate;
import com.sun.cnpi.rss.elements.Source;
import com.sun.cnpi.rss.elements.Title;

public class RSSItem implements Mappable {

	public String title;
	public String description;
	public String link;
	public String pubDate;
	public String category;
	public String author;
	public String comments;
	public String guid;
	public String source;
	public String enclosure;
	public Binary enclosureBinary;

	public RSSItem() {
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}

	public String getCategory() {
		return category;
	}
	
	public Category getItemCategory() {
		if ( category == null ) {
			return null;
		}
		Category c = new Category();
		c.setText(category);
		return c;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Description getItemDescription() {
		if ( description == null ) {
			return null;
		}
		Description c = new Description();
		c.setText(description);
		return c;
	}
	
	public String getLink() {
		return link;
	}
	
	public Link getItemLink() {
		if ( link == null ) {
			return null;
		}
		Link c = new Link();
		c.setText(link);
		return c;
	}
	
	public String getPubDate() {
		return pubDate;
	}
	
	public PubDate getItemPubDate() {
		if ( pubDate == null ) {
			return null;
		}
		PubDate c = new PubDate();
		c.setText(pubDate);
		return c;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Title getItemTitle() {
		if ( title == null ) {
			return null;
		}
		Title c = new Title();
		c.setText(title);
		return c;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public Author getItemAuthor() {
		if ( author == null ) {
			return null;
		}
		Author a = new Author();
		a.setText(author);
		return a;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getComments() {
		return comments;
	}
	
	public Comments getItemComments() {
		if ( comments == null ) {
			return null;
		}
		Comments c = new Comments();
		c.setText(comments);
		return c;
	}
	
	public String getEnclosure() {
		return enclosure;
	}
	
	public void setEnclosure(String s) {
		this.enclosure = s;
	}
	
	public Enclosure getItemEnclosure() {
		if ( enclosure == null ) {
			return null;
		}
		Enclosure c = new Enclosure();
		c.setText(enclosure);
		return c;
	}
	
	public Binary getEnclosureBinary() {
		try {
			return new Binary( new URL(enclosure).openStream() );
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getGuid() {
		return guid;
	}
	
	public Guid getItemGuid() {
		if ( guid == null ) {
			return null;
		}
		Guid c = new Guid();
		c.setText(guid);
		return c;
	}
	
	public String getSource() {
		return source;
	}
	
	public Source getItemSource() {
		if ( source == null ) {
			return null;
		}
		Source c = new Source();
		c.setText(source);
		return c;
	}
	
	public void setSource(String source) {
		this.source = source;
	}

}
