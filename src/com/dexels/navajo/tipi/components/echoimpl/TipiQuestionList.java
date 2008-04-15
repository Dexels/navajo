package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.actions.*;
import java.util.*;
import com.dexels.navajo.tipi.internal.*;
import java.io.*;
import java.awt.*;
import java.awt.print.*;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class TipiQuestionList extends TipiBaseQuestionList {
    // private String messagePath = null;
    // private String questionDefinitionName = null;
    // private String questionGroupDefinitionName = null;
    //  
    private static final String MODE_TABS = "tabs";

    private static final String MODE_PANEL = "panel";

    private String groupMode = MODE_PANEL;

    public TipiQuestionList() {
    }

    protected Object getGroupConstraints(Message groupMessage) {
        return null;
    }

    protected void clearQuestions() {
        removeInstantiatedChildren();
    }

 
    public Object createContainer() {
        // TODO FIX AND TEST
        return null;
    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            // Color background = (Color) object;
            // myColumn.setBackground(background);
            // return;
        }

        super.setComponentValue(name, object);
    }
}
