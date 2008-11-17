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

import com.dexels.navajo.dnd.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;

public class BinaryEditor extends JPanel implements PropertyControlled, PropertyChangeListener, ActionListener {

	private Property myProperty = null;
	private JLabel myBinaryLabel = new JLabel();

	JButton openButton = new JButton();
	JButton clearButton = new JButton();
//	JButton flipButton = new JButton();
	JTextField pathField = new JTextField(15);
	private int maxImgWidth = 32;
	private int maxImgHeight = 32;
	private File currentPath;

	public BinaryEditor() {
		setLayout(new BorderLayout());
		setTransferHandler(new BinaryTransferHandler(this));
		
//		myBinaryLabel.setBorderPainted(false);
		//openButton.setIcon(new ImageIcon(getClass().getResource("add-filter.gif")));
		openButton.setText("Browse");
		clearButton.setIcon(new ImageIcon(getClass().getResource("clear-filter.gif")));
//		flipButton.setIcon(new ImageIcon(getClass().getResource("hide.gif")));
		clearButton.setMargin(new Insets(0,0,0,0));
//		flipButton.setMargin(new Insets(0,0,0,0));
		openButton.setMargin(new Insets(0,0,0,0));
//		myBinaryLabel.setMargin(new Insets(0,0,0,0));
		myBinaryLabel.setPreferredSize(new Dimension(maxImgWidth,maxImgHeight));
		myBinaryLabel.setMinimumSize(new Dimension(maxImgWidth,maxImgHeight));

		add(myBinaryLabel, BorderLayout.WEST);
//		JPanel mainPanel = new JPanel();
		add(pathField,BorderLayout.CENTER);
//		mainPanel.add(pathField);
//		mainPanel.add(openButton);
		JToolBar buttonPanel = new JToolBar();
		buttonPanel.setOpaque(false);
		openButton.setOpaque(false);
		clearButton.setOpaque(false);
		add(buttonPanel,BorderLayout.EAST);
		buttonPanel.setFloatable(false);
		buttonPanel.setBorderPainted(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.add(openButton);
		buttonPanel.add(clearButton);
//		buttonPanel.add(flipButton);
		pathField.setEditable(false);
		openButton.setBorderPainted(false);
		clearButton.setBorderPainted(false);
		openButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				chooseFile();
			}});
		
		clearButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(myProperty!=null) {
					myProperty.setAnyValue((Binary)null);
				} else {
					setBinary(null);
					
				}
			}});
		
		pathField.setTransferHandler(new BinaryTransferHandler(this));
		
