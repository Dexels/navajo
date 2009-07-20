package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.dexels.navajo.document.types.Binary;
import com.dexels.navajo.tipi.components.swingimpl.TipiDialog;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiSwingDialog extends JDialog{

  public TipiSwingDialog(JFrame f, final TipiDialog comp) {
    super(f);
    addComponentListener(new ComponentAdapter(){

		public void componentHidden(ComponentEvent e) {
		}

		public void componentMoved(ComponentEvent e) {
			Rectangle r = getBounds();
			comp.getAttributeProperty("x").setAnyValue(r.x);
			comp.getAttributeProperty("y").setAnyValue(r.y);
		}

		public void componentResized(ComponentEvent e) {
			Rectangle r = getBounds();
			System.err.println("Dialog resize: "+r);
			comp.getAttributeProperty("h").setAnyValue(r.height);
			comp.getAttributeProperty("w").setAnyValue(r.width);
			comp.getAttributeProperty("x").setAnyValue(r.x);
			comp.getAttributeProperty("y").setAnyValue(r.y);
			
		}

		public void componentShown(ComponentEvent e) {
		}});
    
//    addWindowListener(new WindowListener(){})
  }

public TipiSwingDialog(JRootPane rootPane, TipiDialog comp) {
	// TODO Auto-generated constructor stub
}

public void setIconUrl(URL u) {
	setIconImage(new ImageIcon(u).getImage());
	System.err.println("Dialog icon set!");
}

public void setIconUrl(Object u) {
	setIconImage(getIcon(u).getImage());
	System.err.println("Dialog icon set!");
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
