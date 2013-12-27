package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.TipiBreakException;
import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndCapable;
import com.dexels.navajo.tipi.swingimpl.dnd.TipiDndManager;

public class TipiSwingButton extends JButton implements TipiDndCapable {

	private static final long serialVersionUID = -5611343353338141519L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingButton.class);

	public static String STRINGMNEMONIC_CHANGED_PROPERTY = "string_mnemonic";

	private final TipiDndManager myDndManager;

	private boolean isVertical = false;
	private boolean tipiBorderPainted = true;

	public TipiSwingButton(final TipiComponent component) {
		myDndManager = new TipiDndManager(this, component);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				TipiSwingButton.this.setBorderPainted(true);
				try {
					component.performTipiEvent("onMouseEntered", null, false);
				} catch (TipiBreakException e1) {
					logger.error("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				TipiSwingButton.this.setBorderPainted(tipiBorderPainted);
				try {
					component.performTipiEvent("onMouseExited", null, false);
				} catch (TipiBreakException e1) {
					logger.error("Error detected",e1);
				} catch (TipiException e1) {
					logger.error("Error detected",e1);
				}
			}
		});

	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

	@Override
	public String getText() {
		if (isVertical) {
			return "";
		} else {
			return super.getText();
		}
	}

	public void setBorderVisible(boolean b) {
		boolean old = isBorderPainted();
		setBorderPainted(b);
		setContentAreaFilled(b);
		tipiBorderPainted = b;
		firePropertyChange("BorderVisible", old, b);
	}

	public void createVerticalImage(String caption, boolean clockwise) {
		logger.debug("Creating vertical image");
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f);
		int captionHeight = fm.getHeight();
		int captionWidth = fm.stringWidth(caption);
		BufferedImage bi = new BufferedImage(captionHeight + 4,
				captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) bi.getGraphics();

		g.setColor(new Color(0, 0, 0, 0)); // transparent
		g.fillRect(0, 0, bi.getWidth(), bi.getHeight());

		g.setColor(getForeground());
		g.setFont(f);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (clockwise) {
			g.rotate(Math.PI / 2);
		} else {
			g.rotate(-Math.PI / 2);
			g.translate(-bi.getHeight(), bi.getWidth());
		}
		g.drawString(caption, 2, -6);

		Icon icon = new ImageIcon(bi);
		setIcon(icon);
		// super.setText("");
		setMargin(new Insets(2, 0, 2, 0));
		setActionCommand(caption);
	}

	// public void setPreferredSize(Dimension d) {
	// // ignore.
	// }
	//
	//
	//
	@Override
	public void setCursor(Cursor cursor) {
		logger.debug("Setting cursor: " + cursor.getName());
		Thread.dumpStack();
		super.setCursor(cursor);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void setIconUrl(Object u) {
		setIcon(getIcon(u));
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

	public void setStringMnemonic(String s) {
		String old = getStringMnemonic();
		setMnemonic(s.charAt(0));
		firePropertyChange(STRINGMNEMONIC_CHANGED_PROPERTY, old, s);
	}

	public String getStringMnemonic() {
		return new String("" + (char) getMnemonic());
	}

	public void setMnemonic(String s) {
		setMnemonic(s.charAt(0));
	}

	@Override
	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
