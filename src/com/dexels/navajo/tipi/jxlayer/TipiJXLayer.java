package com.dexels.navajo.tipi.jxlayer;

import java.awt.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.*;
import org.jdesktop.jxlayer.plaf.effect.*;
import org.jdesktop.jxlayer.plaf.ext.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.jhlabs.image.*;

public class TipiJXLayer extends TipiSwingDataComponentImpl {

	public Object createContainer() {
		JPanel p = new JPanel();

		JXLayer<JComponent> layer = new JXLayer<JComponent>(p);

		// create custom LayerUI
		AbstractLayerUI<JComponent> layerUI = new AbstractLayerUI<JComponent>() {

			@Override
			protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
				// this paints layer as is
				super.paintLayer(g2, l);
				// custom painting:
				// here we paint translucent foreground
				// over the whole layer
				System.err.println("paaint");
				g2.setColor(new Color(0, 128, 0, 128));
				g2.fillRect(0, 0, l.getWidth(), l.getHeight());
			}
		};

		// set our LayerUI
		layer.setUI(layerUI);
		//
		//		
		//		
		// JXLayer<JComponent> layer = new JXLayer<JComponent>(p);
		//
		// // here we use BufferedLayerUI which can work with BufferedImageOps
		// BufferedLayerUI<JComponent> bufferedLayerUI = new
		// BufferedLayerUI<JComponent>();
		//	        
		// // create a ColorConvertOp to apply grayScale effect
		// BufferedImageOp grayScaleOp =
		// new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		//
		// // create a BufferedImageOpEffect with the provided BufferedImageOp
		// BufferedImageOpEffect imageOpEffect = new
		// BufferedImageOpEffect(grayScaleOp);
		//	        
		// // set BufferedImageOpEffect to the bufferedLayerUI
		// bufferedLayerUI.setLayerEffects(imageOpEffect);
		//	        
		// // set the bufferedLayerUI to the layer
		// layer.setUI(bufferedLayerUI);
		return p;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JButton aaaaap = new JButton("MONEKEY");
//				JPanel gorrila = new JPanel();
				JFrame frame = new JFrame("Monkey");

//				BusyPainter<JComponent> bp = new BusyPainter<JComponent>();

	
				// create custom LayerUI
//				AbstractLayerUI<JComponent> layerUI = new AbstractLayerUI<JComponent>() {
//
//					@Override
//					protected void paintLayer(Graphics2D g2, JXLayer<JComponent> l) {
//						// this paints layer as is
//						super.paintLayer(g2, l);
//						// custom painting:
//						// here we paint translucent foreground
//						// over the whole layer
//						g2.setColor(new Color(0, 128, 0, 128));
//						g2.fillRect(0, 0, l.getWidth(), l.getHeight());
//					}
//				};

				// set our LayerUI
//				layer.setUI(layerUI);


			     BlurFilter blurFilter = new BlurFilter();
			  
					JXLayer<JComponent> layer = new JXLayer<JComponent>(aaaaap);
				frame.getContentPane().add(layer);
//				gorrila.add(aaaaap);
				frame.setVisible(true);
				frame.setSize(400, 300);
				LockableUI lockableUI =  new LockableUI(new BufferedImageOpEffect(blurFilter));
				layer.setUI(lockableUI);
				frame.doLayout();
				
				lockableUI.setLocked(true);
				}
		});
	}

}
