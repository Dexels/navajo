package com.dexels.navajo.scheduler.triggers;

import com.dexels.navajo.document.Message;
import com.dexels.navajo.document.Navajo;
import com.dexels.navajo.document.NavajoException;
import com.dexels.navajo.document.NavajoFactory;
import com.dexels.navajo.document.Property;
import com.dexels.navajo.document.types.Binary;

public class FileTrigger extends Trigger {

	/**
	 * FileTrigger is defined as
	 * 
	 * file:[path]:[filename filter]
	 * 
	 */
	private static final long serialVersionUID = -2321045446684388602L;
	private String description;
	private String path;
	private String fileNameFilter;
	private Binary fileContent;
	
	@Override
	public void activateTrigger() {
		
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public boolean isSingleEvent() {
		return false;
	}

	@Override
	public void removeTrigger() {
		
	}

	@Override
	public void setSingleEvent(boolean b) {
		
	}

	public Navajo perform() {
		
		if ( fileContent != null ) {
			Navajo request = NavajoFactory.getInstance().createNavajo();
			Message filetrigger = NavajoFactory.getInstance().createMessage(request, "FileTrigger");
			try {
				request.addMessage(filetrigger);
				Property content = NavajoFactory.getInstance().createProperty(request, "Content", Property.BINARY_PROPERTY, "", 0
						, "", Property.DIR_OUT);
				content.setAnyValue(fileContent);
				filetrigger.addProperty(content);
			} catch (NavajoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getTask().setNavajo(request);
		}
		getTask().run();
		return null;
	}

}
