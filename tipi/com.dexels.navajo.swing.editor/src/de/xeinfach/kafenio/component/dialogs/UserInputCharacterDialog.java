package de.xeinfach.kafenio.component.dialogs;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.Color;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import java.awt.Container;
import javax.swing.WindowConstants;

/**
 * Description: This class creates a special character input dialog.
 * 
 * @author Karsten Pawlik
 */
public class UserInputCharacterDialog extends JDialog implements ActionListener {
	
	private static LeanLogger log = new LeanLogger("UserInputCharacterDialog.class");

	private KafenioPanel parentKafenioPanel;
	private String[] labels = null;
	private JPanel charPanel = null;
	private String specialChar = null;

	/**
	 * creates a new UserInputCharacterDialog using the given parameters.
	 * @param parent reference to a KafenioPanel Instance
	 * @param title window title
	 * @param bModal boolean value
	 */
	public UserInputCharacterDialog(KafenioPanel parent, String title, boolean bModal) {		
		super(parent.getFrame(), title, bModal);
		parentKafenioPanel = parent;
		init();
	}

   	/**
   	 * handles the given action event.
   	 * @param e ActionEvent to handle
   	 */
   	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("accept")) {
			setVisible(false);
		}	
	  	if(e.getActionCommand().equals("cancel")) {
			specialChar = null;
			setVisible(false);
		}
	}

	/**
	 * initializes this object.
	 */
	public void init() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		setBounds(100,100,400,300);
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

		labels = new String[117];

		for (int i = 0; i < 96; i++) labels[i] = "" + ((char) (160 + i));

		labels[96] = "" + ((char) 338);
		labels[97] = "" + ((char) 339);
		labels[98] = "" + ((char) 352);
		labels[99] = "" + ((char) 353);
		labels[100] = "" + ((char) 376);
		labels[101] = "" + ((char) 402);
		labels[102] = "" + ((char) 8211);
		labels[103] = "" + ((char) 8212);
		labels[104] = "" + ((char) 8216);
		labels[105] = "" + ((char) 8217);
		labels[106] = "" + ((char) 8218);
		labels[107] = "" + ((char) 8220);
		labels[108] = "" + ((char) 8221);
		labels[109] = "" + ((char) 8222);
		labels[110] = "" + ((char) 8224);
		labels[111] = "" + ((char) 8225);
		labels[112] = "" + ((char) 8226);
		labels[113] = "" + ((char) 8230);
		labels[114] = "" + ((char) 8240);
		labels[115] = "" + ((char) 8364);
		labels[116] = "" + ((char) 8482);

		GridLayout grid = new GridLayout(12, 10);
		grid.setHgap(2);
		grid.setVgap(2);

		charPanel = new JPanel(grid);
		charPanel.setBorder(BorderFactory.createTitledBorder( 
							BorderFactory.createBevelBorder(
							BevelBorder.LOWERED), "Special Characters"));
				
		for (int i = 0; i < labels.length; i++) charPanel.add(new MyButton(labels[i]));

		JPanel buttonPanel= new JPanel();	  	

		JButton saveButton = new JButton(parentKafenioPanel.getTranslation("InsertCharacterLabel"));
		saveButton.setActionCommand("accept");
		saveButton.addActionListener(this);

		JButton cancelButton = new JButton(parentKafenioPanel.getTranslation("Cancel"));
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		contentPane.add(charPanel, BorderLayout.NORTH);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
   	}

	/**
	 * @return returns the currently selected character.
	 */
	public String getCharacter() {
		return specialChar;
	}

	/**
	 * Inner class that extends JButton. Each special character button
	 * is an instance of this class. This allows for customized handling
	 * of buttons. Ie. in this case, when a button is clicked, it simple has
	 * it's background colour changed to reflect which special character will
	 * be inserted.
	 */
	protected class MyButton extends JButton implements ActionListener, FocusListener {

		/**
		 * Construct this button with the given special character as a label.
		 * @param label The special character to be inserted if this button is clicked.
		 */
		public MyButton(String label) {
			super(label);

			this.setMargin(new Insets(0, 0, 0, 0));
			this.setFocusPainted(false);
			addActionListener(this);
			addFocusListener(this);
		}

		/**
		 * handles the given ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			specialChar = this.getText();
		}

		/**
		 * handles the given FocusEvent
		 */
		public void focusGained(FocusEvent e) {
			this.setBackground(Color.yellow);
		}

		/**
		 * handles the given FocusEvent
		 */
		public void focusLost(FocusEvent e) {
			this.setBackground(null);
		}
	}
}

