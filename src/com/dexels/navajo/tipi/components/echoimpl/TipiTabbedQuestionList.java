/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.*;
import java.net.*;

import javax.swing.*;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.helpers.*;
import com.dexels.navajo.tipi.components.question.*;

import echopointng.*;
import echopointng.image.*;
import echopointng.tabbedpane.*;

public class TipiTabbedQuestionList extends TipiBaseQuestionList {
//    private Component lastSelectedTab = null;

    private DefaultTabModel defaultTabModel = null;

    private TabbedPane myTabbedPane;

    private ImageReference validImage;

    private ImageReference inValidImage;

    protected Object getGroupConstraints(Message groupMessage) {
        // TODO Auto-generated method stub
        Property name = groupMessage.getProperty("Name");
        if (name != null) {
            if (name.getValue() == null) {
                return "Unknown tab";
            }
        } else {
            return name.getValue();

        }
        return name.getValue();
    }
    public void loadData(final Navajo n,final String method) throws TipiException {
        clearQuestions();
//        lastSelectedTab = null;
        super.loadData(n, method);
        if(defaultTabModel.size()>0) {
        	myTabbedPane.setSelectedIndex(0);
        }
    }
//    public Object createContainer() {
//        final TipiComponent me = this;
//        myTabbedPane = new TabbedPane();
//        defaultTabModel = new DefaultTabModel();
//
//        validImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/ok.gif"));
//        inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));
//        myTabbedPane.setModel(defaultTabModel);
//        TipiHelper th = new EchoTipiHelper();
//        th.initHelper(this);
//        addHelper(th);
//        return myTabbedPane;
//    }
    
    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabbedPane();
        myTabbedPane.setStyleName("Default");
        defaultTabModel = new DefaultTabModel();
//        defaultTabModel.setSelectedBackground(new Color(255, 255, 255));
//        defaultTabModel.setSelectedForeground(new Color(0, 0, 0));
//        defaultTabModel.setForeground(new Color(153, 153, 153));
//        defaultTabModel.setRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10, Extent.PT)));
//        defaultTabModel.setRolloverFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setBackground(new Color(255, 255, 255));
//        defaultTabModel.setRolloverBackground(new Color(255, 255, 255));
//        defaultTabModel.setSelectedRolloverBackground(new Color(255, 255, 255));
        myTabbedPane.setModel(defaultTabModel);
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        myTabbedPane.setTabSpacing(0);
//        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {
//
//            public void propertyChange(PropertyChangeEvent evt) {
//            	System.err.println("Boioioiong");
//            	System.err.println("EVT: old: "+evt.getOldValue()+" new: "+evt.getNewValue());
//            }
//            });
        validImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/ok.gif"));
        inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));
        return myTabbedPane;
    }
    

    public void addToContainer(Object c, Object constraints) {
    	String tabName = ""+constraints;
//    	PushButton pb = new PushButton(tabName);
        defaultTabModel.addTab(tabName, (Component) c);
//        if (lastSelectedTab == null) {
//            lastSelectedTab = (Component) c;
//        }
        // System.err.println("WIDTH: " + myTabbedPane.getWidth());
        // System.err.println("HEIGHT: " + myTabbedPane.getWidth());
        // System.err.println("Tab count: "+getChildCount());
        // ButtonEx notSelected
        // =(ButtonEx)defaultTabModel.getTabAt(getChildCount()-1,false);
        
//        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(getChildCount() - 1, true);
//        selected.setBorder(new Border(1, new Color(50, 50, 50), Border.STYLE_GROOVE));

        // selected.setText("Selected");
        // notSelected.setText("Not selected");
        // System.err.println("Sel: "+selected.toString());
        // System.err.println("NSel: "+notSelected.toString());
        // selected.setForeground(new Color(0,0,0));
        // selected.setBackground(myTabbedPane.getBackground());
        // notSelected.setForeground(new Color(0,0,0));
        // notSelected.setBackground(new Color(150,150,150));
    }
    
    protected void clearQuestions() {
        // TODO Auto-generated method stub

//        if (myTabbedPane != null) {
//            while (myTabbedPane.getComponentCount() > 0) {
//                myTabbedPane.remove(0);
//            }
//        }

        while (defaultTabModel.size() > 0) {
            defaultTabModel.removeTabAt(0);
            
        }
    }

   
    
    public void removeFromContainer(Object c) {
		// TODO Auto-generated method stub
		super.removeFromContainer(c);
	}
	public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
        super.setGroupValid(valid, group);
        int i = myGroups.indexOf(group);
        if (!myGroups.contains(group)) {
            return;
        }
        if (i < 0) {
            System.err.println("Sh!34#@$!");
            return;
        }
        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(myTabbedPane,i, true);
        selected.setIcon(valid ? validImage : inValidImage);
    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            Color background = (Color) object;
            myTabbedPane.setBackground(background);
            return;
        }

        super.setComponentValue(name, object);
    }
}
