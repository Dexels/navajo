package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

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
		frame.setSize( 800, 600);
		frame.setVisible(true);
		final SvgBaseComponent tf = new SvgBatikComponent();

		final JPanel panel = new JPanel(new BorderLayout());

		frame.getContentPane().add(panel);
		panel.setLayout(new BorderLayout());
		panel.add(tf, BorderLayout.CENTER);

		frame.repaint();
		//frame.pack();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				System.exit(0);
			}
		});
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tf.init(TestFrame.class.getClassLoader().getResource(
						"dexels.svg"));
				frame.dispatchEvent(new ComponentEvent(frame,ComponentEvent.COMPONENT_RESIZED));
				

			}
		});
	}

}
