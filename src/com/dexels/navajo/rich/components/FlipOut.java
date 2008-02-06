package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.transitions.Effect;

public class FlipOut extends Effect {
	
	public FlipOut(){
		
	}
	
  private float flipfactor = 0.0f;
	
  
  @Override	
	public void init(Animator anim, Effect parent_effect) {
		Effect targetEffect = (parent_effect == null)? this : parent_effect;
		PropertySetter ps = new PropertySetter(this, "flipfactor", 0.0f, 1.0f);
		anim.addTarget(ps);
		super.init(anim, targetEffect);
	}

	@Override
	public void paint(Graphics2D g) {
		super.paint(g);
		GradientPaint mask = new GradientPaint(0, 0, Color.black, getComponent().getWidth(), 0, new Color(1.0f, 1.0f, 1.0f, 0.0f));
    g.setPaint(mask);
    g.setComposite(AlphaComposite.SrcOver.derive(flipfactor));
    g.fillRect(0, 0, getComponent().getWidth(), getComponent().getHeight());
	}
	
	@Override
	public void setup(Graphics2D g){
		g.translate(flipfactor*getComponent().getWidth()/2, 0.0);
		g.scale(1.0-flipfactor, 1.0 - 0.2f*flipfactor);
    g.setComposite(AlphaComposite.SrcOver.derive(1.0f - 0.5f*flipfactor));
		super.setup(g);
	}
	
	public void setFlipfactor(float ff){
		this.flipfactor = ff;
//		repaint();
	}
	

}
