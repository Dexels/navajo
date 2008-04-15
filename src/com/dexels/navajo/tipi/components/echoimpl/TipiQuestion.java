package com.dexels.navajo.tipi.components.echoimpl;

import com.dexels.navajo.tipi.components.core.*;
import com.dexels.navajo.tipi.components.question.*;
import com.dexels.navajo.document.*;
import com.dexels.navajo.tipi.*;
import java.util.*;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.*;

import com.dexels.navajo.tipi.tipixml.*;
import com.dexels.navajo.tipi.actions.*;
import com.dexels.navajo.tipi.internal.*;
import com.dexels.navajo.parser.*;

import echopointng.*;
import echopointng.able.*;

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
public class TipiQuestion extends TipiBaseQuestion {
    private String messagePath = null;

    private String questionDefinitionName = null;

    // private TipiQuestionGroup questionGroupPath = null;
    private TipiBaseQuestionGroup questionGroup = null;

    private final ArrayList mySubQuestions = new ArrayList();

    private String enabledCondition = null;

    private String visibleCondition = null;

    private String validationCondition = null;

    private Message myMessage = null;

    private Column myColumn;

    private String myId;

//    private GroupBox myGroupBox;

    public TipiQuestion() {
    }

    public Object createContainer() {
//        myGroupBox = new GroupBox();
        myColumn = new Column();
//        myColumn.setStyleName("Default");
        
//        myGroupBox.add(myColumn);
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;

        myColumn.add(comp);
    }

    protected void setQuestionBorder(String val) {
//        myGroupBox.setTitle(val);

    }

    public void setQuestionVisible(boolean b) {
        myColumn.setVisible(b);

    }

    public void setComponentValue(String name, Object object) {
        if (name.equals("background")) {
            Color background = (Color) object;
            myColumn.setBackground(background);
//            myGroupBox.setBackground(background);
            return;
        }

        super.setComponentValue(name, object);
    }

    public void setValid(boolean b, String msg) {
    }
   

}
