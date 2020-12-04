/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.rich.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;

import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;

public class DoubleSidedFrame extends JComponent {
	private static final long serialVersionUID = 4291340054525975327L;
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
			@Override
			public void setupNextScreen() {
				frontSide.setVisible(!frontSide.isVisible());
			}
		}, 1500);

		flipBack = new ScreenTransition(backSide, new TransitionTarget() {
			@Override
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
