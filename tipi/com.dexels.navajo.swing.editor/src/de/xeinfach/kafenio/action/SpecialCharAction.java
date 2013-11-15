package de.xeinfach.kafenio.action;

import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLDocument;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.component.dialogs.SimpleInfoDialog;
import de.xeinfach.kafenio.component.dialogs.UserInputCharacterDialog;

/**
 * Description: class to insert special characters
 * 
 * @author Karsten Pawlik
 */
public class SpecialCharAction extends StyledEditorKit.StyledTextAction {

	private static LeanLogger log = new LeanLogger("SpecialCharAction.class");
	
	private KafenioPanel parentKafenioPanel;
	private String myActionName;
	private String specialChar;
	
	/**
	 * Creates a new SpecialCharAction Object using the given values.
	 * @param kafenio the KafenioPanel to apply the changes to.
	 * @param actionName the name of the SpecialCharAction.
	 */
	public SpecialCharAction(KafenioPanel kafenio, String actionName) {
		super(actionName);
		parentKafenioPanel = kafenio;
		myActionName = actionName;
		log.debug("new SpecialCharAction created.");
	}
	
	/**
	 * handles the given ActionEvent.
	 * @param e the ActionEvent to handle
	 */
	public void actionPerformed(ActionEvent e) {
		if(!(this.isEnabled())) {
			return;
		}
		JEditorPane editor = getEditor(e);
		if(editor != null) {
			JTextPane parentTextPane = parentKafenioPanel.getTextPane();
			int pos = parentTextPane.getCaretPosition();
			if (pos == -1) pos = 0;

			UserInputCharacterDialog uidInput = 
				new UserInputCharacterDialog(	parentKafenioPanel, 
												getString("InsertCharacterDialogTitle"), 
												true);

			String character = uidInput.getCharacter();
			uidInput.dispose();

			if(character != null && !character.equals("")) {
				HTMLDocument doc = (HTMLDocument) parentTextPane.getDocument();
				try {
					doc.insertString(pos, character, null);	
				} catch (BadLocationException e1) {
					SimpleInfoDialog sidWarn = 
						new SimpleInfoDialog(	parentKafenioPanel, 
												"", 
												true, 
												getString("ErrorNoTextSelected"), 
												SimpleInfoDialog.ERROR);
				}
			} else {
				parentKafenioPanel.repaint();
			}
		}
	}

	/**
	 * returns a translated representation of the given string.
	 * @param stringToTranslate the string to translate.
	 * @return returns a translated representation of the given string.
	 */
	private String getString(String stringToTranslate) {
		return parentKafenioPanel.getTranslation(stringToTranslate);
	}
}
