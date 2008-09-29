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
package com.dexels.navajo.adapter;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import com.dexels.navajo.adapter.xmlmap.TagMap;
import com.dexels.navajo.document.nanoimpl.CaseSensitiveXMLElement;
import com.dexels.navajo.document.nanoimpl.XMLElement;
import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.mapping.Mappable;
import com.dexels.navajo.mapping.MappableException;
import com.dexels.navajo.server.Access;
import com.dexels.navajo.server.UserException;

public class XMLMap extends TagMap implements Mappable {
	
	public String start = null;
	public Binary content = null;
	public String stringContent = null;
	public boolean debug = false;
	
	public void load(Access access) throws MappableException, UserException {
	}
	
	public void store() throws MappableException, UserException {
	}

	public void kill() {
	}

	public void setDebug(boolean b) {
		debug = b;
	}
	
	public void setStart(String name) {
		this.name = name;
	}
	
	public String getString() {
		if ( this.name != null ) {
			return getString( 0, this.indent );
		} else {
			return null;
		}
	}
	
	public void setChild(TagMap t) throws UserException {
		if ( this.name != null ) {
			super.setChild(t);
		} else {
			throw new UserException(-1, "No start tag defined.");
		}
	}
		
	public Binary getContent() {
		String r = getString();
		Binary b = new Binary(r.getBytes());
		if ( debug ) {
			System.err.println(new String(b.getData()));
		}
		return b;
	}
	
	protected void parseXML(XMLElement e) throws UserException {
		
		String startName = e.getName();
		this.setName(startName);

		// parse attributes
        Iterator<String> attrib_enum = e.enumerateAttributeNames();
        while ( attrib_enum.hasNext()) {
        	String key = attrib_enum.next();
            String value = e.getStringAttribute(key);
			if ( this.attributes == null ) {
				this.attributes = new HashMap ();
			}
			this.attributes.put( key, value );
        }
        
		// parse children.
		Vector v = e.getChildren();
		for (int i = 0; i < v.size(); i++) {
			XMLElement child = (XMLElement) v.get(i);
			
			TagMap childTag = TagMap.parseXMLElement(child);
			this.setChild(childTag);
		}
		
		// Check for text node.
		if ( e.getContent() != null && !e.getContent().equals("")) {
			this.setText(e.getContent());
		}
	}

	private void parseString(String s) throws UserException {
			
		XMLElement xe = new CaseSensitiveXMLElement();
		try {
			xe.parseFromReader(new StringReader(s));
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		this.name = xe.getName();
		
		parseXML(xe);
	}

	public void setContent(Binary b) throws UserException {
		String stringContent = new String(b.getData());
		setStringContent(stringContent);
	}
	
	public void setStringContent(String s) throws UserException {
		parseString(s);
	}
	
	public static void main(String [] args) throws Exception {
		XMLMap xml = new XMLMap();
		xml.setStart("xml");
		TagMap district = new TagMap();
		district.setName("district");
		xml.setChild(district);
		district.setName("DISTRIKTJE");
		district.setName("WERKT DIT NOG STEEDS");
		System.err.println("child = " + xml.getChildTag("district", 0));
		
		TagMap n = new TagMap();
		n.setName("apenoot");
		n.setText("Achterlijke");
		district.setChild(n);
		
		Binary b = xml.getContent();
		
		xml.setChildName("district");
		TagMap kind = xml.getChild();
		System.err.println("kind = " + kind.getName());
		
		b.write(System.err);
		
	}
}
