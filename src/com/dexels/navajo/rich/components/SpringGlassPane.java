package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

/**
 * 
 * @author Frank
 */
public class SpringGlassPane extends JComponent {
	private static final float MAGNIFY_FACTOR = 2.5f;

	private Rectangle bounds;
	private Image image;
  private boolean showHint = false;
	private float zoom = 0.0f;
	private float hintOpacity = 0.0f;
	public String hint = "";
	public Animator animator;
	private JComponent link;
	private int hint_width_inset = 5;
	private int hint_x_offset = 7; // left compensation, for centering
	private int hint_y_pos = 40; // distance from top of icon.
		
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		
		
		if (image != null && bounds != null) {
			int width = image.getWidth(this);
			width += (int) (image.getWidth(this) * MAGNIFY_FACTOR * getZoom());

			int height = image.getHeight(this);
			height += (int) (image.getHeight(this) * MAGNIFY_FACTOR * getZoom());

			int x = (bounds.width - width) / 2;
			int y = (bounds.height - height) / 2;

			
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

			g2.setComposite(AlphaComposite.SrcOver.derive(1.0f - getZoom()));
			g2.drawImage(image, x + bounds.x, y + bounds.y, width, height, null);
		}

		if(showHint){
			bounds = link.getBounds();
			Point location = new Point(0,0);
		  location = SwingUtilities.convertPoint(link, location, this);
		  bounds.setLocation(location);
			
		  Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12);
		  FontMetrics m = getFontMetrics(font);
	    int width = m.charsWidth(hint.toCharArray(), 0, hint.length());
		  
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.black);
			g2.setComposite(AlphaComposite.SrcOver.derive(hintOpacity));
		  
			int balloon_x = bounds.x + bounds.width/2 - width/2 - hint_x_offset;
			int balloon_width = width + 2*hint_width_inset;
			int balloon_center = balloon_x + balloon_width/2;
			int balloon_height = 20;
			
			int[] arrow_x = new int[3];
			int[] arrow_y = new int[3];
			
			arrow_x[0] = balloon_center - 5;
			arrow_x[1] = balloon_center + 5;
			arrow_x[2] = balloon_center;
		
			arrow_y[0] = bounds.y - hint_y_pos + balloon_height;
			arrow_y[1] = bounds.y - hint_y_pos + balloon_height;
			arrow_y[2] = bounds.y - hint_y_pos + balloon_height + 10;			
			
			g2.fillRoundRect(balloon_x , bounds.y-hint_y_pos, balloon_width, balloon_height, 15, 15);
			g2.fillPolygon(arrow_x, arrow_y, arrow_x.length);
		  g2.setColor(Color.white);
		  g2.setFont(font);
		  g2.setComposite(AlphaComposite.SrcOver.derive(Math.min(1.0f, 2.0f*hintOpacity)));
			g2.drawString(hint, balloon_x + hint_width_inset , bounds.y-hint_y_pos + font.getSize() + 3);
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
			g2.dispose();
		}
	}

	public void showSpring(Rectangle bounds, Image image) {
		this.bounds = bounds;
		this.image = image;
//		setVisible(true);

		animator = PropertySetter.createAnimator(350, this, "zoom", 0.0f, 1.0f);
		animator.setAcceleration(0.2f);
		animator.setDeceleration(0.4f);
		animator.start();
		animator.addTarget(new TimingTargetAdapter() {
			public void end() {
				setVisible(false);
				// getRootPane().setGlassPane(null);
			}

		});
		repaint();
	}
	
	public Animator getAnimator(){
		return animator;
	}
	
	public void hideHint(){
		animator = PropertySetter.createAnimator(100, this, "hintOpacity", 0.5f, 0.0f);
		animator.setAcceleration(0.2f);
		animator.setDeceleration(0.4f);
		animator.start();
		animator.addTarget(new TimingTargetAdapter(){
			public void end(){
				setVisible(false);
				hintOpacity = 0.0f;
			}
		});
		repaint();
	}
	
	public void showHint(JComponent img, String hint){
		this.showHint = true;
		this.link = img;
		this.hint = hint;

		animator = PropertySetter.createAnimator(350, this, "hintOpacity", 0.0f, 0.5f);
		animator.setAcceleration(0.2f);
		animator.setDeceleration(0.4f);
		animator.start();
		repaint();
	}

	public float getZoom() {
		return zoom;
	}

	public void setZoom(float zoom) {
		this.zoom = zoom;
		repaint();
	}
	
	public float getHintOpacity() {
		return hintOpacity;
	}

	public void setHintOpacity(float o) {
		this.hintOpacity = o;
		repaint();
	}
}
