/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.domimpl;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.html2.*;

public class HTMLOptionsCollectionImpl extends DescendentHTMLCollection implements HTMLOptionsCollection {
	public static final NodeFilter OPTION_FILTER = new OptionFilter();
	
	public HTMLOptionsCollectionImpl(HTMLElementImpl selectElement) {
		super(selectElement, OPTION_FILTER, selectElement.treeLock, false);
	}
	
	public void setLength(int length) throws DOMException {
		throw new UnsupportedOperationException();
	}

	private static class OptionFilter implements NodeFilter {
		public boolean accept(Node node) {
			return node instanceof HTMLOptionElement;
		}
	}
}
