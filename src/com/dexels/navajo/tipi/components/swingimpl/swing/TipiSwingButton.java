package com.dexels.navajo.tipi.components.swingimpl.swing;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;

import javax.imageio.*;
import javax.swing.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.swingimpl.dnd.*;

public class TipiSwingButton extends JButton implements TipiDndCapable {
	
	public static String STRINGMNEMONIC_CHANGED_PROPERTY = "string_mnemonic"; 
	
	private final TipiDndManager myDndManager;
	
	private boolean isVertical = false;

	public TipiSwingButton(TipiComponent component) {
		myDndManager = new TipiDndManager(this, component);
	}

	public void setVertical(boolean isVertical) {
		this.isVertical = isVertical;
		if(isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}


	 @Override
	public void setText(String text) {
		super.setText(text);
		if(isVertical) {
			createVerticalImage(super.getText(), true);
		}
	 }
	
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if(isVertical) {
			createVerticalImage(super.getText(), true);
		}
	}

		public String getText() {
			if(isVertical) {
				return "";
			} else {
				return super.getText();
			}
		}
	public void createVerticalImage (String caption, boolean clockwise) {
	        System.err.println("Creating vertical image");
	        Font f = getFont ();
	        FontMetrics fm = getFontMetrics (f);
	        int captionHeight = fm.getHeight ();
	        int captionWidth = fm.stringWidth (caption);
	        BufferedImage bi = new BufferedImage (captionHeight + 4,
	                captionWidth + 4, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g = (Graphics2D) bi.getGraphics ();
	        
	        g.setColor (new Color (0, 0, 0, 0)); // transparent
	        g.fillRect (0, 0, bi.getWidth (), bi.getHeight ());
	        
	        g.setColor (getForeground ());
	        g.setFont (f);
	        g.setRenderingHint (RenderingHints.KEY_TEXT_ANTIALIASING,
	                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	        
	        if (clockwise) {
	            g.rotate (Math.PI / 2);
	        } else {
	            g.rotate (- Math.PI / 2);
	            g.translate (-bi.getHeight (), bi.getWidth ());
	        }
	        g.drawString (caption, 2, -6);
	        
	        Icon icon = new ImageIcon (bi);
	        setIcon (icon);
//	        super.setText("");
	        setMargin (new Insets (2, 0, 2, 0));
	         setActionCommand (caption);
	    }
	    

//	public void setPreferredSize(Dimension d) {
//		// ignore.
//	}
//
//	
//	
	@Override
	public void setCursor(Cursor cursor) {
		System.err.println("Setting cursor: "+cursor.getName());
		Thread.dumpStack();
		super.setCursor(cursor);
	}


	public Dimension getMinimumSize() {
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


	public TipiDndManager getDndManager() {
		return myDndManager;
	}

}
