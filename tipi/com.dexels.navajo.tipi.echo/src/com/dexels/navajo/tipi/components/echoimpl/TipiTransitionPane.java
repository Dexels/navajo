package com.dexels.navajo.tipi.components.echoimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nextapp.echo2.app.Component;
import nextapp.echo2.extras.app.TransitionPane;

public class TipiTransitionPane extends TipiEchoDataComponentImpl {

	private static final long serialVersionUID = 6114605169396560100L;
	
	private final static Logger logger = LoggerFactory
			.getLogger(TipiTransitionPane.class);
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
		logger.info("Adding from transition pane!");
		logger.info("Threadname: "+Thread.currentThread().getName());
		myTransitionPane.add((Component) c);
    }

	@Override
	public void removeFromContainer(Object c) {
		logger.info("REMOBINF from transition pane!");
		logger.info("Threadname: "+Thread.currentThread().getName());
		myTransitionPane.remove((Component) c);
	}

    
    
}
