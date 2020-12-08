/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.extras.app.menu.OptionModel;

import com.dexels.navajo.tipi.TipiComponent;

public class EchoMutableObjectModel implements OptionModel {

	protected String text;
//	protected String id;
	protected ImageReference image;
	protected final TipiComponent myComponent;
	
	public EchoMutableObjectModel(TipiComponent parent) {
		myComponent = parent;
	}
	
	public ImageReference getImage() {
		return image;
	}

	public void setIcon(ImageReference image) {
		this.image = image;
	}

	public void setId(String id) {
	}

	public void setText(String text) {
		this.text = text;
	}

	public ImageReference getIcon() {
		return image;
	}

	public String getText() {
		return text;
	}

	public String getId() {
		return myComponent.getPath();
	}

}
