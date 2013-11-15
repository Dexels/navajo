package de.xeinfach.kafenio.component.dialogs;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.util.Utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * class that displays the Hyperlink insertion dialog.
 * @author Karsten Pawlik, Maxym Mykhalchuk
 */
public class HyperlinkDialog extends JDialog implements ActionListener {
	public static final int INSERT = 0;
	public static final int REMOVE_LINK = 2;
	public static final int CANCEL = 1;
    
	private JButton insertServerUrlButton;
	private static LeanLogger log = new LeanLogger("HyperlinkDialog.class");
	private KafenioPanel parentKafenio;
	private String url;
	private String target;
	private int option;
		
	private JTextField nameField;
	private JTextField urlField;
	private JComboBox targetField;
	private Object[] controls;
	private Object[] options;
	private Vector targets;
	/** 
     * Creates a new instance of HyperlinkAction
     * @param kafenio the parent KafenioPanel object.
     * @param currentUrl currently set URL
     * @param currentTarget currently set Target. 
     */
    public HyperlinkDialog(	KafenioPanel kafenio,
    						String currentUrl,
    						String currentTarget) 
    {
		super();
        parentKafenio = kafenio;
        url = currentUrl;
        target = currentTarget;
		log.debug("new HyperlinkDialog created: " + url + ";" + target);
        init();
    }
    
    /** 
     * The method, which creates and displays the dialog window.
     */
    public void init() {
	                
		nameField = new JTextField(20);
		nameField.setText(parentKafenio.getTextPane().getSelectedText());
        
		urlField = new JTextField(20);
		urlField.setText(url);
		
		insertServerUrlButton = new JButton(getString("ServerFiles"));
		insertServerUrlButton.setActionCommand("openInsertServerDialog");
		insertServerUrlButton.addActionListener(this);
		
		targets = new Vector(Arrays.asList(new String[] {"", "_new", "_blank", "_parent", "top"}));
        if( targets.indexOf(target) == -1 ) {
            targets.add(0,target);
		}
		targetField = new JComboBox(targets);
		targetField.setSelectedIndex(targets.indexOf(target));
        targetField.setEditable(true);
        
        controls = new Object[] {
		            new JLabel(getString("InsertAnchorTextField")),
		            nameField,
		            new JLabel(getString("InsertAnchorURLField")),
		            urlField,
		            insertServerUrlButton,
		            new JLabel(getString("InsertAnchorTargetField")),
		            targetField
		        };

		if( Utils.checkNullOrEmpty(url) != null ) {
			options = new String[] {
				getString("InsertAnchorInsertLinkButton"), 
				getString("DialogCancel"), 
				getString("InsertAnchorRemoveLinkButton")};
		} else {
			options = new String[] {
				getString("InsertAnchorInsertLinkButton"), 
				getString("DialogCancel")};
		}

		option = JOptionPane.showOptionDialog(	parentKafenio, 
												controls, 
												getString("AnchorDialogTitle"), 
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.PLAIN_MESSAGE,
												null,
												options, 
												options[0]);
		switch(option) {
			case 0: // insert link
				url = urlField.getText();
				if (targetField.getSelectedIndex() > -1) {
					target = (String)targets.get(targetField.getSelectedIndex());
				}
				log.debug("insert button pressed.");
				break;
			case 1: // cancel
				log.debug("cancel button pressed.");
				break;
			case 2: // remove link
				url = "";
				target = "";
				log.debug("remove button pressed.");
				break;
			case JOptionPane.CLOSED_OPTION:
				log.debug("close dialog.");
				break;
		}
	}
		
	/**
	 * @return returns the currently selected target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @return returns the currently selected url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param string sets the target
	 */
	public void setTarget(String string) {
		target = string;
	}

	/**
	 * @param string sets the url
	 */
	public void setUrl(String string) {
		url = string;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("openInsertServerDialog")) {
			urlField.setText(parentKafenio.insertFile());
		}
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	private String getString(String stringToTranslate) {
		return parentKafenio.getTranslation(stringToTranslate);
	}

	/**
	 * @return returns the id of the action as specified using the constants in this class.
	 */
	public int getAction(){
		return option;
	}

}
