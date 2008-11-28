package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.TipiComponent;

import nextapp.echo2.app.ImageReference;
import nextapp.echo2.extras.app.menu.OptionModel;

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
