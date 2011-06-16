package com.dexels.navajo.tipi.swingeditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;

import de.xeinfach.kafenio.KafenioPanel;
import de.xeinfach.kafenio.KafenioPanelConfiguration;
import de.xeinfach.kafenio.component.ExtendedHTMLDocument;
import de.xeinfach.kafenio.util.LeanLogger;


/**
 * 
 * @author Frank Lyaruu
 *
 */
public class TipiSwingEditor extends TipiSwingDataComponentImpl  {
	//http://search-result.com/directhit/xml/NL_algemeen.xml
	private KafenioPanel myEditor = null;

	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		super.performComponentMethod(name, compMeth, event);
	}
	


	public Object createContainer() {
		runSyncInEventThread(new Runnable(){

			public void run() {
			//	LeanLogger.setCurrentLogLevel(4);
				KafenioPanelConfiguration gpc = new KafenioPanelConfiguration();
				myEditor = new KafenioPanel(gpc);
				myEditor.addPropertyChangeListener(new PropertyChangeListener(){

					public void propertyChange(PropertyChangeEvent evt) {
							System.err.println("Log: "+evt.getPropertyName()+" val: "+evt.getNewValue());
					
					}});
				myEditor.getSourcePane().getDocument().addDocumentListener(new DocumentListener(){

					public void changedUpdate(DocumentEvent e) {
						fireChange("change", e);
					}

					public void insertUpdate(DocumentEvent e) {
						fireChange("insert", e);
					}

					public void removeUpdate(DocumentEvent e) {
						fireChange("remove", e);
					}
					
					private void fireChange(String changeType, DocumentEvent e) {
						System.err.println("Change: "+changeType+" evt: "+e);
					}
				});

			}});
		return myEditor;
	}

	
	protected Object getComponentValue(String name) {
		if(name.equals("text")) {
			return myEditor.getDocumentText();
		}
		return super.getComponentValue(name);
		
	}


	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if(name.equals("text")) {
			runSyncInEventThread(new Runnable(){

				public void run() {
					myEditor.setDocumentText((String)object);
					
				}});
		}

	}



	public static void main(String[] args) throws BadLocationException   {
		LeanLogger.setCurrentLogLevel(4);
		KafenioPanelConfiguration gpc = new KafenioPanelConfiguration();
		final KafenioPanel kp = new KafenioPanel(gpc);
		JFrame jf = new JFrame("aap");
		jf.getContentPane().add(kp,BorderLayout.CENTER);
		jf.setBounds(100,100,500,300);
		jf.setVisible(true);
		JButton jb = new JButton("Teext");
		jb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				ExtendedHTMLDocument extendedHtmlDoc = kp.getExtendedHtmlDoc();
				try {
					extendedHtmlDoc.getText(0, extendedHtmlDoc.getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				
			}});
		kp.addPropertyChangeListener(new PropertyChangeListener(){

			public void propertyChange(PropertyChangeEvent evt) {
					System.err.println("Log: "+evt.getPropertyName()+" val: "+evt.getNewValue());
			
			}});
		jf.getContentPane().add(jb,BorderLayout.SOUTH);
		kp.setDocumentText("Mujaheddin!");
	}




}
