package com.dexels.navajo.rich.components;

import java.awt.*;

import javax.swing.*;

import org.jdesktop.animation.transitions.*;

public class DoubleSidedFrame extends JComponent {
	JComponent frontSide, backSide;
	ScreenTransition flipFront, flipBack;

	public DoubleSidedFrame(JComponent frameOne, JComponent frameTwo) {
		this.frontSide = frameOne;
		this.backSide = frameTwo;
		frontSide.setBackground(Color.red);
		frontSide.setOpaque(true);
		setLayout(new GridBagLayout());
		add(frontSide, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
				0), 0, 0));
		add(backSide, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
				0), 0, 0));

		flipFront = new ScreenTransition(frontSide, new TransitionTarget() {
			public void setupNextScreen() {
				frontSide.setVisible(!frontSide.isVisible());
			}
		}, 1500);

		flipBack = new ScreenTransition(backSide, new TransitionTarget() {
			public void setupNextScreen() {
				backSide.setVisible(!backSide.isVisible());
			}
		}, 1500);

	}

	public void flip() {
		flipFront.start();
		flipBack.start();
	}

}
