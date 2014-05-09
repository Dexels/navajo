package com.dexels.navajo.tipi.swingeditor;

import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dexels.navajo.tipi.TipiComponentMethod;
import com.dexels.navajo.tipi.components.swingimpl.TipiSwingDataComponentImpl;
import com.dexels.navajo.tipi.internal.TipiEvent;




/**
 * 
 * @author Frank Lyaruu
 *
 */
public class TipiSwingEditor extends TipiSwingDataComponentImpl  {

	private static final long serialVersionUID = -8714674791523166811L;
	
	private final static Logger logger = LoggerFactory.getLogger(TipiSwingEditor.class);
	
	private HTMLEditorPane editor = null;

	@Override
	protected void performComponentMethod(String name, TipiComponentMethod compMeth, TipiEvent event) {
		super.performComponentMethod(name, compMeth, event);
	}
	

	public Object createContainer() {
		
		runSyncInEventThread(new Runnable(){

			@Override
			public void run() {
				editor = new HTMLEditorPane(myContext.getApplicationInstance().getLocaleCode());
			}});
		return editor;
	}
	
	
	@Override
	protected Object getComponentValue(String name) {
		if(name.equals("text")) {
			return editor.getTidyText();
		}
		if(name.equals("tabsVisible")) {
			return editor.getTabsVisible();
		}
		return super.getComponentValue(name);
		
	}


	@Override
	protected void setComponentValue(String name, final Object object) {
		super.setComponentValue(name, object);
		if (name.equals("text")) {
			runSyncInEventThread(new Runnable() {

				@Override
				public void run() {
					editor.setText((String) object);
				}
			});
		}
		if (name.equals("tabsVisible")) {
			runSyncInEventThread(new Runnable() {

				@Override
				public void run() {
					editor.setTabsVisible((boolean) object);
				}
			});
		}
	}
}
