package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.animation.timing.*;
import org.jdesktop.animation.transitions.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.*;
import com.dexels.navajo.tipi.internal.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingFrameImpl extends JFrame implements TopLevel, TipiSwingFrame {
	// TipiContext c;

	BorderLayout borderLayout1 = new BorderLayout();

	// private TipiSwingDataComponentImpl me;

	public TipiSwingFrameImpl(TipiSwingDataComponentImpl me) {
		// final TipiSwingFrameImpl tsf = this;
		setVisible(false);
		getContentPane().setLayout(borderLayout1);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void setIconImage(ImageIcon i) {
		super.setIconImage(i.getImage());
	}

	public void setTipiMenubar(final TipiMenubar tm) {
		setJMenuBar((JMenuBar) tm.getContainer());
	}

	public void setModal(boolean b) {
		// ignored for frames
	}

	public void doActions(final TipiEvent te) {
//		SwingUtilities.invokeLater(new Runnable(){
//
//			public void run() {
//				JComponent jj = 
	//			System.err.println(">>>"+getClass());
				JComponent jjj = (JComponent) getContentPane().getComponent(0);
				Animator animator = new Animator(2500);
				ScreenTransition transition = new ScreenTransition(jjj,new TransitionTarget(){
					
					public void setupNextScreen() {
						try {
							for (int i = 0; i < te.getExecutableChildCount(); i++) {
								TipiExecutable current = te.getExecutableChild(i);
								current.performAction(te, te, i);
							}
						} catch (Throwable ex) {
							te.dumpStack(ex.getMessage());
							ex.printStackTrace();
						}
					}}, animator);
				animator.addTarget(new TimingTarget(){

					public void begin() {
						
					}

					public void end() {
						
					}

					public void repeat() {
						
					}

					public void timingEvent(float arg0) {
						System.err.println("Running: "+arg0); 
					
					}});
			    animator.setAcceleration(.5f);  // Accelerate for first 20%
			       animator.setDeceleration(.5f);  // Decelerate for last 40%
			       transition.start();
			}
//	});
		
//	}

}
