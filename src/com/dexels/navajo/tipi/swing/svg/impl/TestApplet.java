package com.dexels.navajo.tipi.swing.svg.impl;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.tipi.swing.svg.*;
import com.dexels.navajo.tipi.swing.svg.impl.*;

public class TestApplet extends JApplet {

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
		final SvgBaseComponent tf = new SvgBatikComponent();

		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tf);
		repaint();
		
		tf.addSvgMouseListener(new SvgMouseListener(){

			public void onClick(String targetId) {
				System.err.println("click");
			}

			public void onMouseDown(String targetId) {
				
			}

			public void onMouseOut(String targetId) {
				
			}

			public void onMouseOver(String targetId) {
				// TODO Auto-generated method stub
				
			}

			public void onMouseUp(String targetId) {
				// TODO Auto-generated method stub
				
			}

			public void onMouseMove(String targetId) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onActivate(String targetId) {
				// TODO Auto-generated method stub
				
			}});
		
		
		tf.addSvgAnimationListener(new SvgAnimationListener(){

			public void onAnimationEnded(String animationId, String targetId) {
				// TODO Auto-generated method stub
//				System.err.println("Ended");
			}

			public void onAnimationStarted(String animationId, String targetId) {
//				System.err.println("Started");
				
			}});
		
		//frame.pack();
	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				tf.init(TestApplet.class.getClassLoader().getResource(
						"dexels.svg"));
			//	frame.dispatchEvent(new ComponentEvent(frame,ComponentEvent.COMPONENT_RESIZED));
				

			}
		});
	}

}
