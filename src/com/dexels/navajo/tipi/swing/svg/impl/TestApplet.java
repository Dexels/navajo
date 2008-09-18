package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

import org.apache.batik.swing.*;

public class TestApplet extends JApplet {
//	protected JSVGCanvas svgCanvas = null;
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	
	}

	public void init() {
		// TODO Auto-generated method stub
		super.init();
		JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		final JSVGCanvas tf = new JSVGCanvas();
		sp.add(tf);
		sp.add(new JLabel("Shslalalala"));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(sp);
			URL resource = TestApplet.this.getClass().getClassLoader().getResource("Orc.svg");
			tf.setURI(resource.toString());

	}

}
