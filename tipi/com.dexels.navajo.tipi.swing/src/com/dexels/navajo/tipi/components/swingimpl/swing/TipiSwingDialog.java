package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.TipiDialog;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingDialog extends JDialog {

	private static final long serialVersionUID = -8923819403196803897L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingDialog.class);
	
	private ComponentAdapter componentListener;
	private final JFrame myRootParent;

	public TipiSwingDialog(JFrame f, final TipiDialog comp) {
		super(f);
		myRootParent = f;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		componentListener = new ComponentAdapter() {

			public void componentHidden(ComponentEvent e) {
			}

			public void componentMoved(ComponentEvent e) {
				Rectangle r = getBounds();
				comp.getAttributeProperty("x").setAnyValue(r.x);
				comp.getAttributeProperty("y").setAnyValue(r.y);
			}

			public void componentResized(ComponentEvent e) {
				Rectangle r = getBounds();
				// logger.debug("Dialog resize: "+r);
				comp.getAttributeProperty("h").setAnyValue(r.height);
				comp.getAttributeProperty("w").setAnyValue(r.width);
				comp.getAttributeProperty("x").setAnyValue(r.x);
				comp.getAttributeProperty("y").setAnyValue(r.y);

			}

			public void componentShown(ComponentEvent e) {
			}
		};
		addComponentListener(componentListener);

		// addWindowListener(new WindowListener(){})
	}

	@Override
	public void dispose() {
		removeComponentListener(componentListener);
		if (myRootParent != null) {
			myRootParent.removeComponentListener(componentListener);
			myRootParent.getRootPane().removeComponentListener(
					componentListener);
		}
		componentListener = null;
		super.dispose();
	}

	public void setIconUrl(URL u) {
		setIconImage(new ImageIcon(u).getImage());
	}

	public void setIconUrl(Object u) {
		setIconImage(getIcon(u).getImage());
	}

	protected ImageIcon getIcon(Object u) {
		if (u == null) {
			return null;
		}
		if (u instanceof URL) {
			return new ImageIcon((URL) u);
		}
		if (u instanceof Binary) {
			Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				ImageIcon ii = new ImageIcon(i);
				return ii;
			} catch (IOException e) {
				logger.error("Error detected",e);
			}
		}
		return null;
	}

}
