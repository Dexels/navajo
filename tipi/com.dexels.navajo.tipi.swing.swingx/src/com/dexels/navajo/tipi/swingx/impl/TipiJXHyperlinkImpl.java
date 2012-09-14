package com.dexels.navajo.tipi.swingx.impl;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import org.jdesktop.swingx.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.types.*;

public class TipiJXHyperlinkImpl extends JXHyperlink {
	private static final long serialVersionUID = -4871054910728952972L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiJXHyperlinkImpl.class);
	
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
				logger.error("Error: ",e);
			}
		}
		return null;
	}

}
