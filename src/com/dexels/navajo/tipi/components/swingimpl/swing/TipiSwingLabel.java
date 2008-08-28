package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSwingLabel
    extends JLabel {
  public TipiSwingLabel() {
  }
  

  public Dimension getMinumumSize() {
      return getPreferredSize();
  }
	public void setIconUrl(Object u) {
		setIcon(getIcon(u));
	}

	 protected ImageIcon getIcon(Object u) {
		 if(u==null) {
			 return null;
		 }
		 if(u instanceof URL) {
			   return new ImageIcon((URL) u);
		 }
		 if(u instanceof Binary) {
			 Image i;
			try {
				i = ImageIO.read(((Binary) u).getDataAsStream());
				 ImageIcon ii = new ImageIcon(i);
				 return ii;
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return null;
	  }

}
