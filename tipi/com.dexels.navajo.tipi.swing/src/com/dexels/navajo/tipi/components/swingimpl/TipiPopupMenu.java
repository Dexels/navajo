package com.dexels.navajo.tipi.components.swingimpl;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.internal.TipiEvent;

public class TipiPopupMenu extends TipiSwingDataComponentImpl {
	private static final long serialVersionUID = -3119612802393524077L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiPopupMenu.class);
	@Override
	public Object createContainer() {
		JPopupMenu.setDefaultLightWeightPopupEnabled(false);
		JPopupMenu myMenu = new JPopupMenu();
		logger.debug("Created popup");
		return myMenu;
	}

	@Override
	protected synchronized void performComponentMethod(String name,
			TipiComponentMethod compMeth, TipiEvent event)
			throws TipiBreakException {
		super.performComponentMethod(name, compMeth, event);

		JPopupMenu jp = (JPopupMenu) getContainer();
		if (name.equals("hide")) {
			jp.setVisible(false);
		}
	}
	
	@Override
	public void showPopup(MouseEvent e) {
		JPopupMenu jp = (JPopupMenu) getContainer();
		Point point = e.getPoint();
		logger.debug("SOURCE: " + e.getSource());
		// Thread.dumpStack();
		if (jp.isShowing()) {
			logger.debug("one");
			jp.show((Component) e.getSource(), point.x, point.y);
		} else {
			logger.debug("two");
			SwingUtilities.convertPointToScreen(point,
					(Component) e.getSource());
			jp.show((Component) e.getSource(), point.x, point.y);
			jp.setLocation(point);
		}

	}

	@Override
	public void addedToParentContainer(TipiComponent parentTipiComponent,
			Object parentContainer, Object container, Object constraint) {
		if (parentContainer instanceof Component) {
			Component c = (Component) parentContainer;
			c.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					if (e.isPopupTrigger()) {
						logger.debug("Showing popup for component: "
								+ this);
						showPopup(e);
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (e.isPopupTrigger()) {
						logger.debug("Showing popup for component: "
								+ this);
						showPopup(e);
					}
				}
			});
		}
	}

	//
	// public Object getContainer() {
	// return myMenu;
	// }

	// public void addToContainer(final Object c, final Object constraints) {
	// logger.debug("\n\nAdding: "+c+" to: "+getContainer());
	//
	// getSwingContainer().add((Component)c);
	// }

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		final JFrame jd = new JFrame("aap");
		jd.getContentPane().add(new JDesktopPane());
		final JPopupMenu jp = new JPopupMenu("Hoei");
		jp.add(new JMenuItem("monekyyy"));
		jp.add(new JMenuItem("monekyyy"));
		jp.add(new JMenuItem("monekyyy"));
		jp.add(new JMenuItem("monekyyy"));
		jp.add(new JMenuItem("monekyyy"));
		jp.add(new JMenuItem("monekyyy"));
		jd.setSize(200, 150);

		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");

		jd.setVisible(true);
		jd.getContentPane().addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					jp.show(jd.getContentPane(), e.getX(), e.getY());
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					jp.show(jd.getContentPane(), e.getX(), e.getY());
				}

			}

		});
	}

}
