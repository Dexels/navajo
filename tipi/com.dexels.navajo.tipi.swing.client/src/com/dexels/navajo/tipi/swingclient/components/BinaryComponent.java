package com.dexels.navajo.tipi.swingclient.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class BinaryComponent extends JPanel implements PropertyControlled,
		PropertyChangeListener, ActionListener {

	private static final long serialVersionUID = 6097068768465009552L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(BinaryComponent.class);
	
	private Property myProperty = null;
	private JComponent myBinaryLabel = null;

	private int maxImgWidth;
	private int maxImgHeight;

	public BinaryComponent() {
		setLayout(new BorderLayout());
	}

	public Property getProperty() {
		return null;
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

	private void setBinary(final Binary b) {
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
			logger.error("Error: ",e);
		} catch (InvocationTargetException e) {
			logger.error("Error: ",e);
		}
	}

	public static ImageIcon scale(ImageInputStream infile,
			ImageOutputStream outfile, int width, int height,
			boolean keepAspect, float quality) throws IOException {

		BufferedImage original = ImageIO.read(infile);
		if (original == null) {
			throw new IOException("Unsupported file format!");
		}

		BufferedImage scaled = scale(width, height, keepAspect, original);
		return new ImageIcon(scaled);

	}

	public static BufferedImage scale(int width, int height,
			boolean keepAspect, BufferedImage original) {
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
		AreaAveragingScaleFilter scaleFilter = new AreaAveragingScaleFilter(
				Math.round(originalWidth / factorX), Math.round(originalHeight
						/ factorY));
		ImageProducer producer = new FilteredImageSource(original.getSource(),
				scaleFilter);
		ImageGenerator generator = new ImageGenerator();
		producer.startProduction(generator);
		BufferedImage scaled = generator.getImage();
		return scaled;
	}

	private final ImageIcon getScaled(BufferedImage icon, int maxWidth,
			int maxHeight) {
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
				logger.info("Old size: " + old.getLength()
						+ " new size: " + newValue.getLength());
			} else {
				logger.info("Null detected!");
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
		try {
			JFileChooser jf = new JFileChooser();
			jf.showOpenDialog(myBinaryLabel);
			File f = jf.getSelectedFile();
			if (f != null) {
				Binary b = new Binary(f);
				myProperty.setAnyValue(b);
				setProperty(myProperty);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void setSyncBinary(final Binary b) {
		removeAll();
		if (b == null || b.getLength() <= 0) {
			// logger.info("Null-binary found!");
			myBinaryLabel = new JButton();
			add(myBinaryLabel, BorderLayout.CENTER);
			((JButton) myBinaryLabel).addActionListener(BinaryComponent.this);
			if (myProperty == null) {
				myBinaryLabel.setEnabled(false);
			} else {
				myBinaryLabel.setEnabled(myProperty.isDirIn());
			}
			((JButton) myBinaryLabel).setText("<html>-</html>");
			// addPropertyComponent(myBinaryLabel, true);
			return;
		}
		// logger.info("Getting binary data!");
		// byte[] data = b.getData();
		String mime = b.guessContentType();
		if (mime.indexOf("image") != -1) {
			InputStream inp = b.getDataAsStream();
			BufferedImage mm;
			try {
				mm = ImageIO.read(inp);
				// ImageIcon img = new ImageIcon(mm);
				logger.info("WIDTH: " + maxImgWidth + " height: "
						+ maxImgHeight);
				myBinaryLabel = new JButton();
				// ((JButton)myBinaryLabel).setUI(new ButtonUI(){
				//
				// });
				// myBinaryLabel.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
				myBinaryLabel.setOpaque(false);
				myBinaryLabel.setBorder(null);
				((JButton) myBinaryLabel)
						.addActionListener(BinaryComponent.this);

				((JButton) myBinaryLabel)
						.setHorizontalAlignment(SwingConstants.CENTER);
				((JButton) myBinaryLabel)
						.setVerticalAlignment(SwingConstants.CENTER);
				((JButton) myBinaryLabel).setIcon(getScaled(mm, maxImgWidth,
						maxImgHeight));
				// ( (BaseLabel) myBinaryLabel).setIcon(img);
				add(myBinaryLabel, BorderLayout.CENTER);
			} catch (IOException e) {
				logger.error("Error: ",e);
			}
			return;
		}
		if (mime.indexOf("text") != -1) {
			myBinaryLabel = new JTextArea();
			((JTextArea) myBinaryLabel).setText(new String(b.getData()));
			add(myBinaryLabel, BorderLayout.CENTER);
			return;
		}
		// if (mime.indexOf("text") != -1) {
		myBinaryLabel = new JButton();
		((JButton) myBinaryLabel).setText(new String(b.getMimeType()));
		((JButton) myBinaryLabel).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		add(myBinaryLabel, BorderLayout.CENTER);
		return;
		// }
	}

}
