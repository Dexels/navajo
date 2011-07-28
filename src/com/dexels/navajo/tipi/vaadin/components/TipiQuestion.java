package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestion;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;


@SuppressWarnings("deprecation")
public class TipiQuestion extends TipiBaseQuestion {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7918566395000495935L;

    private VerticalLayout myColumn;



    public TipiQuestion() {
    }

    public Object createContainer() {
        myColumn = new VerticalLayout();
        return myColumn;
    }

    public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.addComponent(comp);
        comp.setSizeFull();
    }

    protected void setQuestionBorder(String val) {
//        myGroupBox.setTitle(val);

    }

    public void setQuestionVisible(boolean b) {
        myColumn.setVisible(b);

    }

    public void setComponentValue(String name, Object object) {
        super.setComponentValue(name, object);
    }

    public void setValid(boolean b, String msg) {
    	System.err.println("Setting valid: "+b+" msg: "+msg);
    }
   

}
