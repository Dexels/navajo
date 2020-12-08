/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package org.lobobrowser.html.renderer;

import java.awt.*;

import org.lobobrowser.html.*;

class UIControlWrapper implements UIControl {
	private final Component component;
	private final HtmlObject htmlObject;
	
	public UIControlWrapper(HtmlObject ho) {
		this.htmlObject = ho;
		Component c;
		if(ho == null) {
			c = new BrokenComponent();
		}
		else {
			c = ho.getComponent();
		}			
		this.component = c;
	}

	public void reset(int availWidth, int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}
	
	public Component getComponent() {
		return this.component;
	}
	
	public int getVAlign() {
		return RElement.VALIGN_BASELINE;
	}

	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	public Dimension getPreferredSize() {
		return this.component.getPreferredSize();
	}

	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
	}

	public boolean paintSelection(Graphics g, boolean inSelection,
			RenderableSpot startPoint, RenderableSpot endPoint) {
		// Does not paint selection
		return inSelection;
	}

	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}
	
	public void paint(Graphics g) {
		this.component.paint(g);
	}
}
