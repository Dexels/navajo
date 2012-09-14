package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiExecutable;
import com.dexels.navajo.tipi.components.swingimpl.TipiMenubar;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.components.swingimpl.TopLevel;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiSwingFrameImpl extends JFrame implements TopLevel,
		TipiSwingFrame {
	// TipiContext c;
	private static final long serialVersionUID = -8378198812937375585L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingFrameImpl.class);
	
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
		// SwingUtilities.invokeLater(new Runnable(){
		//
		// public void run() {
		// JComponent jj =
		// logger.debug(">>>"+getClass());
		JComponent jjj = (JComponent) getContentPane().getComponent(0);
		Animator animator = new Animator(2500);
		ScreenTransition transition = new ScreenTransition(jjj,
				new TransitionTarget() {

					public void setupNextScreen() {
						try {
							// TODO I think this isn't correct, no conditions are checked, right?
							for (int i = 0; i < te.getExecutableChildCount(); i++) {
								TipiExecutable current = te
										.getExecutableChild(i);
								current.performAction(te, te, i);
							}
						} catch (Throwable ex) {
							te.dumpStack(ex.getMessage());
							logger.error("Error detected",ex);
						}
					}
				}, animator);
		animator.addTarget(new TimingTarget() {

			public void begin() {

			}

			public void end() {

			}

			public void repeat() {

			}

			public void timingEvent(float arg0) {
				logger.debug("Running: " + arg0);

			}
		});
		animator.setAcceleration(.5f); // Accelerate for first 20%
		animator.setDeceleration(.5f); // Decelerate for last 40%
		transition.start();
	}
	// });

	// }

}
