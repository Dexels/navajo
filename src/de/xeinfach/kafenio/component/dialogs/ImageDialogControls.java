package de.xeinfach.kafenio.component.dialogs;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.util.Utils;

/**
 * ImageDialogControls displays a Panel with<BR>
 * <UL>
 * <LI>scale textfield</LI>
 * <LI>constrain proportions checkbox</LI>
 * <LI>width and height textfields</LI>
 * <LI>orinal width and original height labels</LI>
 * <LI>border-width selectbox</LI>
 * <LI>alignment selectbox</LI>
 * </UL>
 * and can be read
 * @author Karsten Pawlik
 */
public class ImageDialogControls extends JPanel implements ActionListener, KeyListener {

	private static LeanLogger log = new LeanLogger("ImageDialogControls.class");
	
	public static final int DEFAULT_SCALE = 8;
	public static final String SET_SCALE = "SET_SCALE";
	public static final String SET_ALIGN = "SET_ALIGN";
	public static final String SET_SIZE = "SET_SIZE";
	public static final String[] BORDER_SIZES = 
		new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	public static final String[] ALIGNMENTS = 
		new String[] { "none", "left", "right", "top", "middle", "bottom" };
	public static final String[] SCALE = 
		new String[] { 	"1000", "750", "500", "400", "300", "200", "175", "150", 
						"100", "75", "50", "25", "10", "5"};

	private JComboBox borderSizeList;
	private JComboBox alignmentList;
	private JComboBox imageScale;
	private JLabel originalHeightLabel2;
	private JLabel originalWidthLabel2;
	private JTextField imageHeight;
	private JTextField imageWidth;
	private JCheckBox imageConstrainProps;
	private ImageDialog parent;

	/**
	 * creates a new instance of ImageDialogControls
	 */
	public ImageDialogControls() {}

	/**
	 * creates a new instance of ImageDialogControls and
	 * sets the parent object to the given value.
	 * @param newParent a component.
	 */
	public ImageDialogControls(ImageDialog newParent) {
		parent = newParent;
		initComponents();
	}
	
