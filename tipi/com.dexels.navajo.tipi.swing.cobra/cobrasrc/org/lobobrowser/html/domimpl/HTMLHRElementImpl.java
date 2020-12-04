/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.domimpl;

import org.w3c.dom.html2.HTMLHRElement;

public class HTMLHRElementImpl extends HTMLAbstractUIElement implements HTMLHRElement {
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute("align");
	}

	public boolean getNoShade() {
		return "noshade".equalsIgnoreCase(this.getAttribute("noshade"));
	}

	public String getSize() {
		return this.getAttribute("size");
	}

	public String getWidth() {
		return this.getAttribute("width");
	}

	public void setAlign(String align) {
		this.setAttribute("align", align);
	}

	public void setNoShade(boolean noShade) {
		this.setAttribute("noshade", noShade ? "noshade" : null);
	}

	public void setSize(String size) {
		this.setAttribute("size", size);
	}

	public void setWidth(String width) {
		this.setAttribute("width", width);
	}
}
