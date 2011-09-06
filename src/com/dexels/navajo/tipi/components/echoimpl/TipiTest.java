package com.dexels.navajo.tipi.components.echoimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nextapp.echo2.app.Component;
import nextapp.echo2.app.Extent;
import nextapp.echo2.app.Grid;

import com.dexels.navajo.tipi.TipiComponent;

import echopointng.ContainerEx;
import echopointng.LabelEx;
import echopointng.TabbedPane;
import echopointng.tabbedpane.DefaultTabModel;

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
public class TipiTest extends TipiEchoDataComponentImpl {
    private ArrayList tipiList = new ArrayList();

    private ArrayList methodList = new ArrayList();

    private Map tipiMap = new HashMap();

    private TipiComponent selectedComponent = null;

    private Component lastSelectedTab = null;

    private DefaultTabModel defaultTabModel = null;

    private TabbedPane myTabbedPane;

    public Object createContainer() {
        myTabbedPane = new TabbedPane();
//        myTabbedPane.setStyleName("Default");
        
        defaultTabModel = new DefaultTabModel();
        myTabbedPane.setModel(defaultTabModel);
        ContainerEx containerEx = new ContainerEx();
//        containerEx.setStyleName("Default");
        
        //  containerEx.setBackground(new Color(0,50,100));
        LabelEx labelEx = new LabelEx("AAAAAAAAAAP");
        containerEx.add(labelEx);
        defaultTabModel.addTab("ContainerEx"  , containerEx );
        ContainerEx ce = new ContainerEx();
        Grid g = new Grid(1);
        ce.add(g);
        g.setColumnWidth(0, new Extent(500,Extent.PX));
        g.add(myTabbedPane);
        return ce;
    }

}
