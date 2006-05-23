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
package com.dexels.navajo.document.nanoimpl;

import java.io.*;

import com.dexels.navajo.document.Required;

/**
 * @author arjen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class RequiredImpl implements Required, NanoElement {

	private String message;
	private String filter;
	
	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.Required#setMethod(java.lang.String)
	 */
	public void setMessage(String s) {
		message = s;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.Required#getMethod()
	 */
	public String getMessage() {
		return message;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.Required#setFilter(java.lang.String)
	 */
	public void setFilter(String f) {
		filter = f;
	}

	/* (non-Javadoc)
	 * @see com.dexels.navajo.document.Required#getFilter()
	 */
	public String getFilter() {
		return filter;
	}
	
	 public final void fromXml(XMLElement e) {
	 	message = (String)e.getAttribute("message");
	    filter = (String)e.getAttribute("filter");
	  }

	 public final XMLElement toXml(XMLElement parent) {
	    XMLElement x = new CaseSensitiveXMLElement();
	    x.setName("required");
	    if (message != null ) {
	    	x.setAttribute("message",message);
	    }
	    if (filter != null) {
	    	x.setAttribute("filter", filter);
	    }
	    return x;
	  }

    public XMLElement toXml() {
        return toXml(null);
    }

    public final void writeComponent(Writer w) throws IOException {
        toXml().write(w);
    }
}
