package de.xeinfach.kafenio.component.dialogs;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: This class contains common methods for all KafenioDialogs
 *  
 * @author Karsten Pawlik
 */
public abstract class AbstractKafenioDialog extends JDialog {

	private static LeanLogger log = new LeanLogger("AbstractKafenioDialog.class");
	
	private JOptionPane jOptionPane;
	private KafenioPanel parentKafenio;
	private Object[] buttonLabels;
	private int type = -1;
	private int optionType = -1;
	
	/**
	 * @param owner parent frame
	 * @param title frame title
	 * @param modal is a modal window?
	 */
	public AbstractKafenioDialog(KafenioPanel owner, String title, boolean modal) {
		super(owner.getFrame(), title, modal);
		parentKafenio = owner;
		log.debug("new AbstractKafenioDialog created.");
	}

	/**
	 * initializes the AbstractKafenioDialog Object using the given values.
	 * @param panelContents contents of the Dialop JOptionPane
	 */
	public void init(Object[] panelContents) {
		init(panelContents, null, null, null);
	}

	/**
	 * initializes the AbstractKafenioDialog Object using the given values.
	 * @param panelContents contents of the Dialog JOptionPane
	 * @param newButtonLabels button labels
	 * @param newOptionType option types
	 * @param newType panel type
	 */
	public void init(Object[] panelContents, String[] newButtonLabels, Integer newOptionType, Integer newType) {

		buttonLabels = getButtonLabels(newButtonLabels);
		type = getType(newType);
		optionType = getOptionType(newOptionType);
		
		log.debug("set labels, type and optionType, creating new JOptionPane");
				
		jOptionPane = new JOptionPane(	panelContents, 
										type, 
										optionType, 
										null, 
										buttonLabels, 
										buttonLabels[0]);

		setContentPane(jOptionPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				jOptionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		getJOptionPane().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();
				if(	isVisible() 
					&& (e.getSource() == getJOptionPane())
					&& (prop.equals(JOptionPane.VALUE_PROPERTY) || prop.equals(JOptionPane.INPUT_VALUE_PROPERTY)))
				{
					Object value = getJOptionPane().getValue();
					if(value == JOptionPane.UNINITIALIZED_VALUE) {
						return;
					}

					if(value != buttonLabels[0]){
						getJOptionPane().setValue(parentKafenio.getTranslation("DialogCancel"));
					}
//					getJOptionPane().setValue(JOptionPane.UNINITIALIZED_VALUE);

					setDefaultValues(value);
				}
			}

		});
		
		this.pack();
		this.show();
	}

	/**
	 * abstract method that sets the default values for this dialog
	 * @param value currently processed value.
	 */
	public abstract void setDefaultValues(Object value);

	/**
	 * @return returns the button labels as Object[]
	 */
	public Object[] getButtonLabels() {
		return buttonLabels;
	}

	/**
	 * @return returns the used JOptionPane Object
	 */
	public JOptionPane getJOptionPane() {
		return jOptionPane;
	}

	/**
	 * @param objects sets the button labels
	 */
	public void setButtonLabels(Object[] objects) {
		buttonLabels = objects;
	}

	/**
	 * @param pane sets the JOptionPane used in this dialog.
	 */
	public void setJOptionPane(JOptionPane pane) {
		jOptionPane = pane;
	}

	/**
	 * if input is null, the default value is returned.<BR>
	 * if input is not null, the input as int value is returned.<BR>
	 * @param newOptionType an Integer value.
	 * @return int value
	 */
	private int getOptionType(Integer newOptionType) {
		if (newOptionType == null) { 
			return JOptionPane.OK_CANCEL_OPTION;
		} else {
			return newOptionType.intValue();
		}
	}

	/**
	 * if input type is null, the default value is returned.<BR>
	 * if input type is not null, the input value as int is returned.<BR>
	 * @param newType an Integer value
	 * @return int value.
	 */
	private int getType(Integer newType) {
		if (newType == null) { 
			return JOptionPane.QUESTION_MESSAGE;
		} else {
			return newType.intValue();
		}
	}

	/**
	 * if input is null, the default button labels are returned.<BR>
	 * if input is not null, the input is returned.
	 * @param newButtonLabels Object[] containing button labels as strings.
	 * @return Object[] containing button labels
	 */
	private Object[] getButtonLabels(Object[] newButtonLabels) {
		if (newButtonLabels == null) {
			return new Object[] {	parentKafenio.getTranslation("DialogAccept"), 
									parentKafenio.getTranslation("DialogCancel")};
		} else {
			return newButtonLabels;
		}
	}
}
