package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.apache.batik.swing.*;

import com.dexels.navajo.tipi.swing.svg.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

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
		final JSVGCanvas tf = new JSVGCanvas();

		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tf);
			URL resource = TestApplet.this.getClass().getClassLoader().getResource("boxes.svg");
				tf.setURI(resource.toString());

	}

}
