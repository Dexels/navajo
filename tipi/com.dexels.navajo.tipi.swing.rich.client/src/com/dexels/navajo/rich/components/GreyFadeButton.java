/*
This file is part of the Navajo Project. 
It is subject to the license terms in the COPYING file found in the top-level directory of this distribution and at https://www.gnu.org/licenses/agpl-3.0.txt. 
No part of the Navajo Project, including this file, may be copied, modified, propagated, or distributed except according to the terms contained in the COPYING file.
*/
package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class GreyFadeButton extends JButton {
	private static final long serialVersionUID = -1398402298055285867L;
	private float start_factor = 1.0f;
	private float factor = 1.0f;
	private ImageIcon myIcon;
	private int reflectionSize = 15;
	private boolean mouseover = false;
	private boolean animating = false;

	public GreyFadeButton(URL imagepath, String text) {
		this(imagepath);
		setText(text);
	}

	public GreyFadeButton(URL imagepath) {
		myIcon = new ImageIcon(imagepath);
		setBorderPainted(false);
		setOpaque(false);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseover = true;
				if (!animating) {
					animate(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mouseover = false;
				if (!animating) {
					animate(false);
				}
				hideHint();
			}
		});

	}

	public void setIcon(ImageIcon icon) {
		myIcon = icon;
	}

	// Animation framework implies, scale range (0.0 - 1.0)
	public void setFactor(float f) {
		this.factor = f;
		repaint();
	}

	public float getFactor() {
		return factor;
	}

	@Override
	public Dimension getPreferredSize() {
		int width = myIcon.getIconWidth();
		int height = myIcon.getIconHeight() + reflectionSize;
		return new Dimension(width, height);
	}

	public void hideHint() {
		Component pane = ((RootPaneContainer) getTopLevelAncestor()).getGlassPane();

		if (pane != null && pane instanceof SpringGlassPane) {
			SpringGlassPane cc = (SpringGlassPane) pane;
			cc.hideHint();
		}
	}

	public void drawHint(String hint) {
		SpringGlassPane glassPane = new SpringGlassPane();
		((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(glassPane);
		SpringGlassPane cc = (SpringGlassPane) ((RootPaneContainer) getTopLevelAncestor()).getGlassPane();
		cc.setVisible(true);
		Rectangle bounds = getBounds();
		Point location = new Point(0, 0);
		location = SwingUtilities.convertPoint(this, location, ((RootPaneContainer) getTopLevelAncestor()).getRootPane());
		bounds.setLocation(location);
		cc.showHint(this, hint);
		cc.getAnimator().addTarget(new TimingTargetAdapter() {
			@Override
			public void end() {
				// animating = false;
			}
		});
	}

	public void animate(boolean in) {
		if (in) {
			Animator animator = PropertySetter.createAnimator(500, this, "factor", start_factor, 0.0f);
			animator.addTarget(new TimingTargetAdapter() {
				@Override
				public void end() {
					animating = false;
					if (!mouseover) {
						animate(false);
					} else {
						// drawHint(getText());
						// animating = true;
					}
				}
			});
			animator.setAcceleration(0.2f);
			animator.setDeceleration(0.2f);
			animator.start();
			animating = true;
		} else {
			Animator animator = PropertySetter.createAnimator(500, this, "factor", factor, start_factor);
			animator.addTarget(new TimingTargetAdapter() {
				@Override
				public void end() {
					animating = false;
					if (mouseover) {
						animate(true);
					}
				}
			});
			animator.setAcceleration(0.5f);
			animator.setDeceleration(0.2f);
			animator.start();
			animating = true;
		}
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (myIcon != null) {
			// System.err.printl

			Image image = myIcon.getImage();
			Graphics2D g2 = (Graphics2D) g;

			BufferedImage iconImage = new BufferedImage(myIcon.getIconWidth(), myIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D icG = iconImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			icG.drawImage(image, 0, 0, iconImage.getWidth(), iconImage.getHeight(), null);

			BufferedImage grey = GrayScaleTransform.getFadedImage(iconImage, factor);
			BufferedImage reflection = createReflection(grey);

			g2.drawImage(reflection, 0, 0, null);

		} else {
			super.paintComponent(g);
		}
	}

	private BufferedImage createReflection(BufferedImage img) {
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(img.getWidth(), (height + reflectionSize), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.scale(1.0, -1.0);
		g2.drawImage(img, 0, -height - height, null);
		g2.scale(1.0, -1.0);
		g2.translate(0, height);

		GradientPaint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, reflectionSize,
				new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		g2.fillRect(0, 0, img.getWidth(), reflectionSize);
		g2.dispose();
		return result;
	}
}
