package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.StyledEditorKit;

import com.dexels.navajo.document.Property;
import com.dexels.navajo.tipi.PropertyHandler;
import com.dexels.navajo.tipi.TipiEventListener;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.swingimpl.swing.TipiSwingHelper;
import com.dexels.navajo.tipi.internal.PropertyComponent;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiHtmlLabel extends TipiSwingDataComponentImpl implements
		PropertyComponent {
	private JEditorPane myLabel;
	private JScrollPane jsp;
	private String myPropertyName;
	private PropertyHandler myHandler;
	private Property myProperty;

	public Object createContainer() {
		myHandler = new PropertyHandler(this, null);
		// myHandler.addMapping("value", "text");
		myLabel = new JEditorPane();
		TipiHelper th = new TipiSwingHelper();
		myLabel.setAutoscrolls(true);
		th.initHelper(this);
		myLabel.setEditorKit(new StyledEditorKit());
		// myLabel.setFont(new Font("Sans", Font.ITALIC,20));
		myLabel.setContentType("text/html");
		// myLabel.setText("some very very very long text  ....");

		// Font font = UIManager.getFont("Label.font");
		// String bodyRule = "body { font-family: " + font.getFamily() + "; " +
		// "font-size: " + font.getSize() + "pt; }";
		// ((HTMLDocument)myLabel.getDocument()).getStyleSheet().addRule(bodyRule);

		myLabel.setEditable(false);
		myLabel.setEnabled(true);
		addHelper(th);
		jsp = new JScrollPane(myLabel);
		// jsp.getViewport().
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		return jsp;
	}

	public Object getActualComponent() {
		return myLabel;
	}

	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			setHtmlText("" + object);
			// ((TipiSwingLabel) getContainer()).revalidate();
			return;
		}
		if (name.equals("propertyName")) {
			myPropertyName = (String) object;
		}
		super.setComponentValue(name, object);
	}

	/**
	 * @param object
	 */
	private void setHtmlText(final String text) {
		runSyncInEventThread(new Runnable() {
			public void run() {
				myLabel.setText(text);
				myLabel.setCaretPosition(0);
			}
		});

	}

	public void addTipiEventListener(TipiEventListener listener) {
	}

	public Property getProperty() {
		return myProperty;
	}

	public String getPropertyName() {
		return myPropertyName;
	}

	public void setProperty(Property p) {
		myProperty = p;
		myHandler.setProperty(p);
	}

}
