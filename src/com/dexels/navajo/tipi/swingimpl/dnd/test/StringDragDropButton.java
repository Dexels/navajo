package com.dexels.navajo.tipi.swingimpl.dnd.test;

import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class StringDragDropButton extends JButton implements TipiDraggable, TipiDroppable {
	private String myDragCategory;
	private String myDropCategory;

	public StringDragDropButton(String text, String dragCategory, String dropCategory) {
		super(text);
		myDragCategory = dragCategory;
		myDropCategory = dropCategory;
		setTransferHandler(new TipiTransferHandler());
		MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent)e.getSource();
                TransferHandler th = c.getTransferHandler();
                th.exportAsDrag(c, e, TransferHandler.COPY);
            }
        };
        addMouseListener(ml);
	}

	public Object getDragValue() {
//		System.err.println("Getting drag: "+getText());
		return getText();
	}


	public void fireDropEvent(Object o) {
		setText("Drop"+(String)o);
	}

	public boolean acceptsDropCategory(String category) {
		return category.equals(myDropCategory);
	}

	public String getDragCategory() {
		return myDragCategory;
	}

}