	/**
	 * initializes this object.
	 */
	private void initComponents() {
		/* create border */
		Border border = BorderFactory.createEmptyBorder(2,2,2,2);
		
		/* creating scale panel */
		imageScale = new JComboBox(SCALE);
		imageScale.addActionListener(this);
		imageScale.setActionCommand(SET_SCALE);
		imageScale.setSelectedIndex(DEFAULT_SCALE);
		JPanel imageScalePanel1 = new JPanel();
		JLabel imageScaleLabel = new JLabel(parent.getString("InsertImageDialogScaleField"));
		imageScalePanel1.setLayout(new GridLayout(1,2));
		imageScalePanel1.add(imageScaleLabel);
		imageScalePanel1.add(imageScale);

		imageConstrainProps = 
			new JCheckBox(parent.getString("InsertImageDialogConstrainPropsField"));
		imageConstrainProps.setSelected(true);
	
		JPanel imageScalePanel = new JPanel();
		imageScalePanel.setLayout(new GridLayout(2,1));
		imageScalePanel.add(imageScalePanel1);
		imageScalePanel.add(imageConstrainProps);

		/* creating width and height property panel */
		imageWidth = new JTextField("");
		imageWidth.addKeyListener(this);
		imageHeight = new JTextField("");
		imageHeight.addKeyListener(this);
		JLabel imageWidthLabel = new JLabel(parent.getString("InsertImageDialogWidthField"));
		JLabel imageHeightLabel = new JLabel(parent.getString("InsertImageDialogHeightField"));

		JPanel imageSizePanel = new JPanel();
		imageSizePanel.setLayout(new GridLayout(2,2));
		imageSizePanel.add(imageWidthLabel);
		imageSizePanel.add(imageWidth);
		imageSizePanel.add(imageHeightLabel);
		imageSizePanel.add(imageHeight);
		
		/* creating original width and height panel */
		JPanel originalSizePanel = new JPanel(new GridLayout(2,2));
		JLabel originalWidthLabel = 
			new JLabel(parent.getString("InsertImageDialogOriginalWidthField"));
		JLabel originalHeightLabel = 
			new JLabel(parent.getString("InsertImageDialogOriginalHeightField"));
		originalWidthLabel2 = new JLabel("");
		originalHeightLabel2 = new JLabel("");
		originalSizePanel.add(originalWidthLabel);
		originalSizePanel.add(originalWidthLabel2);
		originalSizePanel.add(originalHeightLabel);
		originalSizePanel.add(originalHeightLabel2);

		/* creating alignment property panel */
		JLabel alignmentLabel = new JLabel(parent.getString("InsertImageDialogAlignmentField"));
		alignmentList = new JComboBox(ALIGNMENTS);
		alignmentList.addActionListener(this);
		alignmentList.setActionCommand(SET_ALIGN);
		JPanel imageAlignmentPanel = new JPanel();
		imageAlignmentPanel.setLayout(new BoxLayout(imageAlignmentPanel, BoxLayout.X_AXIS));
		imageAlignmentPanel.add(alignmentLabel);
		imageAlignmentPanel.add(alignmentList);

		/* creating border width property panel */
		JLabel borderWidthLabel = new JLabel(parent.getString("InsertImageDialogBorderWidthField"));
		borderSizeList = new JComboBox(BORDER_SIZES);
		borderSizeList.addActionListener(this);
		borderSizeList.setActionCommand(SET_SIZE);
		JPanel borderSizePanel = new JPanel();
		borderSizePanel.setLayout(new BoxLayout(borderSizePanel, BoxLayout.X_AXIS));
		borderSizePanel.add(borderWidthLabel);
		borderSizePanel.add(borderSizeList);

		/* creating the panel with image properties and adding 
		 * all the different property panels onto it */
		JPanel topPropertiesPanel = new JPanel();
		topPropertiesPanel.setBorder(border);
		topPropertiesPanel.setLayout(new BoxLayout(topPropertiesPanel, BoxLayout.Y_AXIS));
		topPropertiesPanel.setBorder(border);
		topPropertiesPanel.add(imageScalePanel);
		topPropertiesPanel.add(imageSizePanel);
		topPropertiesPanel.add(originalSizePanel);
		topPropertiesPanel.add(imageAlignmentPanel);
		topPropertiesPanel.add(borderSizePanel);
		
		setLayout(new BorderLayout());
		add(new JPanel(), BorderLayout.CENTER);
		add(topPropertiesPanel, BorderLayout.SOUTH);
	}

	/**
	 * resets the controls in this panel to the given values 
	 * @param width the original width of the preview image
	 * @param height the original height of the preview image
	 */
	public void setNewImage(int width, int height) {
		try {
			originalHeightLabel2.setText("" + height);
			originalWidthLabel2.setText("" + width);
			imageWidth.setText("" + width);
			imageHeight.setText("" + height);
			imageScale.setSelectedIndex(DEFAULT_SCALE);
			performUpdatePreview();
		} catch (Exception e) {
			log.error("an error ocurred while trying to set new image: " + e.fillInStackTrace());
		}
	}

	/**
	 * calls the method updatePreview() on the parent object.
	 */
	public void performUpdatePreview() {
		parent.updatePreview();
	}

	/**
	 * updates the image scale. 
	 */
	private void updateScale() {
		try {
			BigDecimal scaleInPercent = new BigDecimal("" + imageScale.getSelectedItem());
			if (imageConstrainProps.isSelected()) {
				imageWidth.setText(
					"" + calcScaledLength(new BigDecimal(originalWidthLabel2.getText()), scaleInPercent));
				imageHeight.setText(
					"" + calcScaledLength(new BigDecimal(originalHeightLabel2.getText()), scaleInPercent));
			} else {
				imageWidth.setText("" + calcScaledLength(new BigDecimal(imageWidth.getText()), scaleInPercent));
				imageHeight.setText("" + calcScaledLength(new BigDecimal(imageHeight.getText()), scaleInPercent));
			}
		} catch (Exception e) {
			log.error("An error ocurred while trying to scale the preview image: " + e.fillInStackTrace());
		}
	}

