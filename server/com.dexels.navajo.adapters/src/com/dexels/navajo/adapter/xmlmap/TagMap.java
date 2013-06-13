/**
 * <p>Title: Navajo Product Project</p>
 * <p>Description: This is the official source for the Navajo server</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Dexels BV</p>
 * @author 
 * @version $Id$.
 *
 * DISCLAIMER
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL DEXELS BV OR ITS CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package com.dexels.navajo.adapter.xmlmap;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.jaxpimpl.xml.XMLutils;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.script.api.Access;
import com.dexels.navajo.script.api.Mappable;
import com.dexels.navajo.script.api.MappableException;
import com.dexels.navajo.script.api.UserException;

public class TagMap implements Mappable {

	
	private final static Logger logger = LoggerFactory.getLogger(TagMap.class);
	
	public final String PREFIX_PATTERN   = "^[0-9]+";
	public final String PREFIX_SEPARATOR = "@";
	public final int    DEFAULT_INDENT   = 2;

	public String     name = "unknown";
	public String     text;
	public String     cdataText;
	public String     attributeText;

	public Binary     insert;
	public TagMap     [] children;
	public TagMap     child;

	public String     childName = "";
	public String     attributeName;
	public String     childText;

	public boolean    exists;
	public boolean    compact      = false;

	public int          indent     = DEFAULT_INDENT;

	protected Map<String,TagMap>   tags       = null;
	protected Map<String,String>   attributes = null;

	protected List<String> tagList    = null;

	protected int tagsIndex = -1;
	protected int tagListIndex = -1;
	private TagMap parent = null;

	private static Random rand = new Random(System.currentTimeMillis());

	public TagMap() {
		// Set random tag name first.
		name = name + rand.nextInt();
	}

	public void load(Access access) throws MappableException, UserException {
	}

	public void setText(String t) {
		text = XMLutils.XMLEscape( t );
	}
	
	public void setCdataText(String t) {
		text = "<![CDATA[ " + t + " ]]>"; 
	}

	public void setAttributeText(String t) throws UserException {

		TagMap child = this.getChild();

		// attribute name must be known
		if ( child.attributeName == null ) {
			throw new UserException(-1, "Set attributeName before calling attributeText");
		}

		// create attributes if not already present
		if ( child.attributes == null ) {
			child.attributes = new HashMap<String,String> ();
		}

		child.attributes.put( child.attributeName, XMLutils.XMLEscape( t ) );
	}

	public void setName(String s) {
		// Remove previous name from tags and tagList.

		if ( parent != null ) {
			parent.tagList.remove( parent.tagListIndex + this.PREFIX_SEPARATOR + this.getName() );
			parent.tags.remove( parent.tagsIndex + this.PREFIX_SEPARATOR + this.getName() );
		}

		// Set new name and inser into tags and tagList structures.
		name = s;
		if ( parent != null ) {
			parent.tags.put(parent.tagsIndex + this.PREFIX_SEPARATOR + this.getName(), this);
			parent.tagList.add( parent.tagListIndex + this.PREFIX_SEPARATOR + this.getName() );
		}
	}

	public void setInsert(Binary b) throws UserException {
		insert = b;

		String insertChild = new String( b.getData() );

		XMLElement xe = new CaseSensitiveXMLElement(true);

		try {
			xe.parseFromReader( new StringReader( insertChild ) );
		}
		catch (IOException ex) {
			logger.error("XML parse problem: ", ex);
		}

		// get child based on childName set
		TagMap child = getChild();

		child.setChild( TagMap.parseXMLElement( xe, this.compact ) );
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public String getAttributeText() {
		return attributeText;
	}

	/**
	 * @throws UserException  
	 */
	public void setChild(TagMap t) throws UserException {
		if ( tags == null ) {
			tags    = new HashMap<String,TagMap>();
			tagList = new ArrayList<String>();
		}
		t.parent = this;
		tagsIndex = 1 + tags.size();
		tags.put( tagsIndex + this.PREFIX_SEPARATOR + t.getName(), t);
		tagListIndex = 1 + tagList.size();
		tagList.add( tagListIndex + this.PREFIX_SEPARATOR + t.getName() );
	}

	public boolean getExists(String name) {
		if ( tags != null ) {
			Iterator<String> keys = tags.keySet().iterator();

			while ( keys.hasNext() ) {
				String key =  keys.next();

				if ( name.equals( key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" ) ) ) {
					return true;
				}
			}
		}

		return false;
	}

	public void setChildName(String s) {
		childName = s;
	}

	public void setAttributeName(String s) throws UserException {

		TagMap child = this.getChild();

		if ( child != null ) {
			child.attributeName = s;
		} else {
			throw new UserException(-1, "Could not find XML child: " + s);
		}
	}

	public void setIndent(int indent) {
		this.indent = ( indent > 0 ) ? indent : this.DEFAULT_INDENT;
	}

	public void setCompact(boolean c) {
		this.compact = c;
	}

	protected TagMap getChildTag(String s, int index) {

		if ( tags != null ) {
			Iterator<String> keys = tags.keySet().iterator();

			while ( keys.hasNext() ) {
				String key = keys.next();

				Pattern pattern = Pattern.compile(s);
				int count = 0;
				if (  pattern.matcher(key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" )).matches()  && count == index ) {
					return tags.get( key );
				} else if ( s.equals( key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" ) ) ) {
					count++;
				}
			}
		}

		return null;
	}

	public void setChildText(String s) throws UserException {
		TagMap t = getChild();
		t.setText( XMLutils.XMLEscape( s ) );
	}

	public String getAttribute(String a) {
		if ( attributes.get(a) != null ) {
			return attributes.get(a);
		}
		return null;
	}

	public String getChildAttribute(String a) throws UserException {
		TagMap t = getChild();
		return t.getAttribute(a);
	}

	public String getChildAttribute(String child, String a) throws UserException {
		setChildName(child);
		TagMap t = getChild();
		return t.getAttribute(a);
	}

	public String getChildText() throws UserException {
		TagMap t = getChild();
		return t.getText();
	}

	public String getChildText(String child) throws UserException {
		setChildName(child);
		return getChildText();
	}

	public TagMap getChild(boolean createIfNotFound) throws UserException {

		if ( childName == null ) {
			throw new UserException(-1, "Specify child name first.");
		}
		
		StringTokenizer childList = new StringTokenizer(childName, "/");
		TagMap child = this;

		while (childList.hasMoreTokens() && child != null) {
			String subChildName = childList.nextToken();
			StringTokenizer indexSpecifier = new StringTokenizer(subChildName, "@");
			int index = 0;
			if ( indexSpecifier.hasMoreTokens() ) {
				subChildName = indexSpecifier.nextToken();
			}
			if ( indexSpecifier.hasMoreTokens() ) {
				index = Integer.parseInt(indexSpecifier.nextToken());
			}
			TagMap parent = child;
			child = child.getChildTag(subChildName, index);
			if ( child == null && createIfNotFound ) {
				child = new TagMap();
				child.setName(subChildName);
				parent.setChild(child);
			}
		}

		return child;
	}
	
	public boolean getChildExists() throws UserException {
		
		child = getChild(false);		
		return ( child != null );
		
	}
	
	public TagMap getChild() throws UserException {

		return getChild(true);

	}

	public TagMap [] getChildren() {

		children = new TagMap[tagList.size()];

		for ( int i = 0; i < tagList.size(); i++) {
			children[i] = tags.get( tagList.get(i) );
		}

		return children;
	}

	public void setChildren(TagMap [] all) throws UserException  {
		for (int i = 0; i < all.length; i++) {
			setChild(all[i]);
		}
	}

	private String getSpaces(int indent) {
		StringWriter sw = new StringWriter();
		for (int i = 0; i < indent; i++) {
			sw.write(" ");
		}
		return sw.toString();
	}

	public String getString(int indent, int tabsize) {
		StringWriter sw = new StringWriter();
		sw.write(getSpaces(indent) + "<" + name);

		if ( attributes != null ) {
			Iterator<String> attrib_keys = attributes.keySet().iterator();

			while ( attrib_keys.hasNext() ) {
				String key = attrib_keys.next();
				String value = attributes.get( key );
				sw.write( " " + key + "=\"" + value + "\"" );
			}
		}

		sw.write(">" + ( ( compact && tags == null && text != null ) ? "" : "\n" ) );

		if ( tags != null ) {
			for ( int i = 0; i < tagList.size(); i++ ) {
				TagMap c = tags.get( tagList.get( i ) );
				String s = c.getString(indent + tabsize, tabsize);
				sw.write(s);
			}
		} else {
			if ( text != null ) {
				sw.write( ( ( compact ) ? "" : getSpaces(indent + tabsize) ) + text + ( ( compact ) ? "" : "\n" ) );
			}
		}

		sw.write( ( ( compact && text != null ) ? "" : getSpaces(indent) ) + "</" + name + ">\n");

		return sw.toString();
	}

	protected static TagMap parseXMLElement(XMLElement e) throws UserException {
		return parseXMLElement( e, false );
	}

	protected static TagMap parseXMLElement(XMLElement e, boolean compact ) throws UserException {

		TagMap t = new TagMap();

		String startName = e.getName();
		t.setName( startName );
		t.setCompact( compact );

		// parse attributes
		Iterator<String> attrib_enum = e.enumerateAttributeNames();
		while ( attrib_enum.hasNext() ) {
			String key = attrib_enum.next();
			String value = e.getStringAttribute(key);
			if ( t.attributes == null ) {
				t.attributes = new HashMap<String,String> ();
			}
			t.attributes.put( key, value );
		}

		// parse children
		Vector<XMLElement> v = e.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = v.get(i);

			TagMap childTag = parseXMLElement( child, compact );

			t.setChild(childTag);
		}

		// Check for text node.
		if ( e.getContent() != null && !e.getContent().equals("")) {
			t.setText(e.getContent());
		}

		return t;
	}

	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}
}
