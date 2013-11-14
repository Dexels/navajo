/*
GNU Lesser General Public License

ImageFileChooserPreview
Copyright (C) 2000-2002  Frits Jalvingh & Howard Kistler
changes to ImageFileChooserPreview
Copyright (C) 2003-2004  Karsten Pawlik

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation; either
version 2.1 of the License, or (at your option) any later version.

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/
package de.xeinfach.kafenio.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import de.xeinfach.kafenio.util.LeanLogger;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/** 
 * Description: Class provides a preview window for the selected image file
 * 
 * @author Howard Kistler
 */
public class ImageFileChooserPreview extends JComponent implements PropertyChangeListener {

	private static LeanLogger log = new LeanLogger("ImageFileChooserPreview.class");
	
	private static final int PREVIEW_WIDTH  = 100;
	private static final int PREVIEW_HEIGHT = 100;

	private ImageIcon imageThumb = null;
	private File imageFile = null;

	/** 
	 * This class requires a file chooser to register with so this class will
	 * be notified when a new file is selected in the browser.
	 * 
	 * @param parent a JFileChooser that this preview window is used in.
	 */
	public ImageFileChooserPreview(JFileChooser parent) {
		setPreferredSize(new Dimension(PREVIEW_WIDTH , PREVIEW_HEIGHT));
		parent.addPropertyChangeListener(this);
		log.debug("new ImageFileChooserPreview created.");
	}

	/** 
	 * Loads a new image into the preview window, and scales it if necessary.
	 */
	public void loadImage() {
		if(imageFile == null) {
			imageThumb = null;
			return;
		}
		imageThumb = new ImageIcon(imageFile.getPath());

		if(imageThumb.getIconHeight() < PREVIEW_HEIGHT && imageThumb.getIconWidth() < PREVIEW_WIDTH) {
			return;
		}

		int	w = PREVIEW_WIDTH;
		int	h = PREVIEW_HEIGHT;
		
		if(imageThumb.getIconHeight() > imageThumb.getIconWidth()) {
			w = -1;
		} else {
			h = -1;
		}
		
		imageThumb = new ImageIcon(imageThumb.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
	}

	/** 
	 * Callback (event handler) to indicate that a property of the
	 * JFileChooser has changed. If the selected file has changed cause a new
	 * thumbnail to load.
	 * @param e Event to handle
	 */
	public void propertyChange(PropertyChangeEvent e) {
		if(e.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
			imageFile = (File)e.getNewValue();
			if(isShowing()) {
				loadImage();
				repaint();
			}
		}
	}

	/** 
	 * Paints the icon of the current image, if one is present.
	 * 
	 * @param g a Graphics object to use when painting the component.
	 */
	public void paintComponent(Graphics g) {
		if(imageThumb == null) loadImage();
		if(imageThumb == null) return;

		int	x = (getWidth() - imageThumb.getIconWidth()) / 2;
		int	y = (getHeight() - imageThumb.getIconHeight()) / 2;
		
		if(y < 0) y = 0;		
		if(x < 5) x = 5;
		
		imageThumb.paintIcon(this, g, x, y);
	}
}