//		flipButton.setVisible(false);
	}

	public Property getProperty() {
		return myProperty;
	}

	public void setProperty(Property p) {
		if (p != myProperty) {
			if (myProperty != null) {
				myProperty.removePropertyChangeListener(this);
			}
			myProperty = p;
			if (myProperty != null) {
				myProperty.addPropertyChangeListener(this);
			}
		}

		if (myProperty == null) {
			setBinary(null);
			return;
		}
		setBinary((Binary) p.getTypedValue());
	}

	public void setBinaryValue(final Binary b) {
		if(myProperty!=null) {
			myProperty.setAnyValue(b);
		}
	}
	
	public void setBinary(final Binary b) {
		try {

			if (SwingUtilities.isEventDispatchThread()) {
				setSyncBinary(b);
			} else {
				SwingUtilities.invokeAndWait(new Runnable() {

					public void run() {

						setSyncBinary(b);
					}
				});

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static ImageIcon scale(ImageInputStream infile, ImageOutputStream outfile, int width, int height, boolean keepAspect,
			float quality) throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}

		BufferedImage scaled = scale(width, height, keepAspect, original);
		return new ImageIcon(scaled);

	}

	public static BufferedImage scale(int width, int height, boolean keepAspect, BufferedImage original) {
		System.err.println("width: " + width + " height: " + height);
		int originalWidth = original.getWidth();
		int originalHeight = original.getHeight();
		if (width > originalWidth) {
			width = originalWidth;
		}
		if (height > originalHeight) {
			height = originalHeight;
		}

		float factorX = (float) originalWidth / width;
		float factorY = (float) originalHeight / height;
		if (keepAspect) {
			factorX = Math.max(factorX, factorY);
			factorY = factorX;
		}

		// The scaling will be nice smooth with this filter
		AreaAveragingScaleFilter scaleFilter = new AreaAveragingScaleFilter(Math.round(originalWidth / factorX), Math.round(originalHeight
				/ factorY));
		ImageProducer producer = new FilteredImageSource(original.getSource(), scaleFilter);
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
	}

	public void propertyChange(PropertyChangeEvent e) {
		if ("value".equals(e.getPropertyName())) {
			Binary old = (Binary) e.getOldValue();
			Binary newValue = (Binary) e.getNewValue();
			if (old != null && newValue != null) {
				System.err.println("Old size: " + old.getLength() + " new size: " + newValue.getLength());
			} else {
				System.err.println("Null detected!");
			}
			setBinary(newValue);
		}

	}

	public int getMaxImgHeight() {
		return maxImgHeight;
	}

	public void setMaxImgHeight(int maxImgHeight) {
		this.maxImgHeight = maxImgHeight;
		if (myProperty != null) {
			setProperty(myProperty);
		}
	}

	public int getMaxImgWidth() {
		return maxImgWidth;
	}

	public void setMaxImgWidth(int maxImgWidth) {
		this.maxImgWidth = maxImgWidth;
		if (myProperty != null) {
			setProperty(myProperty);
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		chooseFile();
	}

	/**
	 * 
	 */
	private void chooseFile() {
		try {
			currentPath = null;
			if(!"".equals(pathField.getText())) {
				File current = new File(pathField.getText());
				currentPath = current.getParentFile();
			}
			
			
			JFileChooser jf = new JFileChooser(currentPath);
			jf.showOpenDialog(myBinaryLabel);
			File f = jf.getSelectedFile();
			if (f != null) {
				Binary b = new Binary(f);
				if(myProperty!=null) {
					myProperty.setAnyValue(b);
					setProperty(myProperty);	
				}


			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void setSyncBinary(final Binary b) {
//		removeAll();
		if(b!=null) {
			File file = b.getFile();
			if(file!=null) {
				pathField.setText(file.getAbsolutePath());
				
			} else {
				pathField.setText("-");
			}
		}
		
		if (b == null || b.getLength() <= 0) {
			// System.err.println("Null-binary found!");
//			myBinaryLabel = new JButton();
//			add(myBinaryLabel, BorderLayout.CENTER);
//			((JButton) myBinaryLabel).addActionListener(BinaryComponent.this);
			pathField.setText("");

//			if (myProperty == null) {
//				myBinaryLabel.setEnabled(false);
//			} else {
//				myBinaryLabel.setEnabled(myProperty.isDirIn());
//			}
			myBinaryLabel.setIcon(null);
			myBinaryLabel.setText("");
			// addPropertyComponent(myBinaryLabel, true);
			if(myProperty!=null) {
				myBinaryLabel.setToolTipText(myProperty.getDescription());
			}
			revalidate();
			return;
		}
		// System.err.println("Getting binary data!");
		// byte[] data = b.getData();
		String mime = b.guessContentType();
		if (mime.indexOf("image") != -1) {
			InputStream inp = b.getDataAsStream();
			BufferedImage mm;
			try {
				mm = ImageIO.read(inp);
				// ImageIcon img = new ImageIcon(mm);
				System.err.println("WIDTH: " + maxImgWidth + " height: " + maxImgHeight);
//				myBinaryLabel = new JButton();
				// ((JButton)myBinaryLabel).setUI(new ButtonUI(){
				//	            	
				// });
				// myBinaryLabel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
//				myBinaryLabel.setOpaque(false);
//				myBinaryLabel.setBorder(null);
//				((JButton) myBinaryLabel).addActionListener(BinaryComponent.this);

				myBinaryLabel.setHorizontalAlignment(SwingConstants.CENTER);
				myBinaryLabel.setVerticalAlignment(SwingConstants.CENTER);
				myBinaryLabel.setIcon(getScaled(mm, maxImgWidth, maxImgHeight));
				// ( (BaseLabel) myBinaryLabel).setIcon(img);
//				add(myBinaryLabel, BorderLayout.CENTER);
				invalidate();
			} catch (IOException e) {
				e.printStackTrace();
			}
			 revalidate();
			return;
		}
//		if (mime.indexOf("text") != -1) {
//			myBinaryLabel = new JTextArea();
//			((JTextArea) myBinaryLabel).setText(new String(b.getData()));
//			add(myBinaryLabel, BorderLayout.CENTER);
//			revalidate();
//
//			return;
//		}
		// if (mime.indexOf("text") != -1) {
//		myBinaryLabel = new JButton();
		myBinaryLabel.setText("?");
//		((JButton) myBinaryLabel).addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//			}
//		});
		revalidate();

//		add(myBinaryLabel, BorderLayout.CENTER);
		return;
		// }

	}

}
