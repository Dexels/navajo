/*
 * Created on Mar 21, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.dexels.navajo.tipi;


import java.io.*;
import java.net.*;
import java.util.List;

//import javax.swing.*;

import com.dexels.navajo.document.*;
import com.dexels.navajo.document.types.*;
import com.dexels.navajo.tipi.*;
import com.dexels.navajo.tipi.components.core.*;

import com.dexels.navajo.tipi.internal.*;

public abstract class TipiEmbedComponent extends TipiDataComponentImpl {

	private static final String TIPLET_PROPERTY_PATH = null;
	private static final String NAVAJO_MESSAGE_PATH = null;
	private static final String NAVAJO_PROPERTY_NAME = null;
	private static final String NAVAJONAME_PROPERTY_NAME = null;
	protected TipiStandaloneToplevelContainer stc = null;

	
	private String tipiCodeBase;
	private String resourceCodeBase;
	
	
	// public void loadDefinition(String tipiPath, String definitionName, String
	// resourceBaseDirectory) throws IOException, TipiException;
	// public void loadClassPathLib(String location);
	// public TipiContext getContext();
	// public void shutDownTipi();

	// String tipiCodeBase = (String) properties.get("tipiCodeBase");
	// String resourceCodeBase = (String) properties.get("resourceCodeBase");
	// setTipiResourceLoader(tipiCodeBase);
	// setGenericResourceLoader(resourceCodeBase);

	public TipiEmbedComponent() {
		// jf.getContentPane().add((Container)stc.getContext().getTopLevel(),BorderLayout.CENTER);
	}

	public void loadData(Navajo n, String method) throws TipiException {
		if (isTipletService(n)) {
			try {
				Binary b = (Binary) n.getProperty(TIPLET_PROPERTY_PATH).getTypedValue();

				ZipResourceLoader zr = new ZipResourceLoader(b);
				stc.getContext().setTipiResourceLoader(new TmlResourceLoader(zr, "tipi/"));
				stc.getContext().setGenericResourceLoader(new TmlResourceLoader(zr, "resource/"));
				parseLocation("init.xml");
				switchToDefinition("init");
				Message m = n.getMessage(NAVAJO_MESSAGE_PATH);
				List<Message> al = m.getAllMessages();
				for (int i = 0; i < al.size(); i++) {
					Message element = al.get(i);
					parseNavajoMessage(element);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void parseNavajoMessage(Message element) {
		Property navajoBinary = element.getProperty(NAVAJO_PROPERTY_NAME);
		Property navajoName = element.getProperty(NAVAJONAME_PROPERTY_NAME);
		Binary bb = (Binary) navajoBinary.getTypedValue();
		Navajo embedded = NavajoFactory.getInstance().createNavajo(bb.getDataAsStream());
		try {
			stc.getContext().loadNavajo(embedded, navajoName.getValue());
		} catch (TipiBreakException e) {
			e.printStackTrace();
		}
	}

	private boolean isTipletService(Navajo n) {
		return false;
	}

				

	@Override
	public void setComponentValue(String name, Object value) {

		if (name.equals("tipiCodeBase")) {
			try {
				tipiCodeBase = (String) value;
				stc.getContext().setTipiResourceLoader(tipiCodeBase);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		if (name.equals("resourceCodeBase")) {
			try {
				resourceCodeBase = (String) value;
				stc.getContext().setGenericResourceLoader(resourceCodeBase);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}
		if (name.equals("binaryCodeBase")) {
			Binary b = (Binary) value;
			ZipResourceLoader tr;
			try {
				tr = new ZipResourceLoader(b);
				stc.getContext().setTipiResourceLoader(new TmlResourceLoader(tr, "tipi/"));
				stc.getContext().setGenericResourceLoader(new TmlResourceLoader(tr, "resource/"));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		super.setComponentValue(name, value);
	}

	
	
	protected Object getComponentValue(String name) {
		if(name.equals("context")) {
			return myContext;
		}
		return super.getComponentValue(name);
	}

	protected void performComponentMethod(final String name, final TipiComponentMethod compMeth, TipiEvent event) {
		if ("loadDefinition".equals(name)) {
			try {
				if(resourceCodeBase==null) {
					stc.getContext().setGenericResourceLoader(myContext.getGenericResourceLoader());
				}
				if(tipiCodeBase==null) {
					stc.getContext().setTipiResourceLoader(myContext.getTipiResourceLoader());
				}
				Operand oo = compMeth.getEvaluatedParameter("location", event);
				String loc = (String) oo.value;
				parseLocation(loc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("switch".equals(name)) {
			try {
				if(resourceCodeBase==null) {
					stc.getContext().setGenericResourceLoader(myContext.getGenericResourceLoader());
				}
				if(tipiCodeBase==null) {
					stc.getContext().setTipiResourceLoader(myContext.getTipiResourceLoader());
				}
				Operand oo = compMeth.getEvaluatedParameter("definition", event);
				String nameVal = (String) oo.value;

				switchToDefinition(nameVal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("addStartupProperty".equals(name)) {
			try {
				Operand nameOperand = compMeth.getEvaluatedParameter("propertyName", event);
				String nameVal = (String) nameOperand.value;
				Operand valueOperand = compMeth.getEvaluatedParameter("value", event);
				String vakueVal = (String) valueOperand.value;
				System.err.println("Adding: " + nameVal + " value: " + vakueVal);
				stc.getContext().setSystemPropertyLocal(nameVal, vakueVal);
				// ((Container) getContainer()).add((Component)
				// stc.getContext().getTopLevel(), BorderLayout.CENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("loadNavajo".equals(name)) {
			try {
				Operand navajoOperand = compMeth.getEvaluatedParameter("navajo", event);
				Navajo navajo = (Navajo) navajoOperand.value;
				Operand methodOperand = compMeth.getEvaluatedParameter("method", event);
				String method = (String) methodOperand.value;
				loadNavajo(navajo, method);
				// ((Container) getContainer()).add((Component)
				// stc.getContext().getTopLevel(), BorderLayout.CENTER);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if ("loadAllNavajo".equals(name)) {
			try {
				Operand messageOperand = compMeth.getEvaluatedParameter("message", event);
				Message arrayMessage = (Message) messageOperand.value;
				List<Message> elements = arrayMessage.getAllMessages();
				for (int i = 0; i < elements.size(); i++) {
					Message current = elements.get(i);
					Property ob = current.getProperty("Navajo");
					Binary b = (Binary) ob.getTypedValue();
					Navajo n = NavajoFactory.getInstance().createNavajo(b.getDataAsStream());
					loadNavajo(n, n.getHeader().getRPCName());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void switchToDefinition(final String nameVal) throws TipiException {
		runSyncInEventThread(new Runnable(){
			public void run() {
				stc.getContext().setTopLevelContainer(getContainer());
				try {
					stc.getContext().switchToDefinition(nameVal);
				} catch (TipiException e) {
					e.printStackTrace();
				}
			}});
	}

	private void parseLocation(String loc) throws IOException, TipiException {
		System.err.println("Parsing: "+loc);
		InputStream tipiResourceStream = stc.getContext().getTipiResourceStream(loc);
		stc.getContext().parseStream(tipiResourceStream);
	}

	private void loadNavajo(Navajo n, String method) throws TipiException {
		try {
			stc.getContext().loadNavajo(n.copy(), method);
		} catch (TipiBreakException e) {
			e.printStackTrace();
		}
	}

	public void disposeComponent() {
		super.disposeComponent();
		stc.shutDownTipi();
	}

}
