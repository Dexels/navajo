package com.dexels.navajo.rich.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PeelPanel extends JPanel implements PeelListener{
	
	private static final long serialVersionUID = -6727304647628662379L;
	private int direction = 1;
	private PeelImagePanel animationPanel = new PeelImagePanel();
	private ArrayList<JComponent> components = new ArrayList<JComponent>();
	private BorderLayout layout = new BorderLayout();
	private Color backSideColor = Color.decode("#97CCC2");
	private BufferedImage backSideImage = null;
	private boolean bottom_only = true;
	JComponent visibleComponent = null;
	JComponent next = null;
	
	private double mouseDistance = 0.0;
	private double max_mouseDistance = 50.0;

	public PeelPanel() {
		setLayout(layout);
		animationPanel.addPeelListener(this);
		
		addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				
			}

			public void mouseMoved(MouseEvent e) {
				if (animationPanel.isAnimating) {
					return;
				}
				boolean valid = true;
				Point cornerLocation = new Point(0, 0);
				Rectangle bounds = getBounds();
				if (e.getPoint().y < bounds.height / 2) {					
					cornerLocation.y = bounds.y;
					if(bottom_only){
						valid = false;
					}
				} else {
					cornerLocation.y = bounds.y + bounds.height;
				}
				if (e.getPoint().x < bounds.width / 2) {
					cornerLocation.x = bounds.x;
					direction = -1;
				} else {
					cornerLocation.x = bounds.x + bounds.width;
					direction = 1;
				}

				double dx = cornerLocation.x - e.getPoint().x;
				double dy = cornerLocation.y - e.getPoint().y;

				mouseDistance = Math.sqrt((Math.pow(dx, 2.0) + Math.pow(dy, 2.0)));
				if (valid && mouseDistance < max_mouseDistance) {
					startPeel();
				}
			}
		});
	}

	public void removeComponent(JComponent c) {
		components.remove(c);
	}

	public void addComponent(JComponent c) {
		components.add(c);
		c.doLayout();
		visibleComponent = c;
		add(c, BorderLayout.CENTER);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).setVisible(false);
		}
		c.setVisible(true);
	}

	public void setBounds(int x, int y, int width, int height) {

		super.setBounds(x, y, width, height);
		for (int i = 0; i < components.size(); i++) {
			components.get(i).setSize(width, height);
		}
		
	}
	
	private void startPeel() {	
		// Indices: red = 0, green = 1, yellow = 2
		getRootPane().getLayeredPane().add(animationPanel, 0);
		int idx = components.indexOf(visibleComponent);
		if (idx > -1) {
			int nextComp = idx + direction;
			if(nextComp == -1){
				nextComp = components.size()-1;
			}else if(nextComp == components.size()){
				nextComp = 0;
			}
			next = components.get(nextComp);

			next.setBounds(visibleComponent.getBounds());
			animationPanel.setBackSideColor(backSideColor);
			animationPanel.setBackSideImage(backSideImage);
			animationPanel.setComponents(visibleComponent, next);
			animationPanel.setVisible(true);
			
			add(animationPanel, BorderLayout.CENTER);
		}
	}
	
	 public void peelStopped(boolean full_peel) {
		animationPanel.setVisible(false);
		for(int i=0;i<components.size();i++){
			components.get(i).setVisible(false);
		}
		if(!full_peel){
			next.setVisible(false);
			visibleComponent.setVisible(true);
		} else{
			visibleComponent.setVisible(false);
			visibleComponent =  next;
			visibleComponent.setVisible(true);
		}
	}
	 
	public void setBackSideColor(Color c){
		this.backSideColor = c;
	}
	
	public void setBackSideImage(URL img){
		try{
			backSideImage = ImageIO.read(img);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Color setBackSideColor(){
		return backSideColor;
	}
	
	public BufferedImage getBackSideImage(){
		return backSideImage;
	}
	
	public static void main(String[] args){
		JFrame aap= new JFrame("Monkey");
		aap.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aap.setSize(new Dimension(800,600));
		JPanel p1 = new JPanel();
		p1.setBackground(Color.red);
		JPanel p2 = new JPanel();
		p2.setBackground(Color.green);
		JPanel p3 = new JPanel();
		p3.setBackground(Color.yellow);
		aap.getContentPane().setLayout(new BorderLayout());
		PeelPanel peel = new PeelPanel();
		peel.addComponent(p1);
		peel.addComponent(p2);
		peel.addComponent(p3);
		peel.setBackSideColor(Color.decode("#000000"));
		
//		try{
//			BufferedImage img = ImageIO.read(new File("c:/workspace/Ticketing/resource/desktop.png"));
//			peel.setBackSideImage();
//		}catch(Exception e){
//			e.printStackTrace();
//		}
		
		
		for(int i=0;i<20;i++){
			p1.add(new JButton("Knop " + i));
			p2.add(new JCheckBox("Check " + i));
			p3.add(new JLabel("Label " + i));
		}
		aap.getContentPane().add(peel, BorderLayout.CENTER);
		aap.setVisible(true);
	}


}
