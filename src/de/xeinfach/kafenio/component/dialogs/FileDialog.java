package de.xeinfach.kafenio.component.dialogs;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.NameValuePair;
import de.xeinfach.kafenio.util.LeanLogger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import java.util.Vector;

/**
 * Description: This class shows a File open/save dialog.
 * 
 * @author Howard Kistler
 */
public class FileDialog extends JDialog implements ActionListener {

	private static LeanLogger log = new LeanLogger("FileDialog.class");

	private KafenioPanel parentKafenioPanel;
	private JList fileList;
	private String fileDir = "";
	private Vector files;
	private String selectedFile;

	/**
	 * constructs a new FileDialog. the List displays the names of the NameValuePair-Objects in the given Vector.
	 * @param kafenioPanel a reference to a KafenioPanel instance.
	 * @param newFileDir Directory-prefix
	 * @param newFileList Vector of NameValuePair-Objects containing name/value-pairs.
	 * @param title window title
	 * @param modal boolean value.
	 */
	public FileDialog(KafenioPanel kafenioPanel, String newFileDir, Vector newFileList, String title, boolean modal) {
		super(kafenioPanel.getFrame(), title, modal);
		if (newFileDir != null && !newFileDir.equals("")) newFileDir = newFileDir + "/";
		files = newFileList;
		parentKafenioPanel = kafenioPanel;
		init();
	}

	/**
	 * constructs a new FileDialog. the List displays the names of the Strings in the given String[]-array.
	 * @param kafenioPanel a reference to a KafenioPanel instance.
	 * @param newFileDir Directory-prefix
	 * @param newFileList Vector of NameValuePair-Objects containing name/value-pairs.
	 * @param title window title
	 * @param modal boolean value.
	 */
	public FileDialog(KafenioPanel kafenioPanel, String newFileDir, String[] newFileList, String title, boolean modal) {
		this(kafenioPanel, newFileDir, arrayToVector(newFileList), title, modal);
	}

	private static Vector arrayToVector(String[] newFileList) {
		Vector myFiles = new Vector();
		if (newFileList != null) {
			for (int i=0; i < newFileList.length; i++) {
				myFiles.add(new NameValuePair(newFileList[i]));
			}
		}
		return myFiles;
	}

	/**
	 * handles the given ActionEvent.
	 * @param e an ActionEvent to handle
	 */
   	public void actionPerformed(ActionEvent e) {
	  	if(e.getActionCommand().equals("save")) {
			hide();
		} else if(e.getActionCommand().equals("cancel")) {
			selectedFile = null;
			hide();
		}
	}

	/**
	 * initializes the FileDialog
	 */
	public void init() {
	  	selectedFile="";

	  	Container contentPane = getContentPane();
	  	contentPane.setLayout(new BorderLayout());

	  	setBounds(100,100,400,500);
	  	setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        fileList = new JList(files);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.clearSelection();

        ListSelectionModel listSelector = fileList.getSelectionModel();
		listSelector.addListSelectionListener(new ListSelectionListener() 
		{
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()) {
	  				ListSelectionModel sm = fileList.getSelectionModel();
	  				if(!sm.isSelectionEmpty()) {
						selectedFile = ((NameValuePair) files.get(sm.getMinSelectionIndex())).getValue();
					}
				}
			}
		});

    	JScrollPane fileScrollPane = new JScrollPane(fileList);
	  	fileScrollPane.setAlignmentX(LEFT_ALIGNMENT);

	  	JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
	  	centerPanel.add(fileScrollPane);
	  	centerPanel.setBorder(BorderFactory.createTitledBorder("Files"));

		JPanel buttonPanel= new JPanel();

	  	JButton saveButton = new JButton("Accept");
	  	saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		JButton cancelButton = new JButton("Cancel");
	  	cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);

		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		contentPane.add(centerPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
	  	setVisible(true);
    }

    /**
     * @return returns the path to the currently selected file.
     */
    public String getSelectedFile() {
	  if(selectedFile != null) {
	  	selectedFile = fileDir + selectedFile;
	  }
	  return selectedFile;
    }
}
