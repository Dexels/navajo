/*
 * ServerImageAction.java
 * Copyright (C) 2004 Maxym Mykhalchuk
 * part of Kafenio project http://kafenio.org
 */
package de.xeinfach.kafenio.action;

import java.awt.event.ActionEvent;
import java.io.IOException;

import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTML;

import de.xeinfach.kafenio.KafenioMenuBar;
import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.component.dialogs.ImageDialog;
import de.xeinfach.kafenio.util.LeanLogger;
import de.xeinfach.kafenio.util.ListLoader;

/**
 * The Action for inserting an image from the server.
 * Pretty simple and straightforward, all exceptions handled.
 * @author Maxym Mykhalchuk
 */
public class ServerImageAction extends StyledEditorKit.StyledTextAction {
	
	private static LeanLogger log = new LeanLogger("ServerImageAction.class");
	private KafenioPanel kafenio;
	
	/** 
	 * Creates a new instance of ServerImageAction
	 * @param newKafenio the parent KafenioPanel. 
	 */
	public ServerImageAction(KafenioPanel newKafenio) {
		super(newKafenio.getTranslation("InsertServerImage") + KafenioMenuBar.DOTS);
		this.kafenio = newKafenio;
	}
	
	/** 
	 * The method, which inserts an image from a server
	 * @param e the action event to process. 
	 */
	public void actionPerformed(ActionEvent e) {
		Vector names = new Vector(), images = new Vector();
		ListLoader.loadImages(kafenio.getConfig(), names, images);
		int caretPos = kafenio.getCaretPosition();
		ImageDialog imageDialog = new ImageDialog(kafenio, names, images);
		String selectedImage = imageDialog.getSelectedImage();
		imageDialog.dispose();
		try {
			if(selectedImage != null  && !selectedImage.equals("")) {
				kafenio.getExtendedHtmlKit().insertHTML(
					kafenio.getExtendedHtmlDoc(), caretPos, selectedImage, 0, 0, HTML.Tag.IMG);
				kafenio.setCaretPosition(caretPos + 1);
			}
		}
		catch (BadLocationException ble) {
			log.error("BadLocationException in ServerImageAction: " + ble.fillInStackTrace());
		}
		catch (IOException ioe) {
			log.error("IOException in ServerImageAction: " + ioe.fillInStackTrace());
		}
		kafenio.getTextPane().requestFocus();
	}
	
}
