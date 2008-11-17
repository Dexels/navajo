package com.dexels.navajo.tipi.swingimpl.dnd.test;

import javax.swing.*;

import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class StringDropButton extends JButton implements TipiDroppable {
	private String myDropCategory;

	public StringDropButton(String text, String category) {
		super(text);
		myDropCategory = category;
		setTransferHandler(new TipiTransferHandler());
	}

	public void fireDropEvent(Object o) {
		setText("Drop" + (String) o);
	}

	public boolean acceptsDropCategory(String category) {
		return category.equals(myDropCategory);
	}

}
