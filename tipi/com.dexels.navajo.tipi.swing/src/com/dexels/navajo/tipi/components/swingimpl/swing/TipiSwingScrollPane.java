/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
/*
 * Created on Mar 29, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Dimension;

import javax.swing.JScrollPane;

public class TipiSwingScrollPane extends JScrollPane {

	// private JComponent scrollingComponent = null;
	//
	// private Dimension checkMax(Dimension preferredSize, Dimension
	// maximumSize) {
	// // Dimension maximumSize = getMaximumSize();
	// if (maximumSize==null) {
	// return preferredSize;
	// }
	// return new Dimension(Math.min(preferredSize.width,
	// maximumSize.width),Math.min(preferredSize.height, maximumSize.height));
	// }
	// private Dimension checkMin(Dimension preferredSize, Dimension
	// minimumSize) {
	// // Dimension minimumSize = getMinimumSize();
	// if (minimumSize==null) {
	// return preferredSize;
	// }
	// return new Dimension(Math.max(preferredSize.width,
	// minimumSize.width),Math.max(preferredSize.height, minimumSize.height));
	// }
	//
	// public Dimension checkMaxMin(Dimension preferredSize, Dimension
	// maximumSize, Dimension minimumSize) {
	// return checkMin(checkMax(preferredSize,maximumSize),minimumSize);
	// }

	/**
	 * 
	 */
	private static final long serialVersionUID = -5967052777735493708L;

	public Dimension getMinumumSize() {
		return getPreferredSize();
	}

	// public Dimension getPreferredSize() {
	// return checkMaxMin(getViewport().getPreferredSize(),
	// getViewport().getMaximumSize(), getViewport().getMaximumSize());
	// }
	// public void setPreferredSize(Dimension d) {
	// logger.debug("SETPREF. in scroller called.: "+d);
	// Thread.dumpStack();
	// }

	// public void setScrollingComponent(JComponent c) {
	// scrollingComponent = c;
	// getViewport().add(c);
	//
	// }
}
