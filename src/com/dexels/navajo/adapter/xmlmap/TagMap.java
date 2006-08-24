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

import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.NavajoConfig;
import com.dexels.navajo.server.Parameters;
import com.dexels.navajo.server.UserException;

public class TagMap implements Mappable {

	public String name;
	public String text;
	public TagMap [] children;
	public TagMap child;
	public String childName;
	public String childText;
	public boolean exists;
	
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
		tags.put(t.getName(), t);
	}
	
	public boolean getExists(String name) {
		if ( tags != null ) {
			return tags.containsKey(name);
		} else {
			return false;
		}
	}
	
	public void setChildName(String s) {
		childName = s;
	}
	
	protected TagMap getChildTag(String s) {
		if ( tags != null ) {
			return (TagMap) tags.get(s);
		} else {
			return null;
		}
	}
	
	public void setChildText(String s) {
		TagMap t = getChild();
		t.setText(s);
	}
	
	public String getChildText() {
		TagMap t = getChild();
		return t.getText();
	}
	
	public TagMap getChild() {
		
		StringTokenizer childList = new StringTokenizer(childName, "/");
		TagMap child = this;
		
		while (childList.hasMoreTokens()) {
			String subChildName = childList.nextToken();
			child = (TagMap) child.getChildTag(subChildName);
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
	
	public String getString(int indent) {
		StringWriter sw = new StringWriter();
		sw.write(getSpaces(indent) + "<" + name + ">\n");
		if ( tags != null ) {
			Iterator all = tags.values().iterator();
			while ( all.hasNext() ) {
				TagMap c = (TagMap) all.next();
				String s = c.getString(indent + 2);
				sw.write(s);
			}
		} else {
			if ( text != null ) {
				sw.write( getSpaces(indent + 2) + text + "\n");
			}
		}
		sw.write(getSpaces(indent) + "</" + name + ">\n");
		return sw.toString();
	}
	
	protected static TagMap parseXMLElement(XMLElement e) throws UserException {
		
		TagMap t = new TagMap();
		
		String startName = e.getName();
		t.setName(startName);
		
		// parse children.
		Vector v = e.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = (XMLElement) v.get(i);
			TagMap childTag = parseXMLElement(child);
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
