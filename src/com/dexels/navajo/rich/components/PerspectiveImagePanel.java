package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.File;
import java.nio.Buffer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;


public class PerspectiveImagePanel extends JPanel {
	BufferedImage img, img1, img2;
	public int angle = 0;
	private int previousAngle;
	private BufferedImage currentImg;
	private boolean direction = false;
	public static Animator a;
	private JComponent c1, c2, set_visible, parent;
	private int speed = 500;
	
	public void setComponents(JComponent c1, JComponent c2){
		this.c1 = c1;
		this.c2 = c2;
			
		BufferedImage b1 = new BufferedImage(c1.getWidth(), c1.getHeight(), BufferedImage.TYPE_INT_ARGB);
		BufferedImage b2 = new BufferedImage(c2.getWidth(), c2.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D b1g = b1.createGraphics();
		Graphics2D b2g = b2.createGraphics();
		
		c1.paint(b1g);
		b1g.dispose();
		c1.setVisible(false);
		c2.setVisible(true);
		c2.paintAll(b2g);
		b2g.dispose();
		
		img1 = b1;
		img2 = b2;
		
//		try{
//			ImageIO.write(img1, "png", new File("/home/aphilip/Desktop/img1.png"));
//			ImageIO.write(img2, "png", new File("/home/aphilip/Desktop/img2.png"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		
		img = img1;
	  
		c1.setVisible(false);
		c2.setVisible(false);
	}
	
	public PerspectiveImagePanel(JComponent parent){
		try{
			this.parent = parent;
			setDirection(false);
			setOpaque(false);
			a = PropertySetter.createAnimator(speed, this, "angle", 0, 84);
			a.setAcceleration(0.5f);
			a.addTarget(new TimingTargetAdapter(){
				public void end(){
					flipImage();
					setDirection(!getDirection());
					Animator b = PropertySetter.createAnimator(speed, PerspectiveImagePanel.this, "angle", 84, 0);
					b.setDeceleration(0.5f);
					b.addTarget(new TimingTargetAdapter(){
						public void end(){
							setAngle(0);
							setDirection(!getDirection());
							PerspectiveImagePanel.this.setVisible(false);
						  c2.setVisible(true);
						}
					});
					b.start();
				}
			});
			
			addComponentListener(new ComponentAdapter(){
				public void componentResized(ComponentEvent e) {
			
				}				
			});
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setSpeed(int halftimeMillis){
		this.speed = halftimeMillis;
	}
	
	public PerspectiveImagePanel(int angle){
		try{
			this.angle = angle;
			img1 = ImageIO.read(new File("/home/aphilip/jmlogo.png"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void setAngle(int angle){
		this.angle = angle;
	  repaint();
	}
	
	public void paintComponent(Graphics g){
		Graphics2D graf = (Graphics2D)g;
		Graphics2D g2 = (Graphics2D)graf.create();
		
		float grey_factor = (float)angle * 1.0f / 80.0f;
		if(currentImg != null && angle == previousAngle){
			g2.drawImage(currentImg, 0, 0, currentImg.getWidth(), currentImg.getHeight(), null);
		}else	if(img != null){
			if(angle > 0 && angle < 85){
				previousAngle = angle;
				currentImg = PerspectiveTransform.transform(img, angle, true, direction);//				
				g2.drawImage(currentImg, 0, 0, currentImg.getWidth(), currentImg.getHeight(), null);
				try{
//					ImageIO.write(currentImg, "png", new File("/home/aphilip/Desktop/img" + angle + ".png"));
//					ImageIO.write(img2, "png", new File("/home/aphilip/Desktop/img2.png"));
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				currentImg = img;
				g2.drawImage(currentImg, 0, 0, currentImg.getWidth(), currentImg.getHeight(), null);
			}
		}		
		g2.dispose();
	}
	
	private BufferedImage createReflection(BufferedImage img, int reflectionSize) {
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(img.getWidth(), (int) (height + reflectionSize), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.scale(1.0, -1.01);
		g2.drawImage(img, 0, -height - height, null);
		g2.scale(1.0, -1.0);
		g2.translate(0, height);

		GradientPaint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, reflectionSize, new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		g2.fillRect(0, 0, img.getWidth(), reflectionSize);
		g2.dispose();
		return result;
	}

	
	public void setDirection(boolean to_left){
		this.direction = to_left;
	}
	
	public void flipImage(){
		if(img == img1){
			img = img2;
		}else{
			img = img1;
		}
	}
	
	public boolean getDirection(){
		return this.direction;
	}

	public void flip(){
		if(!a.isRunning()){
			currentImg = null;
			a.setDuration(speed);
			a.start();
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("aap");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		final PerspectiveImagePanel p = new PerspectiveImagePanel(null);
		p.setBackground(new Color(80,160,224));
		JButton b = new JButton("Go");
		b.setBounds(0, 550, 80, 30);
		p.setBounds(100, 10, 400, 600);
		frame.getContentPane().add(p);
		frame.getContentPane().add(b);
		frame.getContentPane().setBackground(p.getBackground());
		
		final JSlider slide = new JSlider(0,84);
		slide.setBounds(0, 580, 180, 30);
		slide.setValue(0);
		slide.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				p.setAngle(slide.getValue());
				
			}
			
		});
		frame.getContentPane().add(slide);
//		JPanel p1 = new JPanel();
//		JPanel p2 = new JPanel();
//		
//		JButton apenoot = new JButton("Hallo daar");
//		JTextField nootjes = new JTextField("Hoi!");
		
//		p1.setLayout(new BorderLayout());
//		p2.setLayout(new BorderLayout());
//		p1.add(apenoot);
//		p2.add(nootjes);
//		p1.setBounds(0,0,200,200);
//		p2.setBounds(0,0,200,200);
//		p.setLayout(new BorderLayout());
//		p.add(p1, BorderLayout.CENTER);
//		p.add(p2, BorderLayout.CENTER);
//		p.setComponents(p1, p2);
		
		JButton flip = new JButton("flip direction");
		flip.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				p.setDirection(!p.getDirection());
				p.setAngle(slide.getValue()+1);
			}
		});
		flip.setBounds(0, 610, 180, 30);
		frame.getContentPane().add(flip);
		
    
		p.setDirection(true);
		frame.setSize(1024,768);
		frame.setVisible(true);
	}
	
}
