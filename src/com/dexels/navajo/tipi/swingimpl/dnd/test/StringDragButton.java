package com.dexels.navajo.tipi.swingimpl.dnd.test;

import java.awt.event.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class StringDragButton extends JButton implements TipiDraggable {

	private String myCategory;

	public StringDragButton(String text,String category) {
		super(text);
		myCategory = category;
		setTransferHandler(new TipiTransferHandler());
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent c = (JComponent)e.getSource();
                TransferHandler th = c.getTransferHandler();
                System.err.println("GOOOOO: "+th);
                th.exportAsDrag(c, e, TransferHandler.COPY);
            }
        });
	}

	public Object getDragValue() {
		return getText();
	}

	public String getDragCategory() {
		return myCategory;
	}

	


	
}
