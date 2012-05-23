package com.dexels.navajo.tipi.components.swingimpl.tipimegatable;

import java.util.Stack;

import javax.swing.JComponent;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.tipi.tipixml.CaseSensitiveXMLElement;
import com.dexels.navajo.tipi.tipixml.XMLElement;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public abstract class TipiTableBaseLayer {

	protected final TipiMegaTable myTable;
	protected String titleProperty = null;
	protected String messagePath = null;

	public TipiTableBaseLayer(TipiMegaTable tmt) {
		myTable = tmt;
	}

	public String toString() {
		return "Class: " + getClass().getName() + " Title: " + titleProperty
				+ " msgpath: " + messagePath;
	}

	public abstract int getCurrentSelection();

	public abstract void setCurrentSelection(int s);

	public void loadLayer(XMLElement elt) {
		titleProperty = elt.getStringAttribute("title");
		messagePath = elt.getStringAttribute("messagePath");
	}

	public void loadData(Navajo n, Message current,
			Stack<TipiTableBaseLayer> layerStack, JComponent currentPanel) {
	}

	public XMLElement store() {
		XMLElement xx = new CaseSensitiveXMLElement();
		xx.setName("layer");
		xx.setAttribute("messagePath", messagePath);
		xx.setAttribute("title", titleProperty);
		return xx;
	}

	public void updateLayer() {

	}

	public String getMessagePath() {
		return messagePath;
	}

}
