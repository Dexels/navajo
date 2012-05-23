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
    private boolean questionErrorMessageVisible = false;


    public TipiQuestion() {
    }

    public Object createContainer() {
        myColumn = new VerticalLayout();
        return myColumn;
    }

    
  public void setComponentValue(final String name, final Object object) {
	  if("showErrorMessage".equals(name)) {
		  Boolean b = (Boolean)object;
		  questionErrorMessageVisible = b;
	  }
      super.setComponentValue(name, object);

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


    public void setValid(boolean b, String msg) {
    	if(b || !questionErrorMessageVisible) {
    		myColumn.setCaption("");
    	} else {
        	myColumn.setCaption(msg);
    	}
    	if (b) {
			myColumn.removeStyleName("invalidQuestion");
		} else {
			myColumn.addStyleName("invalidQuestion");
		}
    }
   

}
