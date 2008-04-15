package com.dexels.navajo.tipi.components.echoimpl;

import java.net.*;

import nextapp.echo2.app.*;
import nextapp.echo2.webcontainer.*;

import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.echoimpl.embed.*;

public class TipiEchoEmbedComponent extends TipiEmbedComponent {

	private TipiEchoStandaloneToplevel tet;


	public TipiEchoEmbedComponent() {
	}
	public Object createContainer() {
		stc = new TipiEchoStandaloneContainer((TipiEchoInstance)((EchoTipiContext)getContext()).getInstance(),(EchoTipiContext) getContext());
		tet = (TipiEchoStandaloneToplevel) stc.getContext().getDefaultTopLevel();
		Component c = (Component) tet.getContainer();
		c.setBackground(new Color(255,0,0));
		return c;
		//		return new ContentPane();
	}
	public void setComponentValue(String name, Object value) {

		if (name.equals("tipiCodeBase")) {
//			try {
				String tipiCodeBase = (String) value;
				stc.getContext().setTipiResourceLoader(getContext().getTipiResourceLoader());
				return;
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			}
		}
		if (name.equals("resourceCodeBase")) {
//			try {
				String  resourceCodeBase = (String) value;
				//stc.getContext().setGenericResourceLoader(resourceCodeBase);
				stc.getContext().setGenericResourceLoader(getContext().getGenericResourceLoader());
				return;
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			}

		}
		super.setComponentValue(name, value);
	}

	
	protected void switchToDefinition(final String nameVal) throws TipiException {
		System.err.println("Switching to def: "+nameVal);
//		stc.getContext().setTopLevelContainer(getContainer());
//		runAsyncInEventThread(new Runnable(){
//
//			public void run() {
//				try {
					stc.getContext().switchToDefinition(nameVal);
					Component topLevel = (Component) stc.getContext().getTopLevel();
					System.err.println("Toplevel class: "+topLevel.getClass()+" topLevel: "+topLevel);
//					((Component)tet.getContainer()).add(topLevel);
//				} catch (TipiException e) {
//					e.printStackTrace();
//				}
//			}});
	
	}
}
