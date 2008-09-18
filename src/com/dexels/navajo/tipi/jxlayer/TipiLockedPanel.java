package com.dexels.navajo.tipi.jxlayer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.jdesktop.jxlayer.*;
import org.jdesktop.jxlayer.plaf.effect.*;
import org.jdesktop.jxlayer.plaf.ext.*;

import com.dexels.navajo.tipi.components.swingimpl.*;
import com.jhlabs.image.*;

public class TipiLockedPanel extends TipiSwingDataComponentImpl {

	private JPanel myPanel;
	private LockableUI blurUI;

	public Object createContainer() {
		myPanel = new JPanel();
		JXLayer<JComponent> layer = new JXLayer<JComponent>(myPanel) {
		
		};
		BlurFilter blurFilter = new BlurFilter();
//		blurFilter.setUseAlpha(true);

		blurUI = new LockableUI(new BufferedImageOpEffect(blurFilter)) {

		

			@Override
			protected boolean isDirty() {
				return isLocked();
			}
			
			
			
		};
//		blurUI.setLocked(true);
		
		layer.setUI(blurUI);
		blurUI.setLockedCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		return layer;
	}

	@Override
	public void removeFromContainer(final Object c) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myPanel.remove((Component) c);
			}
		});
	}

	@Override
	public void setContainerLayout(final Object layout) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myPanel.setLayout((LayoutManager) layout);
			}
		});
	}

	@Override
	public void addToContainer(final Object c, final Object constraints) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myPanel.add((Component) c, constraints);
			}
		});
	}

	@Override
	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("lock")) {
			final boolean lck = ((Boolean) object).booleanValue();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					blurUI.setLocked(lck);
					System.err.println("Setting lock: "+lck);
				
				}
			});
			
	
		}
		
}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				JButton aaaaap = new JButton("MONEKEY");
				// JPanel gorrila = new JPanel();
				JFrame frame = new JFrame("Monkey");

				// BusyPainter<JComponent> bp = new BusyPainter<JComponent>();

				// create custom LayerUI
				// AbstractLayerUI<JComponent> layerUI = new
				// AbstractLayerUI<JComponent>() {
				//
				// @Override
				// protected void paintLayer(Graphics2D g2, JXLayer<JComponent>
				// l) {
				// // this paints layer as is
				// super.paintLayer(g2, l);
				// // custom painting:
				// // here we paint translucent foreground
				// // over the whole layer
				// g2.setColor(new Color(0, 128, 0, 128));
				// g2.fillRect(0, 0, l.getWidth(), l.getHeight());
				// }
				// };

				// set our LayerUI
				// layer.setUI(layerUI);

//				LockableUI blurUI = new LockableUI(new BufferedImageOpEffect(new BlurFilter()));

				JXLayer<JComponent> layer = new JXLayer<JComponent>(aaaaap);
				JPanel jp = new JPanel();
				jp.setLayout(new FlowLayout());
				
				frame.getContentPane().add(jp);
				jp.add(layer);
				frame.setVisible(true);
				frame.setSize(400, 300);
				final LockableUI lockableUI = new LockableUI(new BufferedImageOpEffect(new BlurFilter()));
				layer.setUI(lockableUI);
				frame.doLayout();
				final JToggleButton b = new JToggleButton("Lock");
				jp.add(b);
				b.addActionListener(new ActionListener(){

					public void actionPerformed(ActionEvent e) {
						Thread t = new Thread(){
							public void run() {
								SwingUtilities.invokeLater(new Runnable() {

									public void run() {
										lockableUI.setLocked(b.isSelected());
																		}
								});
							}
							
						};
						t.start();
					}});
//				lockableUI.setLocked(true);
			}
		});
	}

}
