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
	/**
	 * 
	 */
	private static final long serialVersionUID = 5497698668383090941L;
	private JEditorPane myLabel;
	private JScrollPane jsp;
	private String myPropertyName;
	private PropertyHandler myHandler;
	private Property myProperty;

	@Override
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

	@Override
	public Object getActualComponent() {
		return myLabel;
	}

	@Override
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
			@Override
			public void run() {
				myLabel.setText(text);
				myLabel.setCaretPosition(0);
			}
		});

	}

	@Override
	public void addTipiEventListener(TipiEventListener listener) {
	}

	@Override
	public Property getProperty() {
		return myProperty;
	}

	@Override
	public String getPropertyName() {
		return myPropertyName;
	}

	@Override
	public void setProperty(Property p) {
		myProperty = p;
		myHandler.setProperty(p);
	}

	@Override
	public Boolean isDirty() {
		// always not dirty
		return Boolean.FALSE;
	}

	@Override
	public void setDirty(Boolean b) {
		// ignore dirty
		
	}
}
