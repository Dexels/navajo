package com.dexels.navajo.tipi.components.echoimpl;

import nextapp.echo2.app.Component;
import nextapp.echo2.extras.app.*;
import nextapp.echo2.extras.app.layout.*;

public class TipiTransitionPane extends TipiEchoDataComponentImpl {
    @Override
	protected void setComponentValue(String name, Object object) {
    	if("duration".equals(name)) {
    		myTransitionPane.setDuration((Integer)object);

    	}
    	if("effect".equals(name)) {
    		// hmmm
    	}

    	super.setComponentValue(name, object);
	}

	private TransitionPane myTransitionPane;

    public Object createContainer() {
    	myTransitionPane = new TransitionPane();
    	myTransitionPane.setType(TransitionPane.TYPE_FADE_TO_WHITE);
    	return myTransitionPane;
    }

    public void addToContainer(Object c, Object constraints) {
		System.err.println("Adding from transition pane!");
		System.err.println("Threadname: "+Thread.currentThread().getName());
		myTransitionPane.add((Component) c);
    }

	@Override
	public void removeFromContainer(Object c) {
		System.err.println("REMOBINF from transition pane!");
		System.err.println("Threadname: "+Thread.currentThread().getName());
		myTransitionPane.remove((Component) c);
	}

    
    
}