	/**
	 * updates the width, if maintain aspectratio is true, height is automatically updated, too.
	 */
	private void updateWidth() {
		try {
			if (imageConstrainProps.isSelected()) {
				int newHeight = calcAspectRatioLength(new BigDecimal(imageWidth.getText()), false);
				imageHeight.setText("" + newHeight);
			}
		} catch (Exception e) {
			log.error("error while trying to update the image width: " + e.fillInStackTrace());
		}
	}

	/**
	 * updates the height, if maintain aspectratio is true, width is automatically updated, too
	 */
	private void updateHeight() {
		try {
			if (imageConstrainProps.isSelected()) {
				int newWidth = calcAspectRatioLength(new BigDecimal(imageHeight.getText()), true);
				imageWidth.setText("" + newWidth);
			}
		} catch (Exception e) {
			log.error("error while trying to update the image height: " + e.fillInStackTrace());
		}
	}

	/**
	 * @param decimal
	 * @param decimal2
	 * @return
	 */
	private int calcScaledLength(BigDecimal decimal, BigDecimal scalePercent) {
		BigDecimal percentAsDecimal = scalePercent.divide(new BigDecimal("100"), 10, BigDecimal.ROUND_HALF_UP);
		return decimal.multiply(percentAsDecimal).intValue();
	}

	/**
	 * calculates either the height or the width maintaining aspect ratio of the original image.
	 * @param i width or height
	 * @param j original width
	 * @param k original height
	 * @param returnWidth if true, i is the new height. if false, i is the new width
	 * @return returns the calculated height or width as int value. 
	 */
	private int calcAspectRatioLength(BigDecimal i, boolean returnWidth) {
		BigDecimal origHeight = new BigDecimal(originalHeightLabel2.getText());
		BigDecimal origWidth = new BigDecimal(originalWidthLabel2.getText());
		BigDecimal ratio = origHeight.divide(origWidth,10,BigDecimal.ROUND_HALF_UP);
		BigDecimal result;

		if (returnWidth) {
			result = i.divide(ratio, BigDecimal.ROUND_HALF_UP);
		} else {
			result = i.multiply(ratio);
		}

		result.setScale(0,BigDecimal.ROUND_HALF_UP);
		return result.intValue();
	}
	
	/**
	 * returns the complete link for the currently selected image.
	 * @param imageSrc url to create link for.
	 * @param imageAltText alternate text used in the ALT-attribute. if null, no alt tag is included.
	 * @return returns the complete link for the currently selected image.
	 */
	public String getHtmlImgString(String imageAltText, String imageSrc) {
		StringBuffer attrString = new StringBuffer();
		if (Utils.checkNullOrEmpty(imageHeight.getText()) != null) {
			attrString.append("HEIGHT=\"" + imageHeight.getText() + "\" ");
		}
		if (Utils.checkNullOrEmpty(imageWidth.getText()) != null) {
			attrString.append("WIDTH=\"" + imageWidth.getText() + "\" ");
		}
		if (Utils.checkNullOrEmpty(imageAltText) != null) {
			attrString.append("ALT=\"" + imageAltText + "\" ");
		}
		if (Utils.checkNullOrEmpty(""+alignmentList.getSelectedItem()) != null) {
			String theAlign = ALIGNMENTS[alignmentList.getSelectedIndex()];
			if (!theAlign.equals("none")) {
				attrString.append("ALIGN=\"" + theAlign + "\" ");
			}
		}
		String borderSize = null;
		if (!borderSizeList.getSelectedItem().equals("0")) {
			borderSize = borderSizeList.getSelectedItem().toString();
			attrString.append("BORDER=\"" + borderSize + "\" ");
		}
		
		if (imageSrc != null) return "<IMG SRC=" + imageSrc + " " + attrString.toString() + ">";
		return null;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals(SET_ALIGN) || command.equals(SET_SIZE)) performUpdatePreview();
		if (command.equals(SET_SCALE)) { 
			updateScale();
			performUpdatePreview();
		}
	}

	/**
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {}

	/**
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		Object src = e.getSource();
		validate();
		if (src.equals(imageHeight)) updateHeight();
		else if (src.equals(imageWidth)) updateWidth();
		else if (src.equals(imageScale)) updateScale();
		performUpdatePreview();
	}

	/**
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {}
}
