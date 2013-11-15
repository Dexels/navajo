package de.xeinfach.kafenio.interfaces;

import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

/** 
 * Description: Main application class that creates a Java wysiwyg editor component
 * 
 * @author Karsten Pawlik, Howard Kistler
 */
public interface KafenioPanelInterface  extends ActionListener, KeyListener, DocumentListener {

	public JMenuBar getJMenuBar();
	public JToolBar getJToolBar1();
	public JToolBar getJToolBar2();
	public void setDocumentConfirmed(boolean documentConfirmed);
	public boolean getDocumentConfirmed();
	public Hashtable getTActions();
	public void actionPerformed(ActionEvent ae);
	public void detachFrame();
	public void keyTyped(KeyEvent ke);
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	public void handleDocumentChange(DocumentEvent de);
	public JScrollPane getHTMLScrollPane();
	public JScrollPane getSrcScrollPane();
	public void registerDocumentStyles();
	public void manageListElement(Element element);
	public String insertFile();
	public void serializeOut(HTMLDocument doc) throws IOException;
	public void serializeIn() throws IOException, ClassNotFoundException;
	public JTextPane getTextPane();
	public JTextPane getSourcePane();
	public Frame getFrame();
	public String getAppName();
	public HTMLEditorKit getHTMLEditorKit();
	public Container getKafenioParent();
	public void setKafenioParent(Window newApplet);
	public String getDocumentText();
	public boolean postContentBody();
	public String getDocumentBody();
	public void setDocumentText(String sText);
	public void purgeUndos();
	public void refreshOnUpdate();
	public void dispose();
	public ImageIcon getKafenioIcon(String iconName);
	public ImageIcon getMenuIcon(String iconName);
	public void toggleSourceWindow();
	public int getCaretPosition();
	public void setCaretPosition(int newPositon);
	public java.awt.datatransfer.Clipboard getSysClipboard();
	public String getTranslation(String stringToTranslate);
	public void quitApp();

}
