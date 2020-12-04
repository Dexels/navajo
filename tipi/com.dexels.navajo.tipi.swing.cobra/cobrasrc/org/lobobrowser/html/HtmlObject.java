/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html;

import java.awt.*;

/**
 * This interface should be implemented to provide
 * OBJECT, EMBED or APPLET functionality.
 */
public interface HtmlObject {
	public Component getComponent();
	public void suspend();
	public void resume();
	public void destroy();
	
	/**
	 * Called as the object is layed out, either
	 * the first time it's layed out or whenever
	 * the DOM changes. This is where the object
	 * should reset its state based on element
	 * children or attributes and possibly change
	 * its preferred size if appropriate.
	 */
	public void reset(int availableWidth, int availableHeight);
}
