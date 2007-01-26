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
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Pattern;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TagMap implements Mappable {

	public final String PREFIX_PATTERN   = "^[0-9]+";
	public final String PREFIX_SEPARATOR = "@";
	public final int    DEFAULT_INDENT   = 2;
	
	public String name;
	public String text;
	public Binary insert;
	public TagMap [] children;
	public TagMap child;
	public String childName;
	public String childText;
	public String childAttribute;
	public boolean exists;
	public int indent = DEFAULT_INDENT;
		
	protected HashMap tags = null;
	protected HashMap attributes = null;
	
	public void load(Parameters parms, Navajo inMessage, Access access,
			NavajoConfig config) throws MappableException, UserException {
	}

	public void setText(String t) {
		text = t;
	}
	
	public void setName(String s) {
		name = s;
	}
	
	public void setInsert(Binary b) throws UserException {
		insert = b;
		
		String insertChild = new String( b.getData() );

		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromReader( new StringReader( insertChild ) );
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// get child based on childName set
		TagMap child = getChild();
		
		child.setChild( TagMap.parseXMLElement( xe ) );
	}
	
	public String getName() {
		return name;
	}
	
	public String getText() {
		return text;
	}
	
	public void setChild(TagMap t) throws UserException {
		if ( tags == null ) {
			tags = new HashMap();
		}
		
		tags.put( ( 1 + tags.size() ) + this.PREFIX_SEPARATOR + t.getName(), t);
	}
	
	public boolean getExists(String name) {
		if ( tags != null ) {
			Iterator keys = tags.keySet().iterator();
			
			while ( keys.hasNext() ) {
				String key = (String) keys.next();
				
				if ( name.equals( key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" ) ) ) {
					return true;
				}
			}
		} else {
			return false;
		}
		
		return false;
	}
	
	public void setChildName(String s) {
		childName = s;
	}
	
	public void setIndent(int indent) {
		this.indent = ( indent > 0 ) ? indent : this.DEFAULT_INDENT;
	}

	protected TagMap getChildTag(String s, int index) {

		if ( tags != null ) {
			Iterator keys = tags.keySet().iterator();
			
			while ( keys.hasNext() ) {
				String key = (String) keys.next();
				
				Pattern pattern = Pattern.compile(s);
				int count = 0;
				if (  pattern.matcher(key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" )).matches()  && count == index ) {
					return (TagMap) tags.get( key );
				} else if ( s.equals( key.replaceFirst( this.PREFIX_PATTERN + this.PREFIX_SEPARATOR, "" ) ) ) {
					count++;
				}
			}

			return null;
		} else {
			return null;
		}
	}
	
	public void setChildText(String s) {
		TagMap t = getChild();
		t.setText(s);
	}
	
	public String getAttribute(String a) {
		if ( attributes.get(a) != null ) {
			return (String) attributes.get(a);
		}
		return null;
	}
	
	public String getChildAttribute(String a) {
		TagMap t = getChild();
		return t.getAttribute(a);
	}
	
	public String getChildAttribute(String child, String a) {
		setChildName(child);
		TagMap t = getChild();
		return t.getAttribute(a);
	}
	
	public String getChildText() {
		TagMap t = getChild();
		return t.getText();
	}
	
	public String getChildText(String child) {
		setChildName(child);
		return getChildText();
	}
	
	public TagMap getChild() {
		
		StringTokenizer childList = new StringTokenizer(childName, "/");
		TagMap child = this;
		
		while (childList.hasMoreTokens()) {
			String subChildName = childList.nextToken();
			StringTokenizer indexSpecifier = new StringTokenizer(subChildName, "@");
			int index = 0;
			if ( indexSpecifier.hasMoreTokens() ) {
				subChildName = indexSpecifier.nextToken();
			}
			if ( indexSpecifier.hasMoreTokens() ) {
				index = Integer.parseInt(indexSpecifier.nextToken());
			}
			
			child = (TagMap) child.getChildTag(subChildName, index);
		}
		
		return child;
	}
	
	public TagMap [] getChildren() {
		Collection c = tags.values();
		children = new TagMap[c.size()];
		children = (TagMap []) c.toArray(children);
		return children;
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
			Iterator attrib_keys = attributes.keySet().iterator();
			
			while ( attrib_keys.hasNext() ) {
				String key = (String) attrib_keys.next();
				String value = (String) attributes.get( key );
				sw.write( " " + key + "=\"" + value + "\"" );
			}
		}
		
		sw.write(">\n");
		
		if ( tags != null ) {
			Iterator all = tags.values().iterator();
			while ( all.hasNext() ) {
				TagMap c = (TagMap) all.next();
				String s = c.getString(indent + tabsize, tabsize);
				sw.write(s);
			}
		} else {
			if ( text != null ) {
				sw.write( getSpaces(indent + tabsize) + text + "\n");
			}
		}
		sw.write(getSpaces(indent) + "</" + name + ">\n");
		return sw.toString();
	}
	
	protected static TagMap parseXMLElement(XMLElement e) throws UserException {
		
		TagMap t = new TagMap();
		
		String startName = e.getName();
		t.setName(startName);
		
		// parse attributes
        Enumeration attrib_enum = e.enumerateAttributeNames();
        while ( attrib_enum.hasMoreElements() ) {
            String key = (String) attrib_enum.nextElement();
            String value = e.getStringAttribute(key);
			if ( t.attributes == null ) {
				t.attributes = new HashMap ();
			}
			t.attributes.put( key, value );
        }

		// parse children
		Vector v = e.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = (XMLElement) v.get(i);
			
			TagMap childTag = parseXMLElement(child);
			t.setChild(childTag);
		}
		
		// Check for text node.
		if ( e.getContent() != null && !e.getContent().equals("")) {
			//System.err.println("Setting content: " + e.getContent() + " for tag " + t );
			t.setText(e.getContent());
		}
		
		return t;
		
	}
	
	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}

}
