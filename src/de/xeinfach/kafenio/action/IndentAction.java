package de.xeinfach.kafenio.action;

import java.awt.event.ActionEvent;

import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.CSS;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.util.LeanLogger;

/**
 * Description: class to handle indenting / de-indenting of the current paragraph
 * 
 * @author Karsten Pawlik
 */
public class IndentAction extends StyledEditorKit.StyledTextAction {

	private static LeanLogger log = new LeanLogger("IndentAction.class");
	
	private KafenioPanel parentKafenioPanel;
	private String myActionName;
	private Float currentIndent = null;
	private int newIndent = 0;
	
	/**
	 * creates a new IndentAction Object.
	 * @param kafenio the KafenioPanel object to apply the changes to.
	 * @param actionName the name of the IndentAction
	 */
	public IndentAction(KafenioPanel kafenio, String actionName) {
		super(actionName);
		parentKafenioPanel = kafenio;
		myActionName = actionName;
		log.debug("new IndentAction created.");
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
			MutableAttributeSet sas = new SimpleAttributeSet(parentTextPane.getParagraphAttributes());
			currentIndent = null;
			if (sas.getAttribute(CSS.Attribute.MARGIN_LEFT) != null) {
				currentIndent = new Float(sas.getAttribute(CSS.Attribute.MARGIN_LEFT).toString());
			}
			
			// indent or de-indent?
			if (myActionName.equalsIgnoreCase("deindent")) {
				// check if it is null
				if (currentIndent != null) {
					newIndent = currentIndent.intValue() - 30;
					currentIndent = new Float(newIndent);
					// enforce min size of 0
					if (newIndent < 0) {
						newIndent = 0;
						currentIndent = new Float(newIndent);
					}
					// if indent size = 0, remove attribute for clearer code
					if (newIndent == 0) {
						sas.removeAttribute(CSS.Attribute.MARGIN_LEFT);
						setParagraphAttributes(editor, sas, true);
					} else {
						sas.removeAttribute(CSS.Attribute.MARGIN_LEFT);
						sas.addAttribute( StyleConstants.LeftIndent, new Float(newIndent));
						setParagraphAttributes(editor, sas, true);
					}
				}
				editor.repaint();
			} else if (myActionName.equalsIgnoreCase("indent")) {
				// check if it is null
				if (currentIndent == null) {
					newIndent = 30;
				} else {
					newIndent = currentIndent.intValue() + 30;
					currentIndent = new Float(newIndent);
				}

				// enforce min size of 0
				if (newIndent < 0) {
					newIndent = 0;
					currentIndent = new Float(newIndent);
				}
				// if indent size = 0, remove attribute for clearer code
				sas.removeAttribute(CSS.Attribute.MARGIN_LEFT);
				sas.addAttribute( StyleConstants.LeftIndent, new Float(newIndent));
				setParagraphAttributes(editor, sas, true);
				editor.repaint();
			}
		}
		return;
	}
}
