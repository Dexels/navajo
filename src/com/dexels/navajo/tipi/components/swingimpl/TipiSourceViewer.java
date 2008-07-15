package com.dexels.navajo.tipi.components.swingimpl;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.swingimpl.swing.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.tipi.tipixml.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class TipiSourceViewer
    extends TipiSwingComponentImpl {
  private TipiSwingTextArea myTextArea;
private TipiDefinitionListener myTipiListener;

private String currentDefinition = null;

public TipiSourceViewer() {
  }

  public Object createContainer() {
    myTextArea = new TipiSwingTextArea();
//    myTextArea.setEditable(false);
    TipiHelper th = new TipiSwingHelper();
    th.initHelper(this);
    JScrollPane jsp = new JScrollPane(myTextArea);
    addHelper(th);
    myTipiListener = new TipiDefinitionListener(){

		public void definitionLoaded(String definitionName, XMLElement definition) {
			if(definitionName.equals(currentDefinition)) {
				setDefinition(definitionName);
			}
		}};
		myContext.addDefinitionListener(myTipiListener);
	return jsp;
  }

  public void setComponentValue(final String name, final Object object) {
    if (name.equals("definition")) {
      setDefinition((String)object);
      return;
    }
    super.setComponentValue(name, object);
  }

private void setDefinition(final String object) {
	runSyncInEventThread(new Runnable() {
        public void run() {
        	String definition = object;
        	XMLElement xe = myContext.getTipiDefinition(definition);
        	currentDefinition = definition;
        	// This can happen, in this case, it will be picked up when the defininition gets loaded
        	if(xe!=null) {
            	myTextArea.setText( xe.toString());
        	} else {
        		myTextArea.setText("Component: "+definition+" is not loaded.");
        	}
        }
      });
}

  @Override
public void disposeComponent() {
	  myContext.removeDefinitionListener(myTipiListener);
	  super.disposeComponent();
}

public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myTextArea.getText();
    }
    return super.getComponentValue(name);
  }
protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) throws TipiBreakException {
	super.performComponentMethod(name, compMeth, event);

	if (name.equals("save")) {
		XMLElement xe = new CaseSensitiveXMLElement();
		xe.parseString(myTextArea.getText());
		myContext.addTipiDefinition(xe);
	}

}
}
