package com.dexels.navajo.tipi.vaadin.components;

import com.dexels.navajo.tipi.components.question.TipiBaseQuestion;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;


public class TipiQuestion extends TipiBaseQuestion {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7918566395000495935L;

    private VerticalLayout myColumn;
    private boolean questionErrorMessageVisible = false;


    public TipiQuestion() {
    }

    @Override
	public Object createContainer() {
        myColumn = new VerticalLayout();
        return myColumn;
    }

    
  @Override
public void setComponentValue(final String name, final Object object) {
	  if("showErrorMessage".equals(name)) {
		  Boolean b = (Boolean)object;
		  questionErrorMessageVisible = b;
	  }
      super.setComponentValue(name, object);

  }
    @Override
	public void addToContainer(Object c, Object constraints) {
        Component comp = (Component) c;
        myColumn.addComponent(comp);
        comp.setSizeFull();
    }

    @Override
	protected void setQuestionBorder(String val) {
//        myGroupBox.setTitle(val);

    }

    @Override
	public void setQuestionVisible(boolean b) {
        myColumn.setVisible(b);

    }


    @Override
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
