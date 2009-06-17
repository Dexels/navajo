package de.xeinfach.kafenio.action;

import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.text.StyledEditorKit;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: class to toggle the case of the marked letters
 * 
 * @author Karsten Pawlik
 */
public class ToggleCaseAction extends StyledEditorKit.StyledTextAction {

	private static LeanLogger log = new LeanLogger("ToggleCaseAction.class");
	
	private KafenioPanel parentKafenioPanel;
	private String myActionName;
	private boolean toLower = false; 

	/**
	 * creates a new ToggleCaseAction using the given parameters.
	 * @param kafenio the KafenioPanel instance to apply the changes to.
	 * @param actionName the name of the action.
	 */
	public ToggleCaseAction(KafenioPanel kafenio, String actionName) {
		super(actionName);
		parentKafenioPanel = kafenio;
		myActionName = actionName;
		log.debug("created new ToggleCaseAction.");
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
			int startPos = parentTextPane.getSelectionStart();
			int endPos = parentTextPane.getSelectionEnd();
			if (startPos == -1) startPos = 0; 
			if (endPos == -1) endPos = parentTextPane.getText().length();
			editor.select(startPos, endPos);
			String selText = editor.getSelectedText();
			if (toLower) {
				editor.replaceSelection(selText.toLowerCase());
				toLower = false;
			} else {
				editor.replaceSelection(selText.toUpperCase());
				toLower = true;
			}
			parentTextPane.select(startPos, endPos);
			parentTextPane.requestFocus();
			parentKafenioPanel.refreshOnUpdate();
		}
	}
}
