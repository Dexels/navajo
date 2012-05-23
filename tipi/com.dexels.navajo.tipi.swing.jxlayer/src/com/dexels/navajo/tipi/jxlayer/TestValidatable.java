package com.dexels.navajo.tipi.jxlayer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.jxlayer.JXLayer;
import org.jdesktop.jxlayer.plaf.AbstractLayerUI;

public class TestValidatable {

	/**
	 * @param args
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//		  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    
	  
	  JFrame aap = new JFrame("test");
	  
	  aap.setSize(500,300);
	  aap.getContentPane().setLayout(new FlowLayout());
	  
	  JPanel jp = new JPanel();
	  jp.setBorder(BorderFactory.createTitledBorder("Tralala"));
	  JButton jb = new JButton("Aap");
	  jp.add(jb);
	  
	  
	  JXLayer<JComponent> jj = new JXLayer<JComponent>(jp);
//		return new LockableUI(new BufferedImageOpEffect(new BlurFilter()));
		
	  jj.setUI(new AbstractLayerUI<JComponent>(){

		@Override
		protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
			super.paintLayer(g2, l);
            g2.setColor(new Color(160, 20, 0, 80));
            g2.fillRoundRect(2, 2, l.getWidth()-4, l.getHeight()-4, 4, 4);
           
		}
		  
	  });
	  aap.getContentPane().add(jj);
	  aap.setVisible(true);		
	}

}
