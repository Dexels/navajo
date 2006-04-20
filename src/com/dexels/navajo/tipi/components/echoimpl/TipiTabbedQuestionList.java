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

import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.helpers.*;
import com.dexels.navajo.tipi.components.question.*;

import echopointng.*;
import echopointng.image.*;
import echopointng.tabbedpane.*;

public class TipiTabbedQuestionList extends TipiBaseQuestionList {
    private Component lastSelectedTab = null;

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

    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabbedPane();
        defaultTabModel = new DefaultTabModel();

        validImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/ok.gif"));
        inValidImage = new URLImageReference(getClass().getClassLoader().getResource("com/dexels/navajo/tipi/components/echoimpl/cancel.gif"));
        // myTabbedPane.setTabSpacing(0);
        // myTabbedPane.setForeground(new Color(0,0,0));
        myTabbedPane.setModel(defaultTabModel);
        TipiHelper th = new EchoTipiHelper();
        th.initHelper(this);
        addHelper(th);
        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
            }
        });
        return myTabbedPane;
    }

    public void addToContainer(Object c, Object constraints) {
        defaultTabModel.addTab("" + constraints, (Component) c);
        if (lastSelectedTab == null) {
            lastSelectedTab = (Component) c;
        }
        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(getChildCount() - 1, true);
        if (selected != null) {
            selected.setBorder(new Border(1, new Color(50, 50, 50), Border.STYLE_GROOVE));
        }

        defaultTabModel.setSelectedBackground(new Color(204, 204, 204));
        defaultTabModel.setSelectedForeground(new Color(0, 0, 0));
        defaultTabModel.setBackground(new Color(204, 204, 204));
        defaultTabModel.setForeground(new Color(153, 153, 153));
        defaultTabModel.setRolloverBackground(new Color(204, 204, 204));
        defaultTabModel.setRolloverForeground(new Color(68, 68, 68));
        defaultTabModel.setSelectedRolloverBackground(new Color(204, 204, 204));
        defaultTabModel.setSelectedRolloverForeground(new Color(68, 68, 68));
    }

    protected void clearQuestions() {
        // TODO Auto-generated method stub

        if (myTabbedPane != null) {
            while (myTabbedPane.getComponentCount() > 0) {
                myTabbedPane.remove(0);
            }
        }

        while (defaultTabModel.size() > 0) {
            defaultTabModel.removeTabAt(0);
        }
    }

    public void runSyncInEventThread(Runnable r) {
        r.run();
    }

    public void setGroupValid(boolean valid, TipiBaseQuestionGroup group) {
        super.setGroupValid(valid, group);
        int i = myGroups.indexOf(group);
        if (i < 0) {
            System.err.println("Sh!34#@$!");
        }
        ButtonEx selected = (ButtonEx) defaultTabModel.getTabAt(i, true);
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
