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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final long serialVersionUID = -8714674791523166811L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiSwingEditor.class);
	
	private KafenioPanel myEditor = null;

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		super.performComponentMethod(name, compMeth, event);
	}
	


	@Override
	public Object createContainer() {
		runSyncInEventThread(new Runnable(){

			@Override
			public void run() {
			//	LeanLogger.setCurrentLogLevel(4);
				KafenioPanelConfiguration gpc = new KafenioPanelConfiguration();
				myEditor = new KafenioPanel(gpc);
				myEditor.addPropertyChangeListener(new PropertyChangeListener(){

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
							logger.info("Log: "+evt.getPropertyName()+" val: "+evt.getNewValue());
					
					}});
				myEditor.getSourcePane().getDocument().addDocumentListener(new DocumentListener(){

					@Override
					public void changedUpdate(DocumentEvent e) {
						fireChange("change", e);
					}

					@Override
					public void insertUpdate(DocumentEvent e) {
						fireChange("insert", e);
					}

					@Override
					public void removeUpdate(DocumentEvent e) {
						fireChange("remove", e);
					}
					
					private void fireChange(String changeType, DocumentEvent e) {
						logger.info("Change: "+changeType+" evt: "+e);
					}
				});

			}});
		return myEditor;
	}

	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("text")) {
			return myEditor.getDocumentText();
		}
		return super.getComponentValue(name);
		
	}


	@Override
	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if(name.equals("text")) {
			runSyncInEventThread(new Runnable(){

				@Override
				public void run() {
					myEditor.setDocumentText((String)object);
					
				}});
		}

	}



	public static void main(String[] args)   {
		LeanLogger.setCurrentLogLevel(4);
		KafenioPanelConfiguration gpc = new KafenioPanelConfiguration();
		final KafenioPanel kp = new KafenioPanel(gpc);
		JFrame jf = new JFrame("aap");
		jf.getContentPane().add(kp,BorderLayout.CENTER);
		jf.setBounds(100,100,500,300);
		jf.setVisible(true);
		JButton jb = new JButton("Teext");
		jb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ExtendedHTMLDocument extendedHtmlDoc = kp.getExtendedHtmlDoc();
				try {
					extendedHtmlDoc.getText(0, extendedHtmlDoc.getLength());
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
				
			}});
		kp.addPropertyChangeListener(new PropertyChangeListener(){

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
					logger.info("Log: "+evt.getPropertyName()+" val: "+evt.getNewValue());
			
			}});
		jf.getContentPane().add(jb,BorderLayout.SOUTH);
		kp.setDocumentText("Mujaheddin!");
	}




}
