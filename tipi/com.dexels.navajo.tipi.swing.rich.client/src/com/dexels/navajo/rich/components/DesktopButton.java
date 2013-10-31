package com.dexels.navajo.rich.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.RootPaneContainer;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class DesktopButton extends JButton {
	private static final long serialVersionUID = 2510421764465922070L;
	private ImageIcon myIcon;
	private int reflectionSize = 12;
	private boolean mouseover = false;
	private boolean animating = false;
	private int text_offset_x = 5;
	private int text_offset_y = 3;
	private int text_b_offset_y = 2;
	private int icon_offset_x = 15;
	private int fontsize_a = 17;
	private int fontsize_b = 12;
	private Font textFont = new Font(Font.DIALOG, Font.BOLD, fontsize_a);
	private Font subTextFont = new Font(Font.DIALOG, Font.PLAIN, fontsize_b);
	private float glow = 0.7f;
	private float max_opacity = .7f;
	private String toolTip = "";
	private boolean showSpring = true;
	int animation_speed = 250;

	public DesktopButton(URL imagepath, String text) {
		this(imagepath);
		setText(text);
		setForeground(Color.white);
	}

	public void setShowSpring(boolean b) {
		this.showSpring = b;
	}

	public void setIconUrl(URL iconpath) {
		myIcon = new ImageIcon(iconpath);
	}

	public void setSubText(String text) {
		this.toolTip = text;
	}
		
	public void setTextFont(Font f){
		this.textFont = f;		
	}
	
	public void setSubTextFont(Font f){
		this.subTextFont = f;		
	}

	public DesktopButton(URL imagepath) {
		this();
		setIconUrl(imagepath);
	}
	
	@Override
	public void setEnabled(boolean enabled){
		super.setEnabled(enabled);
		setShowSpring(enabled);		
	}

	public DesktopButton() {

		setBorderPainted(false);
		setOpaque(false);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseover = true;
				if (!animating && isEnabled()) {
					animate(true);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mouseover = false;
				if (!animating && isEnabled()) {
					animate(false);
				}
			}
		});

		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fireSpring();
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
			
		FontMetrics mA = getFontMetrics(textFont);
		FontMetrics mB = getFontMetrics(subTextFont);

		// Determine WIDTH
		int width = icon_offset_x + text_offset_x;
		if (myIcon != null) {
			width += myIcon.getIconWidth();
		}
		int text_width = 0;
		if (getText() != null) {
			text_width = mA.charsWidth(getText().toCharArray(), 0, getText().length());
		}

		int tooltip_width = 0;
		if (toolTip != null) {
			tooltip_width = mB.charsWidth(toolTip.toCharArray(), 0, toolTip.length());
		}
		text_width = Math.max(text_width, tooltip_width);
		width += text_width;

		// Determine HEIGHT
		int height = text_offset_y + text_b_offset_y;
		if (toolTip != null || !"".equals(toolTip)) {
			height += mB.getHeight();
		}
		if (getText() != null || !"".equals(getText())) {
			height += mA.getHeight();
		}
		int ic = reflectionSize;
		if (myIcon != null) {
			ic += myIcon.getIconHeight();
		}
		height = Math.max(height, ic);
		// Return Dimension (WIDTH x HEIGHT)
		return new Dimension(width, height);
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Dimension bounds = getPreferredSize();

		BufferedImage buffer = new BufferedImage(bounds.width, bounds.height - reflectionSize, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gBuf = buffer.createGraphics();

		buffer = new BufferedImage(bounds.width, bounds.height - reflectionSize, BufferedImage.TYPE_INT_ARGB);
		gBuf = buffer.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(isEnabled()? getForeground(): Color.lightGray);
		g2.setFont(textFont);
		g2.setComposite(AlphaComposite.SrcOver.derive(Math.min(1.0f, 1.05f * glow)));

		g2.drawString(getText(), (myIcon != null ? myIcon.getIconWidth() : 0) + icon_offset_x + text_offset_x, text_offset_y + textFont.getSize());
		g2.setFont(subTextFont);

		if (toolTip != null && !"".equals(toolTip)) {
			g2.drawString(toolTip, (myIcon != null ? myIcon.getIconWidth() : 0) + icon_offset_x + text_offset_x, text_offset_y
					+ text_b_offset_y + 2 * textFont.getSize());
		}
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (myIcon != null) {
			gBuf.drawImage(myIcon.getImage(), icon_offset_x, 0, null);
		}
	
		if(!isEnabled()){
			BufferedImage gray = GrayScaleTransform.getFadedImage(buffer, 1f);			
			g2.drawImage(createReflection(gray), 0, 0, gray.getWidth(), gray.getHeight() + reflectionSize, null);			
		}else{
			g2.drawImage(createReflection(buffer), 0, 0, buffer.getWidth(), buffer.getHeight() + reflectionSize, null);
		}
		

	}

	private void animate(boolean direction) {
		if (direction) {
			Animator animator = PropertySetter.createAnimator(animation_speed, this, "glow", max_opacity, 1.0f);
			animator.addTarget(new TimingTargetAdapter() {
				@Override
				public void end() {
					animating = false;
					if (!mouseover) {
						animate(false);
					}
				}
			});
			animator.setAcceleration(0.5f);
			animator.setDeceleration(0.2f);
			animator.start();
			animating = true;
		} else {
			Animator animator = PropertySetter.createAnimator(animation_speed, this, "glow", 1.0f, max_opacity);
			animator.addTarget(new TimingTargetAdapter() {
				@Override
				public void end() {
					animating = false;
					if (mouseover) {
						animate(true);
					}
				}
			});
			animator.setAcceleration(0.5f);
			animator.setDeceleration(0.2f);
			animator.start();
			animating = true;
		}
	}

	public void setGlow(float f) {
		this.glow = f;
		repaint();
	}

	public float getGlow() {
		return this.glow;
	}
	
	public void setSpeed(int speed){
		animation_speed = speed;
	}
	
	public void setOpacity(float opacity){
		this.max_opacity = opacity;
		this.glow = opacity;
	}

	public void fireSpring() {
		if (showSpring) {
			SpringGlassPane glassPane = new SpringGlassPane();
			((RootPaneContainer) getTopLevelAncestor()).getRootPane().setGlassPane(glassPane);
			glassPane.setSize(((RootPaneContainer) getTopLevelAncestor()).getRootPane().getWidth(),
					((RootPaneContainer) getTopLevelAncestor()).getRootPane().getHeight());

			SpringGlassPane cc = (SpringGlassPane) ((RootPaneContainer) getTopLevelAncestor()).getGlassPane();
			cc.setVisible(true);
			BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			// boolean bb = isBorderPainted();
			// setBorderPainted(false);
			paintComponent(bi.getGraphics());
			// setBorderPainted(bb);
			Rectangle bounds = getBounds();
			Point location = new Point(0, 0);
			location = SwingUtilities.convertPoint(this, location, ((RootPaneContainer) getTopLevelAncestor()).getRootPane());
			bounds.setLocation(location);
			cc.showSpring(bounds, bi);
		}
	}

	private BufferedImage createReflection(BufferedImage img) {
		int height = img.getHeight();
		BufferedImage result = new BufferedImage(img.getWidth(), (height + reflectionSize), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = result.createGraphics();

		g2.drawImage(img, 0, 0, null);
		g2.scale(1.0, -1.0);
		g2.drawImage(img, 0, -height - height, null);
		g2.scale(1.0, -1.0);
		g2.translate(0, height);

		GradientPaint mask = new GradientPaint(0, 0, new Color(1.0f, 1.0f, 1.0f, 0.5f), 0, reflectionSize,
				new Color(1.0f, 1.0f, 1.0f, 0.0f));
		g2.setPaint(mask);
		g2.setComposite(AlphaComposite.DstIn);
		g2.fillRect(0, 0, img.getWidth(), reflectionSize);
		g2.dispose();
		return result;
	}

	public static ConvolveOp getGaussianBlurFilter(int radius, boolean horizontal) {
		if (radius < 1) {
			throw new IllegalArgumentException("Radius must be >= 1");
		}

		int size = radius * 2 + 1;
		float[] data = new float[size];

		float sigma = radius / 3.0f;
		float twoSigmaSquare = 2.0f * sigma * sigma;
		float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
		float total = 0.0f;

		for (int i = -radius; i <= radius; i++) {
			float distance = i * i;
			int index = i + radius;
			data[index] = (float) Math.exp(-distance / twoSigmaSquare) / sigmaRoot;
			total += data[index];
		}

		for (int i = 0; i < data.length; i++) {
			data[i] /= total;
		}

		Kernel kernel = null;
		if (horizontal) {
			kernel = new Kernel(size, 1, data);
		} else {
			kernel = new Kernel(1, size, data);
		}
		return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	}

}
