package com.dexels.navajo.tipi.swingclient.components;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.beans.*;
import java.io.*;
import java.lang.reflect.*;

import javax.imageio.*;
import javax.imageio.stream.*;
import javax.swing.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;

public class BinaryComponent extends JPanel implements PropertyControlled, PropertyChangeListener, ActionListener {

	private Property myProperty = null;
	private JComponent myBinaryLabel = null;
	
	private int maxImgWidth;
	private int maxImgHeight;
	
	public BinaryComponent() {
		setLayout(new BorderLayout());
	}
	
	public void gainFocus() {
	}

	public Property getProperty() {
		return null;
	}

	public boolean isGhosted() {
		return false;
	}

	public void setGhosted(boolean b) {
		
	}


	public void setProperty(Property p) {
		if(p!=myProperty) {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			myProperty = p;
			if(myProperty!=null) {
				myProperty.addPropertyChangeListener(this);
			}
		}

		if(myProperty==null) {
			setBinary(null);
			return;
		}
		setBinary((Binary) p.getTypedValue());
	}
	
	
	private void setBinary(final Binary b) {
		try {
			if(SwingUtilities.isEventDispatchThread()) {
				setSyncBinary(b);
			} else {
				SwingUtilities.invokeAndWait(new Runnable(){

					public void run() {
						
				setSyncBinary(b);
					}});
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static ImageIcon scale(ImageInputStream infile, ImageOutputStream outfile, int width, int height, boolean keepAspect, float quality)
			throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}

			BufferedImage scaled = scale(width, height, keepAspect, original);
			return new ImageIcon(scaled);

	}

public static BufferedImage scale(int width, int height, boolean keepAspect, BufferedImage original) {
	int originalWidth = original.getWidth();
	int originalHeight = original.getHeight();
	if (width > originalWidth) {
		width = originalWidth;
	}
	if (height > originalHeight) {
		height = originalHeight;
	}
	
	
	float factorX = (float)originalWidth / width;
	float factorY = (float)originalHeight / height;
	if(keepAspect) {
		factorX = Math.max(factorX, factorY);
		factorY = factorX;
	}
	
	// The scaling will be nice smooth with this filter
	AreaAveragingScaleFilter scaleFilter =
		new AreaAveragingScaleFilter(Math.round(originalWidth / factorX),
				Math.round(originalHeight / factorY));
	ImageProducer producer = new FilteredImageSource(original.getSource(),
			scaleFilter);
	ImageGenerator generator = new ImageGenerator();
	producer.startProduction(generator);
	BufferedImage scaled = generator.getImage();
	return scaled;
}


  private final ImageIcon getScaled(BufferedImage icon, int maxWidth, int maxHeight) {
	 BufferedImage bi = scale(maxWidth, maxHeight, false, icon);
    if (icon == null) {
      return null;
    }
    return new ImageIcon(bi);
    
   
  }


	public void update() {
		// TODO Auto-generated method stub

	}

	public void propertyChange(PropertyChangeEvent e) {
		Binary old = (Binary)e.getOldValue();
		Binary newValue = (Binary)e.getNewValue();
		if(old!=null && newValue!=null) {
			System.err.println("Old size: "+old.getLength()+" new size: "+newValue.getLength());
		} else {
			System.err.println("Null detected!");
		}
		setBinary(newValue);
		
	}

	public int getMaxImgHeight() {
		return maxImgHeight;
	}

	public void setMaxImgHeight(int maxImgHeight) {
		this.maxImgHeight = maxImgHeight;
		if(myProperty!=null) {
			setProperty(myProperty);
		}
	}

	public int getMaxImgWidth() {
		return maxImgWidth;
	}

	public void setMaxImgWidth(int maxImgWidth) {
		this.maxImgWidth = maxImgWidth;
		if(myProperty!=null) {
			setProperty(myProperty);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
        try {
            JFileChooser jf = new JFileChooser();
            jf.showOpenDialog(myBinaryLabel);
            File f = jf.getSelectedFile();
            if (f != null) {
              Binary b = new Binary(f);
                myProperty.setAnyValue(b);
            	setProperty(myProperty);
        		
              }
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }		
	}

	private void setSyncBinary(final Binary b) {
		removeAll();
		if (b == null || b.getLength()<=0) {
//	        System.err.println("Null-binary found!");
		    myBinaryLabel = new JButton();
		    add(myBinaryLabel,BorderLayout.CENTER);
		  ( (JButton) myBinaryLabel).addActionListener(BinaryComponent.this);
		  if(myProperty==null) {
			  myBinaryLabel.setEnabled(false);
		  } else {
		      myBinaryLabel.setEnabled(myProperty.isDirIn());
		  }
		  ( (JButton) myBinaryLabel).setText("<html>-</html>"); 
		  //addPropertyComponent(myBinaryLabel, true);
		  myBinaryLabel.setToolTipText(myProperty.getDescription());
		  return;
		}
 //   System.err.println("Getting binary data!");
//	    byte[] data = b.getData();
		String mime = b.guessContentType();
		if (mime.indexOf("image") != -1) {
		    InputStream inp = b.getDataAsStream(); 
		    BufferedImage mm;
		    try {
		        mm = ImageIO.read(inp);
		        //ImageIcon img = new ImageIcon(mm);
		        System.err.println("WIDTH: "+maxImgWidth+" height: "+maxImgHeight);
		        myBinaryLabel = new JButton();
//	            ((JButton)myBinaryLabel).setUI(new ButtonUI(){
//	            	
//	            });
		        myBinaryLabel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		        myBinaryLabel.setOpaque(false);
		        myBinaryLabel.setBorder(null);
		      ( (JButton) myBinaryLabel).addActionListener(BinaryComponent.this);
		        
		        ( (JButton) myBinaryLabel).setHorizontalAlignment(SwingConstants.CENTER); 
		        ( (JButton) myBinaryLabel).setVerticalAlignment(SwingConstants.CENTER); 
		        ( (JButton) myBinaryLabel).setIcon(getScaled(mm,maxImgWidth,maxImgHeight)); 
//	            ( (BaseLabel) myBinaryLabel).setIcon(img);
		        add(myBinaryLabel,BorderLayout.CENTER);
		   } catch (IOException e) {
		        e.printStackTrace();
		    }
		   return;
		}
		if (mime.indexOf("text") != -1) {
		  myBinaryLabel = new JTextArea();
		  ( (JTextArea) myBinaryLabel).setText(new String(b.getData())); 
		  add(myBinaryLabel,BorderLayout.CENTER);
		  return;
		}
//	    if (mime.indexOf("text") != -1) {
		    myBinaryLabel = new JButton();
		      ( (JButton) myBinaryLabel).setText(new String(b.getMimeType())); 
		      ( (JButton) myBinaryLabel).addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
				}});
		      add(myBinaryLabel,BorderLayout.CENTER);
		      return;
//		    }
	}
	
	
	

}
