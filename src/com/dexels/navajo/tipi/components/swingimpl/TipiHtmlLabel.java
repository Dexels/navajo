package com.dexels.navajo.tipi.components.swingimpl;

import javax.swing.*;
import javax.swing.text.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

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
public class TipiHtmlLabel extends TipiSwingDataComponentImpl {
	private JEditorPane myLabel;
	private JScrollPane jsp;

	public Object createContainer() {
	    myLabel = new JEditorPane();
	    TipiHelper th = new TipiSwingHelper();
	    myLabel.setAutoscrolls(true);
	    th.initHelper(this);
	    myLabel.setEditorKit(new StyledEditorKit());
//	    myLabel.setFont(new Font("Sans", Font.ITALIC,20));
	    myLabel.setContentType("text/html");
//	    myLabel.setText("some very very very long text  ....");
	    myLabel.setEditable(false);
	    myLabel.setEnabled(false);
	    addHelper(th);
	     jsp = new JScrollPane(myLabel);
//	     jsp.getViewport().
	     jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return jsp;
	}

	
	
	public Object getActualComponent() {
		return myLabel;
	}



	public void setComponentValue(final String name, final Object object) {
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {
				public void run() {
					myLabel.setText("" + object);
					myLabel.setCaretPosition(0);
				}
			});
//			((TipiSwingLabel) getContainer()).revalidate();
			return;
		}
		super.setComponentValue(name, object);
	}

//	public Object getComponentValue(String name) {
//		if (name.equals("text")) {
//			return ((TipiSwingLabel) getContainer()).getText();
//		}
//		if (name.equals("icon")) {
//			return ((TipiSwingLabel) getContainer()).getIcon();
//		}
//		return super.getComponentValue(name);
//	}
}
