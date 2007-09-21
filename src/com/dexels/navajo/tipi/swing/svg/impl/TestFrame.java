package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import org.apache.batik.swing.*;

import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TestFrame extends JPanel {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final JFrame frame = new JFrame("Test");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 200);

		final JSVGCanvas tf = new JSVGCanvas();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(tf);
		URL resource = TestFrame.class.getClassLoader().getResource("boxes.svg");
		tf.setURI(resource.toString());
		frame.setVisible(true);
		
		
		

	}
}
