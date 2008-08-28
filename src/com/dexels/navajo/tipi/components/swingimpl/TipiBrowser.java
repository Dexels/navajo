package com.dexels.navajo.tipi.components.swingimpl;

import java.io.*;

import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiBrowser
    extends TipiSwingComponentImpl {
  private TipiEditorPane myItem;

  public Object createContainer() {
    myItem = new TipiEditorPane();
  
    return myItem;
  }

	@Override
	protected void setComponentValue(String name, Object object) {
		if(name.equals("binary")) {
			System.err.println("Setting to binary: "+object.toString());
			try {
				myItem.setBinary((Binary) object);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		super.setComponentValue(name, object);
		
	}
  
  
  
}
