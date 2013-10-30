package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.Binary;


public class TipiSwingTab extends JPanel implements TipiTabbable {

	private static final long serialVersionUID = -1869629608167928815L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingTab.class);
	
	private String tabTooltip;
	private Icon tabIcon;
	private int index;
	private Color tabForegroundColor = null;
	private Color tabBackgroundColor = null;
	private Object tabObject;

	private String tabText;

	@Override
	public String getTabTooltip() {
		return tabTooltip;
	}

	public void setTabTooltip(String tabToolTip) {
		String old = this.tabTooltip;
		this.tabTooltip = tabToolTip;
		firePropertyChange("tabToolTip", old, tabToolTip);
	}

	@Override
	public Icon getTabIcon() {
		return tabIcon;
	}

	public void setTabIcon(Icon tabIcon) {
		Icon old = this.tabIcon;
		this.tabIcon = tabIcon;
		if (old == tabIcon) {
			logger.debug("whoops, identical");
		}
		firePropertyChange("tabIcon", old, tabIcon);
	}

	public int getIndex() {
		return index;
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public Color getTabForegroundColor() {
		return tabForegroundColor;
	}

	public void setTabForegroundColor(Color tabForegroundColor) {
		Color old = this.tabForegroundColor;
		this.tabForegroundColor = tabForegroundColor;
		firePropertyChange("tabForegroundColor", old, tabBackgroundColor);
	}

	@Override
	public Color getTabBackgroundColor() {
		return tabBackgroundColor;
	}

	public void setTabBackgroundColor(Color c) {
		Color old = tabBackgroundColor;
		firePropertyChange("tabBackgroundColor", old, tabBackgroundColor);
	}

	public void setIconUrl(Object u) {
		tabObject = u;
		setTabIcon(getIcon(u));
	}

	public Object getIconUrl() {
		return tabObject;
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

	@Override
	public String getTabText() {
		return tabText;
	}

	public void setTabText(String tabText) {
		String old = this.tabText;
		this.tabText = tabText;
		firePropertyChange("tabText", old, tabText);
	}
}
