package de.xeinfach.kafenio.component.dialogs;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTML;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.ExtendedHTMLDocument;
import de.xeinfach.kafenio.component.ExtendedHTMLEditorKit;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: This class creates an Image-Chooser Dialog
 * 
 * The dialog for showing an insertable Server Image, allowing preview.
 * @author Howard Kistler, Maxym Mykhalchuk, Karsten Pawlik
 */
public class ImageDialog extends JDialog implements ActionListener {
	
	private static final String ACTION_INSERT = "insert";
	private static final String ACTION_PREVIEW = "preview";
	private static final String ACTION_CANCEL = "cancel";
	private static LeanLogger log = new LeanLogger("ImageDialog.class");
	
	private KafenioPanel kafenio;
	private ExtendedHTMLEditorKit htmlKit;
	private ExtendedHTMLDocument htmlDoc;
	private JEditorPane previewPane;
	private Vector names;
	private Vector images;
	private String previewImage;
	private String selectedImage;
	private JList imageList;
	private JTextField imageAltText;
	private ImageDialogControls controls;
	
	/** 
	 * Creating a dialog and showing it modally
	 * @param newKafenio parent KafenioPanel
	 * @param imageNames image names
	 * @param imagePaths image paths.
	 */
	public ImageDialog(KafenioPanel newKafenio, Vector imageNames, Vector imagePaths) {
		super(newKafenio.getFrame(), newKafenio.getTranslation("InsertServerImageDialogTitle"), true);
		
		this.kafenio = newKafenio;
		this.names = imageNames;
		this.images = imagePaths;
		selectedImage="";
		
		initComponents();
	}

	
	/** 
	 * Invoked when user presses any key in the dialog
	 * @param e the action event to process. 
	 */
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (e.getActionCommand().equals(ACTION_INSERT)) {
			ListSelectionModel sm = imageList.getSelectionModel();
			if (sm.isSelectionEmpty()) {
				SimpleInfoDialog sidAbout = new SimpleInfoDialog(
					kafenio, 
					getString("Error"), 
					true, 
					getString("InsertImageDialog_NoImageSelectedText"), 
					SimpleInfoDialog.ERROR);
				imageList.requestFocus();
			} else {
				updatePreview();
				selectedImage = controls.getHtmlImgString(imageAltText.getText(), previewImage);
				hide();
			}
		} else if (e.getActionCommand().equals(ACTION_CANCEL)) {
			hide();
		}
	}
	
	/** 
	 * Creating all the User controls 
	 */
	private void initComponents() {
		/* create default border */
		Border theBorder = BorderFactory.createEmptyBorder(2,2,2,2);
		
		/* Creating the list of images */
		imageList = new JList(names);
		imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		imageList.clearSelection();
		ListSelectionModel lsm = imageList.getSelectionModel();
		
		/* Create the html preview panel */
		previewPane = new JEditorPane();
		previewPane.setEditable(false);
		htmlKit = new ExtendedHTMLEditorKit();
		htmlKit.setDefaultCursor(new Cursor(Cursor.TEXT_CURSOR));
		htmlDoc = getDefaultDocument();
		previewPane.setCaretPosition(0);
		previewPane.setEditorKit(htmlKit);
		previewPane.setDocument(htmlDoc);
		previewPane.setMargin(new Insets(4, 4, 4, 4));
		
		lsm.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					loadSelectedPreviewImage();
				}
			}
		});

		JScrollPane imageScrollPane = new JScrollPane(imageList);
		imageScrollPane.setPreferredSize(new Dimension(200,250));
		imageScrollPane.setMaximumSize(new Dimension(200,250));
		imageScrollPane.setAlignmentX(LEFT_ALIGNMENT);
		
		JScrollPane previewViewport = new JScrollPane(previewPane);
		previewViewport.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		previewViewport.setPreferredSize(new Dimension(250,250));

		/* adding the list of images and the preview onto JSplitPane */
		JSplitPane splitterPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,true,
			imageScrollPane, previewViewport);
		JPanel centerPanel=new JPanel(new BorderLayout());
		centerPanel.add(splitterPanel, BorderLayout.CENTER);
		centerPanel.setBorder(BorderFactory.createTitledBorder(
			getString("InsertImageDialogServerImagesBorder")));
		
		/* creating the panel for alternative text */
		JLabel imageAltTextLabel = 
			new JLabel(getString("InsertImageDialogAlternateTextField"), SwingConstants.LEFT);
		imageAltText = new JTextField("");
		imageAltText.addActionListener(this);

		JPanel altPanel2 = new JPanel();
		altPanel2.setBorder(theBorder);
		altPanel2.setLayout(new BorderLayout());
		altPanel2.add(imageAltTextLabel, BorderLayout.WEST);
		altPanel2.add(imageAltText, BorderLayout.CENTER);
		
		JPanel altPanel = new JPanel(new GridLayout());
		altPanel.add(altPanel2);

		/* create image dialog controls object */
		controls = new ImageDialogControls(this);

		/* creating action buttons */
		JButton insertButton = new JButton(getString("InsertImageDialogInsertButton"));
		insertButton.setActionCommand(ACTION_INSERT);
		insertButton.addActionListener(this);
		
		JButton cancelButton = new JButton(getString("DialogCancel"));
		cancelButton.setActionCommand(ACTION_CANCEL);
		cancelButton.addActionListener(this);
		
		/* and adding them onto the panel */
		JPanel buttonPanel= new JPanel(new FlowLayout(FlowLayout.CENTER,5,0));
		buttonPanel.setBorder(theBorder);
		buttonPanel.add(insertButton);
		buttonPanel.add(cancelButton);
		
		/* adding the properties panel to the east side of the dialog */
		JPanel mainPane = new JPanel(new BorderLayout());
		mainPane.add(centerPanel, BorderLayout.CENTER);
		mainPane.add(controls, BorderLayout.EAST);
		
		/* adding the panels to Content Pane */
		getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
		getContentPane().add(mainPane);
		getContentPane().add(altPanel);
		getContentPane().add(buttonPanel);

		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		this.pack();
		setVisible(true);
	}
	
	/** 
	 * Called when there's a need to preview an image 
	 */
	private void loadSelectedPreviewImage() {
		ListSelectionModel sm = imageList.getSelectionModel();
		if (!sm.isSelectionEmpty()) {
			previewImage = (String)images.get(sm.getMinSelectionIndex());
			try {
				log.debug("trying to display preview image: " + previewImage);
				ImageIcon imageIcon = new ImageIcon(new URL(previewImage));
				controls.setNewImage(imageIcon.getIconWidth(), imageIcon.getIconHeight());
			} catch(Exception ex) {
				log.error("Exception previewing image " + ex.fillInStackTrace());
			}
		}
	}

	/** 
	 * This function is called by dialog executee to know what image was selected. 
	 * @return a selected image 
	 */
	public String getSelectedImage() {
		return selectedImage;
	}

	/**
	 * updates the preview image using the values from ImageDialogControls object.
	 */
	public void updatePreview() {
		try {
			htmlDoc = getDefaultDocument();
			previewPane.setDocument(htmlDoc);
			String imgString = controls.getHtmlImgString(imageAltText.getText(), previewImage);
			htmlKit.insertHTML(htmlDoc, 0, imgString, 0, 0, HTML.Tag.IMG);
			validate();
		} catch (Exception e) {
			log.error("an error ocurred while updating the preview image: " + e.fillInStackTrace());
		}
	}

	private ExtendedHTMLDocument getDefaultDocument() {
		try {
			return (ExtendedHTMLDocument) (htmlKit.createDefaultDocument(new URL(kafenio.getConfig().getCodeBase())));
		} catch (Exception e) {
			return (ExtendedHTMLDocument) (htmlKit.createDefaultDocument());
		}
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	public String getString(String stringToTranslate) {
		return kafenio.getTranslation(stringToTranslate);
	}
	
}
