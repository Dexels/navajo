package com.dexels.navajo.tipi.components.echoimpl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.*;
import nextapp.echo2.app.event.ChangeEvent;
import nextapp.echo2.app.event.ChangeListener;
import nextapp.echo2.extras.app.TabPane;
import nextapp.echo2.extras.app.layout.TabPaneLayoutData;

import com.dexels.navajo.tipi.TipiComponent;
import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.TipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.helpers.EchoTipiHelper;
import com.dexels.navajo.tipi.components.echoimpl.parsers.*;
import com.dexels.navajo.tipi.internal.TipiEvent;

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
public class TipiTabs extends TipiEchoDataComponentImpl {
    private ArrayList tipiList = new ArrayList();

    private ArrayList methodList = new ArrayList();

    private Map tipiMap = new HashMap();

    private TipiComponent selectedComponent = null;

    private Component lastSelectedTab = null;

//    private DefaultTabModel defaultTabModel = null;

    private TabPane myTabbedPane;

    public Object createContainer() {
        final TipiComponent me = this;
        myTabbedPane = new TabPane();
//        myTabbedPane.setStyleName("Default");
//        defaultTabModel = new DefaultTabModel();
//        defaultTabModel.setSelectedBackground(new Color(255, 255, 255));
//        defaultTabModel.setSelectedForeground(new Color(0, 0, 0));
//        defaultTabModel.setForeground(new Color(153, 153, 153));
//        defaultTabModel.setRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedRolloverForeground(new Color(68, 68, 68));
//        defaultTabModel.setSelectedFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setSelectedRolloverFont(new Font(Font.ARIAL, Font.BOLD, new Extent(10, Extent.PT)));
//        defaultTabModel.setFont(new Font(Font.ARIAL, Font.PLAIN, new Extent(10, Extent.PT)));
//        defaultTabModel.setBackground(new Color(255, 255, 255));
//        myTabbedPane.setModel(defaultTabModel);
//        TipiHelper th = new EchoTipiHelper();
//        th.initHelper(this);
//        addHelper(th);
//        myTabbedPane.setTabSpacing(0);
        myTabbedPane.addPropertyChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
            	System.err.println("Hoei: "+evt.getPropertyName());
            	
            }
            });
        
    
        return myTabbedPane;
    }

//    public void processStyles() {
//        super.processStyles();
//        Color c = ColorParser.parseColor(getStyle("foreground"));
//        if (c!=null) {
//            defaultTabModel.setForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("background"));
//        if (c!=null) {
//            defaultTabModel.setBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedforeground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedbackground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverbackground"));
//        if (c!=null) {
//            defaultTabModel.setRolloverBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("rolloverforeground"));
//        if (c!=null) {
//            defaultTabModel.setRolloverForeground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedrolloverbackground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedRolloverBackground(c);
//        }
//        c = ColorParser.parseColor(getStyle("selectedrolloverforeground"));
//        if (c!=null) {
//            defaultTabModel.setSelectedRolloverForeground(c);
//        }
//        String s = getStyle("tabspacing");
//        if (s!=null) {
//            int ii = Integer.parseInt(s);
//            myTabbedPane.setTabSpacing(ii);
//        }
//    }
    
    protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
    }
	public void removeFromContainer(Object c) {
		myTabbedPane.remove((Component)c);
		//		int tab = -1;
//		for (int i = 0; i < myTabbedPane.getComponentCount(); i++) {
//			Component current = defaultTabModel.getTabContentAt(i);
//			if (current==c) {
//				tab = i;
//			}
//		}

//		if (tab>=0) {
//			defaultTabModel.removeTabAt(tab);
//		} else {
//			System.err.println("oops: "+tab);
//		}
//		super.removeFromContainer(c);
	}
    public void addToContainer(Object c, Object constraints) {
    	
//        Label label = new Label("Tab Pane Child " + tabNumber);
//        label.setBackground(StyleUtil.randomBrightColor());
//        TabPaneLayoutData layoutData = new TabPaneLayoutData();
//        layoutData.setTitle("Label #" + tabNumber);
//        label.setLayoutData(layoutData);
    	Component cc = (Component)c;
    	
//    	
//        TabPaneLayoutData tabPaneLayoutData = new TabPaneLayoutData();
//        String tabTitle = ""+constraints;
//        System.err.println("Adding tab with title: "+tabTitle+" and class: "+c.getClass());
//		tabPaneLayoutData.setTitle(tabTitle);
//		cc.setLayoutData(tabPaneLayoutData);
//		if(true) {
//			return;
//		}
		myTabbedPane.add(cc);
//	       if (lastSelectedTab == null) {
//            lastSelectedTab = (Component) c;
//        }
    }

    public void setComponentValue(String name, Object object) {
//        super.setComponentValue(name, object);
//        if (name.equals("selected")) {
//            String sel = (String) object;
//            final TipiComponent tc = getTipiComponent(sel);
//            int ii = ((TabbedPane) getContainer()).indexOf((Component) (tc.getContainer()));
//            if (ii >= 0) {
//                ((TabbedPane) getContainer()).setSelectedIndex(ii);
//            }
//        }
//        if (name.equals("selectedindex")) {
//            final Integer sel = (Integer) object;
//            ((TabbedPane) getContainer()).setSelectedIndex(sel.intValue());
//        }
    }

    public Object getComponentValue(String name) {
        /** @todo Override this com.dexels.navajo.tipi.TipiComponent method */
        if (name.equals("selected")) {
//
//            int ic = ((TabbedPane) getContainer()).getSelectedIndex();
//            if (ic >= 0) {
//                Component c = ((TabbedPane) getContainer()).getComponent(ic);
//                TipiComponent tc = getChildByContainer(c);
//                return tc;
//            } else {
//                return null;
//            }
        }
        if (name.equals("lastselected")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
            return tc;
        }
        if (name.equals("selectedindex")) {
//            return new Integer(((TabbedPane) getContainer()).getSelectedIndex());
        }
        if (name.equals("lastselectedindex")) {
            TipiComponent tc = getChildByContainer(lastSelectedTab);
//            return new Integer(getIndexOfTab(lastSelectedTab));
        }

        return super.getComponentValue(name);
    }

//    private int getIndexOfTab(Component c) {
//        TabbedPane pane = (TabbedPane) getContainer();
//        return pane.indexOf(c);
//    }


}
