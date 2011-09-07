package com.dexels.navajo.tipi.components.echoimpl;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Alignment;
import nextapp.echo2.app.CheckBox;
import nextapp.echo2.app.Style;
import nextapp.echo2.app.event.ActionEvent;
import nextapp.echo2.app.event.ActionListener;

import com.dexels.navajo.echoclient.components.Styles;
import com.dexels.navajo.tipi.TipiException;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;

import echopointng.image.URLImageReference;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TipiCheckbox extends TipiEchoComponentImpl {

	private static final long serialVersionUID = 7429128639740380936L;

private CheckBox myButton;

//  private boolean iAmEnabled = true;

  public Object createContainer() {
    myButton = new CheckBox();
    myButton.setTextPosition(Alignment.ALIGN_LEFT);
    Style ss = Styles.DEFAULT_STYLE_SHEET.getStyle(CheckBox.class, "Default");
    myButton.setStyle(ss);

    TipiHelper th = new EchoTipiHelper();
    th.initHelper(this);
    addHelper(th);
    myButton.addActionListener(new ActionListener(){

		private static final long serialVersionUID = -7214589080218807793L;

		public void actionPerformed(ActionEvent arg0) {
			try {
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("value", myButton.isSelected());
				performTipiEvent("onSelectionChanged", m, false);
				System.err.println("Checkbox clicked!");
				getAttributeProperty("selected").setAnyValue(myButton.isSelected());
			} catch (TipiException e) {
				e.printStackTrace();
			}
			
		}});
    return myButton;
  }

  public void setComponentValue(final String name, final Object object) {
    super.setComponentValue(name, object);
        if (name.equals("text")) {
          myButton.setText( (String) object);
        }
        if ("icon".equals(name)) {
            if (object instanceof URL) {
                URL u = (URL) object;
//                System.err.println("Setting URL icon for button: "+u);
                myButton.setIcon(new URLImageReference(u));
            } else {
                System.err.println("Can not set button icon: I guess it failed to parse (TipiButton)");
            }
        }        if (name.equals("enabled")) {
          // Just for the record.
//          iAmEnabled = ((Boolean)object).booleanValue();
        	myButton.setEnabled(((Boolean)object).booleanValue());
        }
        if (name.equals("selected")) {
          myButton.setSelected(((Boolean)object).booleanValue());
         }

        if ("tooltip".equals(name)) {
            myButton.setToolTipText("" + object);
        }

  }

 
  public Object getComponentValue(String name) {
    if (name.equals("text")) {
      return myButton.getText();
    }
    if (name.equals("selected")) {
      return new Boolean(myButton.isSelected());
    }

    return super.getComponentValue(name);
  }



}
