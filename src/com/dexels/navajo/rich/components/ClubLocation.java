package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;

import javax.swing.*;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import com.dexels.navajo.rich.components.SpringGlassPane;

public class ClubLocation extends JButton {
	private float min_scale = 0.5f;
	private float scale = 0.5f;
	private float max_scale = 1.0f;
	private float zoom = 0.0f;
	private ImageIcon myKNVBIcon = new ImageIcon(getClass().getResource("images/pawn_glass_red.png"));
	private ImageIcon myNHVIcon = new ImageIcon(getClass().getResource("images/pawn_glass_green.png"));
	private ImageIcon myKNKVIcon = new ImageIcon(getClass().getResource("images/pawn_glass_yellow.png"));
	private ImageIcon myKNHBIcon = new ImageIcon(getClass().getResource("images/pawn_glass_blue.png"));
	private ImageIcon myIcon;
	private Dimension defaultSize = new Dimension(32, 32);
	private boolean mouseover = false;
	private boolean animating = false;
	private Point originalLocation = null;
	private String clubIdentifier = null;
	private double lat, lon;
	private String union;
	
	public ClubLocation(Point location, String union) {
		this.union = union;
		originalLocation = location;
		setBounds(originalLocation.x - (int) (0.5 * scale * defaultSize.width), originalLocation.y - (int) (0.5 * scale * defaultSize.height), (int) (scale * defaultSize.width), (int) (scale * defaultSize.height));

		setBorderPainted(false);
		setOpaque(false);
		
		if("nhv".equals(union)){
			myIcon = myNHVIcon;
		}else if("knkv".equals(union)){
			myIcon = myKNKVIcon;
		}else if("knhb".equals(union)){
			myIcon = myKNHBIcon;
		}else {
			myIcon = myKNVBIcon;
		}

		this.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				mouseover = true;
				if (!animating) {
					animate(true);
				}
			}

			public void mouseExited(MouseEvent e) {
				mouseover = false;
				if (!animating) {
					animate(false);
				}
				hideHint();
			}
		});

		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				fireSpring();
			}
		});
	}

	// Animation framework implies, scale range (0.0 - 1.0)
	public void setScale(float f) {
		this.scale = f;
		Rectangle bounds = getBounds();

		int lox = originalLocation.x - (int) (0.5 * scale * defaultSize.width);
		int loy = originalLocation.y - (int) (0.5 * scale * defaultSize.height);

		Rectangle newBounds = new Rectangle(lox, loy, (int) (scale * defaultSize.width), (int) (scale * defaultSize.height));
		this.setBounds(newBounds);
		revalidate();
	}

	public float getScale() {
		return scale;
	}

	public void hideHint() {
		Component pane = ((RootPaneContainer) getTopLevelAncestor()).getGlassPane();

		if (pane != null && pane instanceof SpringGlassPane) {
			SpringGlassPane cc = (SpringGlassPane) pane;
			cc.hideHint();
		}
	}

	public void drawHint(String hint) {
		try {
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
				public void end() {
					// animating = false;
				}
			});
		} catch (Exception e) {
		}
	}

	public void fireSpring() {
		SpringGlassPane glassPane = new SpringGlassPane();
		try {
			((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(glassPane);
			glassPane.setSize(((RootPaneContainer) getTopLevelAncestor()).getRootPane().getWidth(), ((RootPaneContainer) getTopLevelAncestor()).getRootPane().getHeight());

			SpringGlassPane cc = (SpringGlassPane) ((RootPaneContainer) getTopLevelAncestor()).getGlassPane();
			cc.setVisible(true);
			BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			paintComponent(bi.getGraphics());
			Rectangle bounds = getBounds();
			Point location = new Point(0, 0);
			location = SwingUtilities.convertPoint(this, location, ((RootPaneContainer) getTopLevelAncestor()).getRootPane());
			bounds.setLocation(location);
			cc.showSpring(bounds, bi);
		} catch (Exception e) {
		}
	}

	public void animate(boolean in) {
		if (in) {
			Animator animator = PropertySetter.createAnimator(250, this, "scale", scale, max_scale);
			animator.addTarget(new TimingTargetAdapter() {
				public void end() {
					animating = false;
					if (!mouseover) {
						animate(false);
					} else {
						drawHint(getText());
					}
				}
			});
			animator.setAcceleration(0.2f);
			animator.setDeceleration(0.2f);
			animator.start();
			animating = true;
		} else {
			Animator animator = PropertySetter.createAnimator(250, this, "scale", scale, min_scale);
			animator.addTarget(new TimingTargetAdapter() {
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

	public void paintComponent(Graphics g) {
		
		if (myIcon != null) {
			Rectangle bounds = this.getBounds();

			Image image = myIcon.getImage();
			Graphics2D g2 = (Graphics2D) g.create();

			BufferedImage iconImage = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D icG = iconImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			icG.drawImage(image, 0, 0, iconImage.getWidth(), iconImage.getHeight(), null);
			g2.drawImage(iconImage, 0, 0, null);
			g2.dispose();
		} 
	}

	public void setClubIdentifier(String clubId) {
		this.clubIdentifier = clubId;
	}

	public String getClubIdentifier() {
		return this.clubIdentifier;
	}

	public void setLatLonPosition(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return this.lat;
	}

	public double getLon() {
		return this.lon;
	}
	
	public String getUnion(){
		return this.union;
	}
}
