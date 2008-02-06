package com.dexels.navajo.rich.components;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;

import com.sun.org.apache.bcel.internal.generic.LUSHR;

public class MainFrame extends JFrame {
//    FlipPanel flipPanel = new FlipPanel();
    MacBar toolBar= new MacBar();
    Rectangle previous;
    LushContainer desktopButtonPanel = new LushContainer();
    DesktopButton dBut, bBut, aBut;
//    GradientDesktop desktop = new GradientDesktop();   
    GradientDesktop desktop = new GradientDesktop();
    ScreenTransition valmorphication = new ScreenTransition(desktop, new TransitionTarget(){
			public void setupNextScreen() {
				toolBar.setLocation(0, desktop.getHeight());					
			}}, 500);
    ScreenTransition revalmorphication = new ScreenTransition(desktop, new TransitionTarget(){
			public void setupNextScreen() {
				toolBar.setLocation(0, desktop.getHeight()-toolBar.getHeight());					
			}}, 500);
    
    ScreenTransition widen = new ScreenTransition(desktop, new TransitionTarget(){
			public void setupNextScreen() {
				previous = desktopButtonPanel.getBounds();
				desktopButtonPanel.setBounds(10,10,desktop.getWidth()-10, 70);
				dBut.setVisible(true);
				bBut.setVisible(true);
				aBut.setVisible(true);
			}}, 750);
    
    
    
    ScreenTransition orig = new ScreenTransition(desktop, new TransitionTarget(){
			public void setupNextScreen() {
				if(previous != null){
					desktopButtonPanel.setBounds(previous);
					previous = null;
				}
			}}, 750);
    
		public MainFrame(){
			initUI();
						
		}
		
		private void initUI(){
			this.getContentPane().setLayout(new BorderLayout());
			getContentPane().setBackground(new Color(160, 200, 245));
			
			
			desktopButtonPanel.setOpaque(false);
			desktopButtonPanel.setBounds(100,100,300, 610);
			desktopButtonPanel.setBackground(Color.black);
			
			LushContainer gradientTest = new LushContainer();
			gradientTest.setBounds(500,100,200, 200);
			gradientTest.setBackground(Color.black);
			
			getContentPane().add(desktop, BorderLayout.CENTER);
			desktop.setBackground(new Color(160, 200, 245));
			toolBar.setBounds(0, 500, 800, 64);
			desktop.add(toolBar);
			desktop.setLayer(toolBar, 10, 0);
			dBut = new DesktopButton(getClass().getClassLoader().getResource("cpu.png"));
			dBut.setText("Winkelwagen");
			dBut.setToolTipText("Klik hier om inhoud te bekijken");
			dBut.setBounds(100,180,250,68);
			bBut = new DesktopButton(getClass().getClassLoader().getResource("icecube.png"));
			bBut.setText("Fietscomputers");
			bBut.setToolTipText("Meet nu uw eigen snelheid");
			bBut.setBounds(100,260,250,68);		
			aBut = new DesktopButton(getClass().getClassLoader().getResource("shoppingbasket_full.png"));
			aBut.setText("Postkoets");
			aBut.setToolTipText("De postkoetskoetsier");
			aBut.setBounds(100,100,250,68);
			DesktopButton fBut = new DesktopButton(getClass().getClassLoader().getResource("yinyang.png"));
			fBut.setText("Turnover");
			fBut.setToolTipText("Frame transitie effect");
			fBut.setBounds(100,100,250,68);	
			
			
			desktopButtonPanel.add(dBut);
			desktopButtonPanel.add(bBut);
			desktopButtonPanel.add(aBut);
			desktop.add(desktopButtonPanel);
			desktop.add(gradientTest);
			final JInternalFrame frame = new JInternalFrame("Test");
			frame.setMaximizable(true);
			frame.setClosable(true);
			frame.setIconifiable(true);
			frame.setSize(new Dimension(400,400));
			frame.getContentPane().add(fBut);
			toolBar.setOpaque(false);
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("chart.png"), "Statisieken"));
			MacLink system = new MacLink(getClass().getClassLoader().getResource("cpu.png"), "Systeem");
			toolBar.add(system);
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("icecube.png"), "IJsblokjes doe je in je cola of in een ander verfrissend drankje"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("mail_write.png"), "Thunderbird"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("spam.png"), "Vuilnis"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("shoppingbasket_full.png"), "Winkel"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("recycle.png"), "Recycle"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("pawn_glass_green.png"), "Spelletjes"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("tractor_red.png"), "Vervoer"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("workstation2.png"), "Netwerk"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("weight2.png"), "Gewicht"));
			toolBar.add(new MacLink(getClass().getClassLoader().getResource("yinyang.png"), "Zen"));
//			toolBar.add(new JButton("hey motherfucker!"));
			system.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					desktop.add(frame);
					frame.setVisible(true);
				}
			});
			
			desktop.addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseMoved(MouseEvent e) {
					
					if(toolBar.getLocation().y == desktop.getHeight() && e.getPoint().getY() > desktop.getHeight()-toolBar.getHeight()){
						revalmorphicate();
					} else if(toolBar.getLocation().y == desktop.getHeight()-toolBar.getHeight() && e.getPoint().getY() < desktop.getHeight()-toolBar.getHeight()){
						valmorphicate();
					}
				}
			});
			
			desktop.addComponentListener(new ComponentAdapter(){
				public void componentResized(ComponentEvent e) {
					
					toolBar.setBounds(0, desktop.getHeight() - toolBar.getHeight(), desktop.getWidth(), toolBar.getHeight());
					toolBar.revalidate();
				}
			});
			
			aBut.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(previous == null){
						dBut.setVisible(false);
						bBut.setVisible(false);
						aBut.setVisible(false);
						widen.start();
					}else{
						orig.start();
					}
				}
			});
	
		}
		
		public void valmorphicate(){
			
			valmorphication.start();
		}
		
		public void revalmorphicate(){
			revalmorphication.start();
		}

}
