package com.dexels.navajo.tipi.vaadin.components;


import com.dexels.navajo.tipi.components.question.TipiBaseQuestionGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;

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

@SuppressWarnings("deprecation")
public class TipiQuestionGroup extends TipiBaseQuestionGroup {

	private static final long serialVersionUID = -2453065163545438139L;
	private VerticalLayout myColumn;

    public Object createContainer() {
        myColumn = new VerticalLayout();
        return myColumn;
    }

    public void setComponentValue(String name, Object object) {

        super.setComponentValue(name, object);
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.addComponent(comp);
    }
   
}
