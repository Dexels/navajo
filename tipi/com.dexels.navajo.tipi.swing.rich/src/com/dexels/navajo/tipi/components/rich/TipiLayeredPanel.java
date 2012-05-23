package com.dexels.navajo.tipi.components.rich;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;

import com.dexels.navajo.tipi.components.swingimpl.TipiPanel;

public class TipiLayeredPanel extends TipiPanel {

	private static final long serialVersionUID = -1989798226251005367L;

	// private int totalResize = 0;
	public Object createContainer() {
		final JLayeredPane myPanel = new JLayeredPane();

		myPanel.addComponentListener(new ComponentAdapter() {

			public void componentResized(ComponentEvent e) {
				for (int i = 0; i < myPanel.getComponentCount(); i++) {
					Component c = myPanel.getComponent(i);
					Rectangle rectangle = new Rectangle(new Point(0, 0), myPanel.getSize());
					// System.err.println("Rect: "+rectangle+totalResize++);
					c.setBounds(rectangle);

				}
			}

			public void componentShown(ComponentEvent e) {
				for (int i = 0; i < myPanel.getComponentCount(); i++) {
					Component c = myPanel.getComponent(i);
					c.setBounds(new Rectangle(new Point(0, 0), myPanel.getSize()));
				}
			}
		});
		myPanel.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				System.err.println("bliep");
			}

			public void mouseEntered(MouseEvent e) {
				System.err.println("oop");

			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		return myPanel;
	}

	public void addToContainer(final Object c, final Object constraints) {
		if (constraints == null) {
			myContext.showInternalError("Error adding element to layerpanel: " + getPath() + " you need to add an Integer constraint");
			return;
		}
		runSyncInEventThread(new Runnable() {
			public void run() {
				JLayeredPane myPanel = (JLayeredPane) getContainer();
				Component comp = (Component) c;
				try {
					Integer ii = new Integer(Integer.parseInt((String) constraints));
					myPanel.add(comp, ii);
					comp.setBounds(new Rectangle(new Point(0, 0), myPanel.getSize()));
				} catch (NumberFormatException e) {
					myContext.showInternalError("Error adding element to layerpanel: " + getPath()
							+ " you need to add an Integer constraint", e);
					e.printStackTrace();

				}
			}
		});
	}

}